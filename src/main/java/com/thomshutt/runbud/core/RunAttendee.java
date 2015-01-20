package com.thomshutt.runbud.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(
        name = "run_attendees",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "run_id",
                                "user_id"
                        }
                )
        }
)
public class RunAttendee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "run_attendee_id")
    private String runAttendeeId;

    @Column(name = "run_id")
    private String runId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "attending")
    private boolean attending;

    public RunAttendee() { }

    public RunAttendee(String runId, String userId, boolean attending) {
        this.runId = runId;
        this.userId = userId;
        this.attending = attending;
    }

    public String getRunId() {
        return runId;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isAttending() {
        return attending;
    }

    public void setAttending(boolean attending) {
        this.attending = attending;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
