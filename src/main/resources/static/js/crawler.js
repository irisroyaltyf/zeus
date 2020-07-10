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

    $("#beginUrlSave").on("click", function (e) {
        var id = $('.begin-content-tab.active').attr("id");
        var modifyInputId = $("#modifyId").val();
        var error = undefined;
        var nodePre = "  <div class=\"input-group mb-3\">\n" +
            "                                                        <input type=\"input\" name=\"fromUrls\" class=\"form-control\" value='";
        var nodeMi = "' id='";
        var nodeEnd = "'/>\n" +
            "                                                        <div class=\"input-group-prepend\">\n" +
            "                                                            <span class=\"input-group-text url-add\"><i class=\"iconfont iconedit\"></i></span>\n" +
            "                                                        </div>\n" +
            "                                                        <div class=\"input-group-prepend\">\n" +
            "                                                            <span class=\"input-group-text url-close\"><i class=\"iconfont iconguanbi\"></i> </span>\n" +
            "                                                        </div>\n" +
            "                                                    </div>";
        if (id == "manual-fill") {
            var sourceUrls = $("#source_urls").val() + "\n";
            sourceUrls = sourceUrls.replace("\r\n", "\n");
            var matchUrls = sourceUrls.match(/\w+\:\/\/[^\n]+/i);
            if (!matchUrls) {
                $.toast({
                    type: 'error',
                    title: '错误！',
                    content: '请输入正确的网址',
                    delay: 5000
                });
                return ;
            }
            var urls = sourceUrls.split("\n");
            if (urls.length > 0) {
                urls = Array.from(new Set(urls));
                urls.forEach(x=>{
                    var xtr = x.trim().toLowerCase();
                    if (xtr.length > 0) {
                        if (xtr.length >0) {
                            if (!(xtr.startsWith("http://") || xtr.startsWith("https://"))) {
                                $.toast({
                                    type: 'error',
                                    title: '错误！',
                                    content: '请输入正确的网址，网址：' + x + "无效",
                                    delay: 5000
                                });
                                error = true;
                                return;
                            }
                        }
                    }
                });
                if(error) {
                    return ;
                }
                let z = 0;
                for(let i = 0; i< urls.length; ++i) {
                    var xtr = urls[i].trim().toLowerCase();
                    if (xtr != "") {
                        if (z == 0 && modifyInputId) {
                            $("#" + modifyInputId).val(xtr);
                        } else {
                            if ($("input[name='fromUrls'][value='" + xtr +"']").length == 0) {
                                $("#from-urls").append(nodePre + xtr + nodeMi + Math.uuid() + nodeEnd);
                            }
                        }
                        z ++;
                    }
                }
            }
        } else if(id == "templ-fill") {
            let url = paramUrlDo();
            if (url) {
                if (modifyInputId) {
                    $("#" + modifyInputId).val(url);
                } else {
                    if ($("input[name='fromUrls'][value='" + url +"']").length == 0) {
                        $("#from-urls").append(nodePre + url + nodeMi + Math.uuid() + nodeEnd);
                    } else {
                        showMsg("重复地址", 'info');
                    }
                }
            } else {
                return;
            }
        }
        $("#beginUrlModal").modal("hide");
    });

    $("#from-urls").on("click",".url-add", function (e) {
        var modifyInput = $(this).parent().parent().find("input");
        var url = modifyInput.val();

        $("#modifyId").val(modifyInput.attr("id"));
        if (url) {
            $("#begin-url-modal-content").clearForm();
            var match = url.match(/\{param\:(\w+)\,([^\}]*)\}/i);
            if (match) {
                $("#paramUrl").val(url.replace(/\{param\:(\w+)\,([^\}]*)\}/i, "[内容]"));
                if (match[1] == 'num') {
                    var paramVal = match[2].split("\t");
                    $("#param_num_start").val(paramVal[0]);
                    $("#param_num_end").val(paramVal[1]);
                    $("#param_num_inc").val(paramVal[2]);
                    if (paramVal[3] == 1) {
                        $("#param_num_desc").attr("checked", "checked")
                    } else {
                        $("#param_num_desc").attr("checked", false)
                    }
                    $("input:radio[name='paramType'][value='num']").prop("checked", true);
                } else if(match[1] = 'letter') {
                    var paramVal = match[2].split("\t");
                    $("#param_letter_start").val(paramVal[0]);
                    $("#param_letter_end").val(paramVal[1]);
                    if (paramVal[3] == 1) {
                        $("#param_num_desc").attr("checked", "checked")
                    } else {
                        $("#param_num_desc").attr("checked", false)
                    }
                    $("input:radio[name='paramType'][value='letter']").prop("checked", true);
                }
                $('#begin-url-tab a[href="#templ-fill"]').tab('show');
            } else {
                $("#source_urls").val(url);
                $('#begin-url-tab a[href="#manual-fill"]').tab('show');
            }
        }

        $("#beginUrlModal").modal("show");
    })
    $('#beginUrlModal').on('hidden.bs.modal', function (e) {
        $("#modifyId").val("");
    })
    $("#preview").on("click", function () {
        paramUrlDo(true);
    });
    $("#from-urls").on("click",".url-close", function (e) {
        $(this).parent().parent().remove();
    });
    $("#addParamsContent").on("click", function (e) {
        var url =$("#paramUrl").val();
        if (url) {
            var n = url.search("[内容]");
            if (n > 0) {
                $.toast({
                    type: 'error',
                    title: '错误！',
                    content: '在网址中已经存在[内容]',
                    delay: 5000
                });
                return;
            }
        }
        insertAtCaret($("#paramUrl"), "[内容]");
    })
    function paramUrlDo(isPreview) {
        var paramUrl = $("#paramUrl").val();
        if (paramUrl) {
            var xtr = paramUrl.trim();
            if (!(xtr.startsWith("http://") || xtr.startsWith("https://"))) {
                $.toast({
                    type: 'error',
                    title: '错误！',
                    content: '请输入正确的网址，网址：' + xtr + "无效",
                    delay: 5000
                });
            } else {
                var n = xtr.search("[内容]");
                if (n < 0) {
                    $.toast({
                        type: 'error',
                        title: '错误！',
                        content: '请在网址中添加[内容]，才能正确批量生成地址哦',
                        delay: 5000
                    });
                    return ;
                }
                var rst = "";
                var paramType = $("input:radio[name='paramType']:checked").val();
                if (paramType == "num") {
                    var desc = $("#param_num_desc").prop("checked")?1:0;
                    var start = parseInt($("#param_num_start").val());
                    var end = parseInt($("#param_num_end").val());
                    var step = parseInt($("#param_num_inc").val());
                    step = Math.max(1, step);
                    if (!end) {
                        end=start;
                    }
                    end = Math.max(start, end);
                    if (isPreview) {
                        if (desc == 1) {
                            var tmp = start;
                            start = end;
                            end = tmp;
                        }
                        let i = start;
                        do {
                            rst += xtr.replace("[内容]", i.toString()) + "\n";
                            if (desc == 1) {
                                i -= step;
                                if (i < end) {
                                    break;
                                }
                            } else {
                                i += step;
                                if (i > end) {
                                    break;
                                }
                            }
                        } while (true);
                        $("#source_preview").val(rst);
                    } else {
                        let param = "{param:num," + start + "\t" + end +"\t" + step + "\t" + desc + "}";
                        return xtr.replace("[内容]", param);
                    }
                } else if (paramType == "letter") {
                    var desc = $("#param_letter_desc").prop("checked")?1:0;
                    var start = $("#param_letter_start").val();
                    var end = $("#param_letter_end").val();
                    var i = 0, s = 0, e = 0;
                    if (start && start.length > 0) {
                        s = start.charCodeAt(0);
                    }
                    if (end && end.length > 0) {
                        e = end.charCodeAt(0);
                    }
                    if (isPreview) {
                        if (desc == 1) {
                            var tmp = s;
                            s = e;
                            e = tmp;
                        }
                        i = s;
                        do {
                            if (i == 0) {
                                rst += xtr.replace("[内容]", "") + "\n";
                                break;
                            } else {
                                rst += xtr.replace("[内容]", String.fromCharCode(i)) + "\n";
                            }
                            if (desc == 1) {
                                i --;
                                if (i < e) {
                                    break;
                                }
                            } else {
                                i ++;
                                if (i > e) {
                                    break;
                                }
                            }
                        } while (true);
                        $("#source_preview").val(rst);
                    } else {
                        let param = "{param:letter," + String.fromCharCode(s) + "\t" + String.fromCharCode(e) + "\t" + desc + "}";
                        return xtr.replace("[内容]", param);
                    }
                } else if(paramType == "custom") {
                    var customParam = $("#param_custom").val();
                    customParam = customParam.replace("\r\n", "\n");
                    if (customParam) {
                        var params = customParam.trim().split("\n");
                        var p = [];
                        params.forEach(m => {
                           if (m && m != "" && m.trim() != "") {
                               if (isPreview) {
                                   rst += xtr.replace("[内容]", m) + "\n";
                               }
                               p.push(m);
                           }
                        });
                        if (p.length > 0) {
                            if (isPreview) {
                                $("#source_preview").val(rst);
                                return ;
                            } else {
                                let param = "{param:letter," + p.join("\t") + "}";
                                return xtr.replace("[内容]", param);
                            }
                        }
                    }
                    $.toast({
                        type: 'error',
                        title: '错误！',
                        content: '请输入正确的自定义内容',
                        delay: 5000
                    });
                }
            }
        } else {
            $.toast({
                type: 'error',
                title: '错误！',
                content: '请输入正确的网址',
                delay: 5000
            });
        }
    }
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
    $.toastDefaults = {
        position: 'top-center', /** top-left/top-right/top-center/bottom-left/bottom-right/bottom-center - Where the toast will show up **/
        dismissible: true, /** true/false - If you want to show the button to dismiss the toast manually **/
        stackable: true, /** true/false - If you want the toasts to be stackable **/
        pauseDelayOnHover: true, /** true/false - If you want to pause the delay of toast when hovering over the toast **/
        style: {
            toast: '', /** Classes you want to apply separated my a space to each created toast element (.toast) **/
            info: '', /** Classes you want to apply separated my a space to modify the "info" type style  **/
            success: '', /** Classes you want to apply separated my a space to modify the "success" type style  **/
            warning: '', /** Classes you want to apply separated my a space to modify the "warning" type style  **/
            error: '', /** Classes you want to apply separated my a space to modify the "error" type style  **/
        }
    };

    function cpMatch(toObj,options){if(!options){options={}}
        var group='(?<content{:num}>[\\s\\S]*?)';if(options.only){sign=sign.replace('{:num}','');if($(toObj).val().indexOf(sign)<0&&$(toObj).val().indexOf('(?<content>')<0){if(options.group){sign=group.replace('{:num}','')}
            insertAtCaret($(toObj),sign)}else{toastr.error('存在'+sign+'或捕获组')}}else{var reSign=new RegExp(sign.replace('{:num}','(\\d*)').replace('[','\\[').replace(']','\\]'),'g');var reP=new RegExp("\\(\\?<content(\\d*)>",'g');var list=null;var max=0;while((list=reSign.exec($(toObj).val()))!=null){var num=parseInt(list[1]);if(num>max){max=num}}
            list=null;while((list=reP.exec($(toObj).val()))!=null){var num=parseInt(list[1]);if(num>max){max=num}}
            if(options.group){sign=group}
            sign=sign.replace('{:num}',max+1);insertAtCaret($(toObj),sign)}
    }

    function insertAtCaret(myField,myValue){
        var curObj=myField[0];
        if(document.selection){
            myField.focus();
            var sel=document.selection.createRange();
            sel.text=myValue;
            sel.select()
        }else if(curObj.selectionStart||curObj.selectionStart=='0'){
            var startPos=curObj.selectionStart;
            var endPos=curObj.selectionEnd;
            var restoreTop=curObj.scrollTop;
            var value=myField.val();
            value=value.substring(0,startPos)+myValue+value.substring(endPos,value.length);
            myField.val(value);
            myField.focus();
            curObj.selectionStart=startPos+myValue.length;
            curObj.selectionEnd=startPos+myValue.length
        }else{
            myField.val(myField.val()+myValue);
            myField.focus()
        }
    }

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
