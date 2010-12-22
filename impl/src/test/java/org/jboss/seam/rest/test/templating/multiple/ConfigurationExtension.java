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
package org.jboss.seam.rest.test.templating.multiple;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessInjectionTarget;

import org.jboss.seam.rest.templating.TemplatingMessageBodyWriter;
import org.jboss.seam.solder.bean.ForwardingInjectionTarget;

/**
 * Simulates configuring TemplatingMessageBodyWriter via XML extension.
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
public class ConfigurationExtension implements Extension
{
   public void reconfiguredTemplating(@Observes ProcessInjectionTarget<TemplatingMessageBodyWriter> event)
   {
      event.setInjectionTarget(wrapInjectionTarget(event.getInjectionTarget()));
   }

   private InjectionTarget<TemplatingMessageBodyWriter> wrapInjectionTarget(final InjectionTarget<TemplatingMessageBodyWriter> it)
   {
      return new ForwardingInjectionTarget<TemplatingMessageBodyWriter>()
      {
         @Override
         protected InjectionTarget<TemplatingMessageBodyWriter> delegate()
         {
            return it;
         }

         @Override
         public void inject(TemplatingMessageBodyWriter instance, CreationalContext<TemplatingMessageBodyWriter> ctx)
         {
            instance.setPreferedTemplatingProvider(MockTemplatingProvider.class);
            super.inject(instance, ctx);
         }
      };
   }
}
