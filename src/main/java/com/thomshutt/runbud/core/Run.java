package com.thomshutt.runbud.core;

import javax.persistence.*;

@Entity
@Table(name = "runs")
public class Run {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "run_id")
    private String runId;

    @Column(name = "initiating_user_id")
    private String initiatingUserId;

    @Column(name = "start_location")
    private String startLocation;

    @Column(name = "distance_km")
    private double distanceKm;

    @Column(name = "description")
    private String description;

    public Run() {
    }

    public Run(String initiatingUserId, String startLocation, double distanceKm, String description) {
        this.runId = runId;
        this.initiatingUserId = initiatingUserId;
        this.startLocation = startLocation;
        this.distanceKm = distanceKm;
        this.description = description;
    }

    public String getName() {
        return runId;
    }

    public String getInitiatingUserId() {
        return initiatingUserId;
    }

    public String getRunId() {
        return runId;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public String getDescription() {
        return description;
    }
}
