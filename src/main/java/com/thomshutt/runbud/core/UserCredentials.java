package com.thomshutt.runbud.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "user_credentials")
public class UserCredentials {

    @Id
    @Column(name = "userId")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "token")
    private String token;

    @Column(name = "token_expiry")
    private long tokenExpiryUtcMillis;

    public UserCredentials() {}

    public UserCredentials(String userId, String password, String salt, String token, long tokenExpiryUtcMillis) {
        this.userId = userId;
        this.password = password;
        this.salt = salt;
        this.token = token;
        this.tokenExpiryUtcMillis = tokenExpiryUtcMillis;
    }

    public String getUserId() {
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
        // TODO: Generate actual token
        this.token = "TODO!";
        this.tokenExpiryUtcMillis = newTokenExpiryUtcMillis;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
