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
package org.jboss.seam.resteasy.test.validation;

import static org.testng.Assert.assertEquals;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.seam.resteasy.configuration.ErrorMessageWrapper;
import org.jboss.seam.resteasy.util.Interpolator;
import org.jboss.seam.resteasy.validation.ValidateRequest;
import org.jboss.seam.resteasy.validation.ValidationException;
import org.jboss.seam.resteasy.validation.ValidationExceptionMapper;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.weld.extensions.el.Expressions;
import org.testng.annotations.Test;

public class ValidationTest extends Arquillian
{
   @Inject
   private Resource testResource;
   @Inject
   private ResourceChild resourceChild;
   
   @Deployment
   public static JavaArchive createDeployment()
   {
      JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "test.jar");
      jar.addManifestResource("org/jboss/seam/resteasy/test/validation/beans.xml", ArchivePaths.create("beans.xml"));
      jar.addPackage(ValidateRequest.class.getPackage());
      jar.addPackage(ValidationTest.class.getPackage());
      jar.addPackage(Interpolator.class.getPackage());
      jar.addPackage(Expressions.class.getPackage());
      jar.addClass(ErrorMessageWrapper.class);
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
