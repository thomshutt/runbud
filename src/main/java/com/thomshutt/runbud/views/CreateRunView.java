package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.User;
import com.thomshutt.runbud.resources.RunResource;
import com.thomshutt.runbud.util.LatitudeLongitude;

public class CreateRunView extends LoggedInAwareView {

    private final Optional<Run> run;
    private final Optional<String> errorMessage;
    private final boolean isEdit;

    public CreateRunView(Optional<User> user, Optional<Run> run, Optional<String> errorMessage, boolean isEdit) {
        super("create_run.ftl", user);
        this.run = run;
        this.errorMessage = errorMessage;
        this.isEdit = isEdit;
    }

    public Optional<Run> getRun() {
        return run;
    }

    public int getStartTimeHours() {
        return run.isPresent() ? run.get().getStartTimeHours() : 0;
    }

    public int getStartTimeMins() {
        return run.isPresent() ? run.get().getStartTimeMins() : 0;
    }

    public String getDescription() {
        return run.isPresent() ? run.get().getDescription() : "";
    }

    public double getStartLatitude() {
        return run.isPresent() ? run.get().getStartLatitude() : LatitudeLongitude.PICC_CIRCUS_LAT_LON.latitude;
    }

    public double getStartLongitude() {
        return run.isPresent() ? run.get().getStartLongitude() : LatitudeLongitude.PICC_CIRCUS_LAT_LON.longitude;
    }

    public String getStartAddress() {
        return run.isPresent() ? run.get().getStartAddress() : "Piccadilly Circus, London W1J, UK";
    }

    public String getRunName() {
        return run.isPresent() ? run.get().getRunName() : "";
    }

    public double getDistanceKm() {
        return run.isPresent() ? run.get().getDistanceKm() : 1;
    }

    public Long getRunId(){ return run.isPresent() ? run.get().getRunId() : null; }

    public Optional<String> getErrorMessage() {
        return errorMessage;
    }

    public boolean isEdit() {
        return isEdit;
    }
}
