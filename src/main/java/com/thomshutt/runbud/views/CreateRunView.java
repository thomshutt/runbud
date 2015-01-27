package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.User;

public class CreateRunView extends LoggedInAwareView {

    private final Optional<String> errorMessage;

    public CreateRunView(Optional<User> user, Optional<String> errorMessage) {
        super("create_run.ftl", user);
        this.errorMessage = errorMessage;
    }

    public Optional<String> getErrorMessage() {
        return errorMessage;
    }

}
