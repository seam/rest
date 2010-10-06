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
      ValidationErrorMessageWrapper error = new ValidationErrorMessageWrapper();

      for (ConstraintViolation<Object> violation : exception.getViolations())
      {
         error.addMessage(violation.getMessage());
      }
      return response.entity(error).build();
   }
}
