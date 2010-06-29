package org.jboss.seam.resteasy.validation;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import javax.ws.rs.ext.ExceptionMapper;

public class ValidationExceptionMapper implements ExceptionMapper<ValidationException>
{
   public Response toResponse(ValidationException exception)
   {
      ResponseBuilder response = Response.status(BAD_REQUEST);
      
      StringBuilder str = new StringBuilder();
      for (ConstraintViolation<Object> violation : exception.getViolations())
      {
//         str.append(violation.getPropertyPath().toString());
//         str.append(" ");
         str.append(violation.getMessage());
         str.append("\n");
      }
      return response.entity(str.toString()).build();
   }
}
