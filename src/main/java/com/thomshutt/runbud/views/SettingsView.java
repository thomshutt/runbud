package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.User;

public class SettingsView extends LoggedInAwareView {

    public SettingsView(Optional<User> user) {
        super("settings.ftl", user);
    }

}