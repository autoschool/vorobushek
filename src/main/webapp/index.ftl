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
                    <h3 class="panel-title"><a href="/post/${post.id}/showComments" id="${post.id}">${post.title}</a></h3>
                </div>

                <div class="panel-body">
                    <div class="container-fluid">
                        <div class="row" style="height: auto">
                            <div class="col-md-6">
                                <blockquote>
                                    <p>${post.body}</p>
                                </blockquote>
                            </div>
                            <div class="col-md-6">
                                <img src="${post.user.avater}" class="img-responsive img-circle pull-right" alt="Responsive image">
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
                                        Posted on ${post.createdAt} by <span class="label label-info">${post.user.displayName}</span>
                                    </div>
                                    <div>
                                        <span class="badge"><a href="/post/${post.id}/showComments">${post.getCommentsCount()} comments</a></span>
                                    </div>
                                <#--</div>-->
                            </div>
                            <div class="col-md-4">
                                <div class="pull-right">
                                    <#if post.getCanEdit()>
                                        <form class="navbar-form navbar-right" method="GET" action="/post/${post.id}/edit">
                                            <button type="submit" id="edit-button-${post.id}" class="btn btn-warning">Edit</button>
                                        </form>
                                        <form class="navbar-form navbar-right" method="POST" action="/post/${post.id}/delete">
                                            <button type="submit" id="delete-button-${post.id}" class="btn btn-danger">Delete</button>
                                        </form>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <#--<div class="well">-->
                <#--<div class="row">-->
                    <#--<div class="column col-sm-8">-->
                        <#--<a href="/post/${post.id}/showComments" id="${post.id}"><h3>${post.title}</h3></a>-->
                        <#--Posted on ${post.createdAt} by <span class="label label-info">${post.user.displayName}</span>-->
                        <#--<blockquote>-->
                            <#--<p>  ${post.body} </p>-->
                        <#--</blockquote>-->
                        <#--<span class="badge"><a href="/post/${post.id}/showComments">${post.getCommentsCount()} comments</a></span>-->
                    <#--</div>-->

                    <#--<div class="column col-sm-4">-->
                        <#--<img src="${post.user.avater}" class="img-circle pull-right">-->

                        <#--<#if post.getCanEdit()>-->
                            <#--<form class="navbar-form navbar-right" method="GET" action="/post/${post.id}/edit">-->
                                <#--<button type="submit" id="edit-button-${post.id}" class="btn btn-warning">Edit</button>-->
                            <#--</form>-->
                            <#--<form class="navbar-form navbar-right" method="POST" action="/post/${post.id}/delete">-->
                                <#--<button type="submit" id="delete-button-${post.id}" class="btn btn-danger">Delete</button>-->
                            <#--</form>-->
                        <#--</#if>-->
                    <#--</div>-->
                <#--</div>-->
            <#--</div>-->

            <#---->


        </#list>
    </div>
</div>
</@layout.layout>