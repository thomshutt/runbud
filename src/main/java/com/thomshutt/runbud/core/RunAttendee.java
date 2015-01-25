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
    @Column(name = "run_attendee_id")
    @SequenceGenerator(name = "runAttendeeSeq", sequenceName="run_attendee_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "runAttendeeSeq")
    private long runAttendeeId;

    @Column(name = "run_id")
    private long runId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "attending")
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
