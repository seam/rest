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

import java.util.Map;

import javax.el.PropertyNotFoundException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.velocity.context.Context;
import org.jboss.seam.rest.templating.TemplatingModel;
import org.jboss.seam.solder.el.Expressions;

/**
 * Wraps TemplatingModel to allow objects to be referenced via EL in velocity
 * templates.
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
@RequestScoped
public class VelocityModel implements Context
{
   private Map<String, Object> delegate;
   @Inject
   private Expressions expressions;
   
   @Inject
   public void init(TemplatingModel model)
   {
      this.delegate = model.getData();
   }
   
   public Object put(String key, Object value)
   {
      return delegate.put(key, value);
   }

   public Object get(String key)
   {
      if (delegate.containsKey(key))
      {
         return delegate.get(key);
      }
      try
      {
         String elExpression = expressions.toExpression(key);
         return expressions.evaluateValueExpression(elExpression);
      }
      catch (PropertyNotFoundException e)
      {
         /* we need to return null in certain situations, for example
          * 
          * #foreach(${student} in ${university.students})
          *
          * asks for ${student} before considering the foreach "student" variable  
          */
         return null;
      }
   }

   public boolean containsKey(Object key)
   {
      return delegate.containsKey(key);
   }

   public Object[] getKeys()
   {
      return delegate.keySet().toArray();
   }

   public Object remove(Object key)
   {
      return delegate.remove(key);
   }
}
