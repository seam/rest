package org.jboss.seam.resteasy.configuration;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;

import org.jboss.seam.resteasy.util.Interpolator;

/**
 * SeamExceptionMapper allows exceptions to be mapped to HTTP status codes
 * declaratively (at runtime).
 * 
 * @author Jozef Hartinger
 * 
 */
public class SeamExceptionMapper implements ExceptionMapper<Throwable>
{
   private ResponseBuilder responseBuilder;
   private String message;
   private boolean interpolateMessageBody = true;

   @Inject
   private Interpolator interpolator;

   protected void initialize(int status)
   {
      responseBuilder = Response.status(status);
   }

   protected void initialize(ExceptionMapping mapping)
   {
      responseBuilder = Response.status(mapping.getStatusCode());
      message = mapping.getMessage();
      interpolateMessageBody = mapping.isInterpolateMessageBody();

      if (message != null && !interpolateMessageBody)
      {
         // since the error message is always the same, we can cache it
         ErrorMessageWrapper error = new ErrorMessageWrapper(message);
         responseBuilder.entity(error);
      }
   }

   public Response toResponse(Throwable exception)
   {
      if (responseBuilder == null)
      {
         throw new IllegalStateException("ExceptionMapper not initialized.");
      }

      ResponseBuilder response = responseBuilder.clone();

      if (message != null && interpolateMessageBody)
      {
         // since the message might be different every time, we have to recreate
         // it on each request
         ErrorMessageWrapper error = new ErrorMessageWrapper(interpolator.interpolate(message));
         response.entity(error);
      }
      return response.build();
   }
}
