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

import javax.inject.Inject;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.seam.rest.client.RestClient;

public class InjectedBean
{
   @Inject @RestClient("http://localhost:8080/test/ping")
   private ClientRequest request;

   @Inject @RestClient("http://localhost:8080/test")
   private TaskService taskService;
   
   @Inject @RestClient("http://localhost:8080/test")
   private PingService pingService;

   public ClientRequest getRequest()
   {
      return request;
   }

   public int createTask()
   {
      return taskService.createTask(new Task(1, "foo", "bar")).getStatus();
   }
   
   public Task getTask()
   {
      return taskService.getTask(1, 2, 3);
   }
   
   public String ping()
   {
      return pingService.ping();
   }
}
