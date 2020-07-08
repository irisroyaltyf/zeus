;jQuery(function () {
    $(".publish-nav-tabs .publish-nav").bind('click', function () {
        $('.publish-nav-tabs .publish-nav').removeClass('active');
        $(this).addClass('active');
        $(".publish-card").addClass("d-none");
        $('#'+$(this).attr("for")).removeClass("d-none");
    });
    //切换发布方式
    $("#publishType").on('change', function (e) {
        //TODO 根据切换的内容把FORM中不需要的内容去掉
        let selected = $(this).children('option:selected').val();
        if (1 == selected) {
            $(".publish-type").addClass("d-none");
            $('#publish-file').removeClass("d-none");
        } else if(2 == selected) {
            $(".publish-type").addClass("d-none");
            $('#publish-db').removeClass("d-none");
        }
    });
    //file 保存
    $("#save").on('click', function () {
        $('#publishRuleForm').ajaxSubmit({
            type: "post",
            dataType: "json",
            success: function (data) {
                if (data.code == 0) {
                    showMsg("保存成功", "info");
                    setTimeout(function () {
                        window.location.reload();
                    }, 2000);
                } else {
                    showMsg("保存错误,请刷新后重试", "danger");
                }
            }
        })
    });
    //保存数据库链接配置
    $("#saveDbConfig").on("click", function (e) {
        $('#dbConfigForm').ajaxSubmit({
            type: "post",
            dataType: "json",
            success: function (data) {
                if (data.code == 0) {
                    showMsg("保存成功", "info");
                    setTimeout(function () {
                        window.location.reload();
                    }, 2000);
                } else {
                    showMsg("保存错误,请刷新后重试", "danger");
                }
            }
        })
    });
    //保存绑定字段的配置
    $("#saveDBTableConfig").on("click", function (e) {
        $('#fieldBindForm').ajaxSubmit({
            type: "post",
            dataType: "json",
            success: function (data) {
                if (data.code == 0) {
                    showMsg("保存成功", "info");
                    // setTimeout(function () {
                    //     window.location.reload();
                    // }, 2000);
                } else {
                    showMsg("保存错误,请刷新后重试", "danger");
                }
            }
        })
    });
    //测试数据库连通性
    $("#test_connect").on('click', function (e) {
        $.ajax({
            url: "/task/publish/db/test.do",
            data: {
                dbType : $("#db-type").val(),
                host: $('#db-host').val(),
                port: $('#db-port').val(),
                user: $("#db-user").val(),
                pwd:  $("#db-pwd").val(),
                schema:$("#db-schema").val(),
                charset: $("#db-charset").val(),
            },
            type: "post",
            dataType: "json",
            success: function (e) {
                if (e.code == 0) {
                    $('#test_feedback').addClass("db-success");
                    $('#test_feedback').html("数据库连接成功");
                } else {
                    $('#test_feedback').addClass("db-error");
                    $('#test_feedback').html("数据库连接错误:" + e.msg);
                }
            }
        })
    });
    // 查询数据表 弹出框选择表
    $("#schema_info").on('click', function (e) {
        $.ajax({
            url: "/task/publish/db/schema.info",
            data: {
                dbType : $("#db-type").val(),
                host: $('#db-host').val(),
                port: $('#db-port').val(),
                user: $("#db-user").val(),
                pwd:  $("#db-pwd").val(),
                schema:$("#db-schema").val(),
                charset: $("#db-charset").val(),
            },
            type: "post",
            dataType: "json",
            success: function (e) {
                if (e.code == 0) {
                    $("#schema-names-group").html("");
                    $('#schema-info-model').modal('show');
                    e.data.forEach(function (d) {
                        let option = "<option value='" + d + "'>" + d + "</option>";
                        $("#schema-names-group").append(option);
                    })
                } else {
                    showMsg(e.msg, "danger");
                }
            }
        })
    });
    // 数据表选择弹出框完成
    $("#schema_info_sure").on('click', function () {
        $('#db-schema').val($('#schema-names').val());
        $('#schema-info-model').modal('hide');
    })
    // 选完数据表 绑定数据
    $("#bind-table").on('click', function (e) {
        let taskId = $('#task_id').val();
        let publishRuleId = $('#publish_id').val();

        if (!taskId || !publishRuleId) {
            showMsg("发布配置未正确配置,请刷新后重试", "danger");
            return ;
        }
        let tableName = $("#db-select-table").val();
        if (!tableName || tableName == "0") {
            showMsg("请先选择数据表", "danger");
            return ;
        }
       if ($('#table_bind_' + tableName).length > 0) {
           showMsg("当前表已经绑定", "info");
           return ;
       }
        $.ajax({
            url: "/task/publish/db/bind.do",
            dataType: "json",
            data: {
                taskId: taskId,
                table: tableName,
                publishRuleId:publishRuleId
            },
            type:"post",
            success: function (data) {
                console.log(data);
                if (data.code == 0) {
                    var temp = _.template($("#template-table-field").html());
                    o = $(temp({dataset: data.data,tableName:tableName}));
                    $("#table-field-config").append(o);
                } else {
                    showMsg(date.msg, "danger");
                }
            }
        })
    })
    // 删除绑定数据表
    $("#table-field-config").on('click', ".publish-config-close", function (e) {
        let tableId = $(this).attr('data');
        $('#' + tableId).remove();
    });

    function showMsg(msg, type) {
        $.notify({
            // options
            message: msg
        },{
            // settings
            type: type
        });
    }
});

//自定义框
function fieldSelectChange(e) {
    console.log("111")
    let str = $(e).val();
    if (str && str.startsWith("custom:")) {
        $(e).parent().find(".custom-input").removeClass("d-none");
    } else {
        $(e).parent().find(".custom-input").addClass("d-none");
    }
}