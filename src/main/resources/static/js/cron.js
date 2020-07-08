Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};
var hasNull = false;
function syncCron(json){
    var data = $('#cronForm').serializeJSON({
        customTypes:{
            "cron[hour][in]": "array",
            "cron[month][in]": "array",
            "cron[weekday][in]": "array",
            "cron[day][in]": "array",
            "cron[minute][in]":"array",
            "cron[seconds][in]":"array"
        }
    }), cronDesc = '';
    var rule = data.rule, cron = data.cron;
    //修改数据
    if(!!json){
        var jsonData = JSON.parse(json);
        for(var key in jsonData){
            var value=jsonData[key];
            var newArray=new Array();
            newArray.push(value);
            if(key=="hourIn"){
                cron.hour.in=newArray;
            }else if(key=="minuteIn"){
                cron.minute.in=newArray;
            }else if(key=="secondsIn"){
                cron.seconds.in=newArray;
            }else if(key=="dayIn"){
                cron.day.in=newArray;
            }else if(key=="weekdayIn"){
                cron.weekday.in=newArray.toString().split(",");
            }
        }
    }
    var expression = {
        seconds: null,
        minute: null,
        hour: null,
        day: null,
        month: null,
        weekday: null,
        year: '*'
    };
    var desc = {
        seconds: null,
        minute: null,
        hour: null,
        day: null,
        month: null,
        weekday: null,
        year: null
    };
    switch (cron.month.use) {
        case 'every':
            expression.month = '*';
            desc.month = '每月';
            desc.year = null;
            break;
        case 'in':
            if (cron.month.in instanceof Array && cron.month.in.length > 0) {
                expression.month = cron.month.in.join(',');
                desc.month = expression.month + '月的 ';
            }
            break;
        case 'between':
            expression.month = cron.month.between.start + '-' + cron.month.between.end;
            if(cron.month.between.start == cron.month.between.end){
                desc.month = cron.month.between.start + '月的 ';
            } else {
                desc.month = cron.month.between.start + '月到' + cron.month.between.end + '月的 ';
            }
            break;
        case 'interval':
            expression.month = cron.month.interval.start + '/' + cron.month.interval.value;
            var count = Number(cron.month.interval.value) == 1 ? '' : cron.month.interval.value;
            desc.month = '从' + cron.month.interval.start + '月开始，每' + count + '个月的 ';
            break;
    }
    if(rule.enable.daily == 'yes'){
        expression.day = "*";
        expression.weekday = "?";
        expression.month = "*";
        desc.day = '每天的';
        desc.weekday = null;
        desc.month = null;
    }else{
        if(cron.month.use != 'every'){
            desc.year = '每年的 ';
        }
        //月份天数规则
        if(rule.enable.dayWeekday == 'day'){
            expression.weekday = "?";
            desc.weekday = null;
            switch (cron.day.use) {
                case 'interval':
                    expression.day = cron.day.interval.start + '/' + cron.day.interval.value;
                    var count = Number(cron.day.interval.value) == 1 ? '' : cron.day.interval.value;
                    desc.day = '从' + cron.day.interval.start + '号开始，每' + count + '天的 ';
                    break;
                case 'in':
                    if (cron.day.in instanceof Array && cron.day.in.length > 0) {
                        expression.day = cron.day.in.join(',');
                        desc.day = expression.day + '号的 ';
                    }
                    break;
                case 'between':
                    expression.day = cron.day.between.start + '-' + cron.day.between.end;
                    if(cron.day.between.start == cron.day.between.end){
                        desc.day = cron.day.between.start + "的 ";
                    } else {
                        desc.day = cron.day.between.start + '号到' + cron.day.between.end + '号的 ';
                    }
                    break;
                case 'last':
                    expression.day = 'L';
                    desc.day = '月末(最后一天)的 ';
                    break;
                case 'lastWorkday':
                    expression.day = 'LW';
                    desc.day = '当月的最后一个工作日的 ';
                    break;
                case 'recentWorkday':
                    expression.day = cron.day.recentWorkday;
                    desc.day = '当月' + parseInt(expression.day) + '号的最近的一个工作日的 ';
                    break;
            }
        } else {
            //月份星期规则
            expression.day = "?";
            desc.day = null;
            var weekDesc = {
                "1": "星期日",
                "2": "星期一",
                "3": "星期二",
                "4": "星期三",
                "5": "星期四",
                "6": "星期五",
                "7": "星期六"
            };
            switch (cron.weekday.use) {
                case 'between':
                    expression.weekday = cron.weekday.between.start + '-' + cron.weekday.between.end;
                    if(cron.weekday.between.start == cron.weekday.between.end){
                        desc.weekday = weekDesc[cron.weekday.between.start] + "的 ";
                    } else {
                        desc.weekday = weekDesc[cron.weekday.between.start] + '到' + weekDesc[cron.weekday.between.end] + "的 ";
                    }
                    break;
                case 'in':
                    if(cron.weekday.in && cron.weekday.in.length==1){
                        cron.weekday.in=cron.weekday.in.toString().split(",");
                    }
                    if (cron.weekday.in instanceof Array && cron.weekday.in.length > 0) {
                        expression.weekday = cron.weekday.in.join(',');
                        desc.weekday = $.map(cron.weekday.in, function (value) {
                            return weekDesc[value]
                        }).join(' ',)  + "的 ";
                    }
                    break;
                case 'last':
                    expression.weekday = cron.weekday.last;
                    desc.weekday = '当月最后一个' + weekDesc[parseInt(expression.weekday)]  + "的 ";
                    break;
                case 'week':
                    expression.weekday = cron.weekday.week.at + '/' + cron.weekday.week.weekday;
                    desc.weekday = '当月第 ' + cron.weekday.week.at + " 周的" + weekDesc[cron.weekday.week.weekday] + "的 ";
                    break;
            }
        }
    }
    switch (cron.hour.use) {
        case 'at':
            expression.hour = cron.hour.at;
            desc.hour = cron.hour.at + '点';
            break;
        case 'in':
            if (cron.hour.in instanceof Array && cron.hour.in.length > 0) {
                expression.hour = cron.hour.in.join(',');
                desc.hour = expression.hour + '点的 ';
            }
            break;
        case 'between':
            expression.hour = cron.hour.between.start + '-' + cron.hour.between.end;
            if(cron.hour.between.start == cron.hour.between.end){
                desc.hour = cron.hour.between.start + '点的 ';
            } else {
                desc.hour = cron.hour.between.start + '点到' + cron.hour.between.end + '点的 ';
            }
            break;
        case 'interval':
            expression.hour = cron.hour.interval.start + '/' + cron.hour.interval.value;
            desc.hour = '从' + cron.hour.interval.start + ' 点开始，每' + cron.hour.interval.value + '个小时的 ';
            break;
    }
    switch (cron.minute.use) {
        case 'at':
            expression.minute = cron.minute.at;
            desc.minute = cron.minute.at + '分';
            break;
        case 'in':
            if (cron.minute.in instanceof Array && cron.minute.in.length > 0) {
                expression.minute = cron.minute.in.join(',');
                desc.minute = expression.minute + '分的 ';
            }
            break;
        case 'between':
            expression.minute = cron.minute.between.start + '-' + cron.minute.between.end;
            if(cron.minute.between.start == cron.minute.between.end){
                desc.minute = cron.minute.between.start + '分的 ';
            } else {
                desc.minute = cron.minute.between.start + '分到' + cron.minute.between.end + '分的 ';
            }
            break;
        case 'interval':
            expression.minute = cron.minute.interval.start + '/' + cron.minute.interval.value;
            desc.minute = '从' + cron.minute.interval.start + '分开始，每' + cron.minute.interval.value + '分钟的 ';
            break;
    }
    switch (cron.seconds.use) {
        case 'at':
            expression.seconds = cron.seconds.at;
            desc.seconds = cron.seconds.at + '秒 触发一次';
            break;
        case 'in':
            if (cron.seconds.in instanceof Array && cron.seconds.in.length > 0) {
                expression.seconds = cron.seconds.in.join(',');
                desc.seconds = expression.seconds + '秒 触发一次';
            }
            break;
        case 'between':
            expression.seconds = cron.seconds.between.start + '-' + cron.seconds.between.end;
            if(cron.seconds.between.start == cron.seconds.between.end){
                desc.seconds = cron.seconds.between.start + '秒 触发一次';
            } else {
                desc.seconds = cron.seconds.between.start + '秒到' + cron.seconds.between.end + '秒 触发一次';
            }
            break;
        case 'interval':
            expression.seconds = cron.seconds.interval.start + '/' + cron.seconds.interval.value;
            desc.seconds = '从' + cron.seconds.interval.start + '秒开始，每' + cron.seconds.interval.value + '秒 触发一次';
            break;
    }

    var cronArr = [], cronDescArr = [];
    cronArr.push(expression.seconds);
    cronArr.push(expression.minute);
    cronArr.push(expression.hour);
    cronArr.push(expression.day);
    cronArr.push(expression.month);
    cronArr.push(expression.weekday);
    cronArr.push(expression.year);

    if (desc.year != null) {
        cronDescArr.push(desc.year);
    }
    if (desc.month != null) {
        cronDescArr.push(desc.month);
    }
    if (desc.weekday != null) {
        cronDescArr.push(desc.weekday);
    }
    if (desc.day != null) {
        cronDescArr.push(desc.day);
    }
    cronDescArr.push(desc.hour);
    cronDescArr.push(desc.minute);
    cronDescArr.push(desc.seconds);
    hasNull=false;
    $.each(cronArr, function (i, v) {
        if(v == null || v == ""){
            hasNull = true;
        }
    });
    var cronStr = cronArr.join(' ');
    cronDesc = cronDescArr.join(' ');
    if(hasNull){
        $.notify({
            // options
            message: '您使用了多选规则，请至少选择一项'
        // },{
        //     // settings
        //     type: type
        });
    } else {
        // if (cronStr != $('#cron').val()) {
            $.support.cors = true;
            $('#cron').val(cronStr);
            $("#cron").attr("title", cronDesc);
            // $('#cronDesc').val(cronDesc);
            // $('#cronData').JSONView(data);
        // }
    }
}

$("#cronRuleModal").on("show.bs.modal", function () {
    // var form = layui.form, formSelects = layui.formSelects, laydate = layui.laydate, laytpl = layui.laytpl;

    // var monthInput = '#multi-month';
    // layui.laydate.render({
    //     elem: monthInput,
    //     theme: 'hide-header',
    //     type: 'month',
    //     format: 'M',
    //     position: 'static',
    //     showBottom: false,
    //     ready: function (date) {
    //         $('.laydate-set-ym').html('<span>可以选择多个月份</span>');
    //         $('.laydate-month-list li').removeClass('layui-this');
    //     },
    //     change: function (value, date, endDate) {
    //         var select = $('#month-in select');
    //         var ele = $('.laydate-month-list li:eq(' + (date.month - 1) + ')');
    //         var remove = ele.hasClass('layui-selected');
    //         ele.toggleClass('layui-selected').removeClass("layui-this");
    //         var months = $(monthInput).data('selected');
    //         if (months == null) {
    //             months = [];
    //         }
    //         if (remove) {
    //             months.splice($.inArray(date.month, months), 1);
    //         } else {
    //             months.push(date.month);
    //         }
    //         months = months.sort(function (a, b) {
    //             return Number(a) - Number(b)
    //         });
    //         months = $.unique(months);
    //         if (months.length > 0) {
    //             select.children('option').each(function () {
    //                 var selected = $.inArray(Number(this.value), months) != -1;
    //                 $(this).prop('selected', selected);
    //             });
    //         } else {
    //             select.children('option').prop('selected', false);
    //         }
    //         $(monthInput).data('selected', months);
    //         syncCron(null);
    //     }
    // });
    $('input[type=radio][lay-filter=cron-daily]').on("change", function (d) {
        var val = $(this).val();
        if (val == 'yes') {
            $('#ruleOther').hide();
            $('#ruleEveryDay').show();
        } else if (val == 'no') {
            $('#ruleOther').show();
            $('#ruleEveryDay').show();
        } else {
            layer.msg('请选择是否每天执行');
        }
    });
    $('input[type=radio][lay-filter=dayWeekday]').on("change", function (d) {
        var val = $(this).val();
        if (val == 'weekday') {
            $('#rule-weekday').show();
            $('#rule-day').hide();
        } else if (val == 'day') {
            $('#rule-day').show();
            $('#rule-weekday').hide();
        }
    });
    $('select[lay-filter=month-rule]').on("change", function (d) {
        $('[id^="month-"]').hide();
        var val = $(this).val();
        $('#month-select').hide();
        switch (val) {
            case 'in':
                $('#month-in').show();
                $('#month-input').show();
                $('#month-select').show();
                break;
            case 'between':
                $('#month-between').show();
                break;
            case 'interval':
                $('#month-interval').show();
                break;
        }
    });
    $('select[lay-filter=weekday-rule]').on("change", function (d) {
        $('[id^="weekday-"]').hide();
        var val = $(this).val();
        switch (val) {
            case 'in':
                $('#weekday-in').show();
                break;
            case 'between':
                $('#weekday-between').show();
                break;
            case 'last':
                $('#weekday-last').show();
                break;
            case 'week':
                $('#weekday-week').show();
                break;
        }
    });
    $('select[lay-filter=day-rule]').on("change", function (d) {
        $('[id^="day-"]').hide();
        var val = $(this).val()
        switch (val) {
            case 'in':
                $('#day-in').show();
                break;
            case 'recentWorkday':
                $('#day-recentWorkday').show();
                break;
            case 'between':
                $('#day-between').show();
                break;
            case 'interval':
                $('#day-interval').show();
                break;
        }
    });
    $('select[lay-filter=hour-rule]').on('change', function (d) {
        $('[id^="hour-"]').hide();
        var val = $(this).val();
        switch (val) {
            case 'at':
                $('#hour-at').show();
                break;
            case 'in':
                $('#hour-in').show();
                break;
            case 'between':
                $('#hour-between').show();
                break;
            case 'interval':
                $('#hour-interval').show();
                break;
        }
    });
    $('select[lay-filter=minute-rule]').on("change", function (d) {
        $('[id^="minute-"]').hide();
        var val = $(this).val();
        switch (val) {
            case 'at':
                $('#minute-at').show();
                break;
            case 'in':
                $('#minute-in').show();
                break;
            case 'between':
                $('#minute-between').show();
                break;
            case 'interval':
                $('#minute-interval').show();
                break;
        }
    });
    $('select[lay-filter=seconds-rule]').on("change", function (d) {
        $('[id^="seconds-"]').hide();
        var val = $(this).val();
        switch (val) {
            case 'at':
                $('#seconds-at').show();
                break;
            case 'in':
                $('#seconds-in').show();
                break;
            case 'between':
                $('#seconds-between').show();
                break;
            case 'interval':
                $('#seconds-interval').show();
                break;
        }
    });

    $('select').on("change", function (data) {
        syncCron(null);
    });
    $('radio').on("change", function (data) {
        syncCron(null);
    });
    $("#hourInSelect").on("change",function(id, vals, val, isAdd, isDisabled){
        var newValue;
        var oldValue=layui.formSelects.value("hourInSelect","val");
        if(isAdd){
            oldValue.push(val.value);
            newValue=oldValue.join(",");
        }else{
            if(oldValue.indexOf(val.value)>=0){
                oldValue.remove(val.value);
            }
            newValue=oldValue.join(",");
        }
        var json="{\"hourIn\":\""+newValue+"\"}";
        syncCron(json);
    });

    $("minuteInSelect").on("change", function(id, vals, val, isAdd, isDisabled){
        var newValue;
        var oldValue=layui.formSelects.value("minuteInSelect","val");
        if(isAdd){
            oldValue.push(val.value);
            newValue=oldValue.join(",");
        }else{
            if(oldValue.indexOf(val.value)>=0){
                oldValue.remove(val.value);
            }
            newValue=oldValue.join(",");
        }
        var json="{\"minuteIn\":\""+newValue+"\"}";
        syncCron(json);
    });

    $("secondsInSelect").on("change", function(id, vals, val, isAdd, isDisabled){
        var newValue;
        var oldValue=layui.formSelects.value("secondsInSelect","val");
        if(isAdd){
            oldValue.push(val.value);
            newValue=oldValue.join(",");
        }else{
            if(oldValue.indexOf(val.value)>=0){
                oldValue.remove(val.value);
            }
            newValue=oldValue.join(",");
        }
        var json="{\"secondsIn\":\""+newValue+"\"}";
        syncCron(json);
    });

    $("dayInSelect").on("change", function(id, vals, val, isAdd, isDisabled){
        var newValue;
        var oldValue=layui.formSelects.value("dayInSelect","val");
        if(isAdd){
            oldValue.push(val.value);
            newValue=oldValue.join(",");
        }else{
            if(oldValue.indexOf(val.value)>=0){
                oldValue.remove(val.value);
            }
            newValue=oldValue.join(",");
        }
        var json="{\"dayIn\":\""+newValue+"\"}";
        syncCron(json);
    });

    $("weekInSelect").on("change", function(id, vals, val, isAdd, isDisabled){
        var newValue;
        var oldValue=layui.formSelects.value("weekInSelect","val");
        if(isAdd){
            oldValue.push(val.value);
            newValue=oldValue.join(",");
        }else{
            if(oldValue.indexOf(val.value)>=0){
                oldValue.remove(val.value);
            }
            newValue=oldValue.join(",");
        }
        var json="{\"weekdayIn\":\""+newValue+"\"}";
        syncCron(json);
    });
});

//根据表达式默认配置，方便系统修改时自动配置。这里可以使用自己系统的接口查询corn表达式进行改写
$(function(){
    var cron = $("#cronTime").val();
    if (!cron) {
        cron="0 0 0 * * ? *";
    }
    var cronDesc="每天的 0点 0分 0秒 触发一次";
    //根据cron表达式，默认配置
    //拆分
    var splits=cron.split(" ");
    //秒
    var seconds=splits[0];
    //分
    var minute=splits[1];
    //时
    var hour=splits[2];
    //每月月份天数规则
    var monthDay=splits[3];
    //非每月月份规则
    var month=splits[4];
    //月份星期
    var weekDay=splits[5];
    //每天执行
    if(cron.indexOf("* * ? *")>=0){
        //每天执行按钮选中
        $("input:radio[lay-filter='cron-daily'][value='yes']").attr("checked",true);
        //展开配置项
        $('#ruleOther').hide();
        $('#ruleEveryDay').show();
    }else{
        //非每天执行
        //非每天执行按钮选中
        $("input[type='radio'][value='no']").attr("checked",true);
        //展开配置项
        $('#ruleOther').show();
        $('#ruleEveryDay').show();
    }

    /**具体规则**/
    //时规则
    /**指定小时**/
    //指定小时(可多选)
    $('[id^="hour-"]').hide();
    if(hour.indexOf(",")>=0){
        $("select[name='cron[hour][use]']").find("option[value='in']").attr("selected", true);
        $('#hour-in').show();
        //默认选中
        var hourSplits=hour.split(",");
        formSelects.value('hourInSelect', hourSplits);
    }else if(hour.indexOf("-")>=0){
        //指定小时范围(0-23)
        $("select[name='cron[hour][use]']").find("option[value='between']").attr("selected", true);
        $('#hour-between').show();
        //默认选中
        var hourSplits=hour.split("-");
        $("select[name='cron[hour][between][start]']").val(hourSplits[0]);
        $("select[name='cron[hour][between][end]']").val(hourSplits[1]);
    }else if(hour.indexOf("/")>=0){
        //从 x 小时开始执行，之后每 x 小时执行一次
        $("select[name='cron[hour][use]']").find("option[value='interval']").attr("selected", true);
        $('#hour-interval').show();
        //默认选中
        var hourSplits=hour.split("/");
        $("select[name='cron[hour][interval][start]']").val(hourSplits[0]);
        $("select[name='cron[hour][interval][value]']").val(hourSplits[1]);
    }else{
        //指定小时
        $("select[name='cron[hour][use]']").find("option[value='at']").attr("selected", true);
        $('#hour-at').show();
        //默认选中
        $("select[name='cron[hour][at]']").val(hour);
    }
    /**指定小时**/
    /**指定分钟**/
    //指定分钟(可多选)
    $('[id^="minute-"]').hide();
    if(minute.indexOf(",")>=0){
        $("select[name='cron[minute][use]']").find("option[value='in']").attr("selected", true);
        $('#minute-in').show();
        //默认选中
        var minuteSplits=minute.split(",");
        formSelects.value('minuteInSelect', minuteSplits);
    }else if(minute.indexOf("-")>=0){
        //指定分钟范围(0-59)
        $("select[name='cron[minute][use]']").find("option[value='between']").attr("selected", true);
        $('#minute-between').show();
        //默认选中
        var minuteSplits=minute.split("-");
        $("select[name='cron[minute][between][start]']").val(minuteSplits[0]);
        $("select[name='cron[minute][between][end]']").val(minuteSplits[1]);
    }else if(minute.indexOf("/")>=0){
        //从 x 分钟开始执行，之后每 x 分钟执行一次
        $("select[name='cron[minute][use]']").find("option[value='interval']").attr("selected", true);
        $('#minute-interval').show();
        //默认选中
        var minuteSplits=minute.split("/");
        $("select[name='cron[minute][interval][start]']").val(minuteSplits[0]);
        $("select[name='cron[minute][interval][value]']").val(minuteSplits[1]);
    }else{
        //指定分钟
        $("select[name='cron[minute][use]']").find("option[value='at']").attr("selected", true);
        $('#minute-at').show();
        //默认选中
        $("select[name='cron[minute][at]']").val(minute);
    }
    /**指定分钟**/
    /**指定秒**/
    $('[id^="seconds-"]').hide();
    if(seconds.indexOf(",")>=0){
        $("select[name='cron[seconds][use]']").find("option[value='in']").attr("selected", true);
        $('#seconds-in').show();
        //默认选中
        var secondsSplits=seconds.split(",");
        formSelects.value('secondsInSelect', secondsSplits);
    }else if(seconds.indexOf("-")>=0){
        //指定秒范围(0-23)
        $("select[name='cron[seconds][use]']").find("option[value='between']").attr("selected", true);
        $('#seconds-between').show();
        //默认选中
        var secondsSplits=seconds.split("-");
        $("select[name='cron[seconds][between][start]']").val(secondsSplits[0]);
        $("select[name='cron[seconds][between][end]']").val(secondsSplits[1]);
    }else if(seconds.indexOf("/")>=0){
        //从 x 秒开始执行，之后每 x 秒执行一次
        $("select[name='cron[seconds][use]']").find("option[value='interval']").attr("selected", true);
        $('#seconds-interval').show();
        //默认选中
        var secondsSplits=seconds.split("/");
        $("select[name='cron[seconds][interval][start]']").val(secondsSplits[0]);
        $("select[name='cron[seconds][interval][value]']").val(secondsSplits[1]);
    }else{
        //指定秒
        $("select[name='cron[seconds][use]']").find("option[value='at']").attr("selected", true);
        $('#seconds-at').show();
        //默认选中
        $("select[name='cron[seconds][at]']").val(seconds);
    }
    /**指定秒**/

    /**月份规则**/
    $('[id^="month-"]').hide();
    $('#month-select').hide();
    //每月
    if(month=="*"){
        $("select[name='cron[month][use]']").find("option[value='every']").attr("selected", true);
    }else if(month.indexOf(",")>=0){
        //多选月份
        $("select[name='cron[month][use]']").find("option[value='in']").attr("selected", true);
        $('#month-in').show();
        $('#month-input').show();
        $('#month-select').show();
        //默认选中
        var monthSplits=month.split(",");
        for(var i=0;i<monthSplits.length;i++){
            $("#month-in select").children("option[value='"+(monthSplits[i]>0?(monthSplits[i]-1):monthSplits[i])+"']").prop('selected', false);
            $('#multi-month').find("li[lay-ym="+(monthSplits[i]>0?(monthSplits[i]-1):monthSplits[i])+"]").attr("class","layui-selected");
        }
        $('#multi-month').data('selected', monthSplits.map(Number));
    }else if(month.indexOf("-")>=0){
        //指定月份范围
        $("select[name='cron[month][use]']").find("option[value='between']").attr("selected", true);
        $('#month-between').show();
        //默认选中
        var monthSplits=month.split("-");
        $("select[name='cron[month][between][start]']").val(monthSplits[0]);
        $("select[name='cron[month][between][end]']").val(monthSplits[1]);
    }else if(month.indexOf("/")>=0){
        //从 x 月开始执行，之后每间 x 月执行一次
        $("select[name='cron[month][use]']").find("option[value='interval']").attr("selected", true);
        $('#month-interval').show();
        //默认选中
        var monthSplits=month.split("/");
        $("select[name='cron[month][interval][start]']").val(monthSplits[0]);
        $("select[name='cron[month][interval][value]']").val(monthSplits[1]);
    }
    /**月份规则**/

    /**月份天数规则**/
    $('[id^="day-"]').hide();
    //月份天数规则
    if(monthDay!="?"){
        $('#rule-day').show();
        $('#rule-weekday').hide();

        $("input[type='radio'][value='day']").attr("checked",true);
        //月份规则下指定从 x 号开始，每 x 天执行一次
        if(monthDay.indexOf("/")>=0){
            $('#day-interval').show();
            //默认选中
            $("select[name='cron[day][use]']").find("option[value='interval']").attr("selected", true);
            var monthDaySplits=monthDay.split("/");
            $("select[name='cron[day][interval][start]']").val(monthDaySplits[0]);
            $("select[name='cron[day][interval][value]']").val(monthDaySplits[1]);
        }else if(monthDay.indexOf(",")>=0){
            //月份规则下指定一天或多天
            $('#day-in').show();
            //默认选中
            $("select[name='cron[day][use]']").find("option[value='in']").attr("selected", true);
            var monthDaySplits=monthDay.split(",");
            formSelects.value('dayInSelect', monthDaySplits);
        }else if(monthDay.indexOf("-")>=0){
            //月份规则下指定多天(范围选择)
            $('#day-between').show();
            //默认选中
            $("select[name='cron[day][use]']").find("option[value='between']").attr("selected", true);
            var monthDaySplits=monthDay.split("-");
            $("select[name='cron[day][between][start]']").val(monthDaySplits[0]);
            $("select[name='cron[day][between][end]']").val(monthDaySplits[1]);
        }else if(monthDay=="L"){
            //月份规则下最后一天
            //默认选中
            $("select[name='cron[day][use]']").find("option[value='last']").attr("selected", true);
        }else if(monthDay=="LW"){
            //月份规则下最后一个工作日
            //默认选中
            $("select[name='cron[day][use]']").find("option[value='lastWorkday']").attr("selected", true);
        }else if(monthDay.indexOf("W")==1){
            //月份规则下指定一天最近的工作日(不能跨月)
            $('#day-recentWorkday').show();
            //默认选中
            $("select[name='cron[day][use]']").find("option[value='recentWorkday']").attr("selected", true);
            $("select[name='cron[day][recentWorkday]']").val(monthDay);
        } else {
            // 默认interval选中
            $("#day-interval").show();
        }
    }else{
        $('#rule-weekday').show();
        $('#rule-day').hide();
        //月份星期规则
        $("input[type='radio'][value='weekday']").attr("checked",true);

        $('[id^="weekday-"]').hide();
        //指定星期范围
        if(weekDay.indexOf("-")>=0){
            $("select[name='cron[weekday][use]']").find("option[value='between']").attr("selected", true);
            $('#weekday-between').show();
            //默认值
            var weekDaySplits=weekDay.split("-");
            $("select[name='cron[weekday][week][start]']").val(weekDaySplits[0]);
            $("select[name='cron[weekday][week][end]']").val(weekDaySplits[1]);
        }else if(weekDay.indexOf(",")>=0){
            //指定星期(可多选)
            $("select[name='cron[weekday][use]']").find("option[value='in']").attr("selected", true);
            $('#weekday-in').show();
            //默认值
            var weekDaySplits=weekDay.split(",");
            formSelects.value('weekInSelect', weekDaySplits);
        }else if(weekDay.indexOf("L")==1){
            //月份规则下最后一个指定的星期
            $("select[name='cron[weekday][use]']").find("option[value='last']").attr("selected", true);
            $('#weekday-last').show();
            //默认值
            $("select[name='cron[weekday][last]']").val(weekDay);
        }else if(weekDay.indexOf("/")>=0){
            //月份规则下第 x 周的星期 x
            $("select[name='cron[weekday][use]']").find("option[value='week']").attr("selected", true);
            $('#weekday-week').show();
            //默认值
            var weekDaySplits=weekDay.split("/");
            $("select[name='cron[weekday][week][at]']").val(weekDaySplits[0]);
            $("select[name='cron[weekday][week][weekday]']").val(weekDaySplits[1]);
        }
    }
    /**月份天数规则**/
    /**具体规则*/
    $("#cron").val(cron);
    $("#cron").attr("title", cronDesc);
    syncCron(null);
    // $('#cronDesc').val(cronDesc);
    $('[data-toggle="tooltip"]').tooltip();
    $('#saveCron').on('click', function() {
        $("#cronTime").val($("#cron").val());
        $("#cronRuleModal").modal('hide');
    });
});