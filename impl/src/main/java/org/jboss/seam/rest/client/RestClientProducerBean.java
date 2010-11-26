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
package org.jboss.seam.rest.client;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.weld.extensions.bean.AbstractImmutableBean;
import org.jboss.weld.extensions.bean.Beans;
import org.jboss.weld.extensions.literal.DefaultLiteral;

/**
 * We need to create a producer method with the type closure discovered at boot time.
 * Therefore, the producer method has to be registered by extension.
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
public class RestClientProducerBean extends AbstractImmutableBean<Object>
{
   private Bean<RestClientProducer> beanDefiningProducerMethod;
   private BeanManager manager;
   private List<InjectionPoint> injectionPoints;

   public RestClientProducerBean(Bean<RestClientProducer> producerBean, Set<Type> types, BeanManager manager)
   {
      super(RestClientProducer.class, null, Collections.<Annotation>singleton(new RestClient.RestClientLiteral()), Dependent.class, null, types, false, false, null, null);
      
      this.beanDefiningProducerMethod = producerBean;
      this.manager = manager;
      
      AnnotatedMethod<? super RestClientProducer> annotatedMethod = null;
      
      for (AnnotatedMethod<? super RestClientProducer> method : manager.createAnnotatedType(RestClientProducer.class).getMethods())
      {
         if (method.getAnnotations().isEmpty())
         {
            annotatedMethod = method;
         }
      }
      
      if (annotatedMethod == null)
      {
         throw new RuntimeException("Unable to find @RestClient producer method.");
      }
      
      injectionPoints = Beans.createInjectionPoints(annotatedMethod, this, manager);
   }

   public Object create(CreationalContext<Object> creationalContext)
   {
      // get an instance of the bean declaring the producer method
      RestClientProducer producer = (RestClientProducer) manager.getReference(beanDefiningProducerMethod, RestClientProducer.class, manager.createCreationalContext(beanDefiningProducerMethod));
      
      InjectionPoint ip = (InjectionPoint) manager.getInjectableReference(injectionPoints.get(0), creationalContext);
      ClientExecutor executor = (ClientExecutor) manager.getInjectableReference(injectionPoints.get(1), creationalContext);
      
      return producer.produceRestClient(ip, executor);
   }

   public void destroy(Object instance, CreationalContext<Object> creationalContext)
   {
      creationalContext.release();
   }

   @Override
   public Set<InjectionPoint> getInjectionPoints()
   {
      return new HashSet<InjectionPoint>(injectionPoints);
   }
}
