package com.thomshutt.runbud.health;

import com.codahale.metrics.health.HealthCheck;
import com.thomshutt.runbud.resources.RunResource;

public class RunResourceHealthCheck extends HealthCheck {

    private final RunResource runResource;

    public RunResourceHealthCheck(RunResource runResource) {
        this.runResource = runResource;
    }

    @Override
    protected Result check() throws Exception {
        if(runResource.getRuns(null).getNewRuns().size() == 0) {
            return Result.unhealthy("No runs found...");
        }
        return Result.healthy();
    }

}
