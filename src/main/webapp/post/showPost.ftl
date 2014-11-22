<#-- @ftlvariable name="model" type="ru.qatools.school.vorobushek.models.Post" -->
<#import "../layouts/main.ftl" as layout />
<@layout.layout title="Blog: post">
<div class="row">
    <div class="col-md-12">
        <div class="page-header">
            <h2>${model.title}</h2>
        </div>
        <div class="post-body">
            ${model.body}

                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item  active">
                            <span class="badge">${model.getCommentsCount()}</span>
                            Comments
                        </li>
                        <#list model.comments as comment>

                            <li class="list-group-item">${comment.body} by ${comment.user.displayName}</li>

                        </#list>
                    </ul>

                    <form class="form" role="form" action="/post/${model.id}/addComment" method="post">
                        <div class="form-group">
                            <textarea class="form-control" rows="10" name="commentBody"></textarea>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <button type="submit" class="btn btn-default pull-right">Add Comment</button>
                            </div>
                        </div>
                    </form>
                <div>

        </div>
    </div>
</div>
</@layout.layout>
