

<div class="modal fade" id="cronRuleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h6 class="modal-title" id="crawlerTitle">定时规则生成</h6>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

                <div class="form-group">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text">执行时间表达式</span>
                        </div>
                        <input class="form-control"
                               data-toggle="tooltip" data-placement="bottom"
                               id="cron" autocomplete="off" type="text" disabled>
                    </div>
                </div>
                <input type="hidden" id="cronDesc" />
                <form id="cronForm">

<div class="form-group">
    <div class="input-group mb-3">
        <div class="input-group-prepend">
            <span class="input-group-text">每天执行</span>
        </div>
        <div class="form-control">
            <input type="radio" name="rule[enable][daily]" lay-filter="cron-daily" value="yes" title="是"> 是
            <input type="radio" name="rule[enable][daily]" lay-filter="cron-daily"  value="no" title="否"> 否
        </div>
    </div>
</div>
<div id="ruleOther" style="display: none">
    <div class="form-group">
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">月份规则</span>
            </div>
            <div>
                <select class="form-control" name="cron[month][use]" lay-filter="month-rule">
                    <option value="every">每月</option>
                    <option value="in">多选月份</option>
                    <option value="between">指定月份范围</option>
                    <option value="interval">从 x 月开始执行，之后每间 x 月执行一次</option>
                </select>
                <input type="hidden" name="cron[month][every]" value="?">
            </div>
            <div class="layui-input-inline" id="month-in" style="display: none">
                <div class="layui-hide">
                    <select name="cron[month][in][]" class="selectpicker" multiple>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                    </select>
                </div>
                <div id="multi-month" readonly class="layui-laydate-main"></div>
            </div>
            <div class="cron-input-group" id="month-between" style="display:none;">
                <div class="layui-input-inline" style="width: 66px;margin-right: 0;">
                    <select name="cron[month][between][start]" lay-search="" class="selectpicker input-group-btn">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                    </select>
                </div>
                <span class="select-text">至</span>
                <div class="layui-input-inline" style="width: 80px;margin-right: 0;">
                    <select name="cron[month][between][end]" lay-search="" class="selectpicker input-group-btn">
                        <option value="1">1月</option>
                        <option value="2">2月</option>
                        <option value="3">3月</option>
                        <option value="4">4月</option>
                        <option value="5">5月</option>
                        <option value="6">6月</option>
                        <option value="7">7月</option>
                        <option value="8">8月</option>
                        <option value="9">9月</option>
                        <option value="10">10月</option>
                        <option value="11">11月</option>
                        <option value="12">12月</option>
                    </select>
                </div>
            </div>
            <div class="cron-input-group" id="month-interval" style="display: none">
                <span class="select-text">从</span>
                <div class="layui-input-inline input-group-btn">
                    <select class="selectpicker input-group-btn" name="cron[month][interval][start]" lay-search="">
                        <option value="1">1月</option>
                        <option value="2">2月</option>
                        <option value="3">3月</option>
                        <option value="4">4月</option>
                        <option value="5">5月</option>
                        <option value="6">6月</option>
                        <option value="7">7月</option>
                        <option value="8">8月</option>
                        <option value="9">9月</option>
                        <option value="10">10月</option>
                        <option value="11">11月</option>
                        <option value="12">12月</option>
                    </select>
                </div>
                <span class="select-text">开始，每</span>
                <div class="layui-input-inline" style="width: 66px;margin-right: 0;">
                    <select  class="selectpicker input-group-btn" name="cron[month][interval][value]" lay-search="">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                    </select>
                </div>
                <span class="select-text">个月(不能跨年)</span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">特殊规则</span>
            </div>
            <div class="form-control">
                <input type="radio" name="rule[enable][dayWeekday]" lay-filter="dayWeekday" checked value="day" title="月份天数规则">月份天数规则
                <input type="radio" name="rule[enable][dayWeekday]" lay-filter="dayWeekday" value="weekday" title="月份星期规则">月份星期规则
            </div>
        </div>
    </div>
    <div class="form-group" id="rule-weekday" style="display: none;margin-left: 20px">
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">星期规则</span>
            </div>
            <div class="layui-input-inline">
                <select  class="form-control" name="cron[weekday][use]" lay-filter="weekday-rule" style="width: 260px">
                    <option value="between">指定星期范围</option>
                    <option value="in">指定星期(可多选)</option>
                    <option value="last">月份规则下最后一个指定的星期</option>
                    <option value="week">月份规则下第 x 周的星期 x</option>
                </select>
            </div>
            <div class="cron-input-group" id="weekday-in" style="display: none">
                <span class="select-text">星期</span>
                <div class="layui-input-inline">
                    <select name="cron[weekday][in][]" class="selectpicker" xm-select="weekInSelect" multiple>
                        <option value="2">一</option>
                        <option value="3">二</option>
                        <option value="4">三</option>
                        <option value="5">四</option>
                        <option value="6">五</option>
                        <option value="7">六</option>
                        <option value="1">日</option>
                    </select>
                </div>
            </div>
            <div class="cron-input-group" id="weekday-between">
                <div class="layui-input-inline" style="width: 90px;margin-right: 0;">
                    <select name="cron[weekday][between][start]" class="selectpicker input-group-btn">
                        <option value="2">星期一</option>
                        <option value="3">星期二</option>
                        <option value="4">星期三</option>
                        <option value="5">星期四</option>
                        <option value="6">星期五</option>
                        <option value="7">星期六</option>
                        <option value="1">星期日</option>
                    </select>
                </div>
                <span class="select-text">至</span>
                <div class="layui-input-inline" style="width: 90px;margin-right: 0;">
                    <select name="cron[weekday][between][end]"  class="selectpicker input-group-btn">
                        <option value="2">星期一</option>
                        <option value="3">星期二</option>
                        <option value="4">星期三</option>
                        <option value="5">星期四</option>
                        <option value="6">星期五</option>
                        <option value="7">星期六</option>
                        <option value="1">星期日</option>
                    </select>
                </div>
            </div>
            <div class="cron-input-group" id="weekday-last" style="display: none">
                <span class="select-text">月份规则下最后一个</span>
                <div class="layui-input-inline" style="width: 90px;margin-right: 0;">
                    <select name="cron[weekday][last]" class="selectpicker input-group-btn">
                        <option value="2L">星期一</option>
                        <option value="3L">星期二</option>
                        <option value="4L">星期三</option>
                        <option value="5L">星期四</option>
                        <option value="6L">星期五</option>
                        <option value="7L">星期六</option>
                        <option value="1L">星期日</option>
                    </select>
                </div>
            </div>
            <div class="cron-input-group" id="weekday-week" style="display: none">
                <span class="select-text">月份规则下</span>
                <div class="layui-input-inline" style="width: 90px;margin-right: 0;">
                    <select name="cron[weekday][week][at]" class="selectpicker input-group-btn">
                        <option value="1">第 1 周</option>
                        <option value="2">第 2 周</option>
                        <option value="3">第 3 周</option>
                        <option value="4">第 4 周</option>
                        <option value="5">第 5 周</option>
                    </select>
                </div>
                <span class="select-text">的</span>
                <div class="layui-input-inline" style="width: 90px;margin-right: 0;">
                    <select name="cron[weekday][week][weekday]" class="selectpicker input-group-btn">
                        <option value="2">星期一</option>
                        <option value="3">星期二</option>
                        <option value="4">星期三</option>
                        <option value="5">星期四</option>
                        <option value="6">星期五</option>
                        <option value="7">星期六</option>
                        <option value="1">星期日</option>
                    </select>
                </div>
            </div>
        </div>
    </div>
<#--    月份天数规则-->
    <div class="form-group" id="rule-day" style="margin-left: 20px">
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">天数规则</span>
            </div>
            <div class="layui-input-inline" style="width: 300px">
                <select class="form-control" name="cron[day][use]" lay-filter="day-rule">
                    <option value="interval">月份规则下指定从 x 号开始，每 x 天执行一次</option>
                    <option value="in">月份规则下指定一天或多天</option>
                    <option value="between">月份规则下指定多天(范围选择)</option>
                    <option value="last">月份规则下最后一天</option>
                    <option value="lastWorkday">月份规则下最后一个工作日</option>
                    <option value="recentWorkday">月份规则下指定一天最近的工作日(不能跨月)</option>
                </select>
            </div>
            <input type="hidden" name="cron[day][last]" value="L">
            <input type="hidden" name="cron[day][lastWorkday]" value="LW">
            <div class="cron-input-group" id="day-in" style="display: none">
                <span class="select-text">月份规则的</span>
                <div class="layui-input-inline" style="margin-left: 10px;">
                    <select name="cron[day][in][]" xm-select="dayInSelect" class="selectpicker" multiple>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                    </select>
                </div>
                <span class="select-text">号</span>
            </div>
            <div class="cron-input-group" id="day-recentWorkday" style="display: none">
                <span class="select-text">月份规则</span>
                <div class="input-group-btn">
                    <select name="cron[day][recentWorkday]" class="selectpicker input-group-btn">
                        <option value="1W">1</option>
                        <option value="2W">2</option>
                        <option value="3W">3</option>
                        <option value="4W">4</option>
                        <option value="5W">5</option>
                        <option value="6W">6</option>
                        <option value="7W">7</option>
                        <option value="8W">8</option>
                        <option value="9W">9</option>
                        <option value="10W">10</option>
                        <option value="11W">11</option>
                        <option value="12W">12</option>
                        <option value="13W">13</option>
                        <option value="14W">14</option>
                        <option value="15W">15</option>
                        <option value="16W">16</option>
                        <option value="17W">17</option>
                        <option value="18W">18</option>
                        <option value="19W">19</option>
                        <option value="20W">20</option>
                        <option value="21W">21</option>
                        <option value="22W">22</option>
                        <option value="23W">23</option>
                        <option value="24W">24</option>
                        <option value="25W">25</option>
                        <option value="26W">26</option>
                        <option value="27W">27</option>
                        <option value="28W">28</option>
                        <option value="29W">29</option>
                        <option value="30W">30</option>
                        <option value="31W">31</option>
                    </select>
                </div>
                <span class="select-text">号最近的工作日(不能跨月)</span>
            </div>
            <div class="cron-input-group" id="day-between" style="display: none">
                <span class="select-text">月份规则从</span>
                <div class="layui-input-inline">
                    <select name="cron[day][between][start]" class="selectpicker input-group-btn">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                    </select>
                </div>
                <span class="select-text">号到</span>
                <div class="layui-input-inline" style="width: 80px;margin-right: 0;">
                    <select name="cron[day][between][end]" class="selectpicker input-group-btn">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                    </select>
                </div>
                <span class="select-text">号</span>
            </div>
            <div class="cron-input-group" id="day-interval" style="margin-left:10px">
                <span class="select-text">月份规则从</span>
                <div class="layui-input-inline">
                    <select name="cron[day][interval][start]" class="selectpicker input-group-btn">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                    </select>
                </div>
                <span class="select-text">号开始，每</span>
                <div class="layui-input-inline" style="width: 80px;margin-right: 0;">
                    <select name="cron[day][interval][value]" class="selectpicker input-group-btn">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                    </select>
                </div>
                <span class="select-text">天</span>
            </div>
        </div>
    </div>
</div>
<div id="ruleEveryDay" style="display: none;">
<#--    小时规则-->
    <div class="form-group">
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">小时规则</span>
            </div>
            <div class="">
                <select class="form-control" name="cron[hour][use]" lay-filter="hour-rule">
                    <option value="at">指定小时</option>
                    <option value="in">指定小时(可多选)</option>
                    <option value="between">指定小时范围(0-23)</option>
                    <option value="interval">从 x 小时开始执行，之后每 x 小时执行一次</option>
                </select>
            </div>
            <div class="cron-input-group" id="hour-at">
                <span class="select-text">于</span>
                <div class="layui-input-inline" style="">
                    <select class="selectpicker" lay-filter="hour-every" name="cron[hour][at]" lay-search="">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                    </select>
                </div>
                <span class="select-text">时</span>
            </div>
            <div class="cron-input-group" id="hour-in" style="display: none;">
                <span class="select-text">第</span>
                <div class="layui-input-inline">
                    <select name="cron[hour][in][]" id="hourInSelect" multiple class="selectpicker">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                    </select>
                </div>
                <span class="select-text">小时</span>
            </div>
            <div class="cron-input-group"" id="hour-between" style="display: none;">
                <span class="select-text">第</span>
                <div class="layui-input-inline" style="width: 66px;margin-right: 0;">
                    <select name="cron[hour][between][start]" lay-search="" class="selectpicker input-group-btn">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                    </select>
                </div>
                <span class="select-text">至</span>
                <div class="layui-input-inline" style="width: 66px;margin-right: 0;">
                    <select name="cron[hour][between][end]" lay-search="" class="selectpicker input-group-btn">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                    </select>
                </div>
                <span class="select-text">小时</span>
            </div>
            <div class="cron-input-group" id="hour-interval" style="display: none;">
                <span class="select-text">从</span>
                <div class="layui-input-inline" style="width: 66px;margin-right: 0;">
                    <select lay-filter="hour-interval-start" name="cron[hour][interval][start]" lay-search="" class="selectpicker input-group-btn">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                    </select>
                </div>
                <span class="select-text">小时开始，每</span>
                <div class="layui-input-inline" style="width: 66px;margin-right: 0;">
                    <select layer-filter="hour-interval-value" name="cron[hour][interval][value]" lay-search=""  class="selectpicker input-group-btn">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                    </select>
                </div>
                <span class="select-text">小时</span>
            </div>
        </div>
    </div>
<#--    分钟规则-->
    <div class="form-group">
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">分钟规则</span>
            </div>
            <div class="">
                <select class="form-control" name="cron[minute][use]" lay-filter="minute-rule">
                    <option value="at">指定分钟</option>
                    <option value="in">指定分钟(可多选)</option>
                    <option value="between">指定分钟范围(0-59)</option>
                    <option value="interval">从 x 分钟开始执行，之后每间 x 分钟执行一次</option>
                </select>
            </div>
            <div class="cron-input-group" id="minute-at">
                <span class="select-text">&nbsp;&nbsp;的</span>
                <div class="layui-input-inline" style="width: 66px;margin-right: 0;">
                    <select lay-filter="minute-at" name="cron[minute][at]" lay-search=""  class="selectpicker">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                        <option value="32">32</option>
                        <option value="33">33</option>
                        <option value="34">34</option>
                        <option value="35">35</option>
                        <option value="36">36</option>
                        <option value="37">37</option>
                        <option value="38">38</option>
                        <option value="39">39</option>
                        <option value="40">40</option>
                        <option value="41">41</option>
                        <option value="42">42</option>
                        <option value="43">43</option>
                        <option value="44">44</option>
                        <option value="45">45</option>
                        <option value="46">46</option>
                        <option value="47">47</option>
                        <option value="48">48</option>
                        <option value="49">49</option>
                        <option value="50">50</option>
                        <option value="51">51</option>
                        <option value="52">52</option>
                        <option value="53">53</option>
                        <option value="54">54</option>
                        <option value="55">55</option>
                        <option value="56">56</option>
                        <option value="57">57</option>
                        <option value="58">58</option>
                        <option value="59">59</option>
                    </select>
                </div>
                <span class="select-text">分</span>
            </div>
            <div class="cron-input-group" id="minute-in" style="display:none;">
                <span class="select-text">第</span>
                <div class="layui-input-inline" >
                    <select name="cron[minute][in][]" xm-select="minuteInSelect"  class="selectpicker" multiple>
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                        <option value="32">32</option>
                        <option value="33">33</option>
                        <option value="34">34</option>
                        <option value="35">35</option>
                        <option value="36">36</option>
                        <option value="37">37</option>
                        <option value="38">38</option>
                        <option value="39">39</option>
                        <option value="40">40</option>
                        <option value="41">41</option>
                        <option value="42">42</option>
                        <option value="43">43</option>
                        <option value="44">44</option>
                        <option value="45">45</option>
                        <option value="46">46</option>
                        <option value="47">47</option>
                        <option value="48">48</option>
                        <option value="49">49</option>
                        <option value="50">50</option>
                        <option value="51">51</option>
                        <option value="52">52</option>
                        <option value="53">53</option>
                        <option value="54">54</option>
                        <option value="55">55</option>
                        <option value="56">56</option>
                        <option value="57">57</option>
                        <option value="58">58</option>
                        <option value="59">59</option>
                    </select>
                </div>
                <span class="select-text">分钟</span>
            </div>
            <div class="cron-input-group" id="minute-between" style="display:none;">
                <span class="select-text">第</span>
                <div class="layui-input-inline" style="width: 66px;margin-right: 0;">
                    <select name="cron[minute][between][start]" lay-search="" class="selectpicker input-group-btn">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                        <option value="32">32</option>
                        <option value="33">33</option>
                        <option value="34">34</option>
                        <option value="35">35</option>
                        <option value="36">36</option>
                        <option value="37">37</option>
                        <option value="38">38</option>
                        <option value="39">39</option>
                        <option value="40">40</option>
                        <option value="41">41</option>
                        <option value="42">42</option>
                        <option value="43">43</option>
                        <option value="44">44</option>
                        <option value="45">45</option>
                        <option value="46">46</option>
                        <option value="47">47</option>
                        <option value="48">48</option>
                        <option value="49">49</option>
                        <option value="50">50</option>
                        <option value="51">51</option>
                        <option value="52">52</option>
                        <option value="53">53</option>
                        <option value="54">54</option>
                        <option value="55">55</option>
                        <option value="56">56</option>
                        <option value="57">57</option>
                        <option value="58">58</option>
                        <option value="59">59</option>
                    </select>
                </div>
                <span class="select-text">至</span>
                <div class="layui-input-inline" style="width: 66px;margin-right: 0;">
                    <select name="cron[minute][between][end]" lay-search=""  class="selectpicker input-group-btn">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                        <option value="32">32</option>
                        <option value="33">33</option>
                        <option value="34">34</option>
                        <option value="35">35</option>
                        <option value="36">36</option>
                        <option value="37">37</option>
                        <option value="38">38</option>
                        <option value="39">39</option>
                        <option value="40">40</option>
                        <option value="41">41</option>
                        <option value="42">42</option>
                        <option value="43">43</option>
                        <option value="44">44</option>
                        <option value="45">45</option>
                        <option value="46">46</option>
                        <option value="47">47</option>
                        <option value="48">48</option>
                        <option value="49">49</option>
                        <option value="50">50</option>
                        <option value="51">51</option>
                        <option value="52">52</option>
                        <option value="53">53</option>
                        <option value="54">54</option>
                        <option value="55">55</option>
                        <option value="56">56</option>
                        <option value="57">57</option>
                        <option value="58">58</option>
                        <option value="59">59</option>
                    </select>
                </div>
                <span class="select-text">分钟</span>
            </div>
            <div class="cron-input-group" id="minute-interval" style="display: none;">
                <span class="select-text">从</span>
                <div class="layui-input-inline" style="width: 66px;margin-right: 0;">
                    <select name="cron[minute][interval][start]" lay-search="" class="selectpicker input-group-btn">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                        <option value="32">32</option>
                        <option value="33">33</option>
                        <option value="34">34</option>
                        <option value="35">35</option>
                        <option value="36">36</option>
                        <option value="37">37</option>
                        <option value="38">38</option>
                        <option value="39">39</option>
                        <option value="40">40</option>
                        <option value="41">41</option>
                        <option value="42">42</option>
                        <option value="43">43</option>
                        <option value="44">44</option>
                        <option value="45">45</option>
                        <option value="46">46</option>
                        <option value="47">47</option>
                        <option value="48">48</option>
                        <option value="49">49</option>
                        <option value="50">50</option>
                        <option value="51">51</option>
                        <option value="52">52</option>
                        <option value="53">53</option>
                        <option value="54">54</option>
                        <option value="55">55</option>
                        <option value="56">56</option>
                        <option value="57">57</option>
                        <option value="58">58</option>
                        <option value="59">59</option>
                    </select>
                </div>
                <span class="select-text">分钟开始，每</span>
                <div class="layui-input-inline" style="width: 66px;margin-right: 0;">
                    <select name="cron[minute][interval][value]" lay-search="" class="selectpicker input-group-btn">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                        <option value="32">32</option>
                        <option value="33">33</option>
                        <option value="34">34</option>
                        <option value="35">35</option>
                        <option value="36">36</option>
                        <option value="37">37</option>
                        <option value="38">38</option>
                        <option value="39">39</option>
                        <option value="40">40</option>
                        <option value="41">41</option>
                        <option value="42">42</option>
                        <option value="43">43</option>
                        <option value="44">44</option>
                        <option value="45">45</option>
                        <option value="46">46</option>
                        <option value="47">47</option>
                        <option value="48">48</option>
                        <option value="49">49</option>
                        <option value="50">50</option>
                        <option value="51">51</option>
                        <option value="52">52</option>
                        <option value="53">53</option>
                        <option value="54">54</option>
                        <option value="55">55</option>
                        <option value="56">56</option>
                        <option value="57">57</option>
                        <option value="58">58</option>
                        <option value="59">59</option>
                    </select>
                </div>
                <span class="select-text">分钟</span>
            </div>
        </div>
    </div>
<#--    秒规则-->
    <div class="form-group">
<#--        <label class="layui-form-label">秒规则</label>-->
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">秒规则</span>
            </div>
            <div class="">
                <select class="form-control" name="cron[seconds][use]" lay-filter="seconds-rule">
                    <option value="at">指定秒</option>
                    <option value="in">指定秒(可多选)</option>
                    <option value="between">指定秒范围(0-59)</option>
                    <option value="interval">从 x 秒开始执行，之后每 x 秒执行一次</option>
                </select>
            </div>
            <div class="cron-input-group" id="seconds-at">
                <span class="">&nbsp;&nbsp;于</span>
                <div class="" >
                    <select name="cron[seconds][at]" lay-search="" class="selectpicker">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                        <option value="32">32</option>
                        <option value="33">33</option>
                        <option value="34">34</option>
                        <option value="35">35</option>
                        <option value="36">36</option>
                        <option value="37">37</option>
                        <option value="38">38</option>
                        <option value="39">39</option>
                        <option value="40">40</option>
                        <option value="41">41</option>
                        <option value="42">42</option>
                        <option value="43">43</option>
                        <option value="44">44</option>
                        <option value="45">45</option>
                        <option value="46">46</option>
                        <option value="47">47</option>
                        <option value="48">48</option>
                        <option value="49">49</option>
                        <option value="50">50</option>
                        <option value="51">51</option>
                        <option value="52">52</option>
                        <option value="53">53</option>
                        <option value="54">54</option>
                        <option value="55">55</option>
                        <option value="56">56</option>
                        <option value="57">57</option>
                        <option value="58">58</option>
                        <option value="59">59</option>
                    </select>
                </div>
                <span class="select-text">秒</span>
            </div>
            <div class="cron-input-group" id="seconds-in" style="display:none;">
                <span class="select-text">第</span>
                <div class="layui-input-inline">
                    <select name="cron[seconds][in][]" xm-select="secondsInSelect" class="selectpicker" multiple>
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                        <option value="32">32</option>
                        <option value="33">33</option>
                        <option value="34">34</option>
                        <option value="35">35</option>
                        <option value="36">36</option>
                        <option value="37">37</option>
                        <option value="38">38</option>
                        <option value="39">39</option>
                        <option value="40">40</option>
                        <option value="41">41</option>
                        <option value="42">42</option>
                        <option value="43">43</option>
                        <option value="44">44</option>
                        <option value="45">45</option>
                        <option value="46">46</option>
                        <option value="47">47</option>
                        <option value="48">48</option>
                        <option value="49">49</option>
                        <option value="50">50</option>
                        <option value="51">51</option>
                        <option value="52">52</option>
                        <option value="53">53</option>
                        <option value="54">54</option>
                        <option value="55">55</option>
                        <option value="56">56</option>
                        <option value="57">57</option>
                        <option value="58">58</option>
                        <option value="59">59</option>
                    </select>
                </div>
                <span class="select-text">秒</span>
            </div>
            <div class="cron-input-group" id="seconds-between" style="display: none">
                <span class="select-text">第</span>
                <div class="layui-input-inline" style="width: 66px;margin-right: 0;">
                    <select name="cron[seconds][between][start]" lay-search="" class="selectpicker input-group-btn">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                        <option value="32">32</option>
                        <option value="33">33</option>
                        <option value="34">34</option>
                        <option value="35">35</option>
                        <option value="36">36</option>
                        <option value="37">37</option>
                        <option value="38">38</option>
                        <option value="39">39</option>
                        <option value="40">40</option>
                        <option value="41">41</option>
                        <option value="42">42</option>
                        <option value="43">43</option>
                        <option value="44">44</option>
                        <option value="45">45</option>
                        <option value="46">46</option>
                        <option value="47">47</option>
                        <option value="48">48</option>
                        <option value="49">49</option>
                        <option value="50">50</option>
                        <option value="51">51</option>
                        <option value="52">52</option>
                        <option value="53">53</option>
                        <option value="54">54</option>
                        <option value="55">55</option>
                        <option value="56">56</option>
                        <option value="57">57</option>
                        <option value="58">58</option>
                        <option value="59">59</option>
                    </select>
                </div>
                <span class="select-text">至</span>
                <div class="layui-input-inline" style="width: 66px;margin-right: 0;">
                    <select name="cron[seconds][between][end]" lay-search="" class="selectpicker input-group-btn">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                        <option value="32">32</option>
                        <option value="33">33</option>
                        <option value="34">34</option>
                        <option value="35">35</option>
                        <option value="36">36</option>
                        <option value="37">37</option>
                        <option value="38">38</option>
                        <option value="39">39</option>
                        <option value="40">40</option>
                        <option value="41">41</option>
                        <option value="42">42</option>
                        <option value="43">43</option>
                        <option value="44">44</option>
                        <option value="45">45</option>
                        <option value="46">46</option>
                        <option value="47">47</option>
                        <option value="48">48</option>
                        <option value="49">49</option>
                        <option value="50">50</option>
                        <option value="51">51</option>
                        <option value="52">52</option>
                        <option value="53">53</option>
                        <option value="54">54</option>
                        <option value="55">55</option>
                        <option value="56">56</option>
                        <option value="57">57</option>
                        <option value="58">58</option>
                        <option value="59">59</option>
                    </select>
                </div>
                <span class="select-text">秒</span>
            </div>
            <div class="cron-input-group" id="seconds-interval" style="display: none">
                <span class="select-text">从</span>
                <div class="layui-input-inline" style="width: 66px;margin-right: 0;">
                    <select name="cron[seconds][interval][start]" lay-search="" class="selectpicker input-group-btn">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                        <option value="32">32</option>
                        <option value="33">33</option>
                        <option value="34">34</option>
                        <option value="35">35</option>
                        <option value="36">36</option>
                        <option value="37">37</option>
                        <option value="38">38</option>
                        <option value="39">39</option>
                        <option value="40">40</option>
                        <option value="41">41</option>
                        <option value="42">42</option>
                        <option value="43">43</option>
                        <option value="44">44</option>
                        <option value="45">45</option>
                        <option value="46">46</option>
                        <option value="47">47</option>
                        <option value="48">48</option>
                        <option value="49">49</option>
                        <option value="50">50</option>
                        <option value="51">51</option>
                        <option value="52">52</option>
                        <option value="53">53</option>
                        <option value="54">54</option>
                        <option value="55">55</option>
                        <option value="56">56</option>
                        <option value="57">57</option>
                        <option value="58">58</option>
                        <option value="59">59</option>
                    </select>
                </div>
                <span class="select-text">秒开始，每</span>
                <div class="layui-input-inline" style="width: 66px;margin-right: 0;">
                    <select name="cron[seconds][interval][value]" lay-search="" class="selectpicker input-group-btn">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                        <option value="32">32</option>
                        <option value="33">33</option>
                        <option value="34">34</option>
                        <option value="35">35</option>
                        <option value="36">36</option>
                        <option value="37">37</option>
                        <option value="38">38</option>
                        <option value="39">39</option>
                        <option value="40">40</option>
                        <option value="41">41</option>
                        <option value="42">42</option>
                        <option value="43">43</option>
                        <option value="44">44</option>
                        <option value="45">45</option>
                        <option value="46">46</option>
                        <option value="47">47</option>
                        <option value="48">48</option>
                        <option value="49">49</option>
                        <option value="50">50</option>
                        <option value="51">51</option>
                        <option value="52">52</option>
                        <option value="53">53</option>
                        <option value="54">54</option>
                        <option value="55">55</option>
                        <option value="56">56</option>
                        <option value="57">57</option>
                        <option value="58">58</option>
                        <option value="59">59</option>
                    </select>
                </div>
                <span class="select-text">秒</span>
            </div>
        </div>
    </div>
</div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="saveCron">保存</button>
            </div>
        </div>
    </div>
</div>