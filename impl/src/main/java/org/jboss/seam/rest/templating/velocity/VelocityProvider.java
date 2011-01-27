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
package org.jboss.seam.rest.templating.velocity;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.jboss.seam.rest.templating.ModelWrapper;
import org.jboss.seam.rest.templating.ResponseTemplate;
import org.jboss.seam.rest.templating.TemplatingModel;
import org.jboss.seam.rest.templating.TemplatingProvider;
import org.jboss.seam.solder.el.Expressions;

/**
 * Renders response using Apache Velocity.
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 *
 */
@ApplicationScoped
public class VelocityProvider implements TemplatingProvider
{
   @Inject
   private TemplatingModel model;
   @Inject
   private Instance<Expressions> expressions;
   // Instance is only needed because of a bug in GF
   
   public void init(ServletContext servletContext)
   {
      Properties properties = new Properties();
      properties.setProperty("resource.loader", "servlet");
      properties.setProperty("servlet.resource.loader.class", "org.apache.velocity.tools.view.WebappResourceLoader");
      Velocity.setApplicationAttribute("javax.servlet.ServletContext", servletContext);
      Velocity.init(properties);
   }

   public void writeTo(Object o, ResponseTemplate annotation, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream os) throws IOException
   {
      ModelWrapper model = new ModelWrapper(this.model.getData(), expressions.get());
      model.put(annotation.responseName(), o);
      
      Template template = null;
      template = Velocity.getTemplate(annotation.value());
         
      OutputStreamWriter writer = new OutputStreamWriter(os);
      template.merge(new VelocityContext(model), writer);
      writer.flush();
   }
   
   @Override
   public String toString()
   {
      return getClass().getName();
   }
}
