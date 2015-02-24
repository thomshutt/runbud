package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.User;
import io.dropwizard.views.View;

public class LoginView extends LoggedInAwareView {

    private final boolean previousLoginFailed;
    private boolean postSignup;

    public LoginView(Optional<User> user, boolean previousLoginFailed, boolean postSignup) {
        super("login.ftl", user);
        this.previousLoginFailed = previousLoginFailed;
        this.postSignup = postSignup;
    }

    public boolean isPreviousLoginFailed() {
        return previousLoginFailed;
    }

    public boolean isPostSignup() {
        return postSignup;
    }

}