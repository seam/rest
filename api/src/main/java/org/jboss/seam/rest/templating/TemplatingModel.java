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

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
/**
 * Holds objects used for rendering. Populate the underlying data map with
 * objects to be accessible within a template.
 * 
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
@RequestScoped
public class TemplatingModel
{
   private Map<String, Object> data = new HashMap<String, Object>();
   
   /**
    * Returns the underlying map of objects. The objects can be referenced
    * within a template by their names (map keys).
    * @return
    */
   public Map<String, Object> getData()
   {
      return data;
   }
}
