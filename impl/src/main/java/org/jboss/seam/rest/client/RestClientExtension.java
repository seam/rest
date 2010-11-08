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

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessProducerMethod;

import org.jboss.logging.Logger;
import org.jboss.seam.rest.util.Annotations;
import org.jboss.seam.rest.util.DelegatingBean;
import org.jboss.seam.rest.util.Utils;
/**
 * The Seam REST Client extension provides injection of
 * - org.jboss.resteasy.client.ClientRequest instances
 * - REST clients - proxied JAX-RS interfaces capable of invoking client requests
 * 
 * 
 * How @RestClient injection of proxied JAX-RS interfaces works:
 * 1.) We register the RestClientProducer 
 *    - it is useless since by default it produces Object instances only
 *    - we let the container create the Bean instance for the RestClientProducer
 *       and use it as a delegate later on
 * 2.) @RestClient injection points are scanned for JAX-RS interfaces
 * 3.) We create and register the correct RestClientProducer using the delegate from step 1.
 *    - all found JAX-RS interfaces are added to the type closure of the second RestClientProducer
 *    
 *       
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 **/
public class RestClientExtension implements Extension
{
   private static final Logger log = Logger.getLogger(RestClientExtension.class);
   private static final String RESTEASY_PROVIDER_FACTORY_NAME = "org.jboss.resteasy.spi.ResteasyProviderFactory";
   private boolean enabled;
   
   private Set<Type> jaxrsInterfaces = new HashSet<Type>();
   private Bean<Object> restClientProducerDelegate;

   public void registerExtension(@Observes BeforeBeanDiscovery event, BeanManager manager)
   {
      enabled = Utils.isClassAvailable(RESTEASY_PROVIDER_FACTORY_NAME);
      if (enabled)
      {
         log.info("Seam REST Client Extension enabled.");
         // register providers
         event.addAnnotatedType(manager.createAnnotatedType(DefaultClientExecutorProducer.class));
         event.addAnnotatedType(manager.createAnnotatedType(ClientRequestProducer.class));
         event.addAnnotatedType(manager.createAnnotatedType(RestClientProducer.class));
      }
   }
   
   public void getRestClientProducerDelegate(@Observes ProcessProducerMethod<RestClientProducer, Object> event)
   {
      this.restClientProducerDelegate = event.getBean();
   }
   
   public <T> void scanInjectionPointsForJaxrsInterfaces(@Observes ProcessBean<T> event)
   {
      if (!enabled)
      {
         return;
      }
      
      for (InjectionPoint ip : event.getBean().getInjectionPoints())
      {
         RestClient qualifier = Annotations.getAnnotation(ip.getQualifiers(), RestClient.class);
         
         if (qualifier != null)
         {
            if (ip.getType() instanceof Class<?>)
            {
               Class<?> clazz = (Class<?>) ip.getType();
               
               if (clazz.isInterface()) // we only support interfaces
               {
                  jaxrsInterfaces.add(clazz);
               }
            }
         }
      }
   }

   /**
    * Registers the RestClientProducer if there is an injection point that requires it
    */
   public void afterBeanDiscovery(@Observes AfterBeanDiscovery event)
   {
      if (enabled && !jaxrsInterfaces.isEmpty())
      {
         // register an additional RestClientProducer that supports all the interfaces
         event.addBean(wrapBean(restClientProducerDelegate, jaxrsInterfaces));
      }
   }

   private <T> Bean<T> wrapBean(final Bean<T> delegate, final Set<Type> types)
   {
      return new DelegatingBean<T>(delegate)
      {
         @Override
         public Set<Type> getTypes()
         {
            return Collections.unmodifiableSet(types);
         }
      };
   }
}
