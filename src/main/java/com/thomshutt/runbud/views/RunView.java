package com.thomshutt.runbud.views;

import com.thomshutt.runbud.core.Comment;
import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.User;
import io.dropwizard.views.View;

import java.util.List;

public class RunView extends View {

    private final Run run;
    private final User initiatingUser;
    private final List<Comment> comments;

    public RunView(Run run, User initiatingUser, List<Comment> comments) {
        super("run.ftl");
        this.run = run;
        this.initiatingUser = initiatingUser;
        this.comments = comments;
    }

    public Run getRun() {
        return run;
    }

    public User getInitiatingUser() {
        return initiatingUser;
    }

    public List<Comment> getComments() {
        return comments;
    }

}