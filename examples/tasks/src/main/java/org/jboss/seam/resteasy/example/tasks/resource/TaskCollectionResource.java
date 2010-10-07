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
package org.jboss.seam.resteasy.example.tasks.resource;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.annotations.providers.NoJackson;
import org.jboss.seam.resteasy.example.tasks.entity.Task;

/**
 * Collection resource for tasks
 * @author Jozef Hartinger
 *
 */
@Path("/task")
@Produces({ "application/xml", "application/json" })
@Stateless
public class TaskCollectionResource extends AbstractCollectionResource
{
   @PersistenceContext
   private EntityManager em;
   @Inject
   private TaskResource taskSubresource;

   @GET
   @NoJackson
   public List<Task> getTasks(@Context UriInfo uriInfo, @QueryParam("status") @DefaultValue("unresolved") String status, @QueryParam("start") @DefaultValue("0") int start, @QueryParam("limit") @DefaultValue("5") int limit)
   {
      TypedQuery<Task> query = createQuery(uriInfo);
      query = applyResolutionParameter(query, status);
      applyPaginationParameters(query, start, limit);
      return query.getResultList();
   }

   @Path("/{taskId}")
   public TaskResource getTaskSubresource()
   {
      return taskSubresource;
   }

   protected TypedQuery<Task> createQuery(UriInfo uriInfo)
   {
      String categoryName = uriInfo.getPathParameters().getFirst("category");
      if (categoryName == null)
      {
         return em.createNamedQuery("tasks", Task.class);
      }
      else
      {
         return em.createNamedQuery("tasksByCategory", Task.class).setParameter("category", categoryName);
      }
   }
}
