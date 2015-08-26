package com.thomshutt.runbud.resources;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.thomshutt.runbud.core.Comment;
import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.RunAttendee;
import com.thomshutt.runbud.core.User;
import com.thomshutt.runbud.data.CommentDAO;
import com.thomshutt.runbud.data.RunAttendeeDAO;
import com.thomshutt.runbud.data.RunDAO;
import com.thomshutt.runbud.data.UserDAO;
import com.thomshutt.runbud.util.LatitudeLongitude;
import com.thomshutt.runbud.util.NewestThenClosestComparator;
import com.thomshutt.runbud.util.TimezoneToDateConverter;
import com.thomshutt.runbud.util.email.EmailSender;
import com.thomshutt.runbud.views.CancelRunSuccessView;
import com.thomshutt.runbud.views.CreateRunView;
import com.thomshutt.runbud.views.InformationView;
import com.thomshutt.runbud.views.RunView;
import com.thomshutt.runbud.views.RunsView;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;

@Path("/runs")
@Produces(MediaType.TEXT_HTML)
public class RunResource {

    private static final int MAX_RUNS_PER_USER = 2;

    private final RunDAO runDAO;
    private final UserDAO userDAO;
    private final CommentDAO commentDAO;
    private final RunAttendeeDAO runAttendeeDAO;
    private EmailSender emailSender;

    public RunResource(
            RunDAO runDAO,
            UserDAO userDAO,
            CommentDAO commentDAO,
            RunAttendeeDAO runAttendeeDAO,
            EmailSender emailSender
    ) {
        this.runDAO = runDAO;
        this.userDAO = userDAO;
        this.commentDAO = commentDAO;
        this.runAttendeeDAO = runAttendeeDAO;
        this.emailSender = emailSender;
    }

    @GET
    @UnitOfWork
    public RunsView getRuns(@Auth(required = false) User user) {
        return getRunsLatLon(user, null, null, "Piccadilly Circus, London W1D 7ET");
    }

    @POST
    @UnitOfWork
    public RunsView getRunsWithLatLon(@Auth(required = false) User user, @FormParam("address") String address) {
        final LatitudeLongitude latitudeLongitude = LatitudeLongitude.fromAddress(address);
        return getRunsLatLon(user, latitudeLongitude.latitude, latitudeLongitude.longitude, address);
    }

    @GET
    @UnitOfWork
    @Path("/{userLatitude}/{userLongitude}/{address}")
    public RunsView getRunsLatLon(
            @Auth(required = false) User user,
            @PathParam("userLatitude") Double userLatitude,
            @PathParam("userLongitude") Double userLongitiude,
            @PathParam("address") String address
    ) {
        final LatitudeLongitude userLatLon = userLatitude != null && userLongitiude != null ? new LatitudeLongitude(userLatitude, userLongitiude) : LatitudeLongitude.PICC_CIRCUS_LAT_LON;

        final List<Run> list = runDAO.list(
                TimezoneToDateConverter.getCurrentTimeUtc() - TimeUnit.DAYS.toMillis(1)
        );

        final NewestThenClosestComparator orderer =
                new NewestThenClosestComparator(TimezoneToDateConverter.getCurrentTimeUtc(), userLatLon);

        return new RunsView(
                Optional.fromNullable(user),
                filterRuns(list, false, orderer),
                filterRuns(list, true, orderer),
                address
        );
    }

    private List<Run> filterRuns(List<Run> runs, boolean happened, NewestThenClosestComparator orderer) {
        final List <Run> filteredRuns = Lists.newArrayList();
        for (Run run : runs) {
            if(run.alreadyHappened(TimezoneToDateConverter.getCurrentTimeUtc()) == happened) {
                filteredRuns.add(run);
            }
        }
        Collections.sort(filteredRuns, orderer);
        return filteredRuns;
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

        final Run run = runDAO.get(runId);
        if(run.getInitiatingUserId() != user.getUserId()) {
            final User runOwner = userDAO.get(run.getInitiatingUserId());
            emailSender.sendSomeoneCommentedOnYourRunMessage(
                    runOwner.getName(),
                    runOwner.getEmail(),
                    user.getName(),
                    comment,
                    run
            );
        }

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

        final Run run = runDAO.get(runId);
        final User runOwner = userDAO.get(run.getInitiatingUserId());
        emailSender.sendSomeoneJoinedYourRunMessage(runOwner.getName(), runOwner.getEmail(), user.getName(), run);

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
        final Run run = runDAO.get(runId);
        if(run == null || run.getInitiatingUserId() != user.getUserId()) {
            SiteResource.doRedirect("/runs/" + runId);
            return null;
        }

        return new CreateRunView(Optional.of(user), Optional.of(run), Optional.<String>absent(), true);
    }

    @POST
    @UnitOfWork
    @Path("/{runId}/edit")
    public CreateRunView doEditRun(
            @Auth User user,
            @PathParam("runId") long runId,
            @FormParam("start_latitude") double startLatitude,
            @FormParam("start_longitude") double startLongitude,
            @FormParam("start_time_hours") int startTimeHours,
            @FormParam("start_time_mins") int startTimeMins,
            @FormParam("start_address") String startAddress,
            @FormParam("distance_km") int distanceKm,
            @FormParam("run_name") String runName,
            @FormParam("description") String description
    ) {
        // TODO: Remove duplication with create

        final Run run = runDAO.get(runId);
        if(run == null || run.getInitiatingUserId() != user.getUserId()) {
            SiteResource.doRedirect("/runs/" + runId);
            return null;
        }

        if(startTimeHours > 23 || startTimeHours < 0) {
            return new CreateRunView(Optional.of(user), Optional.of(run), Optional.of("Invalid value for 'Hours'"), true);
        }
        if(startTimeMins > 59 || startTimeMins < 0 || startTimeMins % 15 != 0) {
            return new CreateRunView(Optional.of(user), Optional.of(run), Optional.of("Invalid value for 'Minutes'"), true);
        }
        if(distanceKm < 0) {
            return new CreateRunView(Optional.of(user), Optional.of(run), Optional.of("Distance must be at least 0!"), true);
        }
        if(StringUtils.isBlank(runName)) {
            return new CreateRunView(Optional.of(user), Optional.of(run), Optional.of("You need to give your run a name!"), true);
        }
        if(StringUtils.isBlank(description)) {
            return new CreateRunView(Optional.of(user), Optional.of(run), Optional.of("Let people know a bit more about your run with a description."), true);
        }

        run.setStartLatitude(startLatitude);
        run.setStartLongitude(startLongitude);
        run.setStartTimeHours(startTimeHours);
        run.setStartTimeMins(startTimeMins);
        run.setStartAddress(startAddress);
        run.setDistanceKm(distanceKm);
        run.setRunName(runName);
        run.setDescription(description);
        run.setDate(TimezoneToDateConverter.getUtcForCurrentDay(startLatitude, startLongitude, startTimeHours, startTimeMins));
        runDAO.persist(run);
        SiteResource.doRedirect("/runs/" + runId);
        return null;
    }

    @POST
    @UnitOfWork
    @Path("/{runId}/cancel")
    public View doCancelRun(
            @Auth User user,
            @PathParam("runId") long runId
    ) {
        final Run run = runDAO.get(runId);
        if(run == null || run.getInitiatingUserId() != user.getUserId()) {
            SiteResource.doRedirect("/runs/" + runId);
            return null;
        }

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
        return new CreateRunView(Optional.fromNullable(user), Optional.<Run>absent(), Optional.<String>absent(), false);
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
                TimezoneToDateConverter.getUtcForCurrentDay(startLatitude, startLongitude, startTimeHours, startTimeMins),
                startTimeHours,
                startTimeMins,
                runName,
                description,
                user.hasImage() ? user.getImageUrl() : "/assets/img/default_run.png"
        );

        if(startTimeHours > 23 || startTimeHours < 0) {
            return new CreateRunView(Optional.of(user), Optional.of(runValues), Optional.of("Invalid value for 'Hours'"), false);
        }
        if(startTimeMins > 59 || startTimeMins < 0 || startTimeMins % 15 != 0) {
            return new CreateRunView(Optional.of(user), Optional.of(runValues), Optional.of("Invalid value for 'Minutes'"), false);
        }
        if(distanceKm < 0) {
            return new CreateRunView(Optional.of(user), Optional.of(runValues), Optional.of("Distance must be at least 0!"), false);
        }
        if(StringUtils.isBlank(runName)) {
            return new CreateRunView(Optional.of(user), Optional.of(runValues), Optional.of("You need to give your run a name!"), false);
        }
        if(StringUtils.isBlank(description)) {
            return new CreateRunView(Optional.of(user), Optional.of(runValues), Optional.of("Let people know a bit more about your run with a description."), false);
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
