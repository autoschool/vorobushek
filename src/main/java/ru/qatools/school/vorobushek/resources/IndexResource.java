package ru.qatools.school.vorobushek.resources;

import org.glassfish.jersey.server.mvc.ErrorTemplate;
import org.glassfish.jersey.server.mvc.Template;
import org.javalite.activejdbc.LazyList;
import ru.qatools.school.vorobushek.models.User;
import ru.qatools.school.vorobushek.models.UserContext;
import ru.qatools.school.vorobushek.service.DatabaseProvider;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import static java.lang.String.format;

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

        if (queryParams.containsKey("code")) {

            if (DatabaseProvider.isTestingMode()){
                String login = "test" + queryParams.get("code").toString().replace("[", "").replace("]","");
                String email = login + "@yandex.ru";
                String displayName = login.toUpperCase();
                String id = "0";
                UserContext userContext = new UserContext(DatabaseProvider.getUser(login, email, displayName, id));
                DatabaseProvider.setUserContext(httpRequest, userContext);
            }
            else {
                String responseCode = queryParams.get("code").toString().replace("[", "").replace("]","");
                String token = DatabaseProvider.getYandexToken(responseCode);
                UserContext userContext = new UserContext(token);
                DatabaseProvider.setUserContext(httpRequest, userContext);
            }

        }

        return javax.ws.rs.core.Response.seeOther(URI.create("/")).build();

    }


    @GET
    @Path("/logout")
    public Response logout() {
        DatabaseProvider.setUserContext(httpRequest, new UserContext());
        return javax.ws.rs.core.Response.seeOther(URI.create("/")).build();
    }
}
