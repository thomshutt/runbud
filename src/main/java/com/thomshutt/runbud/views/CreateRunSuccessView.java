package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.User;

public class CreateRunSuccessView extends LoggedInAwareView {

    public CreateRunSuccessView(Optional<User> user, Run run) {
        super("create_run_success.ftl", user);
    }

}
