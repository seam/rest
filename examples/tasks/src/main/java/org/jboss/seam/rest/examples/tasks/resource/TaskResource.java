package org.jboss.seam.rest.examples.tasks.resource;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.jboss.seam.rest.examples.tasks.entity.Category;
import org.jboss.seam.rest.examples.tasks.entity.JaxbTaskWrapper;
import org.jboss.seam.rest.examples.tasks.entity.Task;
import org.jboss.seam.rest.examples.tasks.entity.TaskValidationGroup;
import org.jboss.seam.rest.templating.ResponseTemplate;
import org.jboss.seam.rest.validation.ValidateRequest;

/**
 * CRUD resource for resources
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@Consumes({"application/xml", "application/json"})
@ValidateRequest(groups = TaskValidationGroup.class)
@Stateless
public class TaskResource extends AbstractEntityResource {
    @PersistenceContext
    private EntityManager em;

    @GET
    @ResponseTemplate(value = "/freemarker/task.ftl", produces = "application/task+xml", responseName = "task")
    @Produces({"application/task+xml", "application/json"})
    public Task getTask(@PathParam("taskId") long taskId, @Context UriInfo uriInfo) {
        return loadTask(taskId, uriInfo);
    }

    @POST
    @Path("/move")
    public void move(@PathParam("taskId") long taskId, @QueryParam("category") String newCategoryName, @Context UriInfo uriInfo) {
        Task task = loadTask(taskId, uriInfo);
        Category newCategory = (Category) em.createNamedQuery("categoryByName").setParameter("category", newCategoryName)
                .getSingleResult();
        Category oldCategory = task.getCategory();

        oldCategory.getTasks().remove(task);
        newCategory.getTasks().add(task);
        task.setCategory(newCategory);
        task.setUpdated(new Date());
    }

    @PUT
    public void updateTask(@PathParam("taskId") long taskId, @Context UriInfo uriInfo, JaxbTaskWrapper incommingTask) {
        Task task = loadTask(taskId, uriInfo);
        if (incommingTask.getName() != null) {
            task.setName(incommingTask.getName());
        }
        if (incommingTask.isResolved() != null) {
            task.setResolved(incommingTask.isResolved());
        }
        task.setUpdated(new Date());
    }

    @DELETE
    public void deleteTask(@PathParam("taskId") long taskId, @Context UriInfo uriInfo) {
        Task task = loadTask(taskId, uriInfo);
        task.getCategory().getTasks().remove(task);
        em.remove(task);
    }

    protected Task loadTask(long taskId, UriInfo uriInfo) {
        String categoryName = uriInfo.getPathParameters().getFirst("category");
        if (categoryName == null) {
            return (Task) em.createNamedQuery("taskById").setParameter("tid", taskId).getSingleResult();
        } else {
            return (Task) em.createNamedQuery("taskByCategoryAndId").setParameter("tid", taskId)
                    .setParameter("category", categoryName).getSingleResult();
        }
    }
}
