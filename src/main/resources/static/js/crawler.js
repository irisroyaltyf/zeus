;jQuery( function() {
    $(".crawler-nav-tabs .crawler-nav").bind('click', function () {
        $('.crawler-nav-tabs .crawler-nav').removeClass('active');
        $(this).addClass('active');
        $(".crawler-card").addClass("d-none");
        $('#'+$(this).attr("for")).removeClass("d-none");
    });
    $(".save").on('click', function () {
        let saveFor = $(this).attr("data");
        if (saveFor == "crawler") {
            $('#taskEditForm').ajaxSubmit({
                type:"post",
                dataType: "json",
                success: function (data) {
                    console.log(data);
                }
            })
        } else if (saveFor == "begin-url") {
            $("#beginUrlForm").ajaxSubmit({
                type:"post",
                dataType: "json",
                data:{sourceIsContent:$('#source_is_content_check').prop('checked')},
                success: function (data) {
                    if (data && data.code == 0) {
                        showNotify('保存成功', 'info');
                    } else {
                        if (data) {
                            showNotify(data.msg, 'danger');
                        } else {
                            showNotify("系统异常", "danger");
                        }
                    }
                }
            })
        } else if(saveFor == "content-url") {
            $("#contentUrlForm").ajaxSubmit({
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data && data.code == 0) {
                        showNotify("保存成功", 'info')
                    } else {
                        if (data) {
                            showNotify(data.msg, 'danger');
                        } else {
                            showNotify("系统异常", "danger");
                        }
                    }
                }
            })
        } else if (saveFor == "crawler-content") {
            $('#crawlerContentForm').ajaxSubmit({
                type:"post",
                dataType:"json",
                success: function (data) {
                    if (data && data.code == 0) {
                        showNotify("保存成功", 'info')
                    } else {
                        if (data) {
                            showNotify(data.msg, 'danger');
                        } else {
                            showNotify("系统异常", "danger");
                        }
                    }
                }
            });
        }
    });
    $('#crawlerContentForm').on("click", ".field-title", function () {
        let id = $(this).attr('data');
        let fieldObjBase = $("#" + id).find(".hi-field-v").val();
        if (fieldObjBase) {
            let c = Base64.decode(fieldObjBase);
            let fieldObj= eval("(" + c+ ")");
            $('#multi_check').prop('checked', fieldObj.multi);
            $('#field_name').val(fieldObj.name);
            $('#belong_page').find("option[value=" + fieldObj.belongPage +"]").selected(true);
            $('#field_rule').val(fieldObj.rule);
            $('#fieldTableId').val(id);
            $('#field_final_marge').val(fieldObj.finalMerge);
        }
        $('#fieldModal').modal('show');
    });
    $("#crawlerContentForm").on("click", '.mdi-close-outline', function () {
        let id = $(this).attr('data');
        $("#" + id).remove();
    })

    $('#fieldModal').on('hidden.bs.modal', function (e) {
        let form = $('#fieldAddForm');
        form.removeClass("was-validated");
        $('#multi_check').prop('checked', false);
        $('#field_name').val("");
        $('#belong_page').find("option[value='default']").selected(true);
        $('#field_rule').val("");
        $('#fieldTableId').val("");
        $('#field_final_marge').val("");
    })
    $("#fieldSave").on('click', function (event) {
        let form = $('#fieldAddForm');
        if (!form || form[0].checkValidity() == false) {
            event.preventDefault();
            event.stopPropagation();
            form.addClass("was-validated");
            return ;
        }
        form.addClass("was-validated");

        $('#fieldAddForm').ajaxSubmit({
            type:"post",
            dataType: "json",
            data: {multi: $('#multi_check').prop('checked')},
            success: function (data) {
                if (data && data.code == 0) {
                    let fieldId = $('#fieldTableId').val();
                    if (fieldId) { // update
                        let fieldRow = $("#" + fieldId);
                        fieldRow.find(".hi-field-v").val(data.data);
                        fieldRow.find(".field-title").html($('#field_name').val());
                        fieldRow.find(".tdBelongPage").html($('#belong_page').val());
                    } else {
                        var id = data.data.substr(0, 8) + "_" + Math.floor((Math.random() * 100) + 1);
                        $("#fieldTable").append(
                            "<tr id='" + id + "'><td><input type=\"hidden\" class='hi-field-v' name=\"field\" value=\"" + data.data + "\"/>" +
                            "<a href=\"javascript:void(0);\" class='field-title' data='" + id + "'\">" + $('#field_name').val() + "</a></td>" +
                            "<td class='tdBelongPage'>" + $('#belong_page').val() + "</td>" +
                            "<td>规则匹配</td>" +
                            "<td>数据处理<i class=\"mdi mdi-close-outline\" data='" + id + "'></i></td></tr>"
                        );
                    }
                    $('#fieldModal').modal('hide');
                } else {
                    if (data) {
                        showNotify(data.msg, 'danger');
                    } else {
                        showNotify("系统异常, 请刷新后重试", "danger");
                    }
                }
            }
        })
    });
    function showNotify($msg, $type, $delay, $icon, $from, $align) {
        $type  = $type || 'info';
        $delay = $delay || 1000;
        $from  = $from || 'top';
        $align = $align || 'right';
        $enter = $type == 'danger' ? 'animated shake' : 'animated fadeInUp';

        jQuery.notify({
                icon: $icon,
                message: $msg
            },
        {
            element: 'body',
            type: $type,
            allow_dismiss: true,
            newest_on_top: true,
            showProgressbar: false,
            placement: {
                from: $from,
                align: $align
            },
            offset: 20,
            spacing: 10,
            z_index: 10800,
            delay: $delay,
            animate: {
                enter: $enter,
                exit: 'animated fadeOutDown'
            }
        });
    }
});
