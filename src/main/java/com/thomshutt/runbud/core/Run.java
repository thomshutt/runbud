package com.thomshutt.runbud.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "runs")
public class Run {

    @Id
    @Column(name = "RUN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long runId;

    @Column(name = "INITIATING_USER_ID")
    private long initiatingUserId;

    @Column(name = "START_LATITUDE")
    private double startLatitude;

    @Column(name = "START_LONGITUDE")
    private double startLongitude;

    @Column(name = "START_ADDRESS")
    private String startAddress;

    @Column(name = "DISTANCE_KM")
    private double distanceKm;

    @Column(name = "DATE")
    private long date;

    @Column(name = "START_TIME_HOURS")
    private int startTimeHours;

    @Column(name = "START_TIME_MINS")
    private int startTimeMins;

    @Column(name = "RUN_NAME")
    private String runName;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name = "IS_CANCELLED")
    private boolean isCancelled;

    @Column(name = "IMAGE")
    private String imageUrl;

    public Run() {
    }

    public Run(
            long initiatingUserId,
            double startLatitude,
            double startLongitude,
            String startAddress,
            double distanceKm,
            long date,
            int startTimeHours,
            int startTimeMins,
            String runName,
            String description,
            String imageUrl
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
        this.date = date;
        this.imageUrl = imageUrl;
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

    public void setDate(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getStartTimeHours() {
        return startTimeHours;
    }

    public int getStartTimeMins() {
        return startTimeMins;
    }

    public boolean alreadyHappened(long currentTime) {
        return date < currentTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
