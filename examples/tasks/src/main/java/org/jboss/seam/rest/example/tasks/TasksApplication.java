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
package org.jboss.seam.rest.example.tasks;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.jboss.seam.rest.example.tasks.resource.CategoryCollectionResource;
import org.jboss.seam.rest.example.tasks.resource.CategoryResource;
import org.jboss.seam.rest.example.tasks.resource.TaskCollectionResource;
import org.jboss.seam.rest.exceptions.SeamExceptionMapper;
import org.jboss.seam.rest.templating.TemplatingMessageBodyWriter;

@ApplicationPath("/api/*")
public class TasksApplication extends Application
{
   @Override
   public Set<Class<?>> getClasses()
   {
      Set<Class<?>> classes = new HashSet<Class<?>>();
      classes.add(CategoryCollectionResource.class);
      classes.add(CategoryResource.class);
      classes.add(TaskCollectionResource.class);
      classes.add(JacksonJaxbJsonProvider.class);
      classes.add(SeamExceptionMapper.class);
      classes.add(TemplatingMessageBodyWriter.class);
      classes.add(UriInfoProducer.class);
      return classes;
   }
}
