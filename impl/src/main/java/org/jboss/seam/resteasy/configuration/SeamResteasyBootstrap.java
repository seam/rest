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
package org.jboss.seam.resteasy.configuration;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import org.jboss.resteasy.plugins.server.servlet.ListenerBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Jozef Hartinger
 *
 */
public class SeamResteasyBootstrap extends ListenerBootstrap
{
   private FilterRegistration filter;
   private static final Logger log = LoggerFactory.getLogger(SeamResteasyBootstrap.class);
   
   public SeamResteasyBootstrap(ServletContext servletContext)
   {
      super(servletContext);
      filter = servletContext.getFilterRegistration("Resteasy");
      if (filter != null)
      {
         log.debug("Found RESTEasy filter registration.");
      }
   }

   @Override
   public String getInitParameter(String name)
   {
      return getParameter(name);
   }

   @Override
   public String getParameter(String name)
   {
      String value = null;
      if (filter != null)
      {
         value = filter.getInitParameter(name);
      }
      if (value == null)
      {
         value = super.getInitParameter(name);
      }
      return value;
   }
}
