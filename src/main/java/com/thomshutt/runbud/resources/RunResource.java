package com.thomshutt.runbud.resources;

import com.thomshutt.runbud.core.Comment;
import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.User;
import com.thomshutt.runbud.data.CommentDAO;
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
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Path("/runs")
@Produces(MediaType.TEXT_HTML)
public class RunResource {

    private final RunDAO runDAO;
    private final UserDAO userDAO;
    private final CommentDAO commentDAO;

    public RunResource(RunDAO runDAO, UserDAO userDAO, CommentDAO commentDAO) {
        this.runDAO = runDAO;
        this.userDAO = userDAO;
        this.commentDAO = commentDAO;
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
        final List<Comment> comments = commentDAO.listForRunId(runId);
        final User initiatingUser = userDAO.get(run.getInitiatingUserId());
        return new RunView(run, initiatingUser, comments);
    }

    @POST
    @UnitOfWork
    @Path("/{runId}/comment")
    @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.SECONDS)
    public void postComment(
            @PathParam("runId") String runId,
            @FormParam("comment") String comment
    ) {
        final User newUser = userDAO.persist(
                new User(
                        (new Random(System.currentTimeMillis())).nextInt() + "@test.com",
                        "password",
                        "Jeff Goldblum"
                )
        );
        commentDAO.persist(new Comment(runId, newUser.getUserId(), comment));
        SiteResource.doRedirect("/runs/" + runId);
    }

    @GET
    @UnitOfWork
    @Path("/create/new")
    public void persistRun() {
        final User newUser = userDAO.persist(new User("test@test.com", "password", "Jeff Goldblum"));
        runDAO.persist(new Run(newUser.getUserId(), "Piccadilly Circus", 5.2, "Gentle jog around central London"));
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
        SiteResource.doRedirect("/runs");
    }

}
