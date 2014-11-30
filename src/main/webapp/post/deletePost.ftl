<#-- @ftlvariable name="model" type="ru.qatools.school.vorobushek.models.UserContext" -->
<#import "../layouts/main.ftl" as layout />
<@layout.layout title="Blog: posts" userName=model.getCurrentUserString() yandexLoginUrl=model.getUserUrl()>
<div class="row">
    <div class="col-md-12">
    <div class="page-header">
        <h2>${model.lastPost.title}</h2>
    </div>
    <div class="post-body">
    Are you sure to delete this post?

    <div class="panel-body">
        <#if model.hasUser()>
            <form class="form" role="form" action="/post/${model.lastPost.id}/delete" method="post">
                <div class="row">
                    <div class="col-md-12">
                        <button type="submit" class="btn btn-danger pull-right">Delete Post</button>
                    </div>
                </div>
            </form>
        <#else>
            <div class="page-header">
                <h2>Guest can't delete posts. Please, login and try again.</h2>
            </div>
        </#if>
    </div>
</div>
</@layout.layout>
