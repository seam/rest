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
