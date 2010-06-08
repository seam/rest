package org.jboss.seam.resteasy.test.validation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.Form;
import org.jboss.seam.resteasy.validation.ValidateRequest;

@Path("validation")
@Produces("text/plain")
@Consumes("text/plain")
public class Resource
{
   @POST
   @ValidateRequest(groups = PartialValidation.class)
   public void partiallyValidatedOperation(Person person)
   {
   }
   
   @POST
   @ValidateRequest
   public void completelyValidatedOperation(Person person)
   {
   }
   
   @POST
   @ValidateRequest
   public void formOperation(@Form FormBean form1, @Form FormBean form2)
   {
   }
}
