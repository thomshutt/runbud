package com.thomshutt.runbud.resources;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.User;
import com.thomshutt.runbud.core.UserCredentials;
import com.thomshutt.runbud.data.UserCredentialsDAO;
import com.thomshutt.runbud.data.UserDAO;
import com.thomshutt.runbud.security.PasswordHasher;
import com.thomshutt.runbud.util.email.EmailSender;
import com.thomshutt.runbud.views.CreateUserView;
import com.thomshutt.runbud.views.InformationView;
import com.thomshutt.runbud.views.LoginView;
import com.thomshutt.runbud.views.SettingsView;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;
import net.coobird.thumbnailator.Thumbnails;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

@Path("/users")
@Produces(MediaType.TEXT_HTML)
public class UserResource {

    private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    private static final long ONE_WEEK_MILLIS = TimeUnit.DAYS.toMillis(7);
    public static final String RUNBUD_COOKIE_KEY = "youmerun.cookie";

    private final PasswordHasher passwordHasher = new PasswordHasher();

    private final UserDAO userDAO;
    private final UserCredentialsDAO userCredentialsDAO;
    private final EmailSender emailSender;
    private final URI URL_SITE_ROOT;
    private final URI URL_RUNS;

    public UserResource(
            UserDAO userDAO,
            UserCredentialsDAO userCredentialsDAO,
            EmailSender emailSender
    ) {
        this.userDAO = userDAO;
        this.userCredentialsDAO = userCredentialsDAO;
        this.emailSender = emailSender;
        try {
            URL_SITE_ROOT = new URI("/");
            URL_RUNS = new URI("/runs");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @UnitOfWork
    @Path("/create")
    public View createUser(@Auth(required = false) User user) {
        return new CreateUserView(Optional.fromNullable(user));
    }

    @POST
    @UnitOfWork
    @Path("/create")
    public View doCreateUser(
            @FormParam("name") String name,
            @FormParam("email") String email,
            @FormParam("password") String password
    ) {
        final User existingUser = userDAO.getForEmail(email);
        if(existingUser != null) {
            return new CreateUserView(Optional.<User>absent(), "Looks like someone has already registered with that Email Address");
        }
        final String salt = passwordHasher.generateSalt();
        try {
            final User user = userDAO.persist(new User(email, name));
            final UserCredentials userCredentials = new UserCredentials(user.getUserId(), passwordHasher.hash(password, salt), salt, "", 0);
            userCredentials.generateNewToken(System.currentTimeMillis() + ONE_WEEK_MILLIS);
            userCredentialsDAO.persist(userCredentials);
            emailSender.sendSignupSuccessMessage(name, email);
            return new LoginView(Optional.<User>absent(), false, true);
        } catch (IOException e) {
            return new CreateUserView(Optional.<User>absent(), "Whoops! Something went wrong, please try again.");
        }
    }

    @GET
    @UnitOfWork
    @Path("/login")
    public LoginView loginPage(@Auth(required = false) User user) {
        return new LoginView(Optional.fromNullable(user), false, false);
    }

    @POST
    @UnitOfWork
    @Path("/login")
    public Response login(
            @FormParam("email") String email,
            @FormParam("password") String password
    ) {
        final View loginFailureView = new LoginView(Optional.<User>absent(), true, false);
        final User user = userDAO.getForEmail(email);
        if(user == null) {
            return Response.ok().entity(loginFailureView).build();
        }
        final long userId = user.getUserId();
        final UserCredentials userCredentials = userCredentialsDAO.get(userId);
        final boolean passwordMatches = passwordHasher.passwordMatches(password, userCredentials.getSalt(), userCredentials.getPassword());
        if (passwordMatches) {
            userCredentials.generateNewToken(System.currentTimeMillis() + ONE_WEEK_MILLIS);
            userCredentialsDAO.persist(userCredentials);
            final NewCookie c_ = new NewCookie(RUNBUD_COOKIE_KEY, userCredentials.getToken());
            final NewCookie c = new NewCookie(RUNBUD_COOKIE_KEY, userCredentials.getToken(), "/", c_.getDomain(), "", (int) TimeUnit.DAYS.toSeconds(7), false);
            return Response.seeOther(URL_RUNS).cookie(c).build();
        } else {
            return Response.ok().entity(loginFailureView).build();
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

    @GET
    @UnitOfWork
    @Path("/settings")
    public SettingsView settingsPage(@Auth(required = true) User user) {
        return new SettingsView(Optional.fromNullable(user));
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/settings")
    public SettingsView uploadImage(
            @Auth(required = true) User user,
            @FormDataParam("image") FormDataContentDisposition contentDispositionHeader,
            @FormDataParam("image") InputStream fileInputStream
    ) {
        // TODO: Get from settings
        try {
            Thumbnails.of(fileInputStream)
                    .size(160, 160)
                    .outputFormat("png")
                    .toFile(new File("/tmp/", user.getUserId() + ".png"));

            user.setHasImage(true);
            userDAO.persist(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new SettingsView(Optional.fromNullable(user));
    }

}
