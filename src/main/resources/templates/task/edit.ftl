<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
    <meta name="keywords" content="Zeus,采集">
    <meta name="description" content="Zeus数据采集管理平体是一套开源的互联网数据采集管理系统">
    <meta name="author" content="irisroyalty">
    <#if task??>
        <title>Zeus 数据采集管理平台-任务设置</title>
    <#else >
        <title>Zeus 数据采集管理平台-添加任务</title>
    </#if>
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="default">
    <link rel="stylesheet" type="text/css" href="/css/materialdesignicons.min.css">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/animate.min.css">
    <link rel="stylesheet" type="text/css" href="/css/style.min.css">
    <link rel="stylesheet" type="text/css" href="/css/base.css">
    <link rel="stylesheet" type="text/css" href="/css/task.css">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap-select-min.css">
</head>

<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">

        <#--       引入左侧导航-->
        <#include "../menu/left.ftl" />
        <#include "../header/header.ftl" />

        <!--页面主要内容-->
        <main class="lyear-layout-content">
            <div class="container-fluid p-t-15">
                <div class="row">
                    <div class="col-lg-12">
                        <#if task??>
                            <div class="card">
                                <div class="card-body">
                                    <ul class="nav nav-step">
                                        <li class="nav-item">
                                            <span>任务设置</span>
                                            <a class="nav-link active" href=""></a>
                                        </li>

                                        <li class="nav-item">
                                            <span>采集器设置</span>
                                            <a class="nav-link" href="/task/crawler?task_id=${task.id}"></a>
                                        </li>

                                        <li class="nav-item">
                                            <span>发布设置</span>
                                            <a class="nav-link"  href="/task/publish?task_id=${task.id}"></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </#if>
                        <form action="/task/addorupdate.do" method="post" name="edit-form" class="edit-form" id="taskEditForm">
                            <#if task??>
                                <input type="hidden" name="taskId" value="${task.id}" id="task_id" />
                            </#if>
                            <div class="card">
                                <header class="card-header">
                                    <#if task?? >
                                        <div class="card-title">编辑任务</div></header>
                                    <#else>
                                        <div class="card-title">添加任务</div></header>
                                    </#if>
                                <div class="card-body">
                                        <div class="form-group">
                                            <label for="task_name">任务名称</label>
                                            <input type="input" name="taskName" class="form-control" id="task_name" placeholder="请输入任务名称"
                                            <#if task??>value="${task.gettName()}"</#if>/>
                                        </div>
                                        <div class="form-group">
                                            <label >开启自动采集</label>
                                            <div class="form-group">
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" <#if task?? && task.tAuto==1>checked="checked"</#if> name="auto" id="auto-yes" value="1">
                                                    <label class="form-check-label" for="auto-yes">是</label>
                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" <#if task?? && task.tAuto==0>checked="checked"</#if> name="auto" id="auto-no" value="0">
                                                    <label class="form-check-label" for="auto-no">否</label>
                                                </div>
                                            </div>
                                            <small class="help-block">此设置任务自动采集开关，如果总设置自动采集为“否”则任务中的自动采集设置不生效<a href="/setting">去设置</a>
                                            </small>
                                        </div>
                                    <div class="form-group">

                                        <label for="cronTime">自动执行时间</label>
                                        <input id="cronTime" name="cronTime" class="form-control" value="<#if task?? && task.cron??>${task.cron}</#if>"/>
                                    </div>
                                </div>

                            </div>
                            <div class="card">
                                <header class="card-header">
                                    <div class="card-title"><a href="javascript:;" class="card-btn-slide-m">更多设置</a></div>
                                    <ul class="card-actions">
    <#--                                    <li><a href="#!" class="card-btn-close"><i class="mdi mdi-close"></i></a></li>-->
                                        <li><a href="#!" class="card-btn-slide"><i class="mdi mdi-chevron-up"></i></a></li>
                                    </ul>
                                </header>
                                <div class="card-body" style="display: none">
                                    <div class="form-group">
                                        <label for="web_site_title">使用代理</label>
                                        <div class="form-group">
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="proxy" id="proxy-default" value="0">
                                                <label class="form-check-label" for="proxy-default">默认</label>
                                            </div>
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="proxy" id="proxy-yes" value="1">
                                                <label class="form-check-label" for="proxy-yes">是</label>
                                            </div>
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="auto" id="proxy-no" value="0">
                                                <label class="form-check-label" for="proxy-no">否</label>
                                            </div>
                                        </div>
                                        <small class="help-block">默认设置由采集设置中代理开关决定是否开启代理
                                        </small>
                                    </div>
                                </div>

                            </div>
                            <div class="form-group">
                                <button type="button" class="btn btn-primary m-r-5" id="save">确 定</button>
                                <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
                            </div>
                        </form>
                    </div>

                </div>

            </div>
        </main>
        <!--End 页面主要内容-->
    </div>
</div>

<#include "../cron/cron.ftl" />

<script type="text/javascript" src="/js/lib/jquery.min.js"></script>
<script type="text/javascript" src="/js/lib/jquery.serializejson.min.js"></script>

<script type="text/javascript">
    ;jQuery(function() {
        let nav = $("#nav-task-add");
        if (!nav) return;
        nav.parent('li').addClass('active');
        nav.parents('.nav-item-has-subnav').addClass('open').first().addClass('active');
    });
</script>
<script type="text/javascript" src="/js/lib/popper.min.js"></script>
<script type="text/javascript" src="/js/lib/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/lib/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/js/lib/jquery.cookie.min.js"></script>
<script type="text/javascript" src="/js/lib/bootstrap-notify.min.js"></script>
<script type="text/javascript" src="/js/main.min.js" ></script>
<script type="text/javascript" src="/js/index.min.js"></script>
<script type="text/javascript" src="/js/lib/jquery.form.js"></script>
<script type="text/javascript" src="/js/lib/bootstrp-select-min.js"></script>
<script type="text/javascript" src="/js/task.js"></script>
<script type="text/javascript" src="/js/cron.js"></script>
<script type="text/javascript" >
    ;jQuery(function() {
        $("#cronTime").on('click', function () {
            $("#cronRuleModal").modal('show');
        })
    });
</script>

</body>
</html>