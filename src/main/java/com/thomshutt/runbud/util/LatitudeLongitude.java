package com.thomshutt.runbud.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

public class LatitudeLongitude {

    public static final double AVERAGE_RADIUS_OF_EARTH = 6371;
    public static final LatitudeLongitude PICC_CIRCUS_LAT_LON = new LatitudeLongitude(51.510730378916186, -0.13398630345454876);

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

    public static LatitudeLongitude fromAddress(String address) {
        final String url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(address);
        final HttpClient client = new HttpClient();
        final GetMethod get = new GetMethod(url);

        try {
            client.executeMethod(get);
            final String responseBody = new String(get.getResponseBody());
            final JSONObject responseJSON = new JSONObject(responseBody);
            final JSONArray results = responseJSON.getJSONArray("results");
            final JSONObject firstResult = results.getJSONObject(0);
            final JSONObject geometry = firstResult.getJSONObject("geometry");
            final JSONObject location = geometry.getJSONObject("location");
            final double lat = location.getDouble("lat");
            final double lng = location.getDouble("lng");
            return new LatitudeLongitude(lat, lng);
        } catch (Exception e) {
            e.printStackTrace();
            return PICC_CIRCUS_LAT_LON;
        }
    }

    public static long toDate(LatitudeLongitude latitudeLongitude) {
//        https://maps.googleapis.com/maps/api/timezone/json?location=51.510730378916186,-0.13398630345454876&timestamp=1425458131
        return 0;
    }

}
