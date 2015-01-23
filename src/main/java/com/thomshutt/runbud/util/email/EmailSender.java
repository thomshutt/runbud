package com.thomshutt.runbud.util.email;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.glassfish.jersey.client.ClientResponse;

import javax.ws.rs.core.MediaType;

public class EmailSender {

    private final Client client;
    private final WebResource webResource;

    public EmailSender() {
        client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api", "key-79f9a3d0330c0a4933a156f8b2db34a4"));

        webResource = client.resource("https://api.mailgun.net/v2/sandboxb537115489554c9882a653427036f2bb.mailgun.org/messages");
    }

    public void sendSignupSuccessMessage(String toName, String toEmail) {
        final MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("from", "Runbud <postmaster@sandboxb537115489554c9882a653427036f2bb.mailgun.org>");
        formData.add("to", toName + " <" + toEmail + ">");
        formData.add("subject", "Hello " + toName);
        formData.add("text", "Thanks for signing up to Runbud!");

        webResource
            .type(MediaType.APPLICATION_FORM_URLENCODED)
            .post(ClientResponse.class, formData);
    }

}
