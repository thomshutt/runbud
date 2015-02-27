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
import com.thomshutt.runbud.util.LatitudeLongitude;
import com.thomshutt.runbud.util.image.ImageFetcher;
import com.thomshutt.runbud.util.image.InstagramImageFetcher;
import com.thomshutt.runbud.views.*;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Path("/runs")
@Produces(MediaType.TEXT_HTML)
public class RunResource {

    private static final int MAX_RUNS_PER_USER = 2;
    public static final LatitudeLongitude PICC_CIRCUS_LAT_LON = new LatitudeLongitude(51.510730378916186, -0.13398630345454876);

    private final RunDAO runDAO;
    private final UserDAO userDAO;
    private final CommentDAO commentDAO;
    private final RunAttendeeDAO runAttendeeDAO;
    private final ImageFetcher imageFetcher;

    public RunResource(
            RunDAO runDAO,
            UserDAO userDAO,
            CommentDAO commentDAO,
            RunAttendeeDAO runAttendeeDAO,
            ImageFetcher imageFetcher) {
        this.runDAO = runDAO;
        this.userDAO = userDAO;
        this.commentDAO = commentDAO;
        this.runAttendeeDAO = runAttendeeDAO;
        this.imageFetcher = imageFetcher;
    }

    @GET
    @UnitOfWork
    public RunsView getRuns(@Auth(required = false) User user) {
        return getRunsLatLon(user, null, null);
    }

    @GET
    @UnitOfWork
    @Path("/{userLatitude}/{userLongitude}")
    public RunsView getRunsLatLon(
            @Auth(required = false) User user,
            @PathParam("userLatitude") Long userLatitude,
            @PathParam("userLongitude") Long userLongitiude
    ) {
        final LatitudeLongitude userLatLon = userLatitude != null && userLongitiude != null ? new LatitudeLongitude(userLatitude, userLongitiude) : PICC_CIRCUS_LAT_LON;
        final List<Run> list = runDAO.list();
        Collections.sort(list, new Comparator<Run>() {
            @Override
            public int compare(Run run, Run run2) {
                final LatitudeLongitude runStart = new LatitudeLongitude(run.getStartLatitude(), run.getStartLongitude());
                final LatitudeLongitude run2Start = new LatitudeLongitude(run2.getStartLatitude(), run2.getStartLongitude());

                return (int) Math.round(
                        LatitudeLongitude.calculateDistanceKmBetween(userLatLon, runStart) -
                        LatitudeLongitude.calculateDistanceKmBetween(userLatLon, run2Start)
                );
            }
        });
        return new RunsView(Optional.fromNullable(user), list);
    }

    @GET
    @UnitOfWork
    @Path("/{runId}")
    public RunView getRun(@Auth(required = false) User user, @PathParam("runId") long runId) {
        final Run run = runDAO.get(runId);

        final List<Comment> comments = commentDAO.listForRunId(runId);
        for (Comment comment : comments) {
            final User commentImageUrl = userDAO.get(comment.getUserId());
            comment.setUserName(commentImageUrl.getName());
            comment.setUserImageUrl(commentImageUrl.getImageUrl());
        }

        final List<RunAttendee> runAttendees = runAttendeeDAO.listForRunId(runId);
        for (RunAttendee runAttendee : runAttendees) {
            runAttendee.setImageUrl(userDAO.get(runAttendee.getUserId()).getImageUrl());
        }
        final User initiatingUser = userDAO.get(run.getInitiatingUserId());

        return new RunView(Optional.fromNullable(user), run, initiatingUser, comments, runAttendees);
    }

    @POST
    @UnitOfWork
    @Path("/{runId}/comment")
    public void postComment(
            @Auth User user,
            @PathParam("runId") long runId,
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
            @PathParam("runId") long runId
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
            @PathParam("runId") long runId
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
            @PathParam("runId") long runId
    ) {
        // TODO: Confirm user is owner
        // TODO: Confirm run not null
        final Run run = runDAO.get(runId);
        return new EditRunView(Optional.of(user), run);
    }

    @POST
    @UnitOfWork
    @Path("/{runId}/edit")
    public void doEditRun(
            @Auth User user,
            @PathParam("runId") long runId,
            @FormParam("start_latitude") double startLatitude,
            @FormParam("start_longitude") double startLongitude,
            @FormParam("start_address") String startAddress,
            @FormParam("distance_km") int distanceKm,
            @FormParam("description") String description
    ) {
        // TODO: Confirm user is owner
        // TODO: Confirm run not null
        final Run run = runDAO.get(runId);
        run.setDescription(description);
        run.setDistanceKm(distanceKm);
        run.setStartLatitude(startLatitude);
        run.setStartLongitude(startLongitude);
        run.setStartAddress(startAddress);
        runDAO.persist(run);
        SiteResource.doRedirect("/runs/" + runId);
    }

    @POST
    @UnitOfWork
    @Path("/{runId}/cancel")
    public View doCancelRun(
            @Auth User user,
            @PathParam("runId") long runId
    ) {
        // TODO: Confirm user is owner
        final Run run = runDAO.get(runId);
        run.setCancelled(true);
        runDAO.persist(run);
        return new CancelRunSuccessView(Optional.of(user));
    }

    @GET
    @UnitOfWork
    @Path("/create")
    public View getCreateRunPage(@Auth User user) {
        final List<Run> runs = runDAO.listForInitiatingUser(user);
        if(runs.size() == MAX_RUNS_PER_USER) {
            return new InformationView(
                    Optional.of(user),
                    "/assets/img/default_run.png",
                    "Sorry!",
                    "You can't create more than two runs each day."
            );
        }
        return new CreateRunView(Optional.fromNullable(user), Optional.<Run>absent(), Optional.<String>absent());
    }

    @POST
    @UnitOfWork
    @Path("/create")
    public View createNewRun(
            @Auth User user,
            @FormParam("start_latitude") double startLatitude,
            @FormParam("start_longitude") double startLongitude,
            @FormParam("start_time_hours") int startTimeHours,
            @FormParam("start_time_mins") int startTimeMins,
            @FormParam("start_address") String startAddress,
            @FormParam("distance_km") int distanceKm,
            @FormParam("run_name") String runName,
            @FormParam("description") String description
    ) {
        final List<Run> runs = runDAO.listForInitiatingUser(user);
        if(runs.size() == MAX_RUNS_PER_USER) {
            return new InformationView(
                    Optional.of(user),
                    "/assets/img/default_run.png",
                    "Sorry!",
                    "You can't create more than two runs each day."
            );
        }

        final Run runValues = new Run(
                user.getUserId(),
                startLatitude,
                startLongitude,
                startAddress,
                distanceKm,
                startTimeHours,
                startTimeMins,
                runName,
                description,
                user.hasImage() ? user.getImageUrl() : "/assets/img/default_run.png"
        );

        if(startTimeHours > 23 || startTimeHours < 0) {
            return new CreateRunView(Optional.of(user), Optional.of(runValues), Optional.of("Invalid value for 'Hours'"));
        }
        if(startTimeMins > 59 || startTimeMins < 0 || startTimeMins % 15 != 0) {
            return new CreateRunView(Optional.of(user), Optional.of(runValues), Optional.of("Invalid value for 'Minutes'"));
        }
        if(distanceKm < 0) {
            return new CreateRunView(Optional.of(user), Optional.of(runValues), Optional.of("Distance must be at least 0!"));
        }
        if(StringUtils.isBlank(runName)) {
            return new CreateRunView(Optional.of(user), Optional.of(runValues), Optional.of("You need to give your run a name!"));
        }
        if(StringUtils.isBlank(description)) {
            return new CreateRunView(Optional.of(user), Optional.of(runValues), Optional.of("Let people know a bit more about your run with a description."));
        }

        final Run run = runDAO.persist(runValues);

        return new InformationView(
                Optional.of(user),
                run.getImageUrl(),
                "Run Created!",
                "Your run has been created, click <a href='/runs/" + run.getRunId() + "'>here</a> to view it."
        );
    }

}
