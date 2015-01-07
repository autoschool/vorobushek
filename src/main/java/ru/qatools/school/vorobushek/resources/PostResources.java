package ru.qatools.school.vorobushek.resources;

import org.glassfish.jersey.server.mvc.ErrorTemplate;
import org.glassfish.jersey.server.mvc.Template;
import ru.qatools.school.vorobushek.models.*;

import ru.qatools.school.vorobushek.service.DatabaseProvider;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;


/**
 * eroshenkoam
 * 15/11/14
 */
@Path("post")
@Produces(MediaType.TEXT_HTML)
@ErrorTemplate(name = "/error.ftl")
public class PostResources {

    @Context
    HttpServletRequest httpRequest;

    @GET
    @Path("/{id}/showComments")
    @Template(name = "/post/showPost.ftl")
    public UserContext showPost(@PathParam("id") String postId) {

        UserContext userContext = DatabaseProvider.getUserContext(httpRequest);

        userContext.setLastShownPost(postId);

        return userContext;
    }

    @POST
    @Path("/{id}/showComments")
    @Template(name = "/post/showPost.ftl")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public UserContext addComment(@PathParam("id") String postId,
                                    @FormParam("commentBody") String commentBody) {

        UserContext userContext = DatabaseProvider.getUserContext(httpRequest);

        if (commentBody.isEmpty()){
            return userContext;
        }

        if (userContext.hasUser()){
            userContext.addCommentToPost(commentBody, postId);
            userContext.setLastShownPost(postId);
        }

        return userContext;
    }
    
    @POST
    @Path("/{id}/deleteComment")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response deleteComment(@PathParam("id") String commentId) {

        UserContext userContext = DatabaseProvider.getUserContext(httpRequest);
        
        if (userContext.hasUser()) {
            userContext.deleteComment(commentId);
        }

        return javax.ws.rs.core.Response.seeOther(URI.create("/post/" + userContext.getLastShownPost().getId() + "/showComments")).build();
    }
    
    @GET
    @Path("/{id}/edit")
    @Template(name = "/post/editPost.ftl")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public UserContext showEditPost(@PathParam("id") String postId) {

        UserContext userContext = DatabaseProvider.getUserContext(httpRequest);

        userContext.setLastEditedPost(postId);

        return userContext;
    }

    @POST
    @Path("/{id}/edit")
    @Template(name = "/post/showPost.ftl")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public UserContext editPost(@PathParam("id") String postId,
                                  @FormParam("title") String postTitle,
                                  @FormParam("body") String postBody) {

        UserContext userContext = DatabaseProvider.getUserContext(httpRequest);

        if (postBody.isEmpty()){
            userContext.setLastShownPost(postId);
            return userContext;
        }

        if (userContext.hasUser()){
            userContext.updatePost(postId, postTitle, postBody);
            userContext.setLastShownPost(postId);
        }

        return userContext;
    }

    @POST
    @Path("/{id}/delete")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response deletePost(@PathParam("id") String postId) {

        UserContext userContext = DatabaseProvider.getUserContext(httpRequest);

        if (userContext.hasUser()){
            userContext.deletePost(postId);
        }

        return javax.ws.rs.core.Response.seeOther(URI.create("/")).build();
    }

    @GET
    @Path("/new")
    @Template(name = "/post/newPost.ftl")
    public UserContext showCreatePost() {
        return DatabaseProvider.getUserContext(httpRequest);
    }



    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createPost(@FormParam("title") String postTitle,
                                @FormParam("body") String postBody,
                                @FormParam("saveButton") int postVal) {

        UserContext userContext = DatabaseProvider.getUserContext(httpRequest);

        if (postBody.isEmpty()){
            return javax.ws.rs.core.Response.seeOther(URI.create("/")).build();
        }
        
        if (userContext.hasUser()){
            Post post = userContext.createPost(postTitle, postBody, postVal);
            userContext.setLastShownPost(post.getId().toString());
        }
        return javax.ws.rs.core.Response.seeOther(URI.create("/post/" + userContext.getLastShownPost().getId() + "/showComments")).build();
    }

}
