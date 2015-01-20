package com.thomshutt.runbud.resources;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.User;
import com.thomshutt.runbud.views.HomeView;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.caching.CacheControl;
import io.dropwizard.views.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class SiteResource {

    @GET
    @UnitOfWork
    @CacheControl(maxAge = 0, maxAgeUnit = TimeUnit.SECONDS)
    public View getHomepage(@Auth(required = false) User user) {
        return new HomeView(Optional.fromNullable(user));
    }

    public static void doRedirect(String url) {
        try {
            throw new WebApplicationException(
                    Response.seeOther(new URI(url)).build()
            );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
