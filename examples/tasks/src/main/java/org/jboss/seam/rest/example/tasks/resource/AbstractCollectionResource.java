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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * Ugly workaround for https://jira.jboss.org/browse/CDI-6
 * (We must use setter injection instead of field injection which makes things less clear)
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
public abstract class AbstractCollectionResource
{
   protected UriInfo uriInfo;
   @Min(value = 0, message = "start must be a non-negative number")
   protected int start;
   @Min(value = 0, message = "limit must be a non-negative number")
   @Max(value = 100, message = "Cannot return more than 100 items")
   protected int limit;
   
   @QueryParam("start")
   @DefaultValue("0")
   public void setStart(int start)
   {
      this.start = start;
   }

   @QueryParam("limit")
   @DefaultValue("5")
   public void setLimit(int limit)
   {
      this.limit = limit;
   }

   @Context
   public void setUriInfo(UriInfo uriInfo)
   {
      this.uriInfo = uriInfo;
   }
}
