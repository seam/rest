package org.jboss.seam.rest.examples.tasks.resource;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.jboss.seam.rest.examples.tasks.entity.Category;
import org.jboss.seam.rest.templating.ResponseTemplate;
import org.jboss.seam.rest.validation.ValidateRequest;

/**
 * Collection resource for categories
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@Path("/category")
@RequestScoped
@Named
public class CategoryCollectionResource {
    @Inject
    private CollectionBean bean;
    @QueryParam("start")
    @DefaultValue("0")
    @Min(value = 0, message = "start must be a non-negative number")
    protected int start;
    @QueryParam("limit")
    @DefaultValue("5")
    @Min(value = 0, message = "limit must be a non-negative number")
    @Max(value = 100, message = "Cannot return more than 100 items")
    protected int limit;
    @Context
    protected UriInfo uriInfo;

    @GET
    @ValidateRequest
    @Produces({"application/json", "application/categories+xml", "application/categories-short+xml"})
    @ResponseTemplate.List({@ResponseTemplate(value = "/freemarker/categories.ftl", produces = "application/categories+xml"),
            @ResponseTemplate(value = "/freemarker/categories-short.ftl", produces = "application/categories-short+xml")})
    public List<Category> getCategories() {
        return bean.getCategories(start, limit);
    }

    public int getStart() {
        return start;
    }

    public int getLimit() {
        return limit;
    }

    public UriInfo getUriInfo() {
        return uriInfo;
    }
}
