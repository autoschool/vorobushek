<#-- @ftlvariable name="model" type="ru.qatools.school.vorobushek.models.UserContext" -->
<#import "../layouts/main.ftl" as layout />
<@layout.layout title="Blog: posts" userName=model.getCurrentUserString() yandexLoginUrl=model.getUserUrl()>
<head>
    <link href="/public/app/css/showPost.css" type="text/css" rel="stylesheet"/>
</head>
<div class="row">
    <div class="col-md-12">
    <div class="page-header">
        <h2>${model.getLastShownPost().getTitle()}</h2>
    </div>
    <div class="post-body">
        ${model.getLastShownPost().getBody()}
    <div class="panel-body">
        <ul class="list-group">
            <li class="list-group-item  active">
                <span class="badge">${model.getLastShownPost().getCommentsCount()}</span>
                Comments
            </li>
            <#list model.getLastShownPost().comments as comment>
                <li class="list-group-item">
                    <span class="label label-info">${comment.user.displayName}</span>
                    <#if model.hasUser()>
                        <form class="form" role="form" method="POST" action="/post/${comment.getId()}/deleteComment">
                            <button type="submit" class="btn btn-default" id="button-remove"><span class="glyphicon glyphicon-remove" id="glyphicon-remove"></span></button>
                        </form>
                    </#if>
                    <div class="well">${comment.body}</div>
                </li>
            </#list>
        </ul>
        <#if model.hasUser()>
            <form class="form" role="form" method="POST" action="showComments">
                <div class="form-group">
                    <textarea class="form-control" rows="10" name="commentBody"></textarea>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <button type="submit" class="btn btn-default pull-right">Add Comment</button>
                    </div>
                </div>
            </form>
        </#if>
    </div>
</div>
</@layout.layout>
