package com.thomshutt.runbud.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "NAME")
    private String name;

    @Column(name = "HAS_IMAGE")
    private boolean hasImage;

    public User() {}

    public User(String email, String name) {
        this.name = name;
        this.email = email;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public boolean hasImage() {
        return hasImage;
    }

    public String getImageUrl() {
        if(hasImage) {
            return "/assets/users/images/" + userId + ".png";
        }
        return "/assets/img/user/" + (userId % 9) + ".gif";
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
