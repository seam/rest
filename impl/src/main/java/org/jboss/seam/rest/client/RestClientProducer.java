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
package org.jboss.seam.rest.client;

import java.util.Iterator;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.ClientErrorInterceptor;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.seam.rest.client.RestClient;
import org.jboss.seam.rest.util.Interpolator;
import org.jboss.seam.solder.reflection.AnnotationInspector;

/**
 * Produces REST Clients
 * <ul>
 * <li>proxied JAX-RS interface</li>
 * <li>ClientRequest</li>
 * </ul>
 * 
 * <code>
 * 
 * @Inject @RestClient private TaskService tasks;
 * @Inject @RestCLient private ClientRequest request; </code>
 * 
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 * 
 */
@ApplicationScoped
public class RestClientProducer
{
   private static final Logger log = Logger.getLogger(RestClientProducer.class);
   @Inject
   private Interpolator interpolator;
   @Inject
   private BeanManager manager;

   @Inject
   public void initClientSupport(Instance<ClientErrorInterceptor> interceptors)
   {
      /*
       * Automatically Register ClientErrors
       */
      Iterator<ClientErrorInterceptor> iterator = interceptors.iterator();
      while (iterator.hasNext())
      {
         ClientErrorInterceptor interceptor = iterator.next();
         ResteasyProviderFactory.getInstance().addClientErrorInterceptor(interceptor);
         log.infov("Registered ClientErrorInterceptor {0}", interceptor.getClass());
      }
   }
   
   /**
    * Producer method for proxied JAX-RS interfaces - REST Clients This method
    * is not registered as a producer method by default. It is registered by
    * {@link RestClientExtension} only if there is an appropriate injection
    * point.
    */
   public Object produceRestClient(InjectionPoint ip, ClientExecutor executor)
   {
      RestClient qualifier = AnnotationInspector.getAnnotation(ip.getAnnotated(), RestClient.class, manager);

      if (qualifier == null || !(ip.getType() instanceof Class<?>))
      {
         // this should never happen
         throw new IllegalStateException("@RestClient injection point " + ip.getMember() + " is not valid.");
      }

      Class<?> clazz = (Class<?>) ip.getType();
      String url = interpolator.interpolate(qualifier.value());
      return ProxyFactory.create(clazz, url, executor);
   }

   /**
    * Produces ClientRequest instances.
    */
   @Produces
   @RestClient("")
   public ClientRequest produceClientRequest(InjectionPoint ip, ClientExecutor executor)
   {
      RestClient qualifier = AnnotationInspector.getAnnotation(ip.getAnnotated(), RestClient.class, manager);

      if (qualifier == null)
      {
         // this should never happen
         throw new IllegalStateException("@RestClient injection point " + ip.getMember() + " is not valid."); 
      }

      String url = interpolator.interpolate(qualifier.value());

      return new ClientRequest(url, executor);
   }

   // TODO disposer methods?
}
