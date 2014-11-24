<#-- @ftlvariable name="model" type="ru.qatools.school.vorobushek.models.UserContext" -->
<#import "../layouts/main.ftl" as layout />
<@layout.layout title="Blog: posts" userName=model.getCurrentUserString() yandexLoginUrl=model.getUserUrl()>
<div class="row">
    <div class="col-md-12">
    <div class="page-header">
        <h2>${model.lastPost.title}</h2>
    </div>
    <div class="post-body">
    ${model.lastPost.body}

    <div class="panel-body">
        <ul class="list-group">
            <li class="list-group-item  active">
                <span class="badge">${model.lastPost.getCommentsCount()}</span>
                Comments
            </li>
            <#list model.lastPost.comments as comment>
                <li class="list-group-item">${comment.body} by ${comment.user.displayName}</li>
            </#list>
        </ul>
        <#if model.hasUser()>
            <form class="form" role="form" action="/post/${model.lastPost.id}/addComment" method="post">
                <div class="form-group">
                    <textarea class="form-control" rows="10" name="commentBody"></textarea>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <button type="submit" class="btn btn-default pull-right">Add Comment</button>
                    </div>
                </div>
            </form>
        <#else>
            <div class="page-header">
                <h2>Guest can't add any comments. Please, login and try again.</h2>
            </div>
        </#if>
    </div>
</div>
</@layout.layout>
