/*
 * JBoss, Home of Professional Open Source
 * Copyright [2010], Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.seam.rest.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.jboss.seam.rest.SeamRestConfiguration;
import org.jboss.seam.rest.util.Interpolator;
import org.jboss.seam.rest.validation.ValidationException;
import org.jboss.seam.rest.validation.ValidationExceptionHandler;
import org.jboss.seam.servlet.event.Initialized;

/**
 * This {@link ExceptionMapper} implementation converts caught exceptions to HTTP responses
 * based on exception mapping rules. 
 * 
 * <p>
 * If there is no matching rule for an exception, the exception is rethrown wrapped within
 * {@link UnhandledException}. Note that this implementation is replaced by CatchExceptionMapper
 * in environments where Seam Catch is available.
 * </p> 
 * 
 * @see ExceptionMappingConfiguration
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 * 
 */
@Provider
@ApplicationScoped
public class SeamExceptionMapper implements ExceptionMapper<Throwable>
{
   @Inject
   @RestResource
   private ResponseBuilder responseBuilder;
   @Inject
   @RestResource
   private Instance<Response> response;
   @Inject
   private Interpolator interpolator;
   @Inject
   private ValidationExceptionHandler validationExceptionHandler;

   private Map<Class<? extends Throwable>, Mapping> mappings = new HashMap<Class<? extends Throwable>, Mapping>();

   private static final Logger log = Logger.getLogger(SeamExceptionMapper.class);

   /**
    * Mappings are stored in a Map so that we can find them
    * by the exception type.
    */
   @Inject
   public void init(Instance<SeamRestConfiguration> configuration, ExceptionMappingExtension extension)
   {
      log.info("Processing exception mapping configuration.");
      
      // XML-configured mappings
      if (!configuration.isAmbiguous() && !configuration.isUnsatisfied())
      {
         Set<Mapping> exceptionMappings = configuration.get().getMappings();
         for (Mapping mapping : exceptionMappings)
         {
            addExceptionMapping(mapping);
         }
      }
      // annotation-configured mappings
      for (Mapping mapping : extension.getExceptionMappings())
      {
         addExceptionMapping(mapping);
      }
   }
   
   protected void addExceptionMapping(Mapping mapping)
   {
      this.mappings.put(mapping.getExceptionType(), mapping);
      log.infov("Registered {0}", mapping);
   }

   /**
    * This observer method triggers {@link #init(ExceptionMappingConfiguration)}
    * on bootstrap.
    */
   public void init(@Observes @Initialized ServletContext ctx)
   {
   }

   public Response toResponse(Throwable e)
   {
      log.debugv("Handling {0}", e.getClass());

      Throwable exception = e;

      while (exception != null) // iterate over cause chain
      {
         Class<? extends Throwable> exceptionType = exception.getClass();

         if (mappings.containsKey(exceptionType))
         {
            produceResponse(exception, responseBuilder, interpolator);
            return response.get();
         }
         
         if (exception instanceof ValidationException)
         {
            validationExceptionHandler.handleValidationException((ValidationException) exception, responseBuilder);
            return response.get();
         }

         log.debugv("Unwrapping {0}", exception.getClass());
         exception = exception.getCause();
      }

      // No ExceptionMapper/ExceptionMapping, rethrow the exception
      throw new UnhandledException(e);
   }

   protected void produceResponse(Throwable exception, ResponseBuilder builder, Interpolator interpolator)
   {
      Mapping mapping = mappings.get(exception.getClass());
      log.debugv("Found exception mapping {0} for {1}", mapping, exception.getClass());

      builder.status(mapping.getStatusCode());
      
      String message = createMessage(mapping.getMessage(), mapping.isInterpolateMessageBody(), mapping.isUseExceptionMessage(), exception, interpolator);
      if (message != null)
      {
         builder.entity(createEntityBody(mapping, message));
      }
   }

   protected String createMessage(String message, boolean interpolate, boolean useExceptionMessage, Throwable e, Interpolator interpolator)
   {
      String msg = message;
      if (msg == null || msg.length() == 0)
      {
         if (useExceptionMessage)
         {
            msg = e.getMessage();
         }
         else
         {
            return null; // empty body is acceptable
         }
      }
      
      if (interpolate && msg != null)
      {
         return interpolator.interpolate(msg);
      }
      return msg;
   }
   
   protected Object createEntityBody(Mapping mapping, String message)
   {
      if (mapping.isUseJaxb())
      {
         return new ErrorMessageWrapper(message);
      }
      return message;
   }

   public Map<Class<? extends Throwable>, Mapping> getMappings()
   {
      return mappings;
   }
}
