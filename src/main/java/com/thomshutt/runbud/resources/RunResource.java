package com.thomshutt.runbud.resources;

import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.data.RunDAO;
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

    public RunResource(RunDAO runDAO) {
        this.runDAO = runDAO;
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
        return new RunView(runDAO.get(runId));
    }

    @GET
    @UnitOfWork
    @Path("/create/{runId}")
    public void persistRun(@PathParam("runId") String runId) {
        runDAO.persist(new Run(runId));
    }

}
