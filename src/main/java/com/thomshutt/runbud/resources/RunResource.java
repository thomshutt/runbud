package com.thomshutt.runbud.resources;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.Comment;
import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.RunAttendee;
import com.thomshutt.runbud.core.User;
import com.thomshutt.runbud.data.CommentDAO;
import com.thomshutt.runbud.data.RunAttendeeDAO;
import com.thomshutt.runbud.data.RunDAO;
import com.thomshutt.runbud.data.UserDAO;
import com.thomshutt.runbud.views.CreateRunView;
import com.thomshutt.runbud.views.EditRunView;
import com.thomshutt.runbud.views.RunView;
import com.thomshutt.runbud.views.RunsView;
import io.dropwizard.auth.Auth;
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
    private final RunAttendeeDAO runAttendeeDAO;

    public RunResource(
            RunDAO runDAO,
            UserDAO userDAO,
            CommentDAO commentDAO,
            RunAttendeeDAO runAttendeeDAO
    ) {
        this.runDAO = runDAO;
        this.userDAO = userDAO;
        this.commentDAO = commentDAO;
        this.runAttendeeDAO = runAttendeeDAO;
    }

    @GET
    @UnitOfWork
    public RunsView getRuns(@Auth(required = false) User user) {
        return new RunsView(Optional.fromNullable(user), runDAO.list());
    }

    @GET
    @UnitOfWork
    @Path("/{runId}")
    public RunView getRun(@Auth(required = false) User user, @PathParam("runId") String runId) {
        final Run run = runDAO.get(runId);
        final List<Comment> comments = commentDAO.listForRunId(runId);
        final List<RunAttendee> runAttendees = runAttendeeDAO.listForRunId(runId);
        final User initiatingUser = userDAO.get(run.getInitiatingUserId());
        return new RunView(Optional.fromNullable(user), run, initiatingUser, comments, runAttendees);
    }

    @POST
    @UnitOfWork
    @Path("/{runId}/comment")
    public void postComment(
            @Auth User user,
            @PathParam("runId") String runId,
            @FormParam("comment") String comment
    ) {
        commentDAO.persist(new Comment(runId, user.getUserId(), comment));
        SiteResource.doRedirect("/runs/" + runId);
    }

    @POST
    @UnitOfWork
    @Path("/{runId}/attending")
    public void markAsAttending(
            @Auth User user,
            @PathParam("runId") String runId
    ) {
        final RunAttendee attendee = runAttendeeDAO.getForRunIdAndUserId(runId, user.getUserId());
        if(attendee == null) {
            runAttendeeDAO.persist(new RunAttendee(runId, user.getUserId(), true));
        } else {
            attendee.setAttending(true);
            runAttendeeDAO.persist(attendee);
        }
        SiteResource.doRedirect("/runs/" + runId);
    }

    @POST
    @UnitOfWork
    @Path("/{runId}/unattending")
    public void markAsUnattending(
            @Auth User user,
            @PathParam("runId") String runId
    ) {
        final RunAttendee runAttendee = runAttendeeDAO.getForRunIdAndUserId(runId, user.getUserId());
        if(runAttendee != null) {
            runAttendee.setAttending(false);
            runAttendeeDAO.persist(runAttendee);
        }
        SiteResource.doRedirect("/runs/" + runId);
    }

    @GET
    @UnitOfWork
    @Path("/{runId}/edit")
    public View editRun(
            @Auth User user,
            @PathParam("runId") String runId
    ) {
        // TODO: Confirm user is owner
        // TODO: Confirm run not null
        final Run run = runDAO.get(runId);
        return new EditRunView(Optional.of(user), run);
    }

    @GET
    @UnitOfWork
    @Path("/create")
    public View getCreateRunPage(@Auth(required = false) User user) {
        return new CreateRunView(Optional.fromNullable(user));
    }

    @POST
    @UnitOfWork
    @Path("/create")
    public void createNewRun(
            @Auth User user,
            @FormParam("start_location") String startLocation,
            @FormParam("distance_km") int distanceKm,
            @FormParam("description") String description
    ) {
        runDAO.persist(new Run(user.getUserId(), startLocation, distanceKm, description));
        SiteResource.doRedirect("/runs");
    }

}
