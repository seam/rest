package org.jboss.seam.resteasy.configuration;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * GenericExceptionMapper allows exceptions to be mapped to HTTP status codes declaratively (at runtime).
 * @author Jozef Hartinger
 *
 * @param <T> exception
 */
public class BasicExceptionMapper<T extends Throwable> implements ExceptionMapper<T>
{
   private int status;
   
   public BasicExceptionMapper(int status)
   {
      this.status = status;
   }
   
   public Response toResponse(T exception)
   {
      return Response.status(status).build();
   }
}
