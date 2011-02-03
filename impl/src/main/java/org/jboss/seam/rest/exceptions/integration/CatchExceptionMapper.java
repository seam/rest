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

package org.jboss.seam.rest.exceptions.integration;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;

import org.jboss.logging.Logger;
import org.jboss.seam.exception.control.CaughtException;
import org.jboss.seam.exception.control.ExceptionToCatch;
import org.jboss.seam.exception.control.Handles;
import org.jboss.seam.exception.control.HandlesExceptions;
import org.jboss.seam.exception.control.Precedence;
import org.jboss.seam.exception.control.TraversalMode;
import org.jboss.seam.rest.exceptions.RestRequest;
import org.jboss.seam.rest.exceptions.RestResource;
import org.jboss.seam.rest.exceptions.SeamExceptionMapper;
import org.jboss.seam.rest.util.Interpolator;

/**
 * A JAX-RS ExceptionMapper implementation that maps all exceptions (i.e.,
 * Throwable) raised during a JAX-RS request to the Seam Catch exception
 * handling bus.
 * <p>
 * Exceptions are send to Seam Catch by firing an event of type
 * {@link ExceptionToCatch} to the CDI event bus. The event payload
 * contains the exception and the qualifier &#064;RestRequest. The qualifier
 * allows handlers that deal specifically with REST requests to be selected.
 * </p>
 *
 * @author <a href="http://community.jboss.org/people/dan.j.allen">Dan Allen</a>
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@ApplicationScoped
@HandlesExceptions
@Specializes
public class CatchExceptionMapper extends SeamExceptionMapper implements ExceptionMapper<Throwable>
{
   @Inject
   @RestResource
   private Instance<Response> response;
   @Inject
   private Event<ExceptionToCatch> bridgeEvent;
   @Inject
   private Interpolator interpolator;
   
   private static final Logger log = Logger.getLogger(CatchExceptionMapper.class);

   @Override
   public Response toResponse(Throwable exception)
   {
      ExceptionToCatch payload = new ExceptionToCatch(exception, RestRequest.RestRequestLiteral.INSTANCE);
      
      // The call returns if the exception is handled. The exception is rethrown by catch otherwise. 
      bridgeEvent.fire(payload);
      
      return response.get();
   }
   
   public void handleException(@Handles(precedence = Precedence.BUILT_IN, during = TraversalMode.DEPTH_FIRST) @RestRequest CaughtException<Throwable> event, @RestResource ResponseBuilder builder)
   {
      Class<? extends Throwable> exceptionType = event.getException().getClass();
      log.debugv("Handling {0}", exceptionType);
      
      if (getMappings().containsKey(exceptionType))
      {
         produceResponse(event.getException(), builder, interpolator);
         event.handled();
      }
      else
      {
         event.rethrow();
         event.unmute(); // let us handle the causing exception
      }
   }
}
