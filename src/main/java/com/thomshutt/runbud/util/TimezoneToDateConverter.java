package com.thomshutt.runbud.util;

import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.data.RunDAO;
import com.thomshutt.runbud.util.SimpleDate;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TimezoneToDateConverter {

    private static final String DATE_RESOURCE_URL = "http://www.earthtools.org/timezone/LATITUDE/LONGITUDE";

    private final HttpClient client;
    private final Executor executor = Executors.newFixedThreadPool(10);

    public TimezoneToDateConverter() {
        client = new HttpClient();
    }

    public void sendSignupSuccessMessage(final RunDAO runDAO, final long runId, final double latitude, final double longitude) {
        executor.execute(new Runnable() {

            @Override
            public void run() {
                final GetMethod get = new GetMethod(
                        DATE_RESOURCE_URL
                                .replace("LATITUDE", String.valueOf(latitude))
                                .replace("LONGITUDE", String.valueOf(longitude))
                );

                try {
                    client.executeMethod(get);
                    final String responseBody = new String(get.getResponseBody());
                    final Run run = runDAO.get(runId);
                    run.setDate(parseEarthToolsXml(responseBody));
                    runDAO.persist(run);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected static SimpleDate parseEarthToolsXml(String xml) {
        final String isotime = xml.split("<isotime>")[1].split("</isotime>")[0];
        final String isodate = isotime.split(" ")[0];
        final String[] isodateComponents = isodate.split("-");
        return new SimpleDate(
                Integer.valueOf(isodateComponents[2]),
                Integer.valueOf(isodateComponents[1]),
                Integer.valueOf(isodateComponents[0])
        );
    }

}
