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
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.jboss.seam.rest.templating.ResponseTemplate;
import org.jboss.seam.rest.templating.TemplatingModel;
import org.jboss.seam.rest.templating.TemplatingProvider;
import org.jboss.weld.extensions.el.Expressions;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Converts the response object to a rendered FreeMarker template.
 * 
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 * 
 */
@ApplicationScoped
public class FreeMarkerProvider implements TemplatingProvider
{
   private Configuration configuration;
   @Inject
   private TemplatingModel model;
   @Inject
   private Expressions expressions;

   public void init(ServletContext servletContext)
   {
      configuration = new Configuration();
      configuration.setObjectWrapper(new DefaultObjectWrapper());
      configuration.setServletContextForTemplateLoading(servletContext, "/");
   }

   public void writeTo(Object o, ResponseTemplate annotation, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream os) throws IOException
   {
      Template template = configuration.getTemplate(annotation.value());

      model.getData().put(annotation.responseName(), o);

      try
      {
         OutputStreamWriter writer = new OutputStreamWriter(os);
         template.process(new ModelWrapper(model.getData()), writer);
         writer.flush();
      }
      catch (TemplateException e)
      {
         throw new RuntimeException("Unable to write FreeMarker response.", e);
      }
   }

   /**
    * Wraps TemplatingModel to allow objects to be referenced via EL in FreeMarker
    * templates.
    * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
    *
    */
   private class ModelWrapper extends HashMap<String, Object>
   {
      private static final long serialVersionUID = -2967489085535480741L;

      public ModelWrapper(Map<String, Object> model)
      {
         super(model);
      }

      @Override
      public Object get(Object key)
      {
         if (containsKey(key))
         {
            return super.get(key);
         }
         if (key instanceof String)
         {
            String elExpression = expressions.toExpression((String) key);
            return expressions.evaluateValueExpression(elExpression);
         }
         return null;
      }
   }
}
