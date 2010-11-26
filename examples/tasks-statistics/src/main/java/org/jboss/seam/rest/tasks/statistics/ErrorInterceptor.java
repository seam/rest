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
package org.jboss.seam.rest.tasks.statistics;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.ClientErrorInterceptor;

public class ErrorInterceptor implements ClientErrorInterceptor
{
   public static final FacesMessage NOT_FOUND = new FacesMessage("Seam Tasks instance not found. (404)");
   public static final FacesMessage ERROR = new FacesMessage("Error connecting to Seam Tasks server.");
   
   public void handle(ClientResponse<?> response) throws RuntimeException
   {
      if (Status.NOT_FOUND.equals(response.getResponseStatus()))
      {
         FacesContext.getCurrentInstance().addMessage(null, NOT_FOUND);
      }
      else
      {
         FacesContext.getCurrentInstance().addMessage(null, ERROR);
      }
   }
}
