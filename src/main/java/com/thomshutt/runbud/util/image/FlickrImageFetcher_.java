package com.thomshutt.runbud.util.image;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.GeoData;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.test.TestInterface;

import java.util.Collection;
import java.util.Collections;

public class FlickrImageFetcher_ {

    private static final String URL = "https://api.flickr.com/services/rest/?" +
            "method=flickr.photos.geo.photosForLocation&" +
            "api_key=378519318c5db6082984c2519ad97f0f" +
            "lat=123&" +
            "lon=456&" +
            "format=json&" +
            "nojsoncallback=1&" +
            "api_sig=f6d109ca9a11dbb2b7a892988f839a84";

    public static void main(String[] args) throws FlickrException {
        String apiKey = "378519318c5db6082984c2519ad97f0f";
        String sharedSecret = "024b55d75ad6c94c";

        final double latitude = 53.47741975;
        final double longitude = 2.23083329492187;

        Flickr f = new Flickr(apiKey, sharedSecret, new REST());
        PhotosInterface testInterface = f.getPhotosInterface();
        Collection results = testInterface.getGeoInterface().photosForLocation(
                new GeoData(
                        String.valueOf(latitude),
                        String.valueOf(longitude),
                        "15"
                ),
                Collections.EMPTY_SET,
                1,
                1
        );

        System.out.println(results);
    }

}

