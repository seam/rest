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

import java.util.Collections;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.exception.control.CatchResource;
import org.jboss.seam.exception.control.CaughtException;
import org.jboss.seam.exception.control.ExceptionStack;
import org.jboss.seam.exception.control.Handles;
import org.jboss.seam.exception.control.HandlesExceptions;
import org.jboss.seam.exception.control.TraversalPath;
import org.jboss.seam.rest.exceptions.ResponseBuilderProducer;
import org.jboss.seam.rest.exceptions.RestRequest;
import org.jboss.seam.rest.exceptions.RestResource;
import org.jboss.seam.rest.exceptions.integration.CatchValidationExceptionHandler;
import org.jboss.seam.rest.util.Annotations;
import org.jboss.seam.rest.util.Utils;
import org.jboss.seam.rest.validation.ValidateRequest;
import org.jboss.seam.rest.validation.ValidationException;
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
   @Valid
   private PersonResource validResource;
   @Inject
   @Invalid
   private PersonResource invalidResource;
   @Inject
   private ResourceChild resourceChild;
   @Inject
   @RestResource
   private Instance<ResponseBuilder> builder;
   @Inject
   @RestResource
   private Instance<Response> response;
   @Inject
   private Instance<CatchValidationExceptionHandler> handler;

   @Deployment
   public static JavaArchive createDeployment()
   {
      JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "test.jar");
      jar.addManifestResource("org/jboss/seam/rest/test/validation/beans.xml", ArchivePaths.create("beans.xml"));
      jar.addPackage(ValidateRequest.class.getPackage());
      jar.addClass(CatchValidationExceptionHandler.class);
      jar.addPackage(ValidationTest.class.getPackage());
      jar.addClass(RestResource.class);
      jar.addClasses(CaughtException.class, CatchResource.class, Handles.class, HandlesExceptions.class, TraversalPath.class, RestRequest.class, ResponseBuilderProducer.class, ExceptionStack.class);
      jar.addClasses(Annotations.class, Utils.class);
      return jar;
   }

   @Test
   public void testCorrectMessageBody()
   {
      Person tester = new Person("Jozef", "Hartinger", 22, false);
      validResource.partiallyValidatedOperation(tester);
   }

   @Test
   public void testIncorrectMessageBody()
   {
      Person tester = new Person("foo", "b", 5, true);
      try
      {
         validResource.partiallyValidatedOperation(tester);
         throw new RuntimeException("Expected exception not thrown.");
      }
      catch (ValidationException e)
      {
         assertEquals(3, e.getViolations().size());
      }
   }
   
   @Test
   public void testDisabledValidation()
   {
      Person tester = new Person("foo", "b", 5, true);
      validResource.notValidatedOperation(tester);
   }

   @Test
   public void testIncorrectFormBean()
   {
      FormBean form1 = new FormBean(null, "a");
      FormBean form2 = new FormBean(null, "bb");
      try
      {
         validResource.formOperation(form1, form2);
         throw new RuntimeException("Expected exception not thrown.");
      }
      catch (ValidationException e)
      {
         assertEquals(3, e.getViolations().size());
      }
   }

   @Test
   public void testValidationExceptionHandler()
   {
      Person tester = new Person("foo", "bar", 100, true);

      try
      {
         // prepare exception
         validResource.partiallyValidatedOperation(tester);
         throw new RuntimeException("Expected exception not thrown.");
      }
      catch (final ValidationException e)
      {
         // pass the exception to the handler
         ExceptionStack stack = new ExceptionStack(Collections.<Throwable>singleton(e), 0);
         this.handler.get().handleValidationException(new CaughtException<ValidationException>(stack, false, false)
         {
            @Override
            public ValidationException getException()
            {
               return e;
            }

            @Override
            public void handled()
            {
            }

         }, builder.get());

         assertEquals(400, response.get().getStatus());
         assertEquals("must be false", response.get().getEntity().toString().trim());
      }
   }

   @Test
   public void testGroups()
   {
      Person partiallyValidPerson = new Person("foo", "bar", 100, false);
      Person completelyValidPerson = new Person("foo", "bar", 100, false, "foobar");

      validResource.partiallyValidatedOperation(partiallyValidPerson);
      validResource.completelyValidatedOperation(completelyValidPerson);

      try
      {
         validResource.completelyValidatedOperation(partiallyValidPerson);
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

   @Test
   public void testResourceValidation()
   {
      Person validPerson = new Person("foo", "bar", 100, false, "foobar");
      try
      {
         invalidResource.completelyValidatedOperation(validPerson);
         throw new RuntimeException("Expected exception not thrown.");
      }
      catch (ValidationException e)
      {
         assertEquals(3, e.getViolations().size());
      }
   }
}
