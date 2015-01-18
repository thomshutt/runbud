package com.thomshutt.runbud.security;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

public class DefaultUnauthorizedHandler implements UnauthorizedHandler {
    @Override
    public Response buildResponse() {
        try {
            return Response.seeOther(new URI("/users/create")).build();
        } catch (URISyntaxException e) {
            return Response.serverError().build();
        }
    }
}
