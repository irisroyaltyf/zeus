<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
    <meta name="keywords" content="Zeus,采集">
    <meta name="description" content="Zeus数据采集管理平体是一套开源的互联网数据采集管理系统">
    <meta name="author" content="irisroyalty">
    <title>Zeus 数据采集管理平台-发布数据设置</title>
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="default">
    <link rel="stylesheet" type="text/css" href="/css/materialdesignicons.min.css">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/animate.min.css">
    <link rel="stylesheet" type="text/css" href="/css/style.min.css">
    <link rel="stylesheet" type="text/css" href="/css/base.css">
    <link rel="stylesheet" type="text/css" href="/css/publish.css">
</head>

<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">

        <#--       引入左侧导航-->
        <#include "../menu/left.ftl" />
        <#include "../header/header.ftl" />

        <input type="hidden" name="taskId" value="${taskId}" id="task_id" />
        <#if publishRule??>
            <input type="hidden" value="${publishRule.id}" id="publish_id" />
        </#if>
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
                                        <a class="nav-link" href="/task/edit?task_id=${taskId}"></a>
                                    </li>

                                    <li class="nav-item complete">
                                        <span>采集器设置</span>
                                        <a class="nav-link" href="/task/crawler?task_id=${taskId}"></a>
                                    </li>

                                    <li class="nav-item">
                                        <span>发布设置</span>
                                        <a class="nav-link active" ></a>
                                    </li>
                                </ul>
                            </div>
                        </div>

                        <div class="card">
                            <header class="card-header">
                                <div class="card-title">发布设置</div></header>
                            <div class="card-body">
                                <div class="form-group">
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">发布方式</span>
                                        </div>
                                        <select class="form-control" id="publishType">
                                            <option value="0">选择发布方式</option>
                                            <option value="1" <#if publishRule?? && publishRule.type == 1> selected="selected"</#if> >文件存储</option>
                                            <option value="2" <#if publishRule?? && publishRule.type == 2> selected="selected"</#if>>数据库</option>
                                        </select>
                                        <div class="invalid-tooltip">
                                            请选择发布方式！
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div id="publish-file" class="publish-type <#if publishRule?? && publishRule.type == 1><#else>d-none</#if>">
                            <div class="card">
                                <div class="card-body">
                                    <form action="/task/publish/addorupdate.do" method="post" name="edit-form" class="edit-form" id="publishRuleForm">
                                        <input type="hidden" name="taskId" value="${taskId}" />
                                        <input type="hidden" name="publishType" value="1">
                                        <#if publishRule??>
                                            <input type="hidden" value="${publishRule.id}"  name="publishId" />
                                        </#if>
                                        <div class="form-group">
                                            <label for="fileLocation">存储目录</label>
                                            <input type="input" name="fileLocation" class="form-control" id="fileLocation"
                                                   placeholder="存储目录默认是当前目录"
                                                  <#if ruleConfig?? && ruleConfig.fileLocation??> value="${ruleConfig.fileLocation}" </#if> />
                                        </div>
                                        <div class="form-group">
                                            <label for="fileType">文件格式</label>
                                            <div class="custom-control">
                                                <div class="custom-control custom-radio custom-control-inline">
                                                    <input type="radio" name="fileType"
                                                           <#if ruleConfig?? && ruleConfig.fileType ?? && ruleConfig.fileType == "txt">checked="checked"</#if>
                                                           class="custom-control-input" value="txt" id="radio_f_txt">
                                                    <label class="custom-control-label" for="radio_f_txt">txt文本格式</label>
                                                </div>
                                                <div class="custom-control custom-radio custom-control-inline">
                                                    <input type="radio" name="fileType"
                                                           <#if ruleConfig?? && ruleConfig.fileType ?? && ruleConfig.fileType == "xls">checked="checked"</#if>
                                                           class="custom-control-input" value="xls" id="radio_f_xls">
                                                   <label class="custom-control-label" for="radio_f_xls">Excel2003(.xls)</label>
                                                </div>
                                                <div class="custom-control custom-radio custom-control-inline">
                                                    <input type="radio" name="fileType"
                                                           <#if ruleConfig?? && ruleConfig.fileType ?? && ruleConfig.fileType == "xlsx">checked="checked"</#if>
                                                           class="custom-control-input" value="xlsx" id="radio_f_xlsx">
                                                    <label class="custom-control-label" for="radio_f_xlsx">Excel2007(.xlsx)</label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="excludeField">隐藏采集字段</label>
                                            <div class="custom-control">
                                                <#if fieldList??>
                                                    <#list fieldList as field>
                                                    <div class="custom-control custom-checkbox custom-control-inline">
                                                        <input type="checkbox" class="custom-control-input" name="excludeField" value="${field.name}" id="che_${field_index}" >
                                                            <label class="custom-control-label" for="che_${field_index}" > ${field.name}</label>
                                                    </div>
                                                    </#list>
                                                </#if>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="form-group">
                                <button type="button" class="btn btn-primary m-r-5" id="save">确 定</button>
                                <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
                            </div>
                        </div>
                        <div id="publish-db" class="publish-type <#if publishRule?? && publishRule.type == 2><#else>d-none</#if>">
                            <div>
                                <ul class="nav nav-tabs page-tabs pt-2 pl-3 pr-3 publish-nav-tabs">
                                    <li class="nav-item"> <a class="nav-link publish-nav <#if !dbStep??||dbStep==1>active</#if>" for="db-config">数据库配置</a> </li>
                                    <li class="nav-item"> <a class="nav-link publish-nav <#if dbStep??&&dbStep==2>active</#if>" for="db-table">数据表字段设置</a> </li>
                                </ul>
                            </div>

                            <div class="publish-card <#if dbStep??&&dbStep!=1>d-none"</#if> id="db-config">
                                <div class="card">
                                    <div class="card-body">
                                        <form action="/task/publish/addorupdate.do" method="post" name="edit-form" class="edit-form" id="dbConfigForm">
                                            <input type="hidden" name="publishType" value="2">
                                            <input type="hidden" value="1" name="dbStep"/>
                                            <input type="hidden" name="taskId" value="${taskId}" />
                                            <#if publishRule??>
                                                <input type="hidden" value="${publishRule.id}" name="publishId" />
                                            </#if>
                                            <div class="form-group">
                                                <label for="db-type">数据库类型</label>
                                                <select class="form-control" id="db-type" name="dbType">
                                                    <option value="1">mysql</option>
        <#--                                                <option value="2" <#if publishRule?? && publishRule.type == 2> selected="selected"</#if>>数据库</option>-->
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label for="fileLocation">数据库主机</label>
                                                <input type="input" name="host" class="form-control" id="db-host"
                                                        <#if ruleConfig?? && ruleConfig.connectionConfig??> value="${ruleConfig.connectionConfig.host}"<#else> value="localhost"</#if> />
                                            </div>
                                            <div class="form-group">
                                                <label for="fileLocation">数据库端口</label>
                                                <input type="input" name="port" class="form-control" id="db-port"
                                                    <#if ruleConfig?? && ruleConfig.connectionConfig??> value="${ruleConfig.connectionConfig.port}"<#else> value="3306"</#if> />
                                            </div>
                                            <div class="form-group">
                                                <label for="fileLocation">数据库用户</label>
                                                <input type="input" name="user" class="form-control" id="db-user"
                                                    <#if ruleConfig?? && ruleConfig.connectionConfig??> value="${ruleConfig.connectionConfig.user}"</#if> />
                                            </div>
                                            <div class="form-group">
                                                <label for="fileLocation">数据库密码</label>
                                                <input type="password" name="pwd" class="form-control" id="db-pwd"
                                                    <#if ruleConfig?? && ruleConfig.connectionConfig??>
                                                        value='${ruleConfig.connectionConfig.pwd}'</#if> />
                                            </div>
                                            <div class="form-group">
                                                <label for="fileLocation">数据库名称</label>
                                                <div class="input-group mb-3">
                                                    <input type="input" name="schema" class="form-control" id="db-schema"
                                                        <#if ruleConfig?? && ruleConfig.connectionConfig??> value="${ruleConfig.connectionConfig.schema}"</#if> />
                                                    <div class="input-group-prepend">
                                                        <button class="btn btn-secondary btn-w-md" type="button" id="schema_info">选择数据库</button>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="fileLocation">数据库编码</label>
                                                <input type="input" name="charset" class="form-control" id="db-charset"
                                                       placeholder="默认编码为utf8"
                                                        <#if ruleConfig?? && ruleConfig.connectionConfig??> value="${ruleConfig.connectionConfig.charset}"</#if> />
                                            </div>
                                            <button class="btn btn-secondary btn-w-xl" type="button" id="test_connect">测试连接到数据库</button>
                                            <div id="test_feedback"></div>
                                        </form>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button type="button" class="btn btn-primary m-r-5" id="saveDbConfig">确 定</button>
                                    <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
                                </div>
                            </div>
                            <div class="publish-card <#if !dbStep??||dbStep!=2>d-none</#if>" id="db-table">
                                <div class="card">
                                    <div class="card-body">
                                        <#if dbError??>
                                           <span id="db-error-area">${dbError}</span>
                                        <#else >
                                            <form action="/task/publish/fieldbind.do" id="fieldBindForm">
                                                <input type="hidden" name="taskId" value="${taskId}" />
                                                <#if publishRule??>
                                                    <input type="hidden" value="${publishRule.id}"  name="publishRuleId" />
                                                </#if>
                                                <div class="form-group">
                                                    <div class="input-group mb-3">
                                                        <select class="form-control" id="db-select-table">
                                                            <option value="0">请选择数据表</option>
                                                            <#if tableNames??>
                                                                <#list tableNames as tableName>
                                                                    <option value="${tableName}">${tableName}</option>
                                                                </#list>
                                                            </#if>
                                                        </select>
                                                        <div class="input-group-prepend">
                                                            <button class="btn btn-secondary btn-w-md" type="button" id="bind-table">绑定数据</button>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="table-field-config">
                                                    <#if fieldConfig?? && fieldConfig.tableFields??>
                                                        <#list fieldConfig.tableFields as tableConfig>
                                                            <div id="table_bind_${tableConfig.tableName}" class="table-bind-field">
                                                                <span class="d-block p-2 bg-primary text-white">${tableConfig.tableName} 表绑定数据
                                                                        <i class="mdi mdi-close-outline publish-config-close" data="table_bind_${tableConfig.tableName}"></i></span>
                                                                <table class="table table-striped">
                                                                    <thead>
                                                                    <tr>
                                                                        <th>数据库字段</th>
                                                                        <th>类型</th>
                                                                        <th>绑定数据</th>
                                                                    </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    <#list tableConfig.fieldBind?keys as mykey>
                                                                        <tr>
                                                                            <td>
                                                                                <p>${mykey}</p>
                                                                            </td>
                                                                            <td>${tableConfig.forShow["${mykey}"].type}</td>
                                                                            <td>
                                                                                <select class="select-bind-field custom-select" name="dbTableField[${tableConfig.tableName}][${mykey}]"] onchange="fieldSelectChange(this)">
                                                                                    <option value="0" >不绑定</option>
                                                                                    <#if fieldList??>
                                                                                        <#list fieldList as field>
                                                                                            <option value="field:${field.name}"
                                                                                                    <#if tableConfig.fieldBind["${mykey}"]?length gt 6 && tableConfig.fieldBind["${mykey}"]?substring(6)==field.name > selected="selected"</#if>>采集字段:${field.name}</option>
                                                                                        </#list>
                                                                                    </#if>
                                                                                    <option value="custom:" <#if tableConfig.fieldBind["${mykey}"]?starts_with("custom:")>selected="selected"</#if> >自定义内容</option>
                                                                                </select>

                                                                                <#if tableConfig.fieldBind["${mykey}"]?? && tableConfig.fieldBind["${mykey}"]?starts_with("custom:")>
                                                                                    <input class="form-control custom-input" name="dbTableCustom[${tableConfig.tableName}][${mykey}]"
                                                                                           value='${tableConfig.fieldBind["${mykey}"]?substring(7)}'/>
                                                                                <#else >
                                                                                    <input class="form-control custom-input d-none" name="dbTableCustom[${tableConfig.tableName}][${mykey}]"/>
                                                                                </#if>
                                                                            </td>
                                                                        </tr>
                                                                    </#list>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </#list>
                                                    </#if>
                                                </div>
                                            </form>
                                        </#if>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button type="button" class="btn btn-primary m-r-5" id="saveDBTableConfig">确 定</button>
                                    <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
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


<div class="modal fade" id="schema-info-model" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h6 class="modal-title" id="exampleModalChangeTitle">选择数据库</h6>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <div class="input-group mb-3">
                        <select class="form-control" id="schema-names" >
                            <optgroup id="schema-names-group" label="请选择数据库">
                            </optgroup>
                        </select>
                        <div class="input-group-prepend">
                            <button class="btn btn-secondary btn-w-sm" type="button" id="schema_info_sure">确定</button>
                        </div>
                    </div>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script type="text/j-template" id="template-table-field">
    <div id="table_bind_<%=tableName%>" class="table-bind-field">
        <span class="d-block p-2 bg-primary text-white"><%=tableName%> 表绑定数据
            <i class="mdi mdi-close-outline publish-config-close" data="table_bind_<%=tableName%>"></i></span>
        <table class="table table-striped">
            <thead>
              <tr>
                <th>数据库字段</th>
                <th>类型</th>
                <th>绑定数据</th>
              </tr>
            </thead>
            <tbody>
                <% if(dataset.length){ %>
                    <% _.each(dataset,function(item){ %>
                        <tr>
                            <td>
                                <p><%= item.columnName %></p>
                            </td>
                            <td><%= item.type %></td>
                            <td>
                                <select class="select-bind-field custom-select" name="dbTableField[<%=tableName%>][<%=item.columnName%>]" onchange="fieldSelectChange(this)"]>
                                    <option value="0">不绑定</option>
                                    <#if fieldList??>
                                    <#list fieldList as field>
                                        <option value="field:${field.name}">采集字段:${field.name}</option>
                                    </#list>
                                    </#if>
                                    <option value="custom:">自定义内容</option>
                                </select>
                                <input class="form-control custom-input d-none" name="dbTableCustom[<%= tableName %>][<%= item.columnName%>]"/>
                            </td>
                        </tr>
                    <% }) %>
                <% }else{ %>
                    <tr><td class="txtCenter" colspan="6">暂无数据</td></tr>
                <% } %>
            </tbody>
          </table>
    </div>
</script>
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
<script type="text/javascript" src="/js/lib/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/js/lib/jquery.cookie.min.js"></script>
<script type="text/javascript" src="/js/lib/underscope.min.1.10.2.js"></script>
<script type="text/javascript" src="/js/lib/bootstrap-notify.min.js"></script>
<script type="text/javascript" src="/js/main.min.js" ></script>
<script type="text/javascript" src="/js/index.min.js"></script>
<script type="text/javascript" src="/js/lib/jquery.form.js"></script>
<script type="text/javascript" src="/js/publish.js"></script>
</body>
</html>