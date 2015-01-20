package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.User;
import io.dropwizard.views.View;

public class CreateUserView extends LoggedInAwareView {

    public CreateUserView(Optional<User> user) {
        super("create_user.ftl", user);
    }

}
