package org.jboss.seam.rest.test.compat.ejb.deployment;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.jboss.logging.Logger;

@WebFilter(urlPatterns = "/*")
public class Filter implements javax.servlet.Filter
{
   private static final Logger log = Logger.getLogger(Filter.class);
   
   @Inject
   private Echo echo;
   @Inject
   private Foxtrot foxtrot;
   
   @Override
   public void init(FilterConfig filterConfig) throws ServletException
   {
      log.info("Init starting");
      echo.ping();
      foxtrot.ping();
      log.info("Init done");
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
   {
      chain.doFilter(request, response);
   }

   @Override
   public void destroy()
   {
      
   }
   
}
