<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
    <meta name="keywords" content="Zeus,采集">
    <meta name="description" content="Zeus数据采集管理平体是一套开源的互联网数据采集管理系统">
    <meta name="author" content="irisroyalty">
    <title>Zeus 数据采集管理平台-采集数据列表</title>
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="default">
    <link rel="stylesheet" type="text/css" href="/css/materialdesignicons.min.css">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/animate.min.css">
    <link rel="stylesheet" type="text/css" href="/css/style.min.css">
    <link rel="stylesheet" type="text/css" href="/css/base.css">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap-table.css">
    <link rel="stylesheet" type="text/css" href="/css/jquery-confirm.min.css">

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
                            <header class="card-header"><div class="card-title">已采集数据列表</div></header>
                            <div class="card-body">
                                <div class="alert alert-warning" role="alert">已采集网址可防止重复采集，如果删除，将可能导致数据重复采集！
                                    <button type="button" id="btn_clear_error" class="btn btn-sm btn-warning" style="padding:2px 10px;">一键清理失败的数据</button>
                                </div>
                                <div id="toolbar" class="toolbar-btn-action">
                                    <button id="btn_delete" type="button" class="btn btn-danger">
                                        <span class="mdi mdi-window-close" aria-hidden="true"></span>删除
                                    </button>
                                </div>
                                <table id="tb_collecteds"></table>
                            </div>
                        </div>
                    </div>

                </div>

            </div>
        </main>

    </div>
</div>

<script type="text/javascript" src="/js/lib/jquery.min.js"></script>
<script type="text/javascript">
    ;jQuery(function() {
        let nav = $("#nav-collected-list");
        if (!nav) return;
        nav.parent('li').addClass('active');
        nav.parents('.nav-item-has-subnav').addClass('open').first().addClass('active');
    });
</script>
<script type="text/javascript" src="/js/lib/popper.min.js"></script>
<script type="text/javascript" src="/js/lib/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/lib/jquery.cookie.min.js"></script>
<script type="text/javascript" src="/js/lib/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/js/lib/bootstrap-table.min.js"></script>
<script type="text/javascript" src="/js/lib/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="/js/lib/jquery-confirm.min.js"></script>
<script type="text/javascript" src="/js/index.min.js"></script>
<script type="text/javascript" src="/js/collected.js"></script>
</body>
</html>