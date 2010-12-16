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
package org.jboss.seam.rest.validation;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response.ResponseBuilder;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import org.jboss.seam.exception.control.CatchResource;
import org.jboss.seam.exception.control.CaughtException;
import org.jboss.seam.exception.control.Handles;
import org.jboss.seam.exception.control.HandlesExceptions;
import org.jboss.seam.exception.control.TraversalPath;
import org.jboss.seam.rest.exceptions.RestRequest;
import org.jboss.seam.rest.validation.ValidationException;

/**
 * The default handler that converts ValidationException to an HTTP response.
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
@HandlesExceptions
@ApplicationScoped
public class ValidationExceptionHandler
{
   public void handleValidationException(@Handles(precedence = -100, during = TraversalPath.DESCENDING) @RestRequest CaughtException<ValidationException> event, @CatchResource ResponseBuilder builder)
   {
      builder.status(BAD_REQUEST);
      ValidationErrorMessageWrapper error = new ValidationErrorMessageWrapper();

      for (ConstraintViolation<Object> violation : event.getException().getViolations())
      {
         error.addMessage(violation.getMessage());
      }
      builder.entity(error);
      
      event.handled();
   }
}
