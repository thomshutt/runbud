package com.thomshutt.runbud.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "runs")
public class Run {

    @Id
    @Column(name = "run_id")
    @SequenceGenerator(name = "runSeq", sequenceName="run_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "runSeq")
    private long runId;

    @Column(name = "initiating_user_id")
    private long initiatingUserId;

    @Column(name = "start_latitude")
    private double startLatitude;

    @Column(name = "start_longitude")
    private double startLongitude;

    @Column(name = "start_address")
    private String startAddress;

    @Column(name = "distance_km")
    private double distanceKm;

    @Column(name = "start_time_hours")
    private int startTimeHours;

    @Column(name = "start_time_mins")
    private int startTimeMins;

    @Column(name = "run_name")
    private String runName;

    @Column(name = "description")
    private String description;

    @Column(name = "is_cancelled")
    private boolean isCancelled;

    public Run() {
    }

    public Run(
            long initiatingUserId,
            double startLatitude,
            double startLongitude,
            String startAddress,
            double distanceKm,
            int startTimeHours,
            int startTimeMins,
            String runName,
            String description
    ) {
        this.initiatingUserId = initiatingUserId;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.startAddress = startAddress;
        this.distanceKm = distanceKm;
        this.startTimeHours = startTimeHours;
        this.startTimeMins = startTimeMins;
        this.runName = runName;
        this.description = description;
        this.isCancelled = false;
    }

    public long getName() {
        return runId;
    }

    public long getInitiatingUserId() {
        return initiatingUserId;
    }

    public long getRunId() {
        return runId;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public String getDescription() {
        return description;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public String getRunName() {
        return runName;
    }

    public void setRunName(String runName) {
        this.runName = runName;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
