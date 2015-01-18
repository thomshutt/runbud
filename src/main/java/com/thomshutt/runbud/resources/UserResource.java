package com.thomshutt.runbud.resources;

import com.thomshutt.runbud.core.User;
import com.thomshutt.runbud.data.UserDAO;
import com.thomshutt.runbud.views.CreateUserView;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/users")
@Produces(MediaType.TEXT_HTML)
public class UserResource {

    private final UserDAO userDAO;
    private final URI URL_SITE_ROOT;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
        try {
            URL_SITE_ROOT = new URI("/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("/create")
    public View getCreateUserPage() {
        return new CreateUserView();
    }

    @POST
    @UnitOfWork
    @Path("/create")
    public void createNewRun(
            @FormParam("name") String name,
            @FormParam("email") String email,
            @FormParam("password") String password
    ) {
        userDAO.persist(new User(email, password, name));
        SiteResource.doRedirect("/");
    }

    @GET
    @UnitOfWork
    @Path("/login")
    public Response login() {
        NewCookie c_ = new NewCookie("runbud.cookie", "ok");
        NewCookie c = new NewCookie("runbud.cookie", "ok", "/", c_.getDomain(), "", c_.getMaxAge(), false);
        return Response.seeOther(URL_SITE_ROOT).cookie(c).build();
    }

    @GET
    @UnitOfWork
    @Path("/logout")
    public Response logout() {
        NewCookie c_ = new NewCookie("runbud.cookie", "ok");
        NewCookie c = new NewCookie("runbud.cookie", "ok", "/", c_.getDomain(), "", 0, false);
        return Response.seeOther(URL_SITE_ROOT).cookie(c).build();
    }

}
