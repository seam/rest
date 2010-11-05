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
package org.jboss.seam.rest.test.client;

import javax.ws.rs.core.Response;

public class TaskServiceImpl implements TaskService
{
   public Response createTask(Task task)
   {
      if ((task.getId() == 1) && ("foo".equals(task.getName())) && ("bar".equals(task.getDescription())))
      {
         return Response.status(200).build();
      }
      else
      {
         return Response.status(400).build();
      }
   }

   public Task getTask(int a, int b, int c)
   {
      if (a + b + c == 6)
      {
         return new Task(2, "alpha", "bravo");
      }
      else
      {
         return null;
      }
   }
}
