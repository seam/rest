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

import static java.lang.annotation.ElementType.METHOD;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import javax.validation.groups.Default;

/**
 * Triggers validation of incomming HTTP requests.
 * The {@link Default} group is validated if not specified otherwise. By default, the message body parameter
 * and {@link org.jboss.resteasy.annotations.Form} parameters are validated. This behaviour can be altered
 * using {@link #validateMessageBody()} and {@link #validateFormParameters()} attributes. 
 * 
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
@Target( { TYPE, METHOD })
@Retention(RUNTIME)
@Documented
@Inherited
@InterceptorBinding
public @interface ValidateRequest
{
   /**
    * Validation group that will be used during validation process.
    */
   @Nonbinding
   Class<?>[] groups() default Default.class;
   
   /**
    * If set to false, the message body parameter will not be validated. 
    */
   @Nonbinding
   boolean validateMessageBody() default true;

   /**
    * If set to false, form objects will not be validated. 
    */
   @Nonbinding
   boolean validateParameterObjects() default true;

   /**
    * Annotation literal for {@link ValidateRequest} interceptor binding.
    */
   @SuppressWarnings("all")
   static class ValidateLiteral extends AnnotationLiteral<ValidateRequest> implements ValidateRequest
   {
      private static final long serialVersionUID = 6404662043744038090L;
      
      private final Class<?>[] groups;
      private final boolean validateParameterObjects;
      private final boolean validateMessageBody;
      
      public ValidateLiteral(Class<?>[] groups, boolean validateFormObjects, boolean validateMessageBody)
      {
         this.groups = groups;
         this.validateParameterObjects = validateFormObjects;
         this.validateMessageBody = validateMessageBody;
      }

      public Class<?>[] groups()
      {
         return groups;
      }

      public boolean validateParameterObjects()
      {
         return validateParameterObjects;
      }

      public boolean validateMessageBody()
      {
         return validateMessageBody;
      }
   }
}
