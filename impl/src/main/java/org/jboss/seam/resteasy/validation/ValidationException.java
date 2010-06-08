package org.jboss.seam.resteasy.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class ValidationException extends RuntimeException
{
   private static final long serialVersionUID = -2779809222298578247L;
   
   private Set<ConstraintViolation<Object>> violations;

   public ValidationException(Set<ConstraintViolation<Object>> violations)
   {
      this.violations = violations;
   }

   public Set<ConstraintViolation<Object>> getViolations()
   {
      return violations;
   }
}
