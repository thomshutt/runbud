package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.User;

import java.util.List;

public class RunsView extends LoggedInAwareView {

    private final List<Run> newRuns;
    private final List<Run> oldRuns;
    private final String address;

    public RunsView(Optional<User> user, List<Run> newRuns, List<Run> oldRuns, String address) {
        super("runs.ftl", user);
        this.newRuns = newRuns;
        this.oldRuns = oldRuns;
        this.address = address;
    }

    public List<Run> getNewRuns() {
        return newRuns;
    }

    public List<Run> getOldRuns() {
        return oldRuns;
    }

    public String getAddress() {
        return address;
    }

}
