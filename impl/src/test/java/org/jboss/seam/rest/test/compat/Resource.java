package org.jboss.seam.rest.test.compat;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.seam.rest.test.compat.interceptor.Valid;

@Path("/test")
@Produces("text/plain")
@RequestScoped
@Valid
public class Resource {
    @GET
    @Path("/ping")
    public String ping() {
        return "pong";
    }

    @GET
    @Path("/exception")
    public void exception() {
        throw new NullPointerException();
    }
}
