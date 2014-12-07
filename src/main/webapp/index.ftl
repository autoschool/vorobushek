<#-- @ftlvariable name="model" type="ru.qatools.school.vorobushek.models.UserContext" -->
<#import "layouts/main.ftl" as layout />
<@layout.layout title="Blog: posts" userName=model.getCurrentUserString() yandexLoginUrl=model.getUserUrl()>

<div class="list-group">
    <#list model.getPosts() as post>
       <#---->

        <div class="well">
            <#if post.getCanEdit()>

                <a class="btn btn-warning btn-sm pull-right" href="/post/${post.id}/edit" role="button">Edit</a>

                <form class="form" method="POST" action="/post/${post.id}/delete">
                    <button type="submit" id="delete-button-${post.id}" class="btn btn-danger btn-sm">Delete</button>
                </form>

            </#if>
            <a class="list-group-item well" href="/post/${post.id}/showComments" >
                <img src="${post.user.avater}" class="img-circle img-responsive center-block" alt="Responsive image">

                <blockquote class="blockquote-reverse">
                    <p class="postBody">${post.body}</p>
                    <footer><cite>${post.user.displayName}</cite></footer>
                </blockquote>

                <p>Update on ${post.createdAt}</span></p>
                <p>Comments <span class="badge">${post.getCommentsCount()}</span></p>
            <#---->
            </a>
        </div>

    </#list>
</div>

</@layout.layout>