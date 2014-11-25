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
                    <a href="/post/${post.id}"><h3>${post.title}</h3></a>
                     Posted on ${post.createdAt} by ${post.user.displayName}
                    </br> 
                    <p>  ${post.body} </p><span class="badge"><a href="/post/${post.id}">${post.getCommentsCount()} comments</span></a>
                    <form class="navbar-form navbar-right" method="GET" action="/post/${post.id}/edit">
                        <button type="submit" class="btn btn-default">Edit</button>
                    </form>
                </div>
            </div>
        </#list>
    </div>
</div>
</@layout.layout>