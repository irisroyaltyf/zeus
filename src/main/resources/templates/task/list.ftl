<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
    <meta name="keywords" content="Zeus,采集">
    <meta name="description" content="Zeus数据采集管理平体是一套开源的互联网数据采集管理系统">
    <meta name="author" content="irisroyalty">
    <title>Zeus 数据采集管理平台-任务列表</title>
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="default">
    <link rel="stylesheet" type="text/css" href="/css/materialdesignicons.min.css">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/animate.min.css">
    <link rel="stylesheet" type="text/css" href="/css/style.min.css">
    <link rel="stylesheet" type="text/css" href="/css/base.css">
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
                        <div class="card">
                            <header class="card-header"><div class="card-title">任务列表</div></header>
                            <div class="card-body">
                                <table class="table table-bordered table-striped">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>任务名称</th>
                                        <th>采集时间</th>
                                        <th>下次采集时间</th>
                                        <th>自动采集</th>
                                        <th>添加时间</th>
                                        <th>任务分组</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#list tasks as taski>
                                    <tr>
                                        <td scope="row">${taski_index}</td>
                                        <td><a href="/task/edit?task_id=${taski.id}"> ${taski.gettName()}</a></td>
                                        <td><#if taski.lastCaiji==0>无<#else >${taski.lastCaiji?number_to_datetime}</#if>
                                            <a href="javascript:;" class="collect" data="${taski.id}">采集</a>
                                        </td>
                                        <td><#if taski.nextTime==0>无<#else>${taski.nextTime?number_to_datetime}</#if></td>
                                        <td>
                                            <#if taski.tAuto?? && taski.tAuto == 1>
                                                <span class="task-auto">是</span>
                                                &nbsp;&nbsp;&nbsp;<a class="auto_collect" href="javascript:;" data-id="${taski.id}" data-now="1">关闭</a>
                                            <#else >
                                                <span class="task-auto">否</span>
                                                &nbsp;&nbsp;&nbsp;<a class="auto_collect" href="javascript:;" data-id="${taski.id}" data-now="0">开启</a>
                                            </#if>
                                        </td>
                                        <td>${taski.gmtCreate?number_to_datetime}</td>
                                        <td>${taski.groupId!''}</td>
                                        <td>
                                            <a href="/task/crawler?task_id=${taski.id}">采集规则</a>
                                            <span class="sep">|</span>
                                            <a href="/task/publish?task_id=${taski.id}">发布</a>
                                            <span class="sep">|</span>
                                            <a href="javascript:;" class="delete" item-id="${taski.id}">删除</a>
                                        </td>
                                    </tr>
                                    </#list>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>

            </div>
        </main>

        <div class="modal fade" id="crawlerModel" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h6 class="modal-title" id="crawlerTitle">正在采集</h6>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div id="crawlerResultContent" data-spy="scroll" class="scrollspy"></div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
<#--                        <button type="button" class="btn btn-primary">保存</button>-->
                    </div>
                </div>
            </div>
        </div>
        <!--End 页面主要内容-->

    </div>
</div>

<script type="text/javascript" src="/js/lib/jquery.min.js"></script>
<script type="text/javascript">
    ;jQuery(function() {
        let nav = $("#nav-task-list");
        if (!nav) return;
        nav.parent('li').addClass('active');
        nav.parents('.nav-item-has-subnav').addClass('open').first().addClass('active');
    });
</script>
<script type="text/javascript" src="/js/lib/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/lib/bootstrap-notify.min.js"></script>
<script type="text/javascript" src="/js/lib/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/js/lib/jquery.cookie.min.js"></script>
<script type="text/javascript" src="/js/index.min.js"></script>
<script type="text/javascript" src="/js/task.js"></script>
</body>
</html>