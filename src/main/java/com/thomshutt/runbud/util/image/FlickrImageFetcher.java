package com.thomshutt.runbud.util.image;

import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.data.RunDAO;
import com.thomshutt.runbud.util.LatitudeLongitude;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.hibernate.*;
import org.hibernate.context.internal.ManagedSessionContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FlickrImageFetcher implements ImageFetcher {

    private final HttpClient client = new HttpClient();
    private final Executor executor = Executors.newFixedThreadPool(10);
    private final SessionFactory sessionFactory;

    public FlickrImageFetcher(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void fetchImageUrl(final Run run, final long runId, final double latitude, final double longitude) {

        executor.execute(new Runnable() {
            @Override
            public void run() {

                final String url = "https://api.flickr.com/services/rest/?" +
                        "api_key=378519318c5db6082984c2519ad97f0f&" +
                        "method=flickr.photos.search&" +
                        "lat=" + latitude + "&" +
                        "lon=" + longitude + "&" +
                        "min_upload_date=120483200&" +
                        "safe_search=1&" +
                        "radius=0.5";

                final GetMethod get = new GetMethod(url);

                try {
                    client.executeMethod(get);
                    final String responseBody = new String(get.getResponseBody());

                    DocumentBuilderFactory builderFactory =
                            DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = null;
                    try {
                        builder = builderFactory.newDocumentBuilder();

                        final Document document = builder.parse(new ByteArrayInputStream(responseBody.getBytes()));

                        XPath xPath = XPathFactory.newInstance().newXPath();

                        final String id = xPath.compile("/rsp/photos/photo/@id").evaluate(document);
                        final String secret = xPath.compile("/rsp/photos/photo/@secret").evaluate(document);
                        final String server = xPath.compile("/rsp/photos/photo/@server").evaluate(document);
                        final String farm = xPath.compile("/rsp/photos/photo/@farm").evaluate(document);

                        final String imageUrl = "https://farm" +
                                farm +
                                ".staticflickr.com/" +
                                server +
                                "/" + id +
                                "_" + secret + ".jpg";

                        run.setImageUrl(imageUrl);
                        saveToDb(run);

                        System.out.println("Saving run with id: " + runId + " url: " + imageUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

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
