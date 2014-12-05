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


                <div >
                    <#--<h1 class="panel-title"><a href="/post/${post.id}/showComments" id="${post.id}">${post.title}</a></h1>-->
                </div>

                <div >
                    <div class="container-fluid">
                        <div class="row" style="height: auto">
                            <div class="col-sm-11">
                                <blockquote class="blockquote-reverse">
                                    <p>${post.body}</p>
                                    <footer><cite title="Source Title">${post.user.displayName}</cite></footer>
                                </blockquote>
                            </div>
                            <div class="col-sm-1">
                                <img src="${post.user.avater}" class="img-circle" alt="Responsive image">
                            </div>
                        </div>
                    </div>
                </div>

                <div >
                    <div class="container-fluid">
                        <div class="row" style="height: auto">
                            <div class="col-sm-8 pull-left">
                                <#--<div    class="pull-left">-->
                                    <div>
                                        Update on ${post.createdAt}</span>
                                    </div>
                                    <div>
                                        <span class="badge"><a href="/post/${post.id}/showComments">${post.getCommentsCount()} comments</a></span>
                                    </div>
                                <#--</div>-->
                            </div>
                            <div class="col-sm-4">
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


            <hr style="border-color: #333333">

        </#list>
    </div>
</div>
</@layout.layout>