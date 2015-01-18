package com.thomshutt.runbud.security;

import javax.ws.rs.core.Response;

public interface UnauthorizedHandler {
    Response buildResponse();
}
