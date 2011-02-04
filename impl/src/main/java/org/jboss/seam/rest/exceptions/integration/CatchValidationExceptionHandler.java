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
package org.jboss.seam.rest.exceptions.integration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.seam.exception.control.CaughtException;
import org.jboss.seam.exception.control.Handles;
import org.jboss.seam.exception.control.HandlesExceptions;
import org.jboss.seam.exception.control.TraversalMode;
import org.jboss.seam.rest.exceptions.RestRequest;
import org.jboss.seam.rest.exceptions.RestResource;
import org.jboss.seam.rest.validation.ValidationException;
import org.jboss.seam.rest.validation.ValidationExceptionHandler;

/**
 * The default handler that obtains {@link ValidationException} from Seam Catch and
 * converts it to an HTTP response.
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 *
 */
@HandlesExceptions
@ApplicationScoped
public class CatchValidationExceptionHandler
{
   @Inject
   private ValidationExceptionHandler delegate;
   
   public void handleValidationException(@Handles(precedence = -100, during = TraversalMode.DEPTH_FIRST) @RestRequest CaughtException<ValidationException> event, @RestResource ResponseBuilder builder)
   {
      delegate.handleValidationException(event.getException(), builder);
      event.handled();
   }
}
