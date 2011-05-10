package org.jboss.seam.rest.examples.client.tasks;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * Very simplified API of the Seam Tasks's API.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@Path("/api")
@Produces("application/json")
public interface SeamTasksService {
    @Path("/task")
    @GET
    List<Task> getTasks(@QueryParam("status") String status, @QueryParam("start") int start, @QueryParam("limit") int limit);

    @Path("/category")
    @GET
    List<Category> getCategories(@QueryParam("start") int start, @QueryParam("limit") int limit);
}
