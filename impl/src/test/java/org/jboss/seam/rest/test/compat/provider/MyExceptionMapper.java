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
package org.jboss.seam.rest.test.compat.provider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

@Provider
@ApplicationScoped
public class MyExceptionMapper implements ExceptionMapper<NullPointerException>
{
   @Context
   private SecurityContext securityContext;
   @Context
   private Providers providers;
   @Context
   private ServletConfig servletConfig;
   @Context
   private ServletContext servletContext;
   @Inject
   private Foo cdiFieldInjection;
   
   @Override
   public Response toResponse(NullPointerException exception)
   {
      StringBuilder builder = new StringBuilder();
      builder.append("SecurityContext:" + (securityContext != null));
      builder.append(",Providers:" + (providers != null));
      builder.append(",ServletConfig:" + (servletConfig != null));
      builder.append(",ServletContext:" + (servletContext != null));
      builder.append(",CDI field injection:" + (cdiFieldInjection != null));
      return Response.status(200).entity(builder.toString()).type(MediaType.TEXT_HTML).build();
   }
}
