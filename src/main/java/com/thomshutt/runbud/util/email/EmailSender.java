package com.thomshutt.runbud.util.email;

import com.thomshutt.runbud.core.Run;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EmailSender {

    public static final String MAILGUN_URL = "https://api.mailgun.net/v2/sandboxb537115489554c9882a653427036f2bb.mailgun.org/messages";
    public static final String API_KEY = DatatypeConverter.printBase64Binary("api:key-79f9a3d0330c0a4933a156f8b2db34a4".getBytes());
    public static final String FROM_VALUE = "YOU/ME/RUN <postmaster@sandboxb537115489554c9882a653427036f2bb.mailgun.org>";

    private final HttpClient client;
    private final Executor executor = Executors.newFixedThreadPool(10);

    public EmailSender() {
        client = new HttpClient();
    }

    public void sendSignupSuccessMessage(String toName, String toEmail) {
        sendEmail(
                toName,
                toEmail,
                "Welcome to YOU/ME/RUN " + toName,
                "Thanks for signing up to YOU/ME/RUN!\n\n" +
                        "If you have any questions then please get in touch at youmerun@thomshutt.com\n\n\n\n" +
                        "Thanks,\n\n" +
                        "Thom Shutt (YOU/ME/RUN Creator)"
        );
    }

    public void sendSomeoneJoinedYourRunMessage(
            String toName,
            String toEmail,
            String joinerName,
            Run run
    ) {
        sendEmail(
                toName,
                toEmail,
                joinerName + " just signed up for your Run!",
                "Congratulations - " + joinerName + " has decided to join you on your run: " +
                        "\"" + run.getRunName() + "\"!\n\n" +
                        "View the run and see who else will be there at http://www.youmerun.com/runs/" + run.getRunId()
        );
    }

    public void sendSomeoneCommentedOnYourRunMessage(
            String toName,
            String toEmail,
            String joinerName,
            String comment,
            Run run
    ) {
        sendEmail(
                toName,
                toEmail,
                joinerName + " just left a comment on your Run!",
                joinerName + " just left a comment on your run: " +
                        "\"" + run.getRunName() + "\"!\n\n" +
                        "They said: \n\n" +
                        "\"" + comment + "\"\n\n" +
                        "View the run at http://www.youmerun.com/runs/" + run.getRunId()
        );
    }

    private void sendEmail(final String toName, final String toEmail, final String subject, final String message) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final PostMethod post = new PostMethod(MAILGUN_URL);

                post.addRequestHeader("Authorization", "Basic " + API_KEY);

                post.addParameter("from", FROM_VALUE);
                post.addParameter("to", toName + " <" + toEmail + ">");
                post.addParameter("subject", subject);
                post.addParameter("text", message);

                try {
                    int response = client.executeMethod(post);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
