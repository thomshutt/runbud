package com.thomshutt.runbud.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private String commentId;

    @Column(name = "run_id")
    private String runId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "comment")
    private String comment;

    public Comment() {}

    public Comment(String runId, String userId, String comment) {
        this.runId = runId;
        this.userId = userId;
        this.comment = comment;
    }

    public String getUserId() {
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
