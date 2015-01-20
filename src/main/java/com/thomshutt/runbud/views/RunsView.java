package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.User;
import io.dropwizard.views.View;

import java.util.List;

public class RunsView extends LoggedInAwareView {

    private final List<Run> runs;

    public RunsView(Optional<User> user, List<Run> runs) {
        super("runs.ftl", user);
        this.runs = runs;
    }

    public List<Run> getRuns() {
        return runs;
    }

}
