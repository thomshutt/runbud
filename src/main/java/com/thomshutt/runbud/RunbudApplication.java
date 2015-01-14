package com.thomshutt.runbud;

import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.data.RunDAO;
import com.thomshutt.runbud.health.RunResourceHealthCheck;
import com.thomshutt.runbud.resources.RunResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class RunbudApplication extends Application<RunbudConfiguration> {

    private final HibernateBundle<RunbudConfiguration> runBundle = new HibernateBundle<RunbudConfiguration>(Run.class) {
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
    }

    @Override
    public void run(RunbudConfiguration runbudConfiguration, Environment environment) throws Exception {
        final RunResource runResource = new RunResource(new RunDAO(runBundle.getSessionFactory()));
        environment.jersey().register(runResource);

        environment.healthChecks().register("runresource", new RunResourceHealthCheck(runResource));

    }

}
