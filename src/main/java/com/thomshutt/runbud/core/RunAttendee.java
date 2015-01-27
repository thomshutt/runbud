package com.thomshutt.runbud.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(
        name = "RUN_ATTENDEES",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "RUN_ID",
                                "USER_ID"
                        }
                )
        }
)
public class RunAttendee {

    @Id
    @Column(name = "RUN_ATTENDEE_ID")
    @SequenceGenerator(name = "runAttendeeSeq", sequenceName="RUN_ATTENDEE_ID_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "runAttendeeSeq")
    private long runAttendeeId;

    @Column(name = "RUN_ID")
    private long runId;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "ATTENDING")
    private boolean attending;

    public RunAttendee() { }

    public RunAttendee(long runId, long userId, boolean attending) {
        this.runId = runId;
        this.userId = userId;
        this.attending = attending;
    }

    public long getRunId() {
        return runId;
    }

    public long getUserId() {
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
