package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.User;
import io.dropwizard.views.View;

import java.util.List;

public class RunsView extends LoggedInAwareView {

    private final List<Run> runs;
    private final String address;

    public RunsView(Optional<User> user, List<Run> runs, String address) {
        super("runs.ftl", user);
        this.runs = runs;
        this.address = address;
    }

    public List<Run> getRuns() {
        return runs;
    }

    public String getAddress() {
        return address;
    }

}
