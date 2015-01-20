package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.User;
import io.dropwizard.views.View;

public class LoggedInAwareView extends View {

    private final boolean loggedIn;

    public LoggedInAwareView(String template, Optional<User> user) {
        super(template);
        this.loggedIn = user.isPresent();
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

}
