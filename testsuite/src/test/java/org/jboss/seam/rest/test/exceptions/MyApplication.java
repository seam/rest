package org.jboss.seam.rest.test.exceptions;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jboss.seam.rest.exceptions.ExceptionMapping;

@ExceptionMapping.List({
        @ExceptionMapping(exceptionType = IllegalThreadStateException.class, status = 415),
        @ExceptionMapping(exceptionType = NoSuchFieldError.class, status = 416, message = "NoSuchField", useJaxb = false),
        @ExceptionMapping(exceptionType = NumberFormatException.class, status = 417, useExceptionMessage = true, useJaxb = false),
        @ExceptionMapping(exceptionType = StringIndexOutOfBoundsException.class, status = 418, useExceptionMessage = true, useJaxb = false)})
@ApplicationPath("/")
public class MyApplication extends Application {
}
