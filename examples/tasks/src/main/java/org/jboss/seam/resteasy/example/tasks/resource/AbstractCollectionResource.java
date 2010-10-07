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

import javax.persistence.TypedQuery;

/**
 * Common methods for collection resources
 * @author Jozef Hartinger
 *
 */
public abstract class AbstractCollectionResource
{
   /**
    * Modifies query passed as a parameter to filter out tasks based on their status (resolved/unresolved/all) 
    */
   protected <T> TypedQuery<T> applyResolutionParameter(TypedQuery<T> query, String status)
   {
      if ("resolved".equals(status))
      {
         // double assignment as a workaround for HHH-4541
         return query.setParameter("r1", true).setParameter("r2", true);
      }
      else if ("unresolved".equals(status))
      {
         return query.setParameter("r1", false).setParameter("r2", false);
      }
      else
      {
         return query.setParameter("r1", true).setParameter("r2", false);
      }
   }

   /**
    * Sets paginatation parameters
    * @param query JPA query
    * @param start the first item
    * @param limit how many items to return (use 0 for unlimited result)
    * @throws IllegalArgumentException if any of the integer parameters is lesser than 0 
    */
   protected <T> void applyPaginationParameters(TypedQuery<T> query, int start, int limit)
   {
      if (start < 0)
      {
         throw new IllegalArgumentException("start");
      }
      query.setFirstResult(start);
      
      if (limit < 0)
      {
         throw new IllegalArgumentException("limit");
      }
      if (limit > 0)
      {
         query.setMaxResults(limit);
      }
   }
}
