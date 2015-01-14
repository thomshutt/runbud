package com.thomshutt.runbud.views;

import com.thomshutt.runbud.core.Run;
import io.dropwizard.views.View;

public class RunView extends View {

    private final Run run;

    public RunView(Run run) {
        super("run.ftl");
        this.run = run;
    }

    public Run getRun() {
        return run;
    }

}