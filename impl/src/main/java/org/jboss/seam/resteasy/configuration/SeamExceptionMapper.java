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
package org.jboss.seam.resteasy.configuration;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;

import org.jboss.seam.resteasy.util.Interpolator;

/**
 * SeamExceptionMapper allows exceptions to be mapped to HTTP status codes
 * declaratively (at runtime).
 * 
 * @author Jozef Hartinger
 * 
 */
public class SeamExceptionMapper implements ExceptionMapper<Throwable>
{
   private ResponseBuilder responseBuilder;
   private String message;
   private boolean interpolateMessageBody = true;

   @Inject
   private Interpolator interpolator;

   protected void initialize(int status)
   {
      responseBuilder = Response.status(status);
   }

   protected void initialize(ExceptionMapping mapping)
   {
      responseBuilder = Response.status(mapping.getStatusCode());
      message = mapping.getMessage();
      interpolateMessageBody = mapping.isInterpolateMessageBody();

      if (message != null && !interpolateMessageBody)
      {
         // since the error message is always the same, we can cache it
         ErrorMessageWrapper error = new ErrorMessageWrapper(message);
         responseBuilder.entity(error);
      }
   }

   public Response toResponse(Throwable exception)
   {
      if (responseBuilder == null)
      {
         throw new IllegalStateException("ExceptionMapper not initialized.");
      }

      ResponseBuilder response = responseBuilder.clone();

      if (message != null && interpolateMessageBody)
      {
         // since the message might be different every time, we have to recreate
         // it on each request
         ErrorMessageWrapper error = new ErrorMessageWrapper(interpolator.interpolate(message));
         response.entity(error);
      }
      return response.build();
   }
}
