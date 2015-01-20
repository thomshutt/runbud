package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.User;
import io.dropwizard.views.View;

public class LoginView extends LoggedInAwareView {

    private final boolean previousLoginFailed;

    public LoginView(Optional<User> user, boolean previousLoginFailed) {
        super("login.ftl", user);
        this.previousLoginFailed = previousLoginFailed;
    }

    public boolean isPreviousLoginFailed() {
        return previousLoginFailed;
    }

}
