package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.Comment;
import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.RunAttendee;
import com.thomshutt.runbud.core.User;

import java.util.List;

public class RunView extends LoggedInAwareView {

    private final Run run;
    private final User initiatingUser;
    private final List<Comment> comments;
    private final List<RunAttendee> runAttendees;
    private final boolean userIsAttending;
    private final boolean isInitiatingUser;

    public RunView(Optional<User> user, Run run, User initiatingUser, List<Comment> comments, List<RunAttendee> runAttendees) {
        super("run.ftl", user);
        this.run = run;
        this.initiatingUser = initiatingUser;
        this.comments = comments;
        this.runAttendees = runAttendees;

        boolean userIsAttending = false;
        if(user.isPresent()) {
            for (RunAttendee runAttendee : runAttendees) {
                if(runAttendee.getUserId() == user.get().getUserId()) {
                    userIsAttending = true;
                }
            }
        }
        this.userIsAttending = userIsAttending;

        this.isInitiatingUser = user.isPresent() && (user.get().getUserId() == run.getInitiatingUserId());
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

    public boolean isInitiatingUser() {
        return isInitiatingUser;
    }

}