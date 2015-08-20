package com.thomshutt.runbud.util;

import java.util.Comparator;

import com.thomshutt.runbud.core.Run;

public class NewestThenClosestComparator implements Comparator<Run> {

    private final long currentTime;
    private final LatitudeLongitude currentLatLon;

    public NewestThenClosestComparator(long currentTime, LatitudeLongitude currentLatLon) {
        this.currentTime = currentTime;
        this.currentLatLon = currentLatLon;
    }

    @Override
    public int compare(Run run, Run run2) {
        final LatitudeLongitude runStart = new LatitudeLongitude(run.getStartLatitude(), run.getStartLongitude());
        final LatitudeLongitude run2Start = new LatitudeLongitude(run2.getStartLatitude(), run2.getStartLongitude());

        if (run.alreadyHappened(currentTime) && !run2.alreadyHappened(currentTime)) {
            return 1;
        } else if (!run.alreadyHappened(currentTime) && run2.alreadyHappened(currentTime)) {
            return -1;
        }

        return (int) Math.round(
                LatitudeLongitude.calculateDistanceKmBetween(currentLatLon, runStart) -
                        LatitudeLongitude.calculateDistanceKmBetween(currentLatLon, run2Start)
        );
    }

}
