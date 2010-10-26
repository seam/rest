/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.seam.rest.test.validation;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.rest.util.Annotations;
import org.jboss.seam.rest.util.Utils;
import org.jboss.seam.rest.validation.ValidateRequest;
import org.jboss.seam.rest.validation.ValidationException;
import org.jboss.seam.rest.validation.ValidationExceptionMapper;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class ValidationTest
{
   @Inject
   private Resource testResource;
   @Inject
   private ResourceChild resourceChild;
   
   @Deployment
   public static JavaArchive createDeployment()
   {
      JavaArchive war = ShrinkWrap.create(JavaArchive.class, "test.jar");
      war.addManifestResource("org/jboss/seam/rest/test/validation/beans.xml", ArchivePaths.create("beans.xml"));
      war.addPackage(ValidateRequest.class.getPackage());
      war.addPackage(ValidationTest.class.getPackage());
      war.addClasses(Annotations.class, Utils.class);
      return war;
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
         assertEquals(3, e.getViolations().size());
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
         assertEquals(3, e.getViolations().size());
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
         assertEquals("must be false", response.getEntity().toString().trim());
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
   
   @Test
   public void testResourceHierarchy()
   {
      try
      {
         resourceChild.ping(new Person());
         throw new RuntimeException("Expected exception not thrown.");
      }
      catch (ValidationException e)
      {
         // expected
      }
   }
}
