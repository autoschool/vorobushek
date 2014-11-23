package ru.qatools.school.vorobushek.resources;

import org.glassfish.jersey.server.mvc.ErrorTemplate;
import org.glassfish.jersey.server.mvc.Template;
import ru.qatools.school.vorobushek.models.UserContext;
import ru.qatools.school.vorobushek.service.DatabaseProvider;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;



/**
 * eroshenkoam
 * 15/11/14
 */
@Path("/")
@Produces(value = javax.ws.rs.core.MediaType.TEXT_HTML)
@ErrorTemplate(name = "/error.ftl")
public class IndexResource {

    @Context HttpServletRequest httpRequest;

    @GET
    @Path("/")
    @Template(name = "/index.ftl")
    public UserContext showIndex(){
        return DatabaseProvider.getUserContext(httpRequest);
    }

    @GET
    @Path("/login")
    public Response showLogin(@Context UriInfo uriInfo) {

        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();

        if (queryParams.containsKey("code"))
        {
            String responseCode = queryParams.get("code").toString().replace("[", "").replace("]","");
            String token = DatabaseProvider.getYandexToken(responseCode);
            UserContext userContext = new UserContext(token);
            DatabaseProvider.setUserContext(httpRequest, userContext);
        }

        return javax.ws.rs.core.Response.seeOther(URI.create("/")).build();

    }

    @POST
    @Path("/logout")
    public Response logout() {
        DatabaseProvider.setUserContext(httpRequest, new UserContext());
        return javax.ws.rs.core.Response.seeOther(URI.create("/")).build();
    }
}
