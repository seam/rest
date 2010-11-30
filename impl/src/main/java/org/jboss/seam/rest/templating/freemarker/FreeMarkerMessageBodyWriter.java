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
package org.jboss.seam.rest.templating.freemarker;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.jboss.seam.rest.templating.freemarker.FreeMarkerTemplate;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Converts the response object to a rendered FreeMarker template.
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
@Provider
public class FreeMarkerMessageBodyWriter implements MessageBodyWriter<Object>
{
   private Configuration configuration;
   @Inject
   private FreeMarkerModel model;
   
   public FreeMarkerMessageBodyWriter()
   {
      configuration = new Configuration();
      configuration.setObjectWrapper(new DefaultObjectWrapper());
   }
   
   @Context
   public void setServletContext(ServletContext sc)
   {
      configuration.setServletContextForTemplateLoading(sc, "/");
   }

   public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
   {
      return findAnnotation(annotations) != null;
   }

   public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
   {
      return -1;
   }

   public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException
   {
      FreeMarkerTemplate annotation = findAnnotation(annotations);
      
      if (annotation == null)
      {
         throw new IllegalStateException(FreeMarkerTemplate.class.getName() + " not found.");
      }
      
      Template template = configuration.getTemplate(annotation.value());
      
      model.getData().put("response", t);
      
      try
      {
         OutputStreamWriter writer = new OutputStreamWriter(entityStream);
         template.process(model.getWrappedModel(), writer);
         writer.flush();
      }
      catch (TemplateException e)
      {
         throw new RuntimeException("Unable to write FreeMarker response.", e);
      }
   }
   
   private FreeMarkerTemplate findAnnotation(Annotation[] annotations)
   {
      for (Annotation a : annotations)
      {
         if (FreeMarkerTemplate.class.isAssignableFrom(a.annotationType()))
         {
            return (FreeMarkerTemplate) a;
         }
      }
      return null;
   }
}
