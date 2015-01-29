package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.User;

public class CreateRunBlockedView extends LoggedInAwareView {

    public CreateRunBlockedView(Optional<User> user) {
        super("create_run_blocked.ftl", user);
    }

}
