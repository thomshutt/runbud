package com.thomshutt.runbud.security;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;

public final class BasicAuthFactory<T> extends AuthFactory<Cookie[], T> {
    private final Class<T> generatedClass;
    private final boolean required;
    private UnauthorizedHandler unauthorizedHandler = new DefaultUnauthorizedHandler();

    @Context
    private HttpServletRequest request;

    public BasicAuthFactory(
            final Authenticator<Cookie[], T> authenticator,
            final Class<T> generatedClass,
            boolean required
    ) {
        super(authenticator);
        this.generatedClass = generatedClass;
        this.required = required;
    }

    public BasicAuthFactory<T> responseBuilder(UnauthorizedHandler unauthorizedHandler) {
        this.unauthorizedHandler = unauthorizedHandler;
        return this;
    }
    @Override
    public AuthFactory<Cookie[], T> clone(boolean required) {
        return new BasicAuthFactory(authenticator(), this.generatedClass, required).responseBuilder(unauthorizedHandler);
    }
    @Override
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    @Override
    public T provide() {
        if (request != null) {
            try {
                final Cookie[] cookies = request.getCookies();
                final Optional<T> result = authenticator().authenticate(cookies);
                if(result.isPresent()) {
                    return result.get();
                }
                if(required) {
                    throw new WebApplicationException(unauthorizedHandler.buildResponse());
                }
            } catch (AuthenticationException e) {
                return null;
            }
        }
        return null;
    }
    @Override
    public Class<T> getGeneratedClass() {
        return generatedClass;
    }
}
