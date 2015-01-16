package com.thomshutt.runbud.resources;

import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.User;
import com.thomshutt.runbud.data.RunDAO;
import com.thomshutt.runbud.data.UserDAO;
import com.thomshutt.runbud.views.CreateRunView;
import com.thomshutt.runbud.views.RunView;
import com.thomshutt.runbud.views.RunsView;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.caching.CacheControl;
import io.dropwizard.views.View;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

@Path("/runs")
@Produces(MediaType.TEXT_HTML)
public class RunResource {

    private final RunDAO runDAO;
    private final UserDAO userDAO;

    public RunResource(RunDAO runDAO, UserDAO userDAO) {
        this.runDAO = runDAO;
        this.userDAO = userDAO;
    }

    @GET
    @UnitOfWork
    @CacheControl(maxAge = 6, maxAgeUnit = TimeUnit.HOURS)
    public RunsView getRuns() {
        return new RunsView(runDAO.list());
    }

    @GET
    @UnitOfWork
    @Path("/{runId}")
    @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.SECONDS)
    public RunView getRun(@PathParam("runId") String runId) {
        final Run run = runDAO.get(runId);
        return new RunView(run, userDAO.get(run.getInitiatingUserId()));
    }

    @GET
    @UnitOfWork
    @Path("/create/new")
    public void persistRun() {
        userDAO.persist(new User("userId1", "Jeff Goldblum"));
        runDAO.persist(new Run("userId1", "Piccadilly Circus", 5.2, "Gentle jog around central London"));
    }

    @GET
    @Path("/create")
    public View getCreateRunPage() {
        return new CreateRunView();
    }

    @POST
    @UnitOfWork
    @Path("/create")
    public void createNewRun(
            @FormParam("initiating_user_id") String initiatingUserId,
            @FormParam("start_location") String startLocation,
            @FormParam("distance_km") int distanceKm,
            @FormParam("description") String description
    ) {
        runDAO.persist(new Run(initiatingUserId, startLocation, distanceKm, description));
        doRedirect("/runs");
    }

    private void doRedirect(String url) {
        try {
            throw new WebApplicationException(
                    Response.seeOther(
                            new URI(url)
                    ).build()
            );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
