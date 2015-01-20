package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.User;

public class CreateUserSuccessView extends LoggedInAwareView {

    public CreateUserSuccessView(Optional<User> user) {
        super("create_user_confirmation.ftl", user);
    }

}
