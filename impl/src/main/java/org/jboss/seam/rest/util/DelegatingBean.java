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
package org.jboss.seam.rest.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;

public class DelegatingBean<T> implements Bean<T>
{
   private Bean<T> delegate; 
   
   public DelegatingBean(Bean<T> delegate)
   {
      this.delegate = delegate;
   }

   public T create(CreationalContext<T> creationalContext)
   {
      return delegate.create(creationalContext);
   }

   public void destroy(T instance, CreationalContext<T> creationalContext)
   {
      delegate.destroy(instance, creationalContext);
   }

   public Set<Type> getTypes()
   {
      return delegate.getTypes();
   }

   public Set<Annotation> getQualifiers()
   {
      return delegate.getQualifiers();
   }

   public Class<? extends Annotation> getScope()
   {
      return delegate.getScope();
   }

   public String getName()
   {
      return delegate.getName();
   }

   public Set<Class<? extends Annotation>> getStereotypes()
   {
      return delegate.getStereotypes();
   }

   public Class<?> getBeanClass()
   {
      return delegate.getBeanClass();
   }

   public boolean isAlternative()
   {
      return delegate.isAlternative();
   }

   public boolean isNullable()
   {
      return delegate.isNullable();
   }

   public Set<InjectionPoint> getInjectionPoints()
   {
      return delegate.getInjectionPoints();
   }
}
