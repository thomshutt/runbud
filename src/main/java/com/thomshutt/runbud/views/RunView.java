package com.thomshutt.runbud.views;

import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.User;
import io.dropwizard.views.View;

public class RunView extends View {

    private final Run run;
    private final User initiatingUser;

    public RunView(Run run, User initiatingUser) {
        super("run.ftl");
        this.run = run;
        this.initiatingUser = initiatingUser;
    }

    public Run getRun() {
        return run;
    }

    public User getInitiatingUser() {
        return initiatingUser;
    }

}