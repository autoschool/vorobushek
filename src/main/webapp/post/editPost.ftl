<#-- @ftlvariable name="model" type="ru.qatools.school.vorobushek.models.UserContext" -->
<#import "../layouts/main.ftl" as layout />
<@layout.layout title="Blog: posts" userName=model.getCurrentUserString() yandexLoginUrl=model.getUserUrl()>
<div class="row">
    <#if model.hasUser()>
        <div class="page-header">
            <h2>Edit post</h2>
        </div>
        <div class="col-md-12">
            <form class="form" role="form" action="/post" method="post">
                <div class="page-header">
                    <div class="form-group">
                        <input type="text" class="form-control" id="title" name="title"
                               value="${model.currentPost.title}">
                    </div>
                </div>
                <div class="form-group">
                    <textarea class="form-control" rows="10" name="body">${model.currentPost.body}</textarea>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <button type="submit" class="btn btn-danger pull-right">Save</button>
                    </div>
                </div>
            </form>
        </div>
    <#else>
        <div class="page-header">
            <h2>You can't edit somebody else's posts</h2>
        </div>
    </#if>
</div>
</@layout.layout>