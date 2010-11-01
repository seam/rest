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

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;

/**
 * InjectionTarget implementation that delegates all method invocations to the
 * underlying delegate. Used to wrap an InjectionTarger.
 * 
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 * 
 */
public class DelegatingInjectionTarget<T> implements InjectionTarget<T>
{
   private InjectionTarget<T> delegate;

   public DelegatingInjectionTarget(InjectionTarget<T> delegate)
   {
      this.delegate = delegate;
   }

   public T produce(CreationalContext<T> ctx)
   {
      return delegate.produce(ctx);
   }

   public void dispose(T instance)
   {
      delegate.dispose(instance);
   }

   public Set<InjectionPoint> getInjectionPoints()
   {
      return delegate.getInjectionPoints();
   }

   public void inject(T instance, CreationalContext<T> ctx)
   {
      delegate.inject(instance, ctx);
   }

   public void postConstruct(T instance)
   {
      delegate.postConstruct(instance);
   }

   public void preDestroy(T instance)
   {
      delegate.preDestroy(instance);
   }
}
