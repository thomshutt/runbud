package com.thomshutt.runbud.resources;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.User;
import com.thomshutt.runbud.core.UserCredentials;
import com.thomshutt.runbud.data.UserCredentialsDAO;
import com.thomshutt.runbud.data.UserDAO;
import com.thomshutt.runbud.security.PasswordHasher;
import com.thomshutt.runbud.views.CreateUserSuccessView;
import com.thomshutt.runbud.views.CreateUserView;
import com.thomshutt.runbud.views.LoginView;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

@Path("/users")
@Produces(MediaType.TEXT_HTML)
public class UserResource {

    private static final long ONE_WEEK_MILLIS = TimeUnit.DAYS.toMillis(7);
    public static final String RUNBUD_COOKIE_KEY = "runbud.cookie";

    private final PasswordHasher passwordHasher = new PasswordHasher();

    private final UserDAO userDAO;
    private final UserCredentialsDAO userCredentialsDAO;
    private final URI URL_SITE_ROOT;

    public UserResource(UserDAO userDAO, UserCredentialsDAO userCredentialsDAO) {
        this.userDAO = userDAO;
        this.userCredentialsDAO = userCredentialsDAO;
        try {
            URL_SITE_ROOT = new URI("/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("/create")
    public View getCreateUserPage(@Auth(required = false) User user) {
        return new CreateUserView(Optional.fromNullable(user));
    }

    @POST
    @UnitOfWork
    @Path("/create")
    public CreateUserSuccessView createNewRun(
            @FormParam("name") String name,
            @FormParam("email") String email,
            @FormParam("password") String password
    ) {
        final String salt = passwordHasher.generateSalt();
        try {
            final User user = userDAO.persist(new User(email, name));
            final UserCredentials userCredentials = new UserCredentials(user.getUserId(), passwordHasher.hash(password, salt), salt, "", 0);
            userCredentials.generateNewToken(System.currentTimeMillis() + ONE_WEEK_MILLIS);
            userCredentialsDAO.persist(userCredentials);
            return new CreateUserSuccessView(Optional.fromNullable(user));
        } catch (IOException e) {
            // TODO: Handle this better
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("/login")
    public LoginView loginPage(@Auth(required = false) User user) {
        return new LoginView(Optional.fromNullable(user));
    }

    @POST
    @UnitOfWork
    @Path("/login")
    public Response login(
            @FormParam("email") String email,
            @FormParam("password") String password
    ) {
        final User user = userDAO.getForEmail(email);
        final String userId = user.getUserId();
        final UserCredentials userCredentials = userCredentialsDAO.get(userId);
        final boolean passwordMatches = passwordHasher.passwordMatches(password, userCredentials.getSalt(), userCredentials.getPassword());
        if (passwordMatches) {
            userCredentials.generateNewToken(System.currentTimeMillis() + ONE_WEEK_MILLIS);
            userCredentialsDAO.persist(userCredentials);
            final NewCookie c_ = new NewCookie(RUNBUD_COOKIE_KEY, userCredentials.getToken());
            final NewCookie c = new NewCookie(RUNBUD_COOKIE_KEY, userCredentials.getToken(), "/", c_.getDomain(), "", (int) TimeUnit.DAYS.toSeconds(7), false);
            return Response.seeOther(URL_SITE_ROOT).cookie(c).build();
        } else {
            // TODO: Something more sensible
            return Response.seeOther(URL_SITE_ROOT).build();
        }
    }

    @GET
    @UnitOfWork
    @Path("/logout")
    public Response logout() {
        final NewCookie c_ = new NewCookie(RUNBUD_COOKIE_KEY, "ok");
        return Response
                .seeOther(URL_SITE_ROOT)
                .cookie(new NewCookie(RUNBUD_COOKIE_KEY, "ok", "/", c_.getDomain(), "", 0, false))
                .build();
    }

}
