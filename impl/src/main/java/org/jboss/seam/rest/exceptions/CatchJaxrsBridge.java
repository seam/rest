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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.seam.exception.control.CatchResource;
import org.jboss.seam.exception.control.ExceptionToCatch;

/**
 * A JAX-RS ExceptionMapper implementation that maps all exceptions (i.e.,
 * Throwable) raised during a JAX-RS request to the Seam Catch exception
 * handling bus.
 * <p/>
 * <p>
 * Exceptions are send to Seam Catch by firing an event of type
 * {@link ExceptionToCatch} to the CDI event bus. The event payload
 * contains the exception and the qualifier &#064;RestRequest. The qualifier
 * allows handlers that deal specifically with REST requests to be selected.
 * </p>
 *
 * @author <a href="http://community.jboss.org/people/dan.j.allen">Dan Allen</a>
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 */
@Provider
@ApplicationScoped
public class CatchJaxrsBridge implements ExceptionMapper<Throwable>
{
   @Inject @CatchResource
   private Instance<Response> responseProvider;

   @Inject
   private Event<ExceptionToCatch> bridgeEvent;

   public Response toResponse(Throwable exception)
   {
      ExceptionToCatch payload = new ExceptionToCatch(exception, RestRequest.RestRequestLiteral.INSTANCE);
      
      // The call returns if the exception is handled. The exception is rethrown by catch otherwise. 
      bridgeEvent.fire(payload);
      
      return responseProvider.get();
   }
}
