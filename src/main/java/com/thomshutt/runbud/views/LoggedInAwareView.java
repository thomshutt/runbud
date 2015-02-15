package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.User;
import io.dropwizard.views.View;

public class LoggedInAwareView extends View {

    private final boolean loggedIn;
    private final Optional<User> user;

    public LoggedInAwareView(String template, Optional<User> user) {
        super(template);
        this.user = user;
        this.loggedIn = user.isPresent();
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public User getUser() {
        return user.get();
    }

}
