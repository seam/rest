package org.jboss.seam.rest.test.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/ping")
public interface PingService {
    @GET
    @Produces("text/plain")
    String ping();
}
