package org.jboss.seam.rest.example.tasks.statistics;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.jboss.seam.rest.example.tasks.statistics.entity.Category;
import org.jboss.seam.rest.example.tasks.statistics.entity.Task;

@Path("/api")
@Produces("application/json")
public interface SeamTasksService
{
   @Path("/task")
   @GET
   List<Task> getTasks(@QueryParam("status") String status, @QueryParam("start") int start, @QueryParam("limit") int limit);
   
   @Path("/category")
   @GET
   List<Category> getCategories(@QueryParam("start") int start, @QueryParam("limit") int limit);
}
