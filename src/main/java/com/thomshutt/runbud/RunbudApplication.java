package com.thomshutt.runbud;

import com.google.common.base.Optional;
import com.thomshutt.runbud.core.Comment;
import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.User;
import com.thomshutt.runbud.data.CommentDAO;
import com.thomshutt.runbud.data.RunDAO;
import com.thomshutt.runbud.data.UserDAO;
import com.thomshutt.runbud.health.RunResourceHealthCheck;
import com.thomshutt.runbud.resources.RunResource;
import com.thomshutt.runbud.resources.SiteResource;
import com.thomshutt.runbud.resources.UserResource;
import com.thomshutt.runbud.security.BasicAuthFactory;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import javax.servlet.http.Cookie;

public class RunbudApplication extends Application<RunbudConfiguration> {

    private final HibernateBundle<RunbudConfiguration> runBundle = new HibernateBundle<RunbudConfiguration>(
            Run.class,
            User.class,
            Comment.class
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
    }

    @Override
    public void run(RunbudConfiguration runbudConfiguration, Environment environment) throws Exception {
        final UserDAO userDAO = new UserDAO(runBundle.getSessionFactory());
        final RunResource runResource = new RunResource(
                new RunDAO(runBundle.getSessionFactory()),
                userDAO,
                new CommentDAO(runBundle.getSessionFactory())
        );
        environment.jersey().register(runResource);
        environment.jersey().register(new SiteResource());
        environment.jersey().register(new UserResource(userDAO));
        environment.jersey().register(AuthFactory.binder(new BasicAuthFactory<User>(new Authenticator<Cookie[], User>() {
            @Override
            public Optional<User> authenticate(Cookie[] cookies) throws AuthenticationException {
                for (Cookie cookie : cookies) {
                    if("runbud.cookie".equals(cookie.getName())) {
                        return Optional.of(new User("email", "password", "name"));
                    }
                }
                return Optional.absent();
            }
        }, User.class)));

        environment.healthChecks().register("runresource", new RunResourceHealthCheck(runResource));

    }

}
