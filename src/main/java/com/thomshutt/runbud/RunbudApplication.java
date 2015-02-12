package com.thomshutt.runbud;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.*;
import com.thomshutt.runbud.data.*;
import com.thomshutt.runbud.health.RunResourceHealthCheck;
import com.thomshutt.runbud.resources.RunResource;
import com.thomshutt.runbud.resources.SiteResource;
import com.thomshutt.runbud.resources.UserResource;
import com.thomshutt.runbud.security.BasicAuthFactory;
import com.thomshutt.runbud.util.email.EmailSender;
import com.thomshutt.runbud.util.image.FlickrImageFetcher;
import com.thomshutt.runbud.util.image.ImageFetcher;
import com.thomshutt.runbud.util.image.InstagramImageFetcher;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.hibernate.SessionFactory;

import javax.servlet.http.Cookie;

public class RunbudApplication extends Application<RunbudConfiguration> {

    private final HibernateBundle<RunbudConfiguration> runBundle = new HibernateBundle<RunbudConfiguration>(
            Run.class,
            User.class,
            Comment.class,
            UserCredentials.class,
            RunAttendee.class
    ) {
        @Override
        public DataSourceFactory getDataSourceFactory(RunbudConfiguration runbudConfiguration) {
            return runbudConfiguration.getDataSourceFactory();
        }
    };

    public static void main(String[] args) throws Exception {
        new RunbudApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<RunbudConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(runBundle);
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new MigrationsBundle<RunbudConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(RunbudConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(RunbudConfiguration runbudConfiguration, Environment environment) throws Exception {
        final SessionFactory sessionFactory = runBundle.getSessionFactory();
        final UserDAO userDAO = new UserDAO(sessionFactory);
        final UserCredentialsDAO userCredentialsDAO = new UserCredentialsDAO(sessionFactory);
        final ImageFetcher imageFetcher = new InstagramImageFetcher(sessionFactory);
//        final ImageFetcher imageFetcher = new FlickrImageFetcher(sessionFactory);

        final RunResource runResource = new RunResource(
                new RunDAO(sessionFactory),
                userDAO,
                new CommentDAO(sessionFactory),
                new RunAttendeeDAO(sessionFactory),
                imageFetcher);
        environment.jersey().register(runResource);
        environment.jersey().register(new SiteResource());
        environment.jersey().register(new UserResource(userDAO, userCredentialsDAO, new EmailSender()));
        environment.jersey().register(AuthFactory.binder(new BasicAuthFactory<User>(new Authenticator<Cookie[], User>() {
            @Override
            public Optional<User> authenticate(Cookie[] cookies) throws AuthenticationException {
                if(cookies == null) {
                    return Optional.absent();
                }
                for (Cookie cookie : cookies) {
                    if(UserResource.RUNBUD_COOKIE_KEY.equals(cookie.getName())) {
                        final UserCredentials credentials = userCredentialsDAO.getForToken(cookie.getValue());
                        if(credentials != null) {
                            return Optional.of(userDAO.get(credentials.getUserId()));
                        }
                    }
                }
                return Optional.absent();
            }
        }, User.class, true)));

        environment.healthChecks().register("runresource", new RunResourceHealthCheck(runResource));

    }

}
