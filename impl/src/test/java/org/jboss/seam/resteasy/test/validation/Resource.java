package org.jboss.seam.resteasy.test.validation;

import org.jboss.resteasy.annotations.Form;
import org.jboss.seam.resteasy.validation.ValidateRequest;

public class Resource
{
   @ValidateRequest(groups = PartialValidation.class)
   public void partiallyValidatedOperation(Person person)
   {
   }
   
   @ValidateRequest
   public void completelyValidatedOperation(Person person)
   {
   }
   
   @ValidateRequest
   public void formOperation(@Form FormBean form1, @Form FormBean form2)
   {
   }
}
