package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.User;

public class CancelRunSuccessView extends LoggedInAwareView {

    public CancelRunSuccessView(Optional<User> user) {
        super("cancel_run_success.ftl", user);
    }

}
