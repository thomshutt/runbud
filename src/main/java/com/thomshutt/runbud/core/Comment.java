package com.thomshutt.runbud.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long commentId;

    @Column(name = "run_id")
    private long runId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_timestamp")
    private long createdTimestampUtc;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
