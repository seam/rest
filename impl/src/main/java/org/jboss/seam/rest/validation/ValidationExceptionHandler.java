package org.jboss.seam.rest.validation;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * The default handler that converts ValidationException to an HTTP response.
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 *
 */
@ApplicationScoped
public class ValidationExceptionHandler
{
   public void handleValidationException(ValidationException exception, ResponseBuilder builder)
   {
      builder.status(BAD_REQUEST);
      ValidationErrorMessageWrapper error = new ValidationErrorMessageWrapper();
      
      for (ConstraintViolation<Object> violation : exception.getViolations())
      {
         error.addMessage(violation.getMessage());
      }
      builder.entity(error);
   }
}
