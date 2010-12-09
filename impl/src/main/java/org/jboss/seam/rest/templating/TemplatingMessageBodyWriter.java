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
package org.jboss.seam.rest.templating;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.jboss.seam.servlet.event.Initialized;

/**
 * TemplatingMessageBodyWriter is enabled for every JAX-RS method annotated
 * with @ResponseTemplate annotation and delegates response production to the
 * TemplatingProvider.
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
@Provider
@ApplicationScoped
public class TemplatingMessageBodyWriter implements MessageBodyWriter<Object>
{
   private static final Logger log = Logger.getLogger(TemplatingMessageBodyWriter.class);
   private TemplatingProvider provider;
   private Class<? extends TemplatingProvider> preferedTemplatingProvider;

   public TemplatingMessageBodyWriter()
   {
      super();
   }

   @Inject
   public void init(Instance<TemplatingProvider> providerInstance, TemplatingExtension extension)
   {
      Instance<? extends TemplatingProvider> instance = providerInstance;
      if (preferedTemplatingProvider != null)
      {
         instance = providerInstance.select(preferedTemplatingProvider);
      }
      
      // no templating engines found
      if (instance.isUnsatisfied())
      {
         if (preferedTemplatingProvider == null)
         {
            log.info("No TemplateProvider found. Templating support disabled.");
            return;
         }
         else
         {
            throw new RuntimeException("Unable to load prefered TemplateProvider " + preferedTemplatingProvider.getName());
         }
      }

      // multiple templating engines found
      if (instance.isAmbiguous())
      {
         throw new RuntimeException("Multiple TemplatingProviders found on classpath. Select the prefered one.");
      }
      
      provider = instance.get();
      log.infov("Seam REST Templating Extension enabled. Using {0}", provider.toString());
   }
   
   public void setServletContext(@Observes @Initialized ServletContext context)
   {
      if (provider != null)
      {
         provider.init(context);
      }
   }

   public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
   {
      if (provider == null)
      {
         return false;
      }
      ResponseTemplate annotation = findAnnotation(annotations, mediaType);
      return annotation != null;
   }

   public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
   {
      return -1;
   }

   public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException
   {
      ResponseTemplate annotation = findAnnotation(annotations, mediaType);
      provider.writeTo(t, annotation, annotations, mediaType, httpHeaders, entityStream);
   }

   private ResponseTemplate findAnnotation(Annotation[] annotations, MediaType type)
   {
      for (Annotation a : annotations)
      {
         /*
          * multiple @ResponseTemplate annotations
          * 
          * @ResponseTemplate.List({
          * 
          * @ResponseTemplate(...)
          * 
          * @ResponseTemplate(...) })
          */
         if (ResponseTemplate.List.class.isAssignableFrom(a.annotationType()))
         {
            ResponseTemplate.List list = (ResponseTemplate.List) a;
            return findAnnotation(list.value(), type);
         }
         /*
          * single @ResponseTemplate
          */
         if (ResponseTemplate.class.isAssignableFrom(a.annotationType()))
         {
            ResponseTemplate annotation = (ResponseTemplate) a;
            MediaType producedType = MediaType.valueOf(annotation.produces());
            if (producedType.isCompatible(type))
            {
               return annotation;
            }
         }
      }
      return null;
   }

   public Class<? extends TemplatingProvider> getPreferedTemplatingProvider()
   {
      return preferedTemplatingProvider;
   }

   public void setPreferedTemplatingProvider(Class<? extends TemplatingProvider> preferedTemplatingProvider)
   {
      this.preferedTemplatingProvider = preferedTemplatingProvider;
   }
}
