<#-- @ftlvariable name="model" type="ru.qatools.school.vorobushek.models.UserContext" -->
<#import "layouts/main.ftl" as layout />
<@layout.layout title="Blog: posts" userName=model.getCurrentUserString() yandexLoginUrl=model.getUserUrl()>
<div class="row">
    <div class="col-md-12">
        <div class="page-header">
            <h2>All Posts</h2>
        </div>
        <#list model.getPosts() as post>
           <#---->

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h1 class="panel-title"><a href="/post/${post.id}/showComments" id="${post.id}">${post.title}</a></h1>
                </div>

                <div class="panel-body">
                    <div class="container-fluid">
                        <div class="row" style="height: auto">
                            <div class="col-md-10">
                                <blockquote class="blockquote-reverse">
                                    <p>${post.body}</p>
                                    <footer><cite title="Source Title">${post.user.displayName}</cite></footer>
                                </blockquote>
                            </div>
                            <div class="col-md-2 pull-right">
                                <#if post.getCanEdit()>
                                    <form class="navbar-form" method="GET" action="/post/${post.id}/edit">
                                        <button type="submit" id="edit-button-${post.id}" class="btn btn-warning">Edit</button>
                                    </form>
                                    <form class="navbar-form" method="POST" action="/post/${post.id}/delete">
                                        <button type="submit" id="delete-button-${post.id}" class="btn btn-danger">Delete</button>
                                    </form>
                                </#if>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="panel-footer">
                    <div class="container-fluid">
                        <div class="row" style="height: auto">
                            <div class="col-md-8 pull-left">
                                <#--<div    class="pull-left">-->
                                    <div>
                                        Update on ${post.createdAt}</span>
                                    </div>
                                    <div>
                                        <span class="badge"><a href="/post/${post.id}/showComments">${post.getCommentsCount()} comments</a></span>
                                    </div>
                                <#--</div>-->
                            </div>
                            <div class="col-md-4">
                                <div class="pull-right">
                                    <img src="${post.user.avater}" class="img-responsive img-circle pull-right" alt="Responsive image">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
</@layout.layout>