package org.jboss.seam.rest.test.compat.provider.application;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyExceptionMapper implements ExceptionMapper<NullPointerException>
{
   @Context
   private Application application;
   
   @Override
   public Response toResponse(NullPointerException exception)
   {
      StringBuilder builder = new StringBuilder();
      builder.append("Application:" + (application != null));
      return Response.status(200).entity(builder.toString()).type(MediaType.TEXT_HTML).build();
   }
}
