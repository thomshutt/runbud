package com.thomshutt.runbud.views;

import com.google.common.collect.ImmutableList;
import com.thomshutt.runbud.core.Run;
import io.dropwizard.views.View;

import java.util.List;

public class RunsView extends View {

    private final List<Run> runs;

    public RunsView(List<Run> runs) {
        super("runs.ftl");
        this.runs = runs;
    }

    public List<Run> getRuns() {
        return runs;
    }

}
