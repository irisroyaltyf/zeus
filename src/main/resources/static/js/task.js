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
            console.log(evt.data);
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
        $.ajax({
            url: "/task/auto.do",
            type: "post",
            dataType: "json",
            data:{taskId: taskId, auto: ~taskAuto&1},
            success: function (e) {
                if (e.code == 0) {
                    if (taskAuto == 1) {
                        $(this).parent().find(".task-auto").html("否");
                        $(this).html("开启");
                        $(this).attr("data-now", 0);
                    } else {
                        $(this).parent().find(".task-auto").html("是");
                        $(this).html("关闭");
                        $(this).attr("data-now", 1);
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