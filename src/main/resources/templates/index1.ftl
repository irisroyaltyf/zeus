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
    <link rel="stylesheet" type="text/css" href="css/bootstrap-multitabs/multitabs.min.css">
    <link rel="stylesheet" type="text/css" href="css/animate.min.css">
    <link rel="stylesheet" type="text/css" href="css/style.min.css">
</head>

<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">

<#--       引入左侧导航-->
        <#include "./menu/left.ftl">
        <!--头部信息-->
        <header class="lyear-layout-header">

            <nav class="navbar">

                <div class="navbar-left">
                    <div class="lyear-aside-toggler">
                        <span class="lyear-toggler-bar"></span>
                        <span class="lyear-toggler-bar"></span>
                        <span class="lyear-toggler-bar"></span>
                    </div>
                </div>

                <ul class="navbar-right d-flex align-items-center">
                    <li class="dropdown dropdown-notice">
            <span data-toggle="dropdown" class="icon-item">
              <i class="mdi mdi-bell-outline"></i>
              <span class="badge badge-danger">10</span>
            </span>
                        <div class="dropdown-menu dropdown-menu-right">
                            <div class="lyear-notifications">

                                <div class="lyear-notifications-title clearfix"  data-stopPropagation="true"><a href="#!" class="float-right">查看全部</a>你有 10 条未读消息</div>
                                <div class="lyear-notifications-info lyear-scroll">
                                    <a href="#!" class="dropdown-item" title="树莓派销量猛增，疫情期间居家工作学习、医疗领域都需要它">树莓派销量猛增，疫情期间居家工作学习、医疗领域都需要它</a>
                                    <a href="#!" class="dropdown-item" title="GNOME 用户体验团队将为 GNOME Shell 提供更多改进">GNOME 用户体验团队将为 GNOME Shell 提供更多改进</a>
                                    <a href="#!" class="dropdown-item" title="Linux On iPhone 即将面世，支持 iOS 的双启动">Linux On iPhone 即将面世，支持 iOS 的双启动</a>
                                    <a href="#!" class="dropdown-item" title="GitHub 私有仓库完全免费面向团队提供">GitHub 私有仓库完全免费面向团队提供</a>
                                    <a href="#!" class="dropdown-item" title="Wasmtime 为 WebAssembly 增加 Go 语言绑定">Wasmtime 为 WebAssembly 增加 Go 语言绑定</a>
                                    <a href="#!" class="dropdown-item" title="红帽借“订阅”成开源一哥，首创者 Cormier 升任总裁">红帽借“订阅”成开源一哥，首创者 Cormier 升任总裁</a>
                                    <a href="#!" class="dropdown-item" title="Zend 宣布推出两项 PHP 新产品">Zend 宣布推出两项 PHP 新产品</a>
                                </div>

                            </div>
                        </div>
                    </li>
                    <!--切换主题配色-->
                    <li class="dropdown dropdown-skin">
                        <span data-toggle="dropdown" class="icon-item"><i class="mdi mdi-palette"></i></span>
                        <ul class="dropdown-menu dropdown-menu-right" data-stopPropagation="true">
                            <li class="drop-title"><p>LOGO</p></li>
                            <li class="drop-skin-li clearfix">
                <span class="inverse">
                  <input type="radio" name="logo_bg" value="default" id="logo_bg_1" checked>
                  <label for="logo_bg_1"></label>
                </span>
                                <span>
                  <input type="radio" name="logo_bg" value="color_2" id="logo_bg_2">
                  <label for="logo_bg_2"></label>
                </span>
                                <span>
                  <input type="radio" name="logo_bg" value="color_3" id="logo_bg_3">
                  <label for="logo_bg_3"></label>
                </span>
                                <span>
                  <input type="radio" name="logo_bg" value="color_4" id="logo_bg_4">
                  <label for="logo_bg_4"></label>
                </span>
                                <span>
                  <input type="radio" name="logo_bg" value="color_5" id="logo_bg_5">
                  <label for="logo_bg_5"></label>
                </span>
                                <span>
                  <input type="radio" name="logo_bg" value="color_6" id="logo_bg_6">
                  <label for="logo_bg_6"></label>
                </span>
                                <span>
                  <input type="radio" name="logo_bg" value="color_7" id="logo_bg_7">
                  <label for="logo_bg_7"></label>
                </span>
                                <span>
                  <input type="radio" name="logo_bg" value="color_8" id="logo_bg_8">
                  <label for="logo_bg_8"></label>
                </span>
                            </li>
                            <li class="drop-title"><p>头部</p></li>
                            <li class="drop-skin-li clearfix">
                <span class="inverse">
                  <input type="radio" name="header_bg" value="default" id="header_bg_1" checked>
                  <label for="header_bg_1"></label>                      
                </span>
                                <span>
                  <input type="radio" name="header_bg" value="color_2" id="header_bg_2">
                  <label for="header_bg_2"></label>                      
                </span>
                                <span>
                  <input type="radio" name="header_bg" value="color_3" id="header_bg_3">
                  <label for="header_bg_3"></label>
                </span>
                                <span>
                  <input type="radio" name="header_bg" value="color_4" id="header_bg_4">
                  <label for="header_bg_4"></label>                      
                </span>
                                <span>
                  <input type="radio" name="header_bg" value="color_5" id="header_bg_5">
                  <label for="header_bg_5"></label>                      
                </span>
                                <span>
                  <input type="radio" name="header_bg" value="color_6" id="header_bg_6">
                  <label for="header_bg_6"></label>                      
                </span>
                                <span>
                  <input type="radio" name="header_bg" value="color_7" id="header_bg_7">
                  <label for="header_bg_7"></label>
                </span>
                                <span>
                  <input type="radio" name="header_bg" value="color_8" id="header_bg_8">
                  <label for="header_bg_8"></label>
                </span>
                            </li>
                            <li class="drop-title"><p>侧边栏</p></li>
                            <li class="drop-skin-li clearfix">
                <span class="inverse">
                  <input type="radio" name="sidebar_bg" value="default" id="sidebar_bg_1" checked>
                  <label for="sidebar_bg_1"></label>
                </span>
                                <span>
                  <input type="radio" name="sidebar_bg" value="color_2" id="sidebar_bg_2">
                  <label for="sidebar_bg_2"></label>
                </span>
                                <span>
                  <input type="radio" name="sidebar_bg" value="color_3" id="sidebar_bg_3">
                  <label for="sidebar_bg_3"></label>
                </span>
                                <span>
                  <input type="radio" name="sidebar_bg" value="color_4" id="sidebar_bg_4">
                  <label for="sidebar_bg_4"></label>
                </span>
                                <span>
                  <input type="radio" name="sidebar_bg" value="color_5" id="sidebar_bg_5">
                  <label for="sidebar_bg_5"></label>
                </span>
                                <span>
                  <input type="radio" name="sidebar_bg" value="color_6" id="sidebar_bg_6">
                  <label for="sidebar_bg_6"></label>
                </span>
                                <span>
                  <input type="radio" name="sidebar_bg" value="color_7" id="sidebar_bg_7">
                  <label for="sidebar_bg_7"></label>
                </span>
                                <span>
                  <input type="radio" name="sidebar_bg" value="color_8" id="sidebar_bg_8">
                  <label for="sidebar_bg_8"></label>
                </span>
                            </li>
                        </ul>
                    </li>
                    <!--切换主题配色-->
                    <li class="dropdown dropdown-profile">
                        <a href="javascript:void(0)" data-toggle="dropdown" class="dropdown-toggle">
                            <img class="img-avatar img-avatar-48 m-r-10" src="images/users/avatar.jpg" alt="笔下光年" />
                            <span>笔下光年</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <a class="multitabs dropdown-item" data-url="lyear_pages_profile.html" href="javascript:void(0)"><i class="mdi mdi-account"></i> 个人信息</a>
                            </li>
                            <li>
                                <a class="multitabs dropdown-item" data-url="lyear_pages_edit_pwd.html" href="javascript:void(0)"><i class="mdi mdi-lock-outline"></i> 修改密码</a>
                            </li>
                            <li>
                                <a class="dropdown-item" href="javascript:void(0)"><i class="mdi mdi-delete"></i> 清空缓存</a>
                            </li>
                            <li class="dropdown-divider"></li>
                            <li>
                                <a class="dropdown-item" href="lyear_pages_login_1.html"><i class="mdi mdi-logout-variant"></i> 退出登录</a>
                            </li>
                        </ul>
                    </li>
                </ul>

            </nav>

        </header>
        <!--End 头部信息-->

        <!--页面主要内容-->
        <main class="lyear-layout-content">
            <div class="container-fluid p-t-15">

                <div class="row">
                    <div class="col-md-6 col-xl-3">
                        <div class="card bg-primary text-white">
                            <div class="card-body clearfix">
                                <div class="flex-box">
                                    <span class="img-avatar img-avatar-48 bg-translucent"><i class="mdi mdi-currency-cny fs-22"></i></span>
                                    <span class="fs-22 lh-22">102,125.00</span>
                                </div>
                                <div class="text-right">今日收入</div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6 col-xl-3">
                        <div class="card bg-danger text-white">
                            <div class="card-body clearfix">
                                <div class="flex-box">
                                    <span class="img-avatar img-avatar-48 bg-translucent"><i class="mdi mdi-account fs-22"></i></span>
                                    <span class="fs-22 lh-22">920,000</span>
                                </div>
                                <div class="text-right">用户总数</div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6 col-xl-3">
                        <div class="card bg-success text-white">
                            <div class="card-body clearfix">
                                <div class="flex-box">
                                    <span class="img-avatar img-avatar-48 bg-translucent"><i class="mdi mdi-arrow-down-bold fs-22"></i></span>
                                    <span class="fs-22 lh-22">34,005,000</span>
                                </div>
                                <div class="text-right">下载总量</div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6 col-xl-3">
                        <div class="card bg-purple text-white">
                            <div class="card-body clearfix">
                                <div class="flex-box">
                                    <span class="img-avatar img-avatar-48 bg-translucent"><i class="mdi mdi-comment-outline fs-22"></i></span>
                                    <span class="fs-22 lh-22">153 条</span>
                                </div>
                                <div class="text-right">新增留言</div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">

                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header">
                                <div class="card-title">每周用户</div>
                            </div>
                            <div class="card-body">
                                <canvas class="js-chartjs-bars"></canvas>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header">
                                <div class="card-title">交易历史记录</div>
                            </div>
                            <div class="card-body">
                                <canvas class="js-chartjs-lines"></canvas>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="row">

                    <div class="col-lg-12">
                        <div class="card">
                            <header class="card-header"><div class="card-title">项目信息</div></header>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>项目名称</th>
                                            <th>开始日期</th>
                                            <th>截止日期</th>
                                            <th>状态</th>
                                            <th>进度</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>设计新主题</td>
                                            <td>10/02/2019</td>
                                            <td>12/05/2019</td>
                                            <td><span class="badge badge-warning">待定</span></td>
                                            <td>
                                                <div class="progress progress-striped progress-sm">
                                                    <div class="progress-bar progress-bar-warning" style="width: 45%;"></div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>2</td>
                                            <td>网站重新设计</td>
                                            <td>01/03/2019</td>
                                            <td>12/04/2019</td>
                                            <td><span class="badge badge-success">进行中</span></td>
                                            <td>
                                                <div class="progress progress-striped progress-sm">
                                                    <div class="progress-bar progress-bar-success" style="width: 30%;"></div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>3</td>
                                            <td>模型设计</td>
                                            <td>10/10/2019</td>
                                            <td>12/11/2019</td>
                                            <td><span class="badge badge-warning">待定</span></td>
                                            <td>
                                                <div class="progress progress-striped progress-sm">
                                                    <div class="progress-bar progress-bar-warning" style="width: 25%;"></div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>4</td>
                                            <td>后台管理系统模板设计</td>
                                            <td>25/01/2019</td>
                                            <td>09/05/2019</td>
                                            <td><span class="badge badge-success">进行中</span></td>
                                            <td>
                                                <div class="progress progress-striped progress-sm">
                                                    <div class="progress-bar progress-bar-success" style="width: 55%;"></div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>5</td>
                                            <td>前端设计</td>
                                            <td>10/10/2019</td>
                                            <td>12/12/2019</td>
                                            <td><span class="badge badge-danger">未开始</span></td>
                                            <td>
                                                <div class="progress progress-striped progress-sm">
                                                    <div class="progress-bar progress-bar-danger" style="width: 0%;"></div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>6</td>
                                            <td>桌面软件测试</td>
                                            <td>10/01/2019</td>
                                            <td>29/03/2019</td>
                                            <td><span class="badge badge-success">进行中</span></td>
                                            <td>
                                                <div class="progress progress-striped progress-sm">
                                                    <div class="progress-bar progress-bar-success" style="width: 75%;"></div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>7</td>
                                            <td>APP改版开发</td>
                                            <td>25/02/2019</td>
                                            <td>12/05/2019</td>
                                            <td><span class="badge badge-danger">暂停</span></td>
                                            <td>
                                                <div class="progress progress-striped progress-sm">
                                                    <div class="progress-bar progress-bar-danger" style="width: 15%;"></div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>8</td>
                                            <td>Logo设计</td>
                                            <td>10/02/2019</td>
                                            <td>01/03/2019</td>
                                            <td><span class="badge badge-warning">完成</span></td>
                                            <td>
                                                <div class="progress progress-striped progress-sm">
                                                    <div class="progress-bar progress-bar-success" style="width: 100%;"></div>
                                                </div>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
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
<script type="text/javascript" src="/js/lib/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/lib/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/js/lib/jquery.cookie.min.js"></script>
<script type="text/javascript" src="/js/index.min.js"></script>
<script type="text/javascript" src="/js/lib/Chart.min.js"></script>
<script type="text/javascript">
    $(document).ready(function(e) {
        var $dashChartBarsCnt  = jQuery( '.js-chartjs-bars' )[0].getContext( '2d' ),
            $dashChartLinesCnt = jQuery( '.js-chartjs-lines' )[0].getContext( '2d' );

        var $dashChartBarsData = {
            labels: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
            datasets: [
                {
                    label: '注册用户',
                    borderWidth: 1,
                    borderColor: 'rgba(0, 0, 0, 0)',
                    backgroundColor: 'rgba(51, 202, 185, 0.5)',
                    hoverBackgroundColor: "rgba(51, 202, 185, 0.7)",
                    hoverBorderColor: "rgba(0, 0, 0, 0)",
                    data: [2500, 1500, 1200, 3200, 4800, 3500, 1500]
                }
            ]
        };
        var $dashChartLinesData = {
            labels: ['2003', '2004', '2005', '2006', '2007', '2008', '2009', '2010', '2011', '2012', '2013', '2014'],
            datasets: [
                {
                    label: '交易资金',
                    data: [20, 25, 40, 30, 45, 40, 55, 40, 48, 40, 42, 50],
                    borderColor: '#358ed7',
                    backgroundColor: 'rgba(53, 142, 215, 0.175)',
                    borderWidth: 1,
                    fill: false,
                    lineTension: 0.5
                }
            ]
        };

        new Chart($dashChartBarsCnt, {
            type: 'bar',
            data: $dashChartBarsData
        });

        var myLineChart = new Chart($dashChartLinesCnt, {
            type: 'line',
            data: $dashChartLinesData,
        });
    });
</script>
</body>
</html>