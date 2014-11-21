package ru.qatools.school.vorobushek.resources;

import org.glassfish.jersey.server.mvc.ErrorTemplate;
import org.glassfish.jersey.server.mvc.Template;
import org.json.JSONException;
import ru.qatools.school.vorobushek.models.Post;
import ru.qatools.school.vorobushek.service.DatabaseProvider;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.net.URI;
import java.util.List;



/**
 * eroshenkoam
 * 15/11/14
 */
@Path("/")
@Produces(value = javax.ws.rs.core.MediaType.TEXT_HTML)
@ErrorTemplate(name = "/error.ftl")
public class IndexResource {

    @GET
    @Path("/")
    @Template(name = "/index.ftl")
    public List<Post> showIndex(/*@CookieParam("token") String token*/){

        //DatabaseProvider.logger.info("/");

        //if (token != null && !token.isEmpty())
        //{
        //    User user = DatabaseProvider.GetYandexUser(token);

            //DatabaseProvider.logger.info("token from attribute:" + req.getAttribute("token"));
        //}


        return Post.findAll();
    }

    @GET
    @Path("/login")
    public Response showLogin(@Context UriInfo uriInfo) {

        String token = null;
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();

        if (queryParams.containsKey("code"))
        {
            String responseCode = queryParams.get("code").toString().replace("[", "").replace("]","");

            try {
                token = DatabaseProvider.GetYandexToken(responseCode);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return token == null
                ? javax.ws.rs.core.Response.seeOther(URI.create("/"))
                        .cookie(new NewCookie("token", ""))
                        .build()
                : javax.ws.rs.core.Response.seeOther(URI.create("/"))
                        .cookie(new NewCookie("token", token))
                        .build();
    }

}
