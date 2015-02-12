package com.thomshutt.runbud.util.image;

import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.data.RunDAO;
import com.thomshutt.runbud.util.LatitudeLongitude;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.hibernate.*;
import org.hibernate.context.internal.ManagedSessionContext;
import org.json.*;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InstagramImageFetcher implements ImageFetcher {

    private final HttpClient client = new HttpClient();
    private final Executor executor = Executors.newFixedThreadPool(10);
    private final SessionFactory sessionFactory;

    public InstagramImageFetcher(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void fetchImageUrl(final Run run, final long runId, final double latitude, final double longitude) {

        executor.execute(new Runnable() {
            @Override
            public void run() {

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
                    for (int x = 0; x < imageObjects.length(); x++) {
                        final JSONObject imageObject = imageObjects.getJSONObject(x);
                        if ("image".equalsIgnoreCase(imageObject.getString("type"))) {
                            final JSONObject imageUrls = imageObject.getJSONObject("images");
                            final int numLikes = imageObject.getJSONObject("likes").getInt("count");
                            final String imageUrl = imageUrls
                                    .getJSONObject("thumbnail")
                                    .getString("url");

                            final JSONObject location = imageObject.getJSONObject("location");
                            final double photoLatitude = location.getDouble("latitude");
                            final double photoLongitude = location.getDouble("longitude");

                            final double distanceKmBetween = LatitudeLongitude.calculateDistanceKmBetween(latitudeLongitude, new LatitudeLongitude(photoLatitude, photoLongitude));

                            if (distanceKmBetween < minDistance) {
                                currentUmageUrl = imageUrl;
                                minDistance = distanceKmBetween;
                                System.out.println("---------------------------------------------------------");
                                System.out.println(imageObject.toString(2));
                                System.out.println("---------------------------------------------------------");
                            }
                        }
                    }

                    run.setImageUrl(currentUmageUrl);
                    saveToDb(run);

                    System.out.println("Saving run with id: " + runId + " url: " + currentUmageUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void saveToDb(Run run) {
        final Session session = sessionFactory.openSession();
        try {
            configureSession(session);
            ManagedSessionContext.bind(session);
            session.beginTransaction();
            try {
                new RunDAO(sessionFactory).persist(run);
                final Transaction txn = session.getTransaction();
                if (txn != null && txn.isActive()) {
                    txn.commit();
                }
            } catch (Exception e) {
                final Transaction txn = session.getTransaction();
                if (txn != null && txn.isActive()) {
                    txn.rollback();
                }
                e.printStackTrace();
            }
        } finally {
            session.close();
            ManagedSessionContext.unbind(sessionFactory);
        }
    }

    private void configureSession(Session session) {
        session.setDefaultReadOnly(false);
        session.setCacheMode(CacheMode.PUT);
        session.setFlushMode(FlushMode.ALWAYS);
    }

}
