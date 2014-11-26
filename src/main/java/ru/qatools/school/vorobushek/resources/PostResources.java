package ru.qatools.school.vorobushek.resources;

import org.glassfish.jersey.server.mvc.ErrorTemplate;
import org.glassfish.jersey.server.mvc.Template;
import ru.qatools.school.vorobushek.models.*;

import ru.qatools.school.vorobushek.service.DatabaseProvider;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


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
    @Path("/{id}")
    @Template(name = "/post/showPost.ftl")
    public UserContext showPost(@PathParam("id") String id) {

        UserContext userContext = DatabaseProvider.getUserContext(httpRequest);
        userContext.setLastPost(id);

        return userContext;
    }

    @GET
    @Path("/new")
    @Template(name = "/post/newPost.ftl")
    public UserContext newPost() {
        UserContext userContext = DatabaseProvider.getUserContext(httpRequest);
        userContext.setCurrentPost(new Post());
        return DatabaseProvider.getUserContext(httpRequest);
    }
    
    @GET
    @Path("/{id}/edit")
    @Template(name = "/post/editPost.ftl")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public UserContext changePost(@PathParam("id") String postId) {
        Post currentPost = Post.findById(postId);
        UserContext userContext = DatabaseProvider.getUserContext(httpRequest);
        if(currentPost.getUser().getDisplayName().equals(userContext.getCurrentUserString())) {
            userContext.setCurrentPost(currentPost);
            return userContext;
        }
        else
            return new UserContext();
    }

    @POST
    @Path("/")
    @Template(name = "/post/showPost.ftl")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public UserContext savePost(@FormParam("title") String title,
                                  @FormParam("body") String body) {

        UserContext userContext = DatabaseProvider.getUserContext(httpRequest);
        
        if (userContext.hasUser()){
            userContext.savePost(title, body);
        }

        return userContext;
    }


    @POST
    @Path("/{id}/addComment")
    @Template(name = "/post/showPost.ftl")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public UserContext addComment(@PathParam("id") String postId,
                           @FormParam("commentBody") String commentBody) {

        UserContext userContext = DatabaseProvider.getUserContext(httpRequest);

        if (userContext.hasUser()){
            userContext.addCommentToPost(commentBody, postId);
        }

        return userContext;
    }
}
