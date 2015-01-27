package com.thomshutt.runbud.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "COMMENTS")
public class Comment {

    @Id
    @Column(name = "COMMENT_ID")
    @SequenceGenerator(name = "commentSeq", sequenceName="COMMENT_ID_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "commentSeq")
    private long commentId;

    @Column(name = "RUN_ID")
    private long runId;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "CREATED_TIMESTAMP")
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
