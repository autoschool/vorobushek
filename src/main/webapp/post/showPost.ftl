<#-- @ftlvariable name="model" type="ru.qatools.school.vorobushek.models.UserContext" -->
<#import "../layouts/main.ftl" as layout />
<@layout.layout title="Blog: posts" userName=model.getCurrentUserString() yandexLoginUrl=model.getUserUrl()>
<head>

</head>

    <div class="page-header" >
        <h2 id="post-title" style="color: #ffffff">${model.getLastShownPost().getTitle()}</h2>
        <span class="badge">${model.getLastShownPost().getCommentsCount()} Comments</span>
    </div>
    <div class="blockquote-reverse"  style="background-color: hsla(${model.getLastShownPost().getFreq()},60%,60%,0.3)}">
        <p id="post-body" class="panael">${model.getLastShownPost().getBody()}</p>
        <footer><cite title="Source Title">${model.getLastShownPost().user.displayName}</cite></footer>
    </div>



    <p class="list-group">
        <#list model.getLastShownPost().comments as comment>
            <div class="list-group-item well">

                <div>
                    <img src="${comment.user.avater}" class="img-circle" alt="Responsive image">
                    <span class="label label-info">${comment.user.displayName}</span>
                 </div>
                <div>
                    <p>
                        ${comment.body}
                    </p>
                </div>



                <#if comment.getUser().equals(model.getCurrentUser())>
                    <form class="form" role="form" method="POST" action="/post/${comment.getId()}/deleteComment">
                        <button type="submit" class="btn btn-danger btn-sm" id="button-remove">
                            Delete comment
                        </button>
                    </form>
                </#if>

            </div>
        </#list>
    </p>


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
