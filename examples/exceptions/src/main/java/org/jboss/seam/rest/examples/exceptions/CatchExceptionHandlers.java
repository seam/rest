package org.jboss.seam.rest.examples.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.solder.exception.control.CaughtException;
import org.jboss.solder.exception.control.Handles;
import org.jboss.solder.exception.control.HandlesExceptions;
import org.jboss.seam.rest.exceptions.ExceptionMapping;
import org.jboss.seam.rest.exceptions.RestResource;

@HandlesExceptions
@ExceptionMapping.List({
        @ExceptionMapping(exceptionType = IllegalAccessException.class, status = 403),
        @ExceptionMapping(exceptionType = NullPointerException.class, status = 500, message = "NullPointerException was thrown."),
        @ExceptionMapping(exceptionType = RuntimeException.class, status = 500, useExceptionMessage = true)})
public class CatchExceptionHandlers {
    public void arithmeticExceptionHandler(@Handles CaughtException<ArithmeticException> event,
                                           @RestResource ResponseBuilder builder) {
        builder.status(500).entity("Cannot divide by zero. Want to divide by two instead?").type(MediaType.TEXT_PLAIN_TYPE);
        event.handled();
    }
}
