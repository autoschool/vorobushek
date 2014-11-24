<#macro layout title="Vorobushek" userName="" yandexLoginUrl="">
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Vorobushek</title>
    <meta name="generator" content="Bootply" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <script src="/public/jquery/js/jquery.min.js" type="text/javascript"></script>
    <link href="/public/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script src="/public/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

    <link href="/public/app/css/main.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div class="wrapper">
    <div class="box">
        <header>
            <nav class="navbar navbar-menu navbar-fixed-top" role="navigation">
                <div class="container-fluid">
                    <div class="navbar-header navbar-right">
                        <ul class="nav navbar-nav">
                            <li><a href="/post/new" method="GET" action="/post/new" type="submit">New post</a></li>
                        <li>
                            <#if userName?has_content>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">${userName} <span class="caret"></span></a>
                                <ul class="dropdown-menu" role="menu">
                                    <li>
                                        <a href=${yandexLoginUrl}>Profile</a></li>
                                    <li class="divider"></li>
                                    <li><a href="/logout" method="GET" action="/logout">Logout</a></li>
                                </ul>
                            <#else>
                                <a href=${yandexLoginUrl}>Sign in</a></li>
                            </#if>
                        </ul>
                    </div>
                    <div class="navbar-header navbar-left">
                        <ul class="nav navbar-nav">
                            <li class="active"><a href="/">Home <span class="sr-only">(current)</span></a></li>
                            <li><a href="#">About</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
        <div class="row">
            <div class="column col-sm-3" id="sidebar">
                <a class="logo" href="/"><img alt="Brand" src="/public/app/img/logo.jpg"></a>
                <ul class="nav hidden-xs" id="sidebar-footer">
                    <li>
                        <a href="/"><h3>Vorobushek</h3>Made with <i class="glyphicon glyphicon-heart-empty"></i></a>
                    </li>
                </ul>
            </div>
            <!-- /sidebar -->
            <div class="column col-sm-9" id="main">
                <div class="padding">
                    <div class="full col-sm-9">
                        <div class="content">
                            <#nested />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<footer>
</footer>
</body>
</#macro>

<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>