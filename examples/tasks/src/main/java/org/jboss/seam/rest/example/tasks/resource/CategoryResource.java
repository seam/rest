/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.seam.rest.example.tasks.resource;

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

import org.jboss.seam.rest.example.tasks.entity.Category;
import org.jboss.seam.rest.example.tasks.entity.Task;
import org.jboss.seam.rest.example.tasks.entity.TaskValidationGroup;
import org.jboss.seam.rest.validation.ValidateRequest;

/**
 * CRUD resource for categories
 * @author Jozef Hartinger
 *
 */
@Path("/category")
@Produces( { "application/xml", "application/json" })
@Consumes( { "application/xml", "application/json" })
@ValidateRequest(groups = TaskValidationGroup.class)
@Stateless
public class CategoryResource extends AbstractEntityResource
{
   @Inject
   private TaskCollectionResource taskCollectionSubresource;

   @Path("/{category}")
   public TaskCollectionResource getTasks()
   {
      return taskCollectionSubresource;
   }

   @PUT
   @Path("/{category}")
   public void putCategory(@PathParam("category") String categoryName)
   {
      Category category = new Category(categoryName);
      em.persist(category);
   }

   @DELETE
   @Path("/{category}")
   public void deleteCategory(@PathParam("category") String categoryName)
   {
      em.remove(loadCategory(categoryName));
   }

   /**
    * This method only makes sense at /category/{category}/task not /task
    * 
    * 
    */
   @POST
   @Path("/{category}/task")
   public Response createTask(Task incommingTask, @PathParam("category") String categoryName, @Context UriInfo uriInfo)
   {
      Category category = loadCategory(categoryName);
      
      Task task = new Task();
      task.setCategory(category);
      task.setCreated(new Date());
      task.setUpdated(task.getCreated()); // set update date to creation date
      task.setName(incommingTask.getName());
      task.setResolved(false); // not resolved by default
      em.persist(task);
      long id = task.getId();

      URI uri = uriInfo.getBaseUriBuilder().path(TaskCollectionResource.class).path(TaskCollectionResource.class, "getTaskSubresource").build(String.valueOf(id));
      return Response.created(uri).build();
   }
}
