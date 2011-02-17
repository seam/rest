package org.jboss.seam.rest.test.exceptions;

import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.seam.exception.control.CaughtException;
import org.jboss.seam.exception.control.Handles;
import org.jboss.seam.exception.control.HandlesExceptions;
import org.jboss.seam.rest.exceptions.RestRequest;
import org.jboss.seam.rest.exceptions.RestResource;

@HandlesExceptions
public class MoreSpecificExceptionHandler
{
   public void handleInstantiationException(@Handles(precedence = 0) @RestRequest CaughtException<InstantiationException> event, @RestResource ResponseBuilder builder)
   {
      builder.status(415);
      event.handled();
   }
}
