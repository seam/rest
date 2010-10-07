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
package org.jboss.seam.resteasy.validation;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.jboss.resteasy.annotations.Form;
import org.jboss.seam.resteasy.util.Annotations;

@Interceptor
@ValidateRequest
public class ValidationInterceptor implements Serializable
{
   private static final long serialVersionUID = -5804986456381504613L;
   private static final Class<?>[] DEFAULT_GROUPS = new Class<?>[] {Default.class};
   private static final ValidateRequest DEFAULT_INTERCEPTOR_BINDING = new ValidateRequest.ValidateLiteral(DEFAULT_GROUPS, true, true);

   @Inject
   private Validator validator;

   @AroundInvoke
   public Object intercept(InvocationContext ctx) throws Exception
   {
      Set<ConstraintViolation<Object>> violations = new HashSet<ConstraintViolation<Object>>();
      
      ValidateRequest interceptorBinding = getInterceptorBinding(ctx);
      Class<?>[] groups = interceptorBinding.groups();

      // perform validation
      Annotation[][] parameterAnnotations = ctx.getMethod().getParameterAnnotations();
      for (int i = 0; i < parameterAnnotations.length; i++)
      {
         if (parameterAnnotations[i].length == 0 && interceptorBinding.validateMessageBody())
         {
            // entity body
            violations.addAll(validator.validate(ctx.getParameters()[i], groups));
         }
         
         if (parameterAnnotations[i].length > 0 && interceptorBinding.validateFormParameters())
         {
            // @Form parameters
            for (Annotation annotation : parameterAnnotations[i])
            {
               if (annotation instanceof Form)
               {
                  violations.addAll(validator.validate(ctx.getParameters()[i], groups));
               }
            }
         }
      }

      if (violations.isEmpty())
      {
         return ctx.proceed();
      }
      else
      {
         throw new ValidationException(violations);
      }
   }

   private ValidateRequest getInterceptorBinding(InvocationContext ctx)
   {
      ValidateRequest interceptorBinding = Annotations.getAnnotation(ctx.getMethod(), ValidateRequest.class);
      if (interceptorBinding == null)
      {
         // There is no @Validate on the method
         // The interceptor is probably bound to the bean by @Interceptors annotation
         return DEFAULT_INTERCEPTOR_BINDING;
      }
      else
      {
         return interceptorBinding;
      }
   }
}
