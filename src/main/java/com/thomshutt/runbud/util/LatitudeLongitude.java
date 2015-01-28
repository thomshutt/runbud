package com.thomshutt.runbud.util;

public class LatitudeLongitude {

    public final static double AVERAGE_RADIUS_OF_EARTH = 6371;

    public final double latitude;
    public final double longitude;

    public LatitudeLongitude(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static double calculateDistanceKmBetween(LatitudeLongitude coord1, LatitudeLongitude coord2) {
            double latDistance = Math.toRadians(coord1.latitude - coord2.latitude);
            double lngDistance = Math.toRadians(coord1.longitude - coord2.longitude);

            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(coord1.latitude)) * Math.cos(Math.toRadians(coord2.latitude))
                    * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return AVERAGE_RADIUS_OF_EARTH * c;
    }

}
