package org.jboss.seam.resteasy.configuration;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;

public class SeamExceptionMapper implements ExceptionMapper<Throwable>
{
   private ResponseBuilder response;
   
   public SeamExceptionMapper(ExceptionMapping mapping)
   {
      response = Response.status(mapping.getStatusCode());
      if (mapping.getMessageBody() != null)
      {
         response.entity(mapping.getMessageBody());
         response.header("Content-Type", mapping.getMediaType().toString());
      }
   }

   public Response toResponse(Throwable exception)
   {
      return response.clone().build();
   }
}
