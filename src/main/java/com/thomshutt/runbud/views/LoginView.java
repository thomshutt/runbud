package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.User;
import io.dropwizard.views.View;

public class LoginView extends LoggedInAwareView {

    public LoginView(Optional<User> user) {
        super("login.ftl", user);
    }

}
