<#-- @ftlvariable name="model" type="ru.qatools.school.vorobushek.models.UserContext" -->
<#import "layouts/main.ftl" as layout />
<@layout.layout title="Blog: posts" userName=model.getCurrentUserString() yandexLoginUrl=model.getUserUrl()>
<div class="row">
    <div class="col-md-12">
        <div class="page-header">
            <h2>All Posts</h2>
        </div>
        <#list model.getPosts() as post>
            <div class="panel panel-default">
                <div class="panel-body">
                    <a href="/post/${post.id}/showComments"><h3>${post.title}</h3></a>
                     Posted on ${post.createdAt} by <span class="label label-info">${post.user.displayName}</span>
                    </br>
                    <p>  ${post.body} </p><span class="badge">${post.getCommentsCount()} comments</span>
                    <#if post.getCanEdit()>
                        <form class="navbar-form navbar-right" method="GET" action="/post/${post.id}/edit">
                            <button type="submit" class="btn btn-default">Edit</button>
                        </form>
                        <form class="navbar-form navbar-right" method="POST" action="/post/${post.id}/delete">
                            <button type="submit" class="btn btn-default">Delete</button>
                        </form>
                    </#if>
                </div>
            </div>
        </#list>
    </div>
</div>
</@layout.layout>