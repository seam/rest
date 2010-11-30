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
import org.jboss.seam.rest.client.RestClientExtension;
import org.jboss.seam.rest.templating.freemarker.FreeMarkerMessageBodyWriter;
import org.jboss.seam.rest.templating.freemarker.FreeMarkerModel;
import org.jboss.seam.rest.util.Utils;

public class TemplatingExtension implements Extension
{
   private static final String FREEMARKER_TEMPLATE_CLASS_NAME = "freemarker.template.Template";
   private static final Logger log = Logger.getLogger(RestClientExtension.class);
   
   public void registerExtension(@Observes BeforeBeanDiscovery event, BeanManager manager)
   {
      if (Utils.isClassAvailable(FREEMARKER_TEMPLATE_CLASS_NAME))
      {
         event.addAnnotatedType(manager.createAnnotatedType(FreeMarkerMessageBodyWriter.class));
         event.addAnnotatedType(manager.createAnnotatedType(FreeMarkerModel.class));
         log.info("Seam REST FreeMarker Extension enabled.");
      }
   }
}
