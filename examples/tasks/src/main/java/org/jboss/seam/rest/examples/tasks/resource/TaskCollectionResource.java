package org.jboss.seam.rest.examples.tasks.resource;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.jboss.seam.rest.examples.tasks.entity.Task;
import org.jboss.seam.rest.templating.ResponseTemplate;
import org.jboss.seam.rest.validation.ValidateRequest;

/**
 * Collection resource for tasks
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@Path("/task")
@RequestScoped
@Named
public class TaskCollectionResource {
    @Inject
    private CollectionBean bean;
    @Inject
    private TaskResource taskSubresource;
    @Context
    protected UriInfo uriInfo;
    @QueryParam("start")
    @DefaultValue("0")
    @Min(value = 0, message = "start must be a non-negative number")
    protected int start;
    @QueryParam("limit")
    @DefaultValue("5")
    @Min(value = 0, message = "limit must be a non-negative number")
    @Max(value = 100, message = "Cannot return more than 100 items")
    protected int limit;
    @Pattern(regexp = "resolved|unresolved|all", message = "Unknown task status. Allowed values: resolved, unresolved, all")
    private String status;

    @GET
    @ValidateRequest
    @Produces({"application/tasks+xml", "application/json"})
    @ResponseTemplate(value = "/freemarker/tasks.ftl", produces = "application/tasks+xml")
    public List<Task> getTasks() {
        return bean.getTasks(start, limit, status, uriInfo.getPathParameters().getFirst("category"));
    }

    @Path("/{taskId}")
    public TaskResource getTaskSubresource() {
        return taskSubresource;
    }

    /**
     * Ugly workaround for https://jira.jboss.org/browse/CDI-6 (We must use setter injection instead of field injection which
     * makes things less clear)
     */
    @QueryParam("status")
    @DefaultValue("unresolved")
    public void setStatus(String status) {
        this.status = status;
    }

    public UriInfo getUriInfo() {
        return uriInfo;
    }

    public int getStart() {
        return start;
    }

    public int getLimit() {
        return limit;
    }
}
