<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
    <meta name="keywords" content="Zeus,采集">
    <meta name="description" content="Zeus数据采集管理平体是一套开源的互联网数据采集管理系统">
    <meta name="author" content="irisroyalty">
    <title>Zeus 数据采集管理平台-采集器设置</title>
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="default">
    <link rel="stylesheet" type="text/css" href="/css/materialdesignicons.min.css">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/animate.min.css">
    <link rel="stylesheet" type="text/css" href="/css/style.min.css">
    <link rel="stylesheet" type="text/css" href="/css/toast.min.css">
    <link rel="stylesheet" type="text/css" href="/css/base.css">
    <link rel="stylesheet" type="text/css" href="/css/crawler.css">
    <link rel="stylesheet" type="text/css" href="https://at.alicdn.com/t/font_1931442_gyy2fbzblp.css">
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
                            <div class="card-body">
                                <ul class="nav nav-step">
                                    <li class="nav-item complete">
                                        <span>任务设置</span>
                                        <a class="nav-link "  href="/task/edit?task_id=${taskId}"></a>
                                    </li>

                                    <li class="nav-item">
                                        <span>采集器设置</span>
                                        <a class="nav-link active"></a>
                                    </li>

                                    <li class="nav-item">
                                        <span>发布设置</span>
                                        <a class="nav-link" href="/task/publish?task_id=${taskId}"></a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div>
                        <ul class="nav nav-tabs page-tabs pt-2 pl-3 pr-3 crawler-nav-tabs">
                            <li class="nav-item"> <a class="nav-link crawler-nav active" for="crawler">采集器设置</a> </li>
                            <li class="nav-item"> <a class="nav-link crawler-nav" for="begin-url">起始页网址</a> </li>
                            <li class="nav-item"> <a class="nav-link crawler-nav" for="content-url">内容页网址</a> </li>
                            <li class="nav-item"> <a class="nav-link crawler-nav" for="crawler-content">获取内容</a> </li>
                        </ul>
                        </div>

                        <div class="crawler-card" id="crawler">
                            <form action="/task/crawler/savecrawler.do" method="post" name="edit-form" class="edit-form" id="taskEditForm">
                            <#if crawlerRule??>
                                <input type="hidden" value="${crawlerRule.id}" id="crawler_id" />
                            </#if>
                            <div class="card">
                                <div class="card-body">
                                    <div class="form-group">
                                        <label for="task_name">采集规则名称</label>
                                        <input type="input" name="crawler_name" class="form-control" id="crawler_name" placeholder="请输入采集规则名称"
                                               <#if crawlerRule??>value="${crawlerRule.cName!''}"</#if>/>
                                    </div>
<#--                                    <div class="form-group">-->
<#--                                        <label for="web_site_title">开启自动采集</label>-->
<#--                                        <div class="form-group">-->
<#--                                            <div class="form-check form-check-inline">-->
<#--                                                <input class="form-check-input" type="radio" name="auto" id="auto-yes" value="1">-->
<#--                                                <label class="form-check-label" for="auto-yes">是</label>-->
<#--                                            </div>-->
<#--                                            <div class="form-check form-check-inline">-->
<#--                                                <input class="form-check-input" type="radio" checked="checked" name="auto" id="auto-no" value="0">-->
<#--                                                <label class="form-check-label" for="auto-no">否</label>-->
<#--                                            </div>-->
<#--                                        </div>-->
<#--                                        <small class="help-block">此设置为总控制开关，如设置“否”则任务中的自动采集设置不生效-->
<#--                                        </small>-->
<#--                                    </div>-->

                                </div>

                            </div>
                            <div class="card">
                                <header class="card-header">
                                    <div class="card-title"><a href="javascript:;" class="card-btn-slide-m ">请求头信息</a></div>
                                    <ul class="card-actions">
                                        <#--                                    <li><a href="#!" class="card-btn-close"><i class="mdi mdi-close"></i></a></li>-->
                                        <li><a href="#!" class="card-btn-slide rotate-180"><i class="mdi mdi-chevron-up"></i></a></li>
                                    </ul>
                                </header>
                                <div class="card-body" style="display: none">
                                    <div class="form-group">
                                        <label for="task_name">UserAgent 浏览器标识</label>
                                        <input type="input" name="user_agent" class="form-control" id="user_agent" placeholder="请输入浏览器标识"
                                               <#if crawlerRule??>value="${crawlerRule.cName!''}"</#if>/>
                                    </div>
                                    <div class="form-group">
                                        <label for="task_name">Refer 来源</label>
                                        <input type="input" name="refer" class="form-control" id="refer" placeholder="请输入Refer来源网址"
                                               <#if crawlerRule??>value="${crawlerRule.cName!''}"</#if>/>
                                    </div>
                                </div>

                            </div>
                            <div class="form-group">
                                <button type="button" class="btn btn-primary m-r-5" id="save">确 定</button>
                                <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
                            </div>
                        </form>
                        </div>
                        <div class="crawler-card d-none" id="begin-url">
                            <form action="/task/crawler/savebeginurl.do" method="post" name="edit-form" class="edit-form" id="beginUrlForm">
                                <input type="hidden" name="taskId" value="${taskId}" />
                                <#if crawlerRule??>
                                    <input type="hidden" value="${crawlerRule.id}" name="crawlerId" />
                                </#if>
                                <div class="card">
                                    <div class="card-body">
                                        <div class="form-group">
                                            <label for="from_url">起始网址
                                                <a href="javascript:;" class="add-field" title="添加" style="margin-left: 5px;"
                                                   data-toggle="modal" data-target="#beginUrlModal"><img src="/imgs/plus.svg" width="18px" height="18px"></a>
                                            </label>
                                            <div id="from-urls">
                                                <input type="hidden" name="fromUrls" class="form-control" value=""/>

                                                <#if crawlerRuleConfig?? && crawlerRuleConfig.fromUrls??>
                                                    <#list crawlerRuleConfig.fromUrls as url>
                                                        <div class="input-group mb-3">
                                                            <input type="input" name="fromUrls" class="form-control" value="${url}" id="${uuid()}"/>
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text url-add"><i class="iconfont iconedit"></i></span>
                                                            </div>
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text url-close"><i class="iconfont iconguanbi"></i> </span>
                                                            </div>
                                                        </div>
                                                    </#list>
                                                <#else >
                                                    <div class="input-group mb-3">
                                                        <input type="input" name="fromUrls" class="form-control" id="${uuid()}"/>
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text url-add"><i class="iconfont iconedit"></i></span>
                                                        </div>
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text url-close"><i class="iconfont iconguanbi"></i> </span>
                                                        </div>
                                                    </div>
                                                </#if>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                           <input type="checkbox" id="source_is_content_check"
                                                   <#if crawlerRuleConfig?? && crawlerRuleConfig.sourceIsContent?? && crawlerRuleConfig.sourceIsContent>
                                                        checked="checked"
                                                   </#if>/>
                                            设置为内容页网址（不选则为列表页）
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button type="button" class="btn btn-primary m-r-5 save" data="begin-url">确 定</button>
                                    <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
                                </div>
                            </form>
                            <div class="modal fade" id="beginUrlModal" tabindex="-1" role="dialog">
                                <div class="modal-dialog modal-lg" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h6 class="modal-title" id="fieldModalTitle">添加起始网址</h6>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <form id="begin-url-modal-content">
                                                <input type="hidden" name="modifyId" id="modifyId">
                                                <ul class="nav nav-tabs nav-fill"  id="begin-url-tab">
                                                    <li class="nav-item">
                                                        <a class="nav-link active" data-toggle="tab" href="#manual-fill" aria-selected="true">手动指定</a>
                                                    </li>
                                                    <li class="nav-item">
                                                        <a class="nav-link" data-toggle="tab" href="#templ-fill" aria-selected="false">批量生成</a>
                                                    </li>
                                                </ul>
                                                <div class="tab-content">
                                                    <div class="begin-content-tab tab-pane fade active show" id="manual-fill" role="tabpanel">
                                                        <span >一行一条列表页网址(http://或https://开头)</span>
                                                        <textarea id="source_urls" class="form-control" rows="5"></textarea>
                                                    </div>
                                                    <div class="begin-content-tab tab-pane fade" id="templ-fill" role="tabpanel">
                                                        <div class="input-group mb-3">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text">网址格式</span>
                                                            </div>
                                                            <input type="input" class="form-control" id="paramUrl" name="paramUrl"/>
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text"><a href="javascript:;" id="addParamsContent">[内容]</a></span>
                                                            </div>
                                                        </div>
                                                        <div class="x-line">将 [内容] 替换成</div>
                                                        <div class="input-group mb-3" source-param="num">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text">
                                                                    <input type="radio" name="paramType" value="num" checked="checked">
                                                                数字
                                                                </span>
                                                            </div>
                                                            <div class="form-control url-line">
                                                                从 <input id="param_num_start" type="number" value="1" class="url-input" >
                                                                到 <input id="param_num_end" type="number" value="10" class="url-input">
                                                                递增数 <input id="param_num_inc" type="number" value="1" class="url-input">
                                                                <label><input type="checkbox" id="param_num_desc"> 倒序</label>
                                                            </div>
                                                        </div>
                                                        <div class="input-group mb-3" source-param="letter">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text">
                                                                    <input type="radio" name="paramType" value="letter" >
                                                                字母
                                                                </span>
                                                            </div>
                                                            <div class="form-control url-line">
                                                                从 <input id="param_letter_start"  value="A" class="url-input" >
                                                                到 <input id="param_letter_end"  value="Z" class="url-input">
                                                                <label><input type="checkbox" id="param_letter_desc"> 倒序</label>
                                                            </div>
                                                        </div>
                                                        <button type="button" class="btn btn-block btn-secondary" id="preview" style="margin-bottom: 10px">预览</button>
                                                        <textarea class="form-control disabled" rows="5" id="source_preview" readonly="readonly"></textarea>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                                            <button type="button" class="btn btn-primary" id="beginUrlSave">保存</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="crawler-card d-none" id="content-url">
                            <form action="/task/crawler/savecontenturl.do" method="post" name="edit-form" class="edit-form" id="contentUrlForm">
                                <input type="hidden" name="taskId" value="${taskId}" />
                                <#if crawlerRule??>
                                    <input type="hidden" value="${crawlerRule.id}" name="crawlerId" />
                                </#if>
                                <div class="card">
                                    <header class="card-header"><div class="card-title">内容页网址获取</div></header>
                                    <div class="card-body">
                                        <div class="form-group">
                                            <label for="url_are">获取网址区域</label>
                                            <textarea name="urlArea" class="form-control" id="url_area"
                                                      placeholder="默认整个页面,示例<div id=&quot;content&quot;>[内容]</div>"><#if crawlerUrlConfig?? && crawlerUrlConfig.contentArea??>${crawlerUrlConfig.contentArea?html}</#if></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="card">
                                    <header class="card-header"><div class="card-title">匹配内容网址</div></header>
                                    <div class="card-body">
                                        <div class="form-group">
                                            <label for="url_are">提取网址规则</label>
                                            <textarea name="contentUrlRule" class="form-control" id="content_url_rule"
                                                      placeholder="默认获取所有链接并保存成[内容]供拼接使用,示例<a href=&quot;http://www.demo.com/[内容1]/[内容2]&quot;>(*)</div>"
                                                      ><#if crawlerUrlConfig?? && crawlerUrlConfig.contentUrlRule??>${crawlerUrlConfig.contentUrlRule?html}</#if></textarea>
                                        </div>
                                        <div class="form-group">
                                            <label for="url_are">拼接成最终网址</label>
                                            <input name="finalMergeUrl" class="form-control" id="final_merge_url"
                                                      placeholder="默认拼接所有[内容]标签,示例:http://www.demo.com/[内容1]-[内容2].html"
                                                      <#if crawlerUrlConfig?? && crawlerUrlConfig.finalMergeRule??>value="${crawlerUrlConfig.finalMergeRule!''}"</#if> ></input>
                                        </div>
                                    </div>
                                </div>

                                <div class="card">
                                    <header class="card-header"><div class="card-title">结果网址过滤</div></header>
                                    <div class="card-body">
                                        <div class="form-group">
                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text">必须包含</span>
                                                </div>
                                                <input type="text" class="form-control" placeholder="可模糊匹配">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text">正则</span>
                                                </div>
                                            </div>
                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text">不能包含</span>
                                                </div>
                                                <input type="text" class="form-control" placeholder="可模糊匹配">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text">正则</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <button type="button" class="btn btn-primary m-r-5 save" data="content-url">确 定</button>
                                    <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
                                </div>
                            </form>
                        </div>
                        <div class="crawler-card d-none" id="crawler-content">
                            <form action="/task/crawler/savecrawlercontent.do" method="post" name="edit-form" class="edit-form" id="crawlerContentForm">
                                <input type="hidden" name="taskId" value="${taskId}" />
                                <#if crawlerRule??>
                                    <input type="hidden" value="${crawlerRule.id}" name="crawlerId" />
                                </#if>
                                <div class="card">
                                    <div class="card-header"><div class="card-title">字段列表<a href="javascript:;" class="add-field" title="添加" style="margin-left: 5px;" data-toggle="modal" data-target="#fieldModal"><img src="/imgs/plus.svg" width="18px" height="18px"></img></a></div></div>
                                    <div class="card-body">
                                        <div class="form-group">
                                            <table class="table table-bordered table-striped">
                                                <thead>
                                                <tr>
                                                    <th>字段</th>
                                                    <th>数据源</th>
                                                    <th>获取方式</th>
                                                    <th>操作</th>
                                                </tr>
                                                </thead>
                                                <tbody id="fieldTable">
                                                <#if fieldList??>
                                                    <#list fieldList as field>
                                                        <#assign id=randomId(field.base64Config)>
                                                        <tr id='${id}'>
                                                            <td>
                                                                <input type="hidden" class='hi-field-v' name="field" value='${field.base64Config}'/>
                                                                <a href="javascript:void(0);" class='field-title' data='${id}'>${field.name}</a></td>
                                                            <td class='tdBelongPage'>${field.belongPage}</td>
                                                            <td>规则匹配</td>
                                                            <td>数据处理<i class="mdi mdi-close-outline" data='${id}'></i></td>
                                                        </tr>
                                                    </#list>
                                                </#if>
                                                </tbody>
                                            </table>
                                        </div>
<#--                                        <div class="form-group">-->
<#--                                            <label for="task_name">采集规则名称4</label>-->
<#--                                            <input type="input" name="crawler_name" class="form-control" id="crawler_name" placeholder="请输入采集规则名称"-->
<#--                                                   <#if crawlerRule??>value="${crawlerRule.cName!''}"</#if>/>-->
<#--                                        </div>-->
                                    </div>

                                </div>
                                <div class="form-group">
                                    <button type="button" class="btn btn-primary m-r-5 save" data="crawler-content">确 定</button>
                                    <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
                                </div>
                            </form>
                            <div class="modal fade" id="fieldModal" tabindex="-1" role="dialog">
                                <div class="modal-dialog modal-lg" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h6 class="modal-title" id="fieldModalTitle">添加字段</h6>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <input type="hidden" id="fieldTableId"/>
                                            <form action="/task/crawler/field/base64" id="fieldAddForm" class="needs-validation" novalidate>
                                                <div class="form-group">
                                                    <div class="input-group mb-3">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text">字段名称</span>
                                                        </div>
                                                        <input type="text" class="form-control" name="fieldName" id="field_name" required>
                                                        <div class="invalid-tooltip">
                                                            请输入有效的字段名称
                                                        </div>
                                                        <div class="input-group-prepend">
                                                            <select class="form-control" id="belong_page" name="belongPage">
                                                                <optgroup label="选择数据源">
                                                                    <option value="default">默认页</option>
                                                                    <option value="frompage">起始页</option>
                                                                </optgroup>
                                                            </select>
                                                            <div class="invalid-tooltip">
                                                                请选择有效的数据源！
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="task_name">字段规则</label>
                                                    <div class="input-group mb-3">
                                                        <textarea type="input" class="form-control" id="field_rule" name="fieldRule"
                                                                  placeholder='示例：<div id="a">[内容1]</div>(*)<div id="b">[内容2]</div>'
                                                                  required></textarea>
                                                        <div class="invalid-tooltip">
                                                            请输入有效的数据规则！
                                                        </div>
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text">正则</span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="task_name">拼接成最终内容</label>
                                                    <div class="input-group mb-3">
                                                        <input type="input" class="form-control" id="field_final_marge" name="finalMerge"
                                                                  placeholder='默认拼接所有[内容]标签，示例：[内容1] [内容2]'/>
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text">正则</span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <input type="checkbox" id="multi_check" name="multi"/>
                                                    允许匹配多个元素
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                                            <button type="button" class="btn btn-primary" id="fieldSave">保存</button>
                                        </div>
                                    </div>
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
<script type="text/javascript">
    ;jQuery(function() {
        let nav = $("#nav-task-list");
        if (!nav) return;
        nav.parent('li').addClass('active');
        nav.parents('.nav-item-has-subnav').addClass('open').first().addClass('active');
    });
</script>
<script type="text/javascript" src="/js/lib/uuid.min.js"></script>
<script type="text/javascript" src="/js/lib/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/lib/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/js/lib/jquery.cookie.min.js"></script>
<script type="text/javascript" src="/js/lib/bootstrap-notify.min.js"></script>
<script type="text/javascript" src="/js/lib/toast.min.js"></script>
<script type="text/javascript" src="/js/main.min.js" ></script>
<script type="text/javascript" src="/js/index.min.js"></script>
<script type="text/javascript" src="/js/lib/jquery.form.js"></script>
<script type="text/javascript" src="/js/crawler.js"></script>
<script type="text/javascript" src="/js/lib/base64.min.js"></script>
</body>
</html>