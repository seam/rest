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

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.seam.rest.client.RestClient;
import org.jboss.seam.rest.util.Annotations;

/**
 * Produces REST Clients - proxied JAX-RS interfaces.
 * 
 * @Inject @RestClient
 * private TaskService tasks;
 * 
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
@ApplicationScoped
public class RestClientProducer
{
   @Produces @RestClient("")
   public Object produce(InjectionPoint ip, ClientExecutor executor)
   {
      RestClient qualifier = Annotations.getAnnotation(ip.getQualifiers(), RestClient.class);
      
      if (qualifier == null || ! (ip.getType() instanceof Class<?>))
      {
         throw new IllegalStateException("@RestClient injection point " + ip.getMember() + " is not valid."); // this should never happen
      }
      
      Class<?> clazz = (Class<?>) ip.getType();
      return ProxyFactory.create(clazz, qualifier.value(), executor);
   }
   
   // TODO disposer method?
}
