;jQuery(function () {
    $('#tb_collecteds').bootstrapTable( {
        classes: 'table table-bordered table-hover table-striped',
        url: '/collected/list.data',
        method: 'get',
        dataType : 'json',        // 因为本示例中是跨域的调用,所以涉及到ajax都采用jsonp,
        uniqueId: 'id',
        toolbar: '#toolbar',       // 工具按钮容器
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
            field: 'example',
            checkbox: true    // 是否显示复选框
        }, {
            field: 'id',
            title: 'ID',
            sortable: true    // 是否排序
        }, {
            field: 'url',
            title: '采集网址'
        }, {
            field: 'target',
            title: '已发布至',
        }, {
            field: 'publishType',
            title: '发布方式'
        }, {
            field: 'taskName',
            title: '任务'
        }, {
            field: 'gmtCreate',
            title: '采集时间',
            formatter: function (value, row, index) {
                return changeDateFormat(value)
            }
        }, {
            field: 'operate',
            title: '操作',
            formatter: btnGroup,  // 自定义方法
            events: {
                'click .del-btn': function (event, value, row, index) {
                    var selectIds = [];
                    selectIds.push(row.id);
                    $.confirm({
                        title: "删除已采集数据",
                        content: '确定删除已采集数据 ID:' + row.id  + '吗？' ,
                        backgroundDismiss: true,
                        buttons: {
                            confirm: {
                                btnClass: 'btn-red',
                                text: '确认',
                                action: function() {
                                    $.ajax({
                                        url: "/collected/delete.do",
                                        type: "post",
                                        dataType: "json",
                                        data: {selectedIds: selectIds},
                                        success: function (e) {
                                            $("#tb_collecteds").bootstrapTable("refresh");
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
    function btnGroup ()
    {
        let html =
            '<a href="#!" class="btn btn-xs btn-default del-btn" title="删除" data-toggle="tooltip"><i class="mdi mdi-window-close"></i></a>';
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
    $("#btn_delete").on("click", function (e) {
        var row=$("#tb_collecteds").bootstrapTable('getSelections');
        var selectIds = [];
        for (var i = 0;i < row.length; i ++) {
            selectIds.push(row[i].id);
        }
        if (selectIds.length == 0) {
            return ;
        }

        $.confirm({
            title: "删除已采集数据",
            content: '确定删除么',
            backgroundDismiss: true,
            buttons: {
                confirm: {
                    btnClass: 'btn-red',
                    text: '确认',
                    action: function() {
                        $.ajax({
                            url: "/collected/delete.do",
                            type: "post",
                            dataType: "json",
                            data: {selectedIds: selectIds},
                            success: function () {
                                $("#tb_collecteds").bootstrapTable("refresh");
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
    })
});