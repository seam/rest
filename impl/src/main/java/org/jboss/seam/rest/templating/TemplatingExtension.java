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

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

import org.jboss.logging.Logger;
import org.jboss.seam.rest.templating.freemarker.FreeMarkerProvider;
import org.jboss.seam.rest.templating.velocity.VelocityModel;
import org.jboss.seam.rest.templating.velocity.VelocityProvider;
import org.jboss.seam.rest.util.Utils;

/**
 * Seam REST templating extensions allows HTTP responses to be created using 
 * a templating engine. FreeMarker and Apache Velocity are supported out of the
 * box. Support for additional templating engines can be added by implementing
 * the TemplatingProvider interface. 
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
public class TemplatingExtension implements Extension
{
   private static final String FREEMARKER_TEMPLATE_CLASS_NAME = "freemarker.template.Template";
   private static final String VELOCITY_TEMPLATE_CLASS_NAME = "org.apache.velocity.Template";
   private static final Logger log = Logger.getLogger(TemplatingExtension.class);
   
   public void registerExtension(@Observes BeforeBeanDiscovery event, BeanManager manager)
   {
      boolean freemarkerEnabled = Utils.isClassAvailable(FREEMARKER_TEMPLATE_CLASS_NAME);
      boolean velocityEnabled = Utils.isClassAvailable(VELOCITY_TEMPLATE_CLASS_NAME);
      
      if (freemarkerEnabled)
      {
         log.debug("FreeMarker found on classpath, adding support beans.");
         event.addAnnotatedType(manager.createAnnotatedType(FreeMarkerProvider.class));
      }
      
      if (velocityEnabled)
      {
         log.debug("Velocity found on classpath, adding support beans.");
         event.addAnnotatedType(manager.createAnnotatedType(VelocityModel.class));
         event.addAnnotatedType(manager.createAnnotatedType(VelocityProvider.class));
      }

      event.addAnnotatedType(manager.createAnnotatedType(TemplatingMessageBodyWriter.class));
   }
}
