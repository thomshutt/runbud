package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.User;

public class CreateRunView extends LoggedInAwareView {

    public CreateRunView(Optional<User> user) {
        super("create_run.ftl", user);
    }

}
