package org.jboss.seam.rest.examples.tasks.resource;

import java.net.URI;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.seam.rest.examples.tasks.entity.Category;
import org.jboss.seam.rest.examples.tasks.entity.JaxbTaskWrapper;
import org.jboss.seam.rest.examples.tasks.entity.Task;
import org.jboss.seam.rest.examples.tasks.entity.TaskValidationGroup;
import org.jboss.seam.rest.validation.ValidateRequest;

/**
 * CRUD resource for categories
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@Path("/category/{category}")
@Produces({"application/xml", "application/json"})
@Consumes({"application/xml", "application/json"})
@ValidateRequest(groups = TaskValidationGroup.class)
@Stateless
public class CategoryResource extends AbstractEntityResource {
    @Inject
    private TaskCollectionResource taskCollectionSubresource;

    @Path("/")
    // subresource locator
    public TaskCollectionResource getTasks() {
        return taskCollectionSubresource;
    }

    @PUT
    public void putCategory(@PathParam("category") String categoryName) {
        Category category = new Category(categoryName);
        em.persist(category);
    }

    @DELETE
    public void deleteCategory(@PathParam("category") String categoryName) {
        em.remove(loadCategory(categoryName));
    }

    /**
     * This method only makes sense at /category/{category}/task not /task
     */
    @POST
    @Path("/task")
    public Response createTask(JaxbTaskWrapper incommingTask, @PathParam("category") String categoryName,
                               @Context UriInfo uriInfo) {
        Category category = loadCategory(categoryName);

        Task task = new Task();
        task.setCategory(category);
        task.setCreated(new Date());
        task.setUpdated(task.getCreated()); // set update date to creation date
        task.setName(incommingTask.getName());
        task.setResolved(false); // not resolved by default
        em.persist(task);
        long id = task.getId();

        URI uri = uriInfo.getBaseUriBuilder().path(TaskCollectionResource.class)
                .path(TaskCollectionResource.class, "getTaskSubresource").build(String.valueOf(id));
        return Response.created(uri).build();
    }
}
