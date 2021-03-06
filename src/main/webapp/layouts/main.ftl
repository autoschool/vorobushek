<#macro layout title="Vorobushek" userName="" yandexLoginUrl="">
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Vorobushek</title>
    <meta name="generator" content="Bootply" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <script src="http://yastatic.net/jquery/2.1.1/jquery.min.js" type="text/javascript"></script>
    <script src="http://yastatic.net/bootstrap/3.3.1/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="http://yastatic.net/bootstrap/3.3.1/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>

    <script src="/public/ysk/js/recorder.js" type="text/javascript"></script>
    <script src="/public/ysk/js/recognizer.js" type="text/javascript"></script>
    <script src="/public/ysk/js/dictation.js" type="text/javascript"></script>

</head>
<body style="background-color: #fdffe2; padding-top: 70px;">

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#vorobushek-navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">
                <p><img src="/public/app/img/sparow-48x48.png" class="img-circle"> Vorobushek (${model.getCurrentProjectVersion()})</p>
            </a>

        </div>

        <div class="collapse navbar-collapse" id="vorobushek-navbar-collapse">
            <ul class="nav navbar-nav navbar-left">
                <li><a href="https://github.com/autoschool/vorobushek">About</a></li>
             </ul>

            <ul class="nav navbar-nav navbar-right">
                <#if model.hasUser()>
                    <li><a href="/post/new" id="new-post-link">New post</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" >
                            <span class="glyphicon glyphicon-user"/>
                            <span id="user-name">${userName}</span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a id="user-profile" href=${yandexLoginUrl}>Profile</a></li>
                            <li class="divider"></li>
                            <li><a id="logout" href="/logout">Logout</a></li>
                        </ul>
                    </li>
                <#else>
                    <li> <a id="sign-in" href=${yandexLoginUrl}>Sign in</a></li>
                </#if>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>


<div class="center-block" id="main">
    <div class="row" style="width: 100%">
        <div class="col-sm-2"></div>
        <div class="col-sm-8"><#nested /></div>
        <div class="col-sm-2"></div>
    </div>
</div>

</body>
</html>
</#macro>
