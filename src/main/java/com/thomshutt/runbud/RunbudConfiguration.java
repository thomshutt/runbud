package com.thomshutt.runbud;

import com.bazaarvoice.dropwizard.assets.AssetsBundleConfiguration;
import com.bazaarvoice.dropwizard.assets.AssetsConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;

public class RunbudConfiguration extends Configuration implements AssetsBundleConfiguration {

    final static Logger LOGGER = LoggerFactory.getLogger(RunbudConfiguration.class);

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    @Valid
    @NotNull
    @JsonProperty
    private final AssetsConfiguration assets = new AssetsConfiguration();

    private DataSourceFactory envDatabase;

    private final String databaseUrl = System.getenv("DATABASE_URL");

    public DataSourceFactory getDataSourceFactory() {
        if (databaseUrl != null) {
            if(envDatabase == null) {
                envDatabase = create(databaseUrl);
            }
            return envDatabase;
        } else {
            return database;
        }
    }

    @Override
    public AssetsConfiguration getAssetsConfiguration() {
        return assets;
    }

    public static DataSourceFactory create(String databaseUrl) {
        LOGGER.info("Creating DB for " + databaseUrl);
        if (databaseUrl == null) {
            throw new IllegalArgumentException("The DATABASE_URL environment variable must be set before running the app " +
                    "example: DATABASE_URL=\"postgres://username:password@host:5432/dbname\"");
        }
        try {
            URI dbUri = new URI(databaseUrl);
            final String user = dbUri.getUserInfo().split(":")[0];
            final String password = dbUri.getUserInfo().split(":")[1];
            final String url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
            DataSourceFactory dsf = new DataSourceFactory();
            dsf.setUser(user);
            dsf.setPassword(password);
            dsf.setUrl(url);
            dsf.setDriverClass("org.postgresql.Driver");
            return dsf;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
