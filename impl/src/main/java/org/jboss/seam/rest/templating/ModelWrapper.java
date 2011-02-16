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
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

/**
 * Wraps TemplatingModel to allow objects to be referenced via EL in templates.
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 *
 */
public class ModelWrapper extends HashMap<String, Object>
{
   private static final long serialVersionUID = -4511289530418970162L;
   private BeanManager manager;
   
   public ModelWrapper(Map<String, Object> model, BeanManager manager)
   {
      super(model);
      this.manager = manager;
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
         String name = (String) key;
         Set<Bean<?>> beans = manager.getBeans(name);
         if (!beans.isEmpty())
         {
            Bean<?> bean = manager.resolve(beans);
            if (bean != null)
            {
               CreationalContext<?> ctx = manager.createCreationalContext(bean);
               return manager.getReference(bean, bean.getBeanClass(), ctx);
            }
         }
      }
      return null;
   }
}
