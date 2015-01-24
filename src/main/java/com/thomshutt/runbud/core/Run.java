package com.thomshutt.runbud.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

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

    @Column(name = "start_latitude")
    private double startLatitude;

    @Column(name = "start_longitude")
    private double startLongitude;

    @Column(name = "start_address")
    private String startAddress;

    @Column(name = "distance_km")
    private double distanceKm;

    @Column(name = "description")
    private String description;

    @Column(name = "isCancelled")
    private boolean isCancelled;

    public Run() {
    }

    public Run(String initiatingUserId, double startLatitude, double startLongitude, String startAddress, double distanceKm, String description) {
        this.initiatingUserId = initiatingUserId;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.startAddress = startAddress;
        this.distanceKm = distanceKm;
        this.description = description;
        this.isCancelled = false;
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
