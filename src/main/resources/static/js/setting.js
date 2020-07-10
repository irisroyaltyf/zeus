;jQuery(function () {
    $("input:radio[name='imageTransfer']").on("change", function () {
        $(".image-config").addClass("d-none");
        var layFor = $(this).attr("lay-for");
        if (layFor) {
            $(layFor).removeClass("d-none");
        }
    });
    $("#image-save").on("click", function () {
        $("#image-form").ajaxSubmit({
           type: "post",
           dataType: "json",
           success: function (e) {
                if (e.code == 0) {
                    showMsg("保存成功", 'info');
                } else {
                    showMsg(e.msg, 'danger');
                }
           }
        });
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