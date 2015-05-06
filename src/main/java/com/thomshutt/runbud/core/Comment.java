package com.thomshutt.runbud.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @Column(name = "COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(name = "RUN_ID")
    private long runId;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "CREATED_TIMESTAMP")
    private long createdTimestampUtc;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_IMAGE_URL")
    private String userImageUrl;

    public Comment() {}

    public Comment(long runId, long userId, String comment) {
        this.runId = runId;
        this.userId = userId;
        this.comment = comment;
        this.createdTimestampUtc = System.currentTimeMillis();
    }

    public long getUserId() {
        return userId;
    }

    public String getComment() {
        return comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
