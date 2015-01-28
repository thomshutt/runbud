package com.thomshutt.runbud.util;

import com.thomshutt.runbud.core.Run;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.*;

import java.io.IOException;

public class ImageFetcher {

    private final HttpClient client = new HttpClient();

    public String fetchImage(double latitude, double longitude) {

        final LatitudeLongitude latitudeLongitude = new LatitudeLongitude(latitude, longitude);

        final String url = "https://api.instagram.com/v1/media/search?" +
                "lat=" + latitude +
                "&lng=" + longitude +
                "&distance=" + 1000 +
                "&access_token=31433758.5b9e1e6.5f0609ae684143dd984d587bc9a2954b";

        final GetMethod get = new GetMethod(url);

        try {
            client.executeMethod(get);
            final String responseBody = new String(get.getResponseBody());
            final JSONObject responseJSON = new JSONObject(responseBody);
            final JSONArray imageObjects = responseJSON.getJSONArray("data");
            String currentUmageUrl = "";
            double minDistance = 1000000000;
            for(int x = 0; x < imageObjects.length(); x++) {
                final JSONObject imageObject = imageObjects.getJSONObject(x);
                if("image".equalsIgnoreCase(imageObject.getString("type"))) {
                    final JSONObject imageUrls = imageObject.getJSONObject("images");
                    final int numLikes = imageObject.getJSONObject("likes").getInt("count");
                    final String imageUrl = imageUrls
                            .getJSONObject("thumbnail")
                            .getString("url");

                    final JSONObject location = imageObject.getJSONObject("location");
                    final double photoLatitude = location.getDouble("latitude");
                    final double photoLongitude = location.getDouble("longitude");

                    final double distanceKmBetween = LatitudeLongitude.calculateDistanceKmBetween(latitudeLongitude, new LatitudeLongitude(photoLatitude, photoLongitude));

                    if(distanceKmBetween < minDistance) {
                        currentUmageUrl = imageUrl;
                        minDistance = distanceKmBetween;
                        System.out.println("---------------------------------------------------------");
                        System.out.println(imageObject.toString(2));
                        System.out.println("---------------------------------------------------------");
                    }
                }
            }
            return currentUmageUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
