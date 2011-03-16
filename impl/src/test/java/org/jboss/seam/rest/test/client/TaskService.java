package org.jboss.seam.rest.test.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/task")
public interface TaskService {
    @POST
    @Consumes("application/xml")
    Response createTask(Task task);

    @GET
    @Produces("application/xml")
    Task getTask(@QueryParam("a") int a, @MatrixParam("b") int b, @CookieParam("c") int c);
}
