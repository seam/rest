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
package org.jboss.seam.resteasy.validation;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.jboss.resteasy.core.PropertyInjectorImpl;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.PropertyInjector;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

/**
 * This interceptor workarounds https://jira.jboss.org/browse/CDI-6. Enable it if you experience problems with JAX-RS injection into CDI-intercepted beans.
 */
@ValidateRequest
@Interceptor
public class InjectionInterceptor implements Serializable
{
   private static final long serialVersionUID = -4805100726530802448L;

   @PostConstruct
   public void performJaxrsInjection(InvocationContext ctx)
   {
      HttpRequest request = ResteasyProviderFactory.getContextData(HttpRequest.class);
      HttpResponse response = ResteasyProviderFactory.getContextData(HttpResponse.class);

      PropertyInjector propertyInjector = new PropertyInjectorImpl(ctx.getTarget().getClass(), ResteasyProviderFactory.getInstance());
      
      if ((request != null) && (response != null))
      {
         propertyInjector.inject(request, response, ctx.getTarget());
      }
      else
      {
         throw new IllegalStateException("Trying to instantiate a resource outside of HTTP request.");
      }
   }
}
