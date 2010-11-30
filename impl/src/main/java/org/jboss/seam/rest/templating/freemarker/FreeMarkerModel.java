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

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jboss.weld.extensions.el.Expressions;

/**
 * Holds objects to be used for rendering.
 * Furthermore, ModelWrapper allows CDI beans to be referenced via their
 * @Name in the FreeMarker template.
 * 
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
@RequestScoped
public class FreeMarkerModel
{
   private Map<String, Object> data = new HashMap<String, Object>();
   
   @Inject
   private Expressions expressions;
   
   public Map<String, Object> getData()
   {
      return data;
   }
   
   protected Map<String, Object> getWrappedModel()
   {
      return new ModelWrapper();
   }

   private class ModelWrapper extends HashMap<String, Object>
   {
      private static final long serialVersionUID = -2967489085535480741L;

      public ModelWrapper()
      {
         super(data);
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
            return expressions.evaluateValueExpression(createElExpression((String) key));
         }
         return null;
      }
      
      private String createElExpression(String key)
      {
         return "#{" + key + "}";
      }
   }
}
