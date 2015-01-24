package com.thomshutt.runbud.util.email;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class EmailSender {

    public static final String MAILGUN_URL = "https://api.mailgun.net/v2/sandboxb537115489554c9882a653427036f2bb.mailgun.org/messages";
    public static final String API_KEY = DatatypeConverter.printBase64Binary("api:key-79f9a3d0330c0a4933a156f8b2db34a4".getBytes());
    public static final String FROM_VALUE = "Runbud <postmaster@sandboxb537115489554c9882a653427036f2bb.mailgun.org>";

    private final HttpClient client;
    private final Executor executor = Executors.newFixedThreadPool(10);

    public EmailSender() {
        client = new HttpClient();
    }

    public void sendSignupSuccessMessage(final String toName, final String toEmail) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final PostMethod post = new PostMethod(MAILGUN_URL);

                post.addRequestHeader("Authorization", "Basic " + API_KEY);

                post.addParameter("from", FROM_VALUE);
                post.addParameter("to", toName + " <" + toEmail + ">");
                post.addParameter("subject", "Hello " + toName);
                post.addParameter("text", "Thanks for signing up to Runbud!");

                try {
                    int response = client.executeMethod(post);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
