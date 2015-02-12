package com.thomshutt.runbud.views;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.User;

public class InformationView extends LoggedInAwareView {

    private final boolean hasImageUrl;
    private final String imageUrl;

    private final boolean hasHeaderText;
    private final String headerText;

    private final boolean hasInformationText;
    private final String informationText;

    public InformationView(Optional<User> user, String imageUrl, String headerText, String informationText) {
        super("information.ftl", user);

        this.imageUrl = imageUrl;
        this.hasImageUrl = imageUrl != null && imageUrl.trim().length() != 0;

        this.headerText = headerText;
        this.hasHeaderText = headerText != null && headerText.trim().length() != 0;

        this.informationText = informationText;
        this.hasInformationText = informationText != null && informationText.trim().length() != 0;
    }

    public boolean isHasImageUrl() {
        return hasImageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isHasHeaderText() {
        return hasHeaderText;
    }

    public String getHeaderText() {
        return headerText;
    }

    public boolean isHasInformationText() {
        return hasInformationText;
    }

    public String getInformationText() {
        return informationText;
    }

}