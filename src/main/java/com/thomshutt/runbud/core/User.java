package com.thomshutt.runbud.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "USER_ID")
    @SequenceGenerator(name = "userSeq", sequenceName="USER_ID_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "userSeq")
    private long userId;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "NAME")
    private String name;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
