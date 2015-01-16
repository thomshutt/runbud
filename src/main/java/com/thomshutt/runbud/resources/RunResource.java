package com.thomshutt.runbud.resources;

import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.User;
import com.thomshutt.runbud.data.RunDAO;
import com.thomshutt.runbud.data.UserDAO;
import com.thomshutt.runbud.views.RunView;
import com.thomshutt.runbud.views.RunsView;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.caching.CacheControl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    @Path("/create/{runId}")
    public void persistRun(@PathParam("runId") String runId) {
        userDAO.persist(new User("userId1", "Jeff Goldblum"));
        runDAO.persist(new Run(runId, "userId1", "Piccadilly Circus", 5.2, "Gentle jog around central London"));
    }

}
