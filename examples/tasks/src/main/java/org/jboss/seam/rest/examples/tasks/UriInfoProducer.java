package org.jboss.seam.rest.examples.tasks;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@SuppressWarnings("unused")
@ApplicationScoped
@Provider
public class UriInfoProducer implements ExceptionMapper<UriInfoProducer.FooException> {
    @Produces
    @Named
    private UriInfo uriInfo;

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    @SuppressWarnings("serial")
    public class FooException extends Exception {
    }

    public Response toResponse(FooException exception) {
        return null;
    }
}
