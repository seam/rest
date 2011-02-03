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
package org.jboss.seam.rest;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.jboss.logging.Logger;
import org.jboss.seam.rest.client.RestClientExtension;
import org.jboss.seam.rest.exceptions.ExceptionMappingExtension;
import org.jboss.seam.rest.exceptions.RestResource;
import org.jboss.seam.rest.exceptions.SeamExceptionMapper;
import org.jboss.seam.rest.templating.TemplatingMessageBodyWriter;

/**
 * TODO: This listener will be replaced with Seam Servlet.
 * Do not observe the event fired by this listener as it will be removed in future releases.
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
@WebListener
public class SeamRestStartup implements ServletContextListener
{
   private static final Logger log = Logger.getLogger(SeamRestStartup.class);
   @Inject
   private RestClientExtension restClientExtension;
   @Inject
   private ExceptionMappingExtension exceptionMappingExtension;
   @Inject
   private SeamExceptionMapper exceptionMapper;
   @Inject
   private TemplatingMessageBodyWriter templating;
   
   @Inject @RestResource
   private Event<ServletContext> event;
   
   @Override
   public void contextInitialized(ServletContextEvent sce)
   {
      event.fire(sce.getServletContext());
      
      log.infov("Seam REST {0} (Client integration: {1}, Catch integration: {2}, {3} exception mapping rules, Templating provider: {4})", 
            this.getClass().getPackage().getSpecificationVersion(),
            restClientExtension.isClientIntegrationEnabled() ? "enabled" : "disabled",
            exceptionMappingExtension.isCatchIntegrationEnabled()  ? "enabled" : "disabled",
            exceptionMapper.getMappings().size(),
            templating.getProvider().toString());
   }

   @Override
   public void contextDestroyed(ServletContextEvent sce)
   {
   }
}
