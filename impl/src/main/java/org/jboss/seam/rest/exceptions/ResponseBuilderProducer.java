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

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * A request-scoped resource for customizing an REST error response from within
 * a Seam Catch exception handler.
 *
 * @author <a href="http://community.jboss.org/people/dan.j.allen">Dan Allen</a>
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@RequestScoped
public class ResponseBuilderProducer
{
   private ResponseBuilder responseBuilder;

   @Produces
   @RequestScoped
   @RestResource
   public ResponseBuilder getResponseBuilder()
   {
      return responseBuilder;
   }

   @Produces
   @RestResource
   public Response buildCatchResponse()
   {
      return responseBuilder.build();
   }

   @PostConstruct
   public void initialize()
   {
      responseBuilder = Response.serverError();
   }
}
