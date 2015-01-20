package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.User;
import io.dropwizard.views.View;

public class HomeView extends LoggedInAwareView {

    public HomeView(Optional<User> user) {
        super("home.ftl", user);
    }

}