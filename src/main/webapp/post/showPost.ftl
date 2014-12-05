<#-- @ftlvariable name="model" type="ru.qatools.school.vorobushek.models.UserContext" -->
<#import "../layouts/main.ftl" as layout />
<@layout.layout title="Blog: posts" userName=model.getCurrentUserString() yandexLoginUrl=model.getUserUrl()>
<head>
    <link href="/public/app/css/showPost.css" type="text/css" rel="stylesheet"/>
</head>

    <div class="page-header">
        <h2 id="post-title">${model.getLastShownPost().getTitle()}</h2>
        <span class="badge">${model.getLastShownPost().getCommentsCount()} Comments</span>
    </div>
    <div class="blockquote-reverse">
        <p id="post-body">${model.getLastShownPost().getBody()}</p>
        <footer><cite title="Source Title">${model.getLastShownPost().user.displayName}</cite></footer>
    </div>

        <#list model.getLastShownPost().comments as comment>
            <div class="well">
                <div class="row" style="height: auto">
                    <div class="col-sm-10">
                        <div>
                            <img src="${comment.user.avater}" class="img-circle" alt="Responsive image">
                            <span class="label label-info">${comment.user.displayName}</span>
                         </div>

                        <div>
                            ${comment.body}
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <#if comment.getUser().equals(model.getCurrentUser())>
                            <form class="form" role="form" method="POST" action="/post/${comment.getId()}/deleteComment">
                                <button type="submit" class="btn btn-default" id="button-remove">
                                    <span class="glyphicon glyphicon-remove" id="glyphicon-remove"></span>
                                </button>
                            </form>
                        </#if>
                    </div>
                </div>
            </div>
        </#list>

    <#if model.hasUser()>
        <form class="form" role="form" method="POST" action="showComments">
            <div class="form-group">
                <textarea id="new-comment-textarea" class="form-control" rows="10" name="commentBody"></textarea>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <button id="add-comment-button" type="submit" class="btn btn-default pull-right">Add Comment</button>
                </div>
            </div>
        </form>
    </#if>


</@layout.layout>
