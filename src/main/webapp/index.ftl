<#-- @ftlvariable name="model" type="ru.qatools.school.vorobushek.models.UserContext" -->
<#import "layouts/main.ftl" as layout />
<@layout.layout title="Blog: posts" userName=model.getCurrentUserString() yandexLoginUrl=model.getUserUrl()>

<div class="list-group" id="listPosts">
    <#list model.getPosts() as post>


        <div class="well" id="post${post.id}">
            <#if post.getCanEdit()>

                <a class="btn btn-warning btn-sm pull-right" href="/post/${post.id}/edit" role="button" id="editPost${post.id}">Edit</a>

                <form class="form" method="POST" action="/post/${post.id}/delete">
                    <button type="submit" id="delete-button-${post.id}" class="btn btn-danger btn-sm" id="deletePost${post.id}">Delete</button>
                </form>

            </#if>
            <a class="list-group-item well" href="/post/${post.id}/showComments" style="background-color: hsla(${post.getFreq()},60%,60%,0.3)}">
                <img src="${post.user.avater}" class="img-circle img-responsive center-block" alt="Responsive image" id="userAvatarPost${post.id}">

                <blockquote class="blockquote-reverse">
                    <p class="postBody" id="bodyPost${post.id}">${post.body}</p>
                    <footer id="authorPost${post.id}"><cite>${post.user.displayName}</cite></footer>
                </blockquote>

                <p id="datePost${post.id}">Update on ${post.createdAt}</span></p>
                <p>Comments <span class="badge" id="countCommentsPost${post.id}">${post.getCommentsCount()}</span></p>

            </a>
        </div>

    </#list>
</div>

</@layout.layout>