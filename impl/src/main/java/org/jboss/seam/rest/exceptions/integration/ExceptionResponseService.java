package org.jboss.seam.rest.exceptions.integration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;

import org.jboss.seam.solder.servicehandler.ServiceHandler;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ServiceHandler(ExceptionResponseServiceHandler.class)
public @interface ExceptionResponseService
{
   @SuppressWarnings("all")
   public static class ExceptionResponseServiceLiteral extends AnnotationLiteral<ExceptionResponseService> implements ExceptionResponseService
   {
      public static ExceptionResponseServiceLiteral INSTANCE = new ExceptionResponseServiceLiteral();
   }
}
