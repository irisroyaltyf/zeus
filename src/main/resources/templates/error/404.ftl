<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>错误页面 - Zeus 采集管理系统</title>
    <link rel="icon" href="favicon.ico" type="image/ico">
    <meta name="keywords" content="LightYear,LightYearAdmin,光年,后台模板,后台管理系统,光年HTML模板">
    <meta name="description" content="Light Year Admin V4是一个后台管理系统的HTML模板，基于Bootstrap v4.4.1。">
    <meta name="author" content="yinqi">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/materialdesignicons.min.css" rel="stylesheet">
    <link href="/css/style.min.css" rel="stylesheet">
    <style>
        .error-page {
            height: 100%;
            position: fixed;
            width: 100%;
        }
        .error-body {
            padding-top: 5%;
        }
        .error-body h1 {
            font-size: 210px;
            font-weight: 700;
            text-shadow: 4px 4px 0 #f5f6fa, 6px 6px 0 #868e96;
            line-height: 210px;
            color: #868e96;
        }
    </style>
</head>

<body>
<section class="error-page">
    <div class="error-box">
        <div class="error-body text-center">
            <span>
                <img src="/imgs/error.png">
            </span>
            <h5 class="mb-5 mt-3 text-gray">页面好像不见了</h5>
            <span id="tmv" style="color:red;font-weight:bold;"></span>秒钟后返回<a href="" onclick="goBack();">页面</a>
            <a href="/" class="btn btn-primary">返回首页</a>
        </div>
    </div>
</section>
<script type="text/javascript">
    var waitTm=3;
    function goHome(){
        if(waitTm>=1){
            document.getElementById('tmv').innerHTML=waitTm;
            waitTm--;
            setTimeout("goHome()",1000);
        }else{
            goBack();
        }
    }
    goHome();
    function  goBack() {
        console.log(window.history.length);
        if(window.history.length > 2){
            window.history.go( -1 );
        }else {
            window.location.href = "/";
        }
    }
</script>
<script type="text/javascript" src="/js/lib/jquery.min.js"></script>
<script type="text/javascript" src="/js/lib/bootstrap.min.js"></script>

</body>
</html>