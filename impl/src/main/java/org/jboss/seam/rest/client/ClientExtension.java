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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessInjectionTarget;

import org.jboss.logging.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.seam.rest.util.Annotations;
import org.jboss.seam.rest.util.Utils;

public class ClientExtension implements Extension
{
   private static final String RESTEASY_PROVIDER_FACTORY_NAME = "org.jboss.resteasy.spi.ResteasyProviderFactory";
   private static final Logger log = Logger.getLogger(ClientExtension.class);
   private boolean enabled;

   public void registerExtension(@Observes BeforeBeanDiscovery event, BeanManager manager)
   {
      enabled = Utils.isClassAvailable(RESTEASY_PROVIDER_FACTORY_NAME) ;
         
      if (enabled)
      {
         log.info("Seam REST Client Extension starting...");
         event.addAnnotatedType(manager.createAnnotatedType(DefaultClientExecutorProducer.class));
      }
   }

   public void enableRestClientInjection(@Observes ProcessInjectionTarget<?> event, BeanManager manager)
   {
      if (enabled)
      {
         Map<Field, RestClient> clientRequestFields = new HashMap<Field, RestClient>();
         Map<Field, RestClient> webServiceFields = new HashMap<Field, RestClient>();
         
         // scan injection target's fields
         for (AnnotatedField<?> field : event.getAnnotatedType().getFields())
         {
            RestClient annotation = Annotations.getAnnotation(field.getAnnotations(), RestClient.class);
            if (annotation != null)
            {
               Field javaField = field.getJavaMember();
               
               if (!Modifier.isPublic(javaField.getModifiers()))
               {
                  javaField.setAccessible(true);
               }
               
               /*
                * @RestClient("http://example.com")
                * private ClientRequest request
                */
               if (ClientRequest.class.equals(field.getBaseType()))
               {
                  clientRequestFields.put(javaField, annotation);
                  log.infov("Found @RestClient injection point {0}", field); // TODO
                  continue;
               }
               /*
                * @RestClient("http://example.com")
                * private TaskService service
                */
               if (javaField.getType().isInterface())
               {
                  webServiceFields.put(javaField, annotation);
                  log.infov("Found @RestClient injection point {0}", field); // TODO
                  continue;
               }
               
               // other types are not supported
               event.addDefinitionError(new RuntimeException("@RestClient injection of " + javaField.getType()+ " is not supported."));
            }
         }
         
         if (!clientRequestFields.isEmpty() || !webServiceFields.isEmpty())
         {
          log.infov("@RestClient injection enabled for {0}", event.getAnnotatedType().getJavaClass()); // TODO
          wrapInjectionTarget(event, clientRequestFields, webServiceFields, manager);
         }
      }
   }

   private <T> void wrapInjectionTarget(ProcessInjectionTarget<T> event, Map<Field, RestClient> clientRequestFields,Map<Field, RestClient> webServiceFields, BeanManager manager)
   {
      RestClientInjectionTarget<T> wrappedInjectionTarget = new RestClientInjectionTarget<T>(event.getAnnotatedType(), clientRequestFields, webServiceFields, event.getInjectionTarget(), manager);
      event.setInjectionTarget(wrappedInjectionTarget);
   }
}
