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

import org.jboss.seam.rest.example.tasks.entity.Category;
import org.jboss.seam.rest.example.tasks.entity.JaxbTaskWrapper;
import org.jboss.seam.rest.example.tasks.entity.Task;
import org.jboss.seam.rest.example.tasks.entity.TaskValidationGroup;
import org.jboss.seam.rest.templating.ResponseTemplate;
import org.jboss.seam.rest.validation.ValidateRequest;

/**
 * CRUD resource for resources
 * @author Jozef Hartinger
 *
 */
@Consumes({ "application/xml", "application/json" })
@ValidateRequest(groups = TaskValidationGroup.class)
@Stateless
public class TaskResource extends AbstractEntityResource
{
   @PersistenceContext
   private EntityManager em;

   @GET
   @ResponseTemplate(value = "/freemarker/task.ftl", produces = "application/task+xml", responseName = "task")
   @Produces({ "application/task+xml", "application/json" })
   public Task getTask(@PathParam("taskId") long taskId, @Context UriInfo uriInfo)
   {
      return loadTask(taskId, uriInfo);
   }

   @POST
   @Path("/move")
   public void move(@PathParam("taskId") long taskId, @QueryParam("category") String newCategoryName, @Context UriInfo uriInfo)
   {
      Task task = loadTask(taskId, uriInfo);
      Category newCategory = (Category) em.createNamedQuery("categoryByName").setParameter("category", newCategoryName).getSingleResult();
      Category oldCategory = task.getCategory();
      
      oldCategory.getTasks().remove(task);
      newCategory.getTasks().add(task);
      task.setCategory(newCategory);
      task.setUpdated(new Date());
   }
   
   @PUT
   public void updateTask(@PathParam("taskId") long taskId, @Context UriInfo uriInfo, JaxbTaskWrapper incommingTask)
   {
      Task task = loadTask(taskId, uriInfo);
      if (incommingTask.getName() != null)
      {
         task.setName(incommingTask.getName());
      }
      if (incommingTask.isResolved() != null)
      {
         task.setResolved(incommingTask.isResolved());
      }
      task.setUpdated(new Date());
   }

   @DELETE
   public void deleteTask(@PathParam("taskId") long taskId, @Context UriInfo uriInfo)
   {
      Task task = loadTask(taskId, uriInfo);
      em.remove(task);
   }

   protected Task loadTask(long taskId, UriInfo uriInfo)
   {
      String categoryName = uriInfo.getPathParameters().getFirst("category");
      if (categoryName == null)
      {
         return (Task) em.createNamedQuery("taskById").setParameter("tid", taskId).getSingleResult();
      }
      else
      {
         return (Task) em.createNamedQuery("taskByCategoryAndId").setParameter("tid", taskId).setParameter("category", categoryName).getSingleResult();
      }
   }
}
