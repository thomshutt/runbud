package com.thomshutt.runbud.util;

import com.thomshutt.runbud.core.Run;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.*;

import java.io.IOException;

public class ImageFetcher {

    private final HttpClient client = new HttpClient();

    public String fetchImage(double latitude, double longitude) {

        final String url = "https://api.instagram.com/v1/media/search?" +
                "lat=" + latitude +
                "&lng=" + longitude +
                "&distance=" + 50 +
                "&access_token=31433758.5b9e1e6.5f0609ae684143dd984d587bc9a2954b";

        final GetMethod get = new GetMethod(url);

        try {
            client.executeMethod(get);
            final String responseBody = new String(get.getResponseBody());
            final JSONObject responseJSON = new JSONObject(responseBody);
            final JSONArray imageObjects = responseJSON.getJSONArray("data");
            String currentUmageUrl = "";
            int maxLikeCount = 0;
            for(int x = 0; x < imageObjects.length(); x++) {
                final JSONObject imageObject = imageObjects.getJSONObject(x);
                if("image".equalsIgnoreCase(imageObject.getString("type"))) {
                    final JSONObject imageUrls = imageObject.getJSONObject("images");
                    final int numLikes = imageObject.getJSONObject("likes").getInt("count");
                    final String imageUrl = imageUrls.getJSONObject("low_resolution").getString("url");

                    if(numLikes >= maxLikeCount) {
                        currentUmageUrl = imageUrl;
                        maxLikeCount = numLikes;
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
