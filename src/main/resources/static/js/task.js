;jQuery( function() {
    $("#save").on('click', function () {
        var taskID = $("#task_id").val();
        $("#taskEditForm").ajaxSubmit({
            type:"post",
            dataType: "json",
            success: function (data) {
                console.log(data);
               if (data && data.code == 0) {
                   if (taskID) {
                       showMsg("任务更新成功,页面即将刷新", 'info');
                   } else {
                       showMsg("添加任务成功,页面即将跳转", 'info');
                   }
                   setTimeout(function () {
                       location.href="/task/edit?task_id=" + data.data;
                   }, 3000)
               } else {
                   showMsg(data.msg, "danger");
               }
            }
        });
    });
    $('#tb_task').bootstrapTable( {
        classes: 'table table-bordered table-hover table-striped',
        url: '/task/list.data',
        method: 'get',
        dataType : 'json',        // 因为本示例中是跨域的调用,所以涉及到ajax都采用jsonp,
        uniqueId: 'id',
        // toolbar: '#toolbar',       // 工具按钮容器
        idField: 'id',             // 每行的唯一标识字段
        // clickToSelect: true,     // 是否启用点击选中行
        showColumns: true,         // 是否显示所有的列
        showRefresh: true,         // 是否显示刷新按钮

        // showToggle: true,        // 是否显示详细视图和列表视图的切换按钮(clickToSelect同时设置为true时点击会报错)

        pagination: true,                    // 是否显示分页
        sortOrder: "asc",                    // 排序方式
        queryParams: function(params) {
            var temp = {
                size: params.limit,         // 每页数据量
                offset: params.offset,       // sql语句起始索引
                page: (params.offset / params.limit) + 1,
                sort: params.sort,           // 排序的列名
                sortOrder: params.order      // 排序方式'asc' 'desc'
            };
            return temp;
        },                                   // 传递参数
        sidePagination: "server",            // 分页方式：client客户端分页，server服务端分页
        pageNumber: 1,                       // 初始化加载第一页，默认第一页
        pageSize: 10,                        // 每页的记录行数
        pageList: [10, 25, 50, 100],         // 可供选择的每页的行数
        //search: true,                      // 是否显示表格搜索，此搜索是客户端搜索

        //showExport: true,        // 是否显示导出按钮, 导出功能需要导出插件支持(tableexport.min.js)
        //exportDataType: "basic", // 导出数据类型, 'basic':当前页, 'all':所有数据, 'selected':选中的数据

        columns: [{
        //     field: 'example',
        //     checkbox: true    // 是否显示复选框
        // }, {
            field: 'id',
            title: 'ID',
            sortable: true    // 是否排序
        }, {
            field: 'tName',
            title: '任务名称',
            formatter: function (value, row, index) {
                return "<a href=\"/task/edit?task_id=" +row.id + "\">" + row.tName + "</a>";
            }
        }, {
            field: 'lastCaiji',
            title: '采集时间',
            formatter: function (value, row, index) {
                return changeDateFormat(value)
            }
        }, {
            field: 'nextTime',
            title: '下次采集时间',
            formatter: function (value, row, index) {
                if (value == 0) {
                    return "无";
                }
                return changeDateFormat(value)
            }
        }, {
            field: 'tAuto',
            title: '自动采集',
            formatter: function (value, row, index) {
                if (value == 1) {
                    return "<span class=\"task-auto\">是</span>" +
                        "                    &nbsp;&nbsp;&nbsp;<a class=\"auto_collect\" href=\"javascript:;\" >关闭</a>"
                } else {
                    return "<span class=\"task-auto\">否</span>" +
                        "                    &nbsp;&nbsp;&nbsp;<a class=\"auto_collect\" href=\"javascript:;\">开启</a>"
                }
            },
            events: {
                'click .auto_collect': function (event, value, row, index) {
                    $.ajax({
                        url: "/task/auto.do",
                        type: "post",
                        dataType: "json",
                        data:{taskId: row.id, auto: ~value&1},
                        success: function (e) {
                            if (e.code == 0) {
                                if (value == 1) {
                                    var rows = {
                                        index : index, //更新列所在行的索引
                                        field : "tAuto", //要更新列的field
                                        value : 0 //要更新列的数据
                                    }//更新表格数据
                                    $('#tb_task').bootstrapTable("updateCell",rows);
                                } else {
                                    var rows = {
                                        index : index, //更新列所在行的索引
                                        field : "tAuto", //要更新列的field
                                        value : 1 //要更新列的数据
                                    }//更新表格数据
                                    $('#tb_task').bootstrapTable("updateCell",rows);
                                }
                            } else {
                                showMsg(e.msg, 'danger');
                            }
                        }
                    });
                }
            }
        }, {
            field: 'gmtCreate',
            title: '添加时间',
            formatter: function (value, row, index) {
                return changeDateFormat(value)
            }
        }, {
            field: 'taskName',
            title: '任务分组'
        }, {
            field: 'operate',
            title: '操作',
            formatter: btnGroup,  // 自定义方法
            events: {
                'click .del-btn': function (event, value, row, index) {
                    var rowId = row.id;
                    $.confirm({
                        title: "删除任务",
                        content: '确定删除任务 ID:' + row.id  + '吗？' ,
                        backgroundDismiss: true,
                        buttons: {
                            confirm: {
                                btnClass: 'btn-red',
                                text: '确认',
                                action: function() {
                                    $.ajax({
                                        url: "/task/delete.do",
                                        type: "post",
                                        dataType: 'json',
                                        data: {
                                            taskId: rowId
                                        },
                                        success: function (t) {
                                            if (t.code == 0) {
                                                showMsg("删除成功", "info");
                                                $("#tb_task").bootstrapTable("refresh");
                                            } else {
                                                showMsg(t.msg, "danger");
                                            }
                                        }
                                    });
                                }
                            },
                            cancelAction: {
                                text: '取消',
                                action: function() {
                                }
                            }
                        }
                    });
                }
            }
        }],
        onLoadSuccess: function(data){
            $("[data-toggle='tooltip']").tooltip();
        }
    });
    // 操作按钮
    function btnGroup (e, row, index) {
        let html =
            "                                            <a href=\"/task/crawler?task_id=" + row.id + "\">采集规则</a>\n" +
            "                                            <span class=\"sep\">|</span>" +
            "                                            <a href=\"/task/publish?task_id=" + row.id + "\">发布</a>\n" +
            "                                            <span class=\"sep\">|</span>" +
            "                                            <a href=\"javascript:;\" class=\"del-btn\">删除</a>";
        return html;
    }
    //转换日期格式(时间戳转换为datetime格式)
    function changeDateFormat(cellval) {
        var dateVal = cellval + "";
        if (cellval != null) {
            var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
            var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
            var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
            var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
            var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
            var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
            return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
        }
    }
    $(".delete").on('click', function () {
        var taskId = $(this).attr("item-id");

        if (!taskId) {
            $.notify({
                // options
                message: 'taskId 为空,请刷新后重试!'
            },{
                // settings
                type: 'danger'
            });
            return
        }
        $.ajax({
            url: "/task/delete.do",
            type: "post",
            dataType: 'json',
            data: {
                taskId:taskId
            },
            success: function (t) {
                if (t.code == 0) {
                    showMsg('删除成功,页面即将刷新', 'info'),
                    setTimeout(function () {
                    window.location.reload()}, 3000);
                } else {
                    showMsg(t.msg, "danger");
                }
            }
        });
    });
    $('.collect').on('click', function (e) {
        sse($(this).attr('data'));
    })
    function showMsg(msg, type) {
        $.notify({
            // options
            message: msg
        },{
            // settings
            type: type
        });
    }
    function sse(taskId) {
        $("#crawlerModel").modal('show');
        var source = new EventSource("/task/collect/" + taskId);
        //当抓取到消息
        source.onmessage = function (evt) {
            let msg = isJSON(evt.data);
            if (msg && msg.type) {
                if (msg.type == "url") {
                    let url = msg.contentUrl;
                    let appendStr = "<p>采集内容页：" +
                        "<a href='" + url + "' target='_blank'>" +
                        url + "</a> </p>" +
                        "<p>----采集起始页:<a href='" + msg.fromUrl + "' target='_blank'>" +
                        msg.fromUrl + "</a> </p>";
                    $('#crawlerResultContent').append(appendStr);
                } else if(msg.type == "fromUrl"){
                    let appendStr = "<p>采集起始页:<a href='" + msg.url +
                        "' target='_blank'>" + msg.url + "</a> </p>";
                    if (msg.contentUrlCount == msg.validUrlCnt) {
                        appendStr += "<p>采集到" + msg.contentUrlCount + "条有效网址</p>"
                    }else {
                        appendStr += "<p>采集到" + msg.contentUrlCount +"条网址,其中"+ msg.validUrlCnt+"条有效</p>"
                    }
                    $('#crawlerResultContent').append(appendStr);
                } else if (msg.type == "publish") {
                    let appendStr  = "";
                    if (msg.rst == 1) {
                        appendStr += "<p>成功将<a href='" + msg.url+"'>内容</a>发布至:" + msg.location + "</p>"
                    }
                    $('#crawlerResultContent').append(appendStr);
                } else if (msg.type == "publishRst") {
                    let appendStr = "<p>成功发布" + msg.total + "条数据</p>";
                    $('#crawlerResultContent').append(appendStr);
                } else if(msg.type == "error") {
                    let appendStr = "<p>采集错误:" + msg.msg + "</p>";
                    $('#crawlerResultContent').append(appendStr);
                }
            }
            // document.getElementById("data").innerHTML = "股票行情：" + evt.data;
        };
        source.onerror = function (evt) {
            console.log(evt);
            source.close();
        }
    }

    $('#crawlerModel').on('hidden.bs.modal', function (e) {
        $('#crawlerResultContent').html("");
    })

    $(".auto_collect").on('click', function (e) {
        var taskId = $(this).attr("data-id");
        $(this).attr("disabled", true);
        var taskAuto = $(this).attr("data-now");
        var auto = $(this).parent().find(".task-auto");
        var autoa = $(this);
        $.ajax({
            url: "/task/auto.do",
            type: "post",
            dataType: "json",
            data:{taskId: taskId, auto: ~taskAuto&1},
            success: function (e) {
                if (e.code == 0) {
                    if (taskAuto == 1) {
                        auto.html("否");
                        autoa.html("开启");
                        autoa.attr("data-now", 0);
                    } else {
                        auto.html("是");
                        autoa.html("关闭");
                        autoa.attr("data-now", 1);
                    }
                } else {
                    showMsg(e.msg, 'danger');
                }
            }
        });

        $(this).attr("disabled", false);

    })

    function isJSON(str) {
        if (typeof str == 'string') {
            try {
                let obj=JSON.parse(str);
                if(typeof obj == 'object' && obj ){
                    return obj;
                }else{
                    return undefined;
                }

            } catch(e) {
                return undefined;
            }
        }
        return  undefined;
    }
});