package ru.qatools.school.vorobushek.resources;

import org.glassfish.jersey.server.mvc.ErrorTemplate;
import org.glassfish.jersey.server.mvc.Template;
import ru.qatools.school.vorobushek.models.Comment;
import ru.qatools.school.vorobushek.models.Post;
import org.json.JSONException;

import ru.qatools.school.vorobushek.service.DatabaseProvider;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * eroshenkoam
 * 15/11/14
 */
@Path("post")
@Produces(MediaType.TEXT_HTML)
@ErrorTemplate(name = "/error.ftl")
public class PostResources {

    @GET
    @Path("/{id}")
    @Template(name = "/post/showPost.ftl")
    public Post showPost(@PathParam("id") int id) {
        return Post.findById(id);
    }

    @GET
    @Path("/new")
    @Template(name = "/post/newPost.ftl")
    public Post newPost() {
        return new Post();
    }

    @POST
    @Path("/")
    @Template(name = "/post/showPost.ftl")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Post createPost(@FormParam("title") String title,
                           @FormParam("body") String body,
                           @CookieParam("token") String token) throws IOException, JSONException {

        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        post.setUser(DatabaseProvider.GetYandexUser(token));
        post.saveIt();
        return post;
    }


    @POST
    @Path("/{id}/addComment")
    @Template(name = "/post/showPost.ftl")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Post addComment(@PathParam("id") String idPost,
                           @FormParam("commentBody") String bodyComment,
                           @CookieParam("token") String token) throws IOException, JSONException {
        Comment comment = new Comment();
        comment.setBody(bodyComment);
        comment.setParent(Post.findById(idPost));
        comment.setUser(DatabaseProvider.GetYandexUser(token));
        comment.saveIt();

        return Post.findById(idPost);
    }
}
