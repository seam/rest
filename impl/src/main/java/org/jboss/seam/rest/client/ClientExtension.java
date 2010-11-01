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

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessInjectionTarget;

import org.jboss.logging.Logger;
import org.jboss.seam.rest.util.Annotations;

public class ClientExtension implements Extension
{
   private static final String RESTEASY_PROVIDER_FACTORY_NAME = "org.jboss.resteasy.spi.ResteasyProviderFactory";
   private static final Logger log = Logger.getLogger(ClientExtension.class);
   private boolean enabled;

   public void registerExtension(@Observes BeforeBeanDiscovery event, BeanManager manager)
   {
      enabled = isResteasyAvailable();
      
      if (enabled)
      {
         log.info("Seam REST Client Extension starting...");
         event.addAnnotatedType(manager.createAnnotatedType(DefaultClientExecutorProducer.class));
      }
   }

   public void enableServiceInjection(@Observes ProcessInjectionTarget<?> event, BeanManager manager)
   {
      if (enabled && usesRestClientInjection(event.getAnnotatedType()))
      {
         log.debugf("@RestClient injection enabled for {}", event.getAnnotatedType().getJavaClass());
         wrapInjectionTarget(event, manager);
      }
   }

   private boolean usesRestClientInjection(AnnotatedType<?> type)
   {
      for (AnnotatedField<?> field : type.getFields())
      {
         // TODO this scan is duplicated in RestClientInjectionTarget
         if (Annotations.getAnnotation(field.getAnnotations(), RestClient.class) != null)
         {
            return true;
         }
      }
      return false;
   }

   private <T> void wrapInjectionTarget(ProcessInjectionTarget<T> event, BeanManager manager)
   {
      RestClientInjectionTarget<T> wrappedInjectionTarget = new RestClientInjectionTarget<T>(event.getAnnotatedType(), event.getInjectionTarget(), manager);
      event.setInjectionTarget(wrappedInjectionTarget);
   }

   private boolean isResteasyAvailable()
   {
      try
      {
         Thread.currentThread().getContextClassLoader().loadClass(RESTEASY_PROVIDER_FACTORY_NAME);
         return true;
      }
      catch (ClassNotFoundException e)
      {
         return false;
      }
   }
}
