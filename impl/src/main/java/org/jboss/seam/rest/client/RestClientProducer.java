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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.seam.rest.client.RestClient;
import org.jboss.seam.rest.util.Annotations;
import org.jboss.seam.rest.util.Interpolator;

/**
 * Produces REST Clients
 * <ul>
 * <li>proxied JAX-RS interface</li>
 * <li>ClientRequest</li>
 * </ul>
 * 
 * <code>
 * @Inject @RestClient
 * private TaskService tasks;
 * @Inject @RestCLient
 * private ClientRequest request;
 * </code>
 * 
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
@ApplicationScoped
public class RestClientProducer
{
   @Inject
   private Interpolator interpolator;
   
   /**
    * Producer method for proxied JAX-RS interfaces - REST Clients
    * This method is not registered as a producer method by default.
    * It is registered by {@link RestClientExtension} only if there is an appropriate injection point.
    */
   public Object produceRestClient(InjectionPoint ip, ClientExecutor executor)
   {
      RestClient qualifier = Annotations.getAnnotation(ip.getQualifiers(), RestClient.class);
      
      if (qualifier == null || ! (ip.getType() instanceof Class<?>))
      {
         throw new IllegalStateException("@RestClient injection point " + ip.getMember() + " is not valid."); // this should never happen
      }
      
      Class<?> clazz = (Class<?>) ip.getType();
      String url = interpolator.interpolate(qualifier.value());
      return ProxyFactory.create(clazz, url, executor);
   }
   
   /**
    * Produces ClientRequest instances.
    */
   @Produces @RestClient("")
   public ClientRequest produceClientRequest(InjectionPoint ip, ClientExecutor executor)
   {
      RestClient qualifier = Annotations.getAnnotation(ip.getQualifiers(), RestClient.class);
      
      if (qualifier == null)
      {
         throw new IllegalStateException("@RestClient injection point " + ip.getMember() + " is not valid."); // this should never happen
      }
      
      String url = interpolator.interpolate(qualifier.value());
      
      return new ClientRequest(url, executor);
   }
   
   // TODO disposer methods?
}
