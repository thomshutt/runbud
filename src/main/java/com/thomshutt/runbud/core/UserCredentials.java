package com.thomshutt.runbud.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_credentials")
public class UserCredentials {

    @Id
    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "SALT")
    private String salt;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "TOKEN_EXPIRY")
    private long tokenExpiryUtcMillis;

    public UserCredentials() {}

    public UserCredentials(long userId, String password, String salt, String token, long tokenExpiryUtcMillis) {
        this.userId = userId;
        this.password = password;
        this.salt = salt;
        this.token = token;
        this.tokenExpiryUtcMillis = tokenExpiryUtcMillis;
    }

    public long getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getToken() {
        return token;
    }

    public long getTokenExpiryUtcMillis() {
        return tokenExpiryUtcMillis;
    }

    public void generateNewToken(long newTokenExpiryUtcMillis) {
        this.token = UUID.randomUUID().toString();
        this.tokenExpiryUtcMillis = newTokenExpiryUtcMillis;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
