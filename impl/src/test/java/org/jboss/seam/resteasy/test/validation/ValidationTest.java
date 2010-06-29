package org.jboss.seam.resteasy.test.validation;

import static org.testng.Assert.assertEquals;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.seam.resteasy.validation.ValidateRequest;
import org.jboss.seam.resteasy.validation.ValidationException;
import org.jboss.seam.resteasy.validation.ValidationExceptionMapper;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.testng.annotations.Test;

public class ValidationTest extends Arquillian
{
   @Inject
   private Resource testResource;
   
   @Deployment
   public static JavaArchive createDeployment()
   {
      JavaArchive jar = ShrinkWrap.create("test.jar", JavaArchive.class);
      jar.addManifestResource("org/jboss/seam/resteasy/test/validation/beans.xml", ArchivePaths.create("beans.xml"));
      jar.addPackage(ValidateRequest.class.getPackage());
      jar.addPackage(ValidationTest.class.getPackage());
      return jar;
   }
   
   @Test
   public void testCorrectMessageBody()
   {
      Person tester = new Person("Jozef", "Hartinger", 22, false);
      testResource.partiallyValidatedOperation(tester);
   }
   
   @Test
   public void testIncorrectMessageBody()
   {
      Person tester = new Person("foo", "b", 5, true);
      try
      {
         testResource.partiallyValidatedOperation(tester);
         throw new RuntimeException("Expected exception not thrown.");
      }
      catch (ValidationException e)
      {
         assertEquals(e.getViolations().size(), 3);
      }
   }
   
   @Test
   public void testIncorrectFormBean()
   {
      FormBean form1 = new FormBean(null, "a");
      FormBean form2 = new FormBean(null, "bb");
      try
      {
         testResource.formOperation(form1, form2);
         throw new RuntimeException("Expected exception not thrown.");
      }
      catch (ValidationException e)
      {
         assertEquals(e.getViolations().size(), 3);
      }
   }
   
   @Test
   public void testValidationExceptionMapper()
   {
      Person tester = new Person("foo", "bar", 100, true);
      ValidationExceptionMapper mapper = new ValidationExceptionMapper();
      try
      {
         testResource.partiallyValidatedOperation(tester);
         throw new RuntimeException("Expected exception not thrown.");
      }
      catch (ValidationException e)
      {
         Response response = mapper.toResponse(e);
         assertEquals(response.getEntity().toString().trim(), "must be false");
      }
   }
   
   @Test
   public void testGroups()
   {
      Person partiallyValidPerson = new Person("foo", "bar", 100, false);
      Person completelyValidPerson = new Person("foo", "bar", 100, false, "foobar");

      testResource.partiallyValidatedOperation(partiallyValidPerson);
      testResource.completelyValidatedOperation(completelyValidPerson);
      
      try
      {
         testResource.completelyValidatedOperation(partiallyValidPerson);
         throw new RuntimeException("Expected exception not thrown.");
      }
      catch (ValidationException e)
      {
         // expected
      }
   }
}
