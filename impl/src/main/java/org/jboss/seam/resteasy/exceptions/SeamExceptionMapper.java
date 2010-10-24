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

package org.jboss.seam.resteasy.exceptions;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

import org.jboss.seam.resteasy.exceptions.ExceptionMapping;
import org.jboss.seam.resteasy.util.Interpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SeamExceptionMapper allows exceptions to be mapped to HTTP status codes
 * declaratively (at runtime).
 * 
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 * 
 */
@Provider
@ApplicationScoped
public class SeamExceptionMapper implements ExceptionMapper<Throwable>
{

   @Context
   private Providers providers;
   @Inject
   private Interpolator interpolator;
   private Map<Class<? extends Throwable>, ExceptionMapping> mappings = new HashMap<Class<? extends Throwable>, ExceptionMapping>();

   private static final Logger log = LoggerFactory.getLogger(SeamExceptionMapper.class);

   /**
    * Store mappings in a Map so that we can find them by the exception type
    */
   @Inject
   public void init(ExceptionMappingConfiguration configuration)
   {
      log.info("Processing exception mapping configuration.");
      for (ExceptionMapping mapping : configuration.getExceptionMappings())
      {
         this.mappings.put(mapping.getExceptionType(), mapping);
         log.info("Registered {}", mapping);
      }
   }

   public Response toResponse(Throwable e)
   {

      log.info("Handling {}", e.getClass()); // TODO

      Throwable exception = e;

      while (exception != null) // iterate over cause chain
      {

         Class<? extends Throwable> exceptionType = exception.getClass();

         if (mappings.containsKey(exceptionType))
         {
            return handleException(exception);
         }

         // Check if there is an ExceptionMapper to handle the request -
         // according to spec there should not be since it would be chosen
         // to handle the exception instead of the SeamExceptionMapper
         ExceptionMapper<? extends Throwable> mapper = providers.getExceptionMapper(exceptionType);
         // do not do recursion 
         if (mapper != null && !(mapper instanceof SeamExceptionMapper)) 
         {
            return delegateException(mapper, exception);
         }

         log.info("Unwrapping {}", exception.getClass()); // TODO
         exception = exception.getCause();
      }

      // No ExceptionMapper/ExceptionMapping, rethrow the exception
      throw new SeamRestException("Unhandled exception", e);
   }

   // TODO visibility
   private Response handleException(Throwable exception)
   {
      ExceptionMapping mapping = mappings.get(exception.getClass());
      log.info("Found exception mapping {} for {}", mapping, exception.getClass()); // TODO

      ResponseBuilder builder = Response.status(mapping.getStatusCode());
      if (mapping.getMessage() != null) 
      {
         builder.entity(createEntityBody(mapping));
      }
      return builder.build();
   }

   // TODO make this extensible - allow
   // users to provide own ExceptionMapping
   // subclasses
   protected Object createEntityBody(ExceptionMapping mapping)
   {
      if (mapping instanceof PlainTextExceptionMapping)
      {
         return getInterpolatedMessage(mapping);
      }
      else
      {
         return new ErrorMessageWrapper(getInterpolatedMessage(mapping));
      }
   }
   
   private String getInterpolatedMessage(ExceptionMapping mapping)
   {
      if (mapping.isInterpolateMessageBody())
      {
         return interpolator.interpolate(mapping.getMessage());
      }
      else
      {
         return mapping.getMessage();
      }
   }

   // TODO visibility
   @SuppressWarnings({ "rawtypes", "unchecked" })
   private Response delegateException(ExceptionMapper delegate, Throwable exception)
   {
      return delegate.toResponse(exception);
   }
}
