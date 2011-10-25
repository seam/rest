package org.jboss.seam.rest.test.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MoreSpecificExceptionMapper implements ExceptionMapper<ArrayIndexOutOfBoundsException> {

    public Response toResponse(ArrayIndexOutOfBoundsException exception) {
        return Response.status(421).build();
    }
}
