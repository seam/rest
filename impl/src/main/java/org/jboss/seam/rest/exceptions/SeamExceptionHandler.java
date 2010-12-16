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
package org.jboss.seam.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.logging.Logger;
import org.jboss.seam.exception.control.CatchResource;
import org.jboss.seam.exception.control.CaughtException;
import org.jboss.seam.exception.control.Handles;
import org.jboss.seam.exception.control.HandlesExceptions;
import org.jboss.seam.exception.control.TraversalPath;
import org.jboss.seam.rest.exceptions.ExceptionMapping;
import org.jboss.seam.rest.exceptions.ExceptionMappingConfiguration;
import org.jboss.seam.rest.exceptions.PlainTextExceptionMapping;
import org.jboss.seam.rest.util.Interpolator;
import org.jboss.seam.servlet.event.Initialized;

/**
 * SeamExceptionHandler allows exceptions to be mapped to HTTP status codes
 * declaratively (at runtime).
 * 
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 * 
 */
@ApplicationScoped
@HandlesExceptions
public class SeamExceptionHandler
{

   @Inject
   private Interpolator interpolator;
   private Map<Class<? extends Throwable>, ExceptionMapping> mappings = new HashMap<Class<? extends Throwable>, ExceptionMapping>();

   private static final Logger log = Logger.getLogger(SeamExceptionHandler.class);

   /**
    * Store mappings in a Map so that we can find them by the exception type
    */
   public void init(@Observes @Initialized ServletContext context, ExceptionMappingConfiguration configuration)
   {
      log.info("Processing exception mapping configuration.");
      for (ExceptionMapping mapping : configuration.getExceptionMappings())
      {
         this.mappings.put(mapping.getExceptionType(), mapping);
         log.infov("Registered {0}", mapping);
      }
   }

   public void handleException(@Handles(precedence = -100, during = TraversalPath.DESCENDING) @RestRequest CaughtException<Throwable> event, @CatchResource ResponseBuilder builder)
   {
      Class<? extends Throwable> exceptionType = event.getException().getClass();
      log.debugv("Handling {0}", exceptionType);
      
      if (mappings.containsKey(exceptionType))
      {
         produceResponse(event.getException(), builder);
         event.handled();
      }
      else
      {
         event.rethrow();
         event.unmute(); // let us handle the causing exception
      }
   }
   
   protected void produceResponse(Throwable exception, ResponseBuilder builder)
   {
      ExceptionMapping mapping = mappings.get(exception.getClass());
      log.debugv("Found exception mapping {0} for {1}", mapping, exception.getClass());

      builder.status(mapping.getStatusCode());
      if (mapping.getMessage() != null)
      {
         builder.entity(createEntityBody(mapping));
      }
   }

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
}
