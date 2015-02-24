package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.User;
import io.dropwizard.views.View;

public class CreateUserView extends LoggedInAwareView {

    private final String errorMessage;

    public CreateUserView(Optional<User> user) {
        this(user, null);
    }

    public CreateUserView(Optional<User> user, String errorMessage) {
        super("create_user.ftl", user);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
