package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.User;

public class EditRunView extends LoggedInAwareView {

    private final Run run;

    public EditRunView(Optional<User> user, Run run) {
        super("edit_run.ftl", user);
        this.run = run;
    }

    public Run getRun() {
        return run;
    }

}
