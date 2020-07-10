<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
    <meta name="keywords" content="Zeus,采集">
    <meta name="description" content="Zeus数据采集管理平体是一套开源的互联网数据采集管理系统">
    <meta name="author" content="irisroyalty">
    <title>Zeus 数据采集管理平台</title>
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="default">
    <link rel="stylesheet" type="text/css" href="css/materialdesignicons.min.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<#--    <link rel="stylesheet" type="text/css" href="css/bootstrap-multitabs/multitabs.min.css">-->
    <link rel="stylesheet" type="text/css" href="css/animate.min.css">
    <link rel="stylesheet" type="text/css" href="css/style.min.css">
    <link rel="stylesheet" type="text/css" href="css/base.css">
</head>

<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">

        <#--       引入左侧导航-->
        <#include "./menu/left.ftl" />
        <#include "./header/header.ftl" />

        <!--页面主要内容-->
        <main class="lyear-layout-content">
            <div class="container-fluid p-t-15">

                <div class="row">

                    <div class="col-lg-12">
                        <div class="card">
                            <ul class="nav nav-tabs">
                                <li class="nav-item"> <a href="#base-setting" data-toggle="tab" class="nav-link active">基本</a> </li>
                                <li class="nav-item"> <a href="#image-setting" data-toggle="tab" class="nav-link">图片设置</a> </li>
                                <li class="nav-item"> <a href="#up-setting" data-toggle="tab" class="nav-link">上传</a> </li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active show" id="base-setting" role="tabpanel">
                                    <form action="/config.do" method="post" name="edit-form" class="edit-form" id="config-form">
                                        <div class="form-group">
                                            <label for="web_site_title">遵守robots协议</label>
                                            <div class="form-group">
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio"
                                                          <#if robots?? && robots=="1"> checked="checked"</#if> name="robots" id="robots-yes" value="1">
                                                    <label class="form-check-label" for="robots-yes">是</label>
                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio"
                                                            <#if robots?? && robots=="0"> checked="checked"</#if>name="robots" id="robots-no" value="0">
                                                    <label class="form-check-label" for="robots-no">否</label>
                                                </div>
                                            </div>
                                            <small class="help-block">严格按照目标网站的robots.txt设置爬取数据，避免采集到隐私、侵权等具有争议性的内容
                                                <a href="https://www.baidu.com/s?wd=robots%E5%8D%8F%E8%AE%AE" target="_blank">了解robots协议</a>
                                            </small>
                                        </div>
                                        <div class="form-group">
                                            <label for="web_site_title">开启自动采集</label>
                                            <div class="form-group">
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio"
                                                            <#if auto?? && auto=="1"> checked="checked"</#if>name="auto" id="auto-yes" value="1">
                                                    <label class="form-check-label" for="auto-yes">是</label>
                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio"
                                                            <#if auto?? && auto=="0"> checked="checked"</#if>name="auto" id="auto-no" value="0">
                                                    <label class="form-check-label" for="auto-no">否</label>
                                                </div>
                                            </div>
                                            <small class="help-block">此设置为总控制开关，如设置“否”则任务中的自动采集设置不生效
                                            </small>
                                        </div>

                                        <div class="form-group">
                                            <button type="button" class="btn btn-primary m-r-5" id="config-save">确 定</button>
                                            <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
                                        </div>
                                    </form>
                                </div>
                                <div class="tab-pane" id="image-setting" role="tabpanel">
                                    <form action="/config/image.do" method="post" name="edit-form" class="edit-form" id="image-form">
                                        <div class="form-group">
                                            <label for="web_site_title">开启图片转存</label>
                                            <div class="form-group">
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio"
                                                            <#if !imageConfig?? || (imageConfig.imageTransfer?? && imageConfig.imageTransfer==0)>
                                                                checked="checked"
                                                            </#if>
                                                            name="imageTransfer" id="image-no" value="0">
                                                    <label class="form-check-label label-cursor" for="image-no"
                                                           data-toggle="tooltip" data-placement="bottom"
                                                           data-original-title="什么都不做, 还是原来的图片URL">什么都不做</label>
                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio"
                                                            <#if  imageConfig?? && imageConfig.imageTransfer?? && imageConfig.imageTransfer==1> checked="checked"</#if>
                                                           name="imageTransfer" id="image-to-local" value="1" lay-for="#image-config-local">
                                                    <label class="form-check-label label-cursor" for="image-to-local"
                                                           data-toggle="tooltip" data-placement="bottom"
                                                           data-original-title="图片存储到本地,图片src会被替换">存储到本地</label>
                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio"
                                                            <#if imageConfig?? && imageConfig.imageTransfer?? && imageConfig.imageTransfer==2> checked="checked"</#if>
                                                           name="imageTransfer" id="image-to-cloud" value="2" lay-for="#image-config-cloud">
                                                    <label class="form-check-label label-cursor" for="image-to-cloud"
                                                           data-toggle="tooltip" data-placement="bottom"
                                                           data-original-title="图片存储到云上, src替换成云上src">存储第三方云</label>
                                                </div>
                                            </div>
                                            <div id="image-config-local" class="image-config
                                            <#if !imageConfig?? || (imageConfig.imageTransfer?? && imageConfig.imageTransfer!=1)>d-none</#if>">
                                                <div class="form-group">
                                                    <label for="url_are">
                                                        图片保存目录（绝对路径）
                                                    </label>
                                                    <input name="imageDir" class="form-control" id="image_dir"
                                                           <#if imageConfig?? && imageConfig.imageDir??>value="${imageConfig.imageDir}"</#if>
                                                           placeholder="默认/data/zeus"/>
                                                    <small class="help-block">可设置任意文件夹（必须开启读写权限）
                                                    </small>
                                                </div>
                                                <div class="form-group">
                                                    <label for="url_are">
                                                        图片链接地址（绝对地址）
                                                    </label>
                                                    <input name="imageUrl" class="form-control" id="image_dir"
                                                           <#if imageConfig?? && imageConfig.imageUrl??>value="${imageConfig.imageUrl}"</#if>
                                                           placeholder="默认http://localhost:8080/data/images"/>
                                                    <small class="help-block">可设置任意文件夹（必须开启读写权限）
                                                    </small>
                                                </div>
                                                <div class="form-group">
                                                    <label for="url_are">
                                                        图片名称
                                                    </label>
                                                    <input name="image_name" class="form-control" id="image_name" value="暂时使用时间日期命名" disabled/>
                                                    <small class="help-block">可设置任意文件夹（必须开启读写权限）
                                                    </small>
                                                </div>
                                            </div>
                                            <div id="image-config-cloud" class="image-config d-none">
暂未实现

                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <button type="button" class="btn btn-primary m-r-5" id="image-save">确 定</button>
                                            <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
                                        </div>
                                    </form>
                                </div>
                                <div class="tab-pane" id="up-setting" role="tabpanel">
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

            </div>
        </main>
        <!--End 页面主要内容-->
    </div>
</div>

<script type="text/javascript" src="/js/lib/jquery.min.js"></script>
<script type="text/javascript" src="/js/lib/popper.min.js"></script>
<script type="text/javascript">
    ;jQuery(function() {
        let nav = $("#nav-main");
        if (!nav) return;
        nav.parent('li').addClass('active');
        nav.parents('.nav-item-has-subnav').addClass('open').first().addClass('active');

        $(function () {
            $('[data-toggle="tooltip"]').tooltip()
        })
    });
</script>
<script type="text/javascript" src="/js/lib/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/lib/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/js/lib/jquery.cookie.min.js"></script>
<script type="text/javascript" src="/js/lib/bootstrap-notify.min.js"></script>
<script type="text/javascript" src="/js/lib/jquery.form.js"></script>
<script type="text/javascript" src="/js/index.min.js"></script>
<script type="text/javascript" src="/js/setting.js"></script>
</body>
</html>