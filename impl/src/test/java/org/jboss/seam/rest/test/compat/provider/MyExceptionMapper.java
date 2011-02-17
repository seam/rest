package org.jboss.seam.rest.test.compat.provider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

@Provider
@ApplicationScoped
public class MyExceptionMapper implements ExceptionMapper<NullPointerException>
{
   @Context
   private SecurityContext securityContext;
   @Context
   private Providers providers;
   @Context
   private ServletConfig servletConfig;
   @Context
   private ServletContext servletContext;
   @Inject
   private Foo cdiFieldInjection;
   
   @Override
   public Response toResponse(NullPointerException exception)
   {
      StringBuilder builder = new StringBuilder();
      builder.append("SecurityContext:" + (securityContext != null));
      builder.append(",Providers:" + (providers != null));
      builder.append(",ServletConfig:" + (servletConfig != null));
      builder.append(",ServletContext:" + (servletContext != null));
      builder.append(",CDI field injection:" + (cdiFieldInjection != null));
      return Response.status(200).entity(builder.toString()).type(MediaType.TEXT_HTML).build();
   }
}
