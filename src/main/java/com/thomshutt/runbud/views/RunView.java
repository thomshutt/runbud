package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.Comment;
import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.RunAttendee;
import com.thomshutt.runbud.core.User;
import io.dropwizard.views.View;

import java.util.List;

public class RunView extends LoggedInAwareView {

    private final Run run;
    private final User initiatingUser;
    private final List<Comment> comments;
    private final List<RunAttendee> runAttendees;
    private final boolean userIsAttending;

    public RunView(Optional<User> user, Run run, User initiatingUser, List<Comment> comments, List<RunAttendee> runAttendees) {
        super("run.ftl", user);
        this.run = run;
        this.initiatingUser = initiatingUser;
        this.comments = comments;
        this.runAttendees = runAttendees;

        boolean userIsAttending = false;
        if(user.isPresent()) {
            for (RunAttendee runAttendee : runAttendees) {
                if(runAttendee.getUserId().equals(user.get().getUserId())) {
                    userIsAttending = true;
                }
            }
        }
        this.userIsAttending = userIsAttending;
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

    public List<RunAttendee> getRunAttendees() {
        return runAttendees;
    }

    public boolean isUserIsAttending() {
        return userIsAttending;
    }
}