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

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.core.Context;

import org.jboss.logging.Logger;
import org.jboss.seam.rest.util.Annotations;
import org.jboss.seam.rest.validation.ValidateRequest;
import org.jboss.seam.rest.validation.ValidationException;

import static org.jboss.seam.rest.util.Utils.isPrimitiveWrapper;

@Interceptor
@ValidateRequest
public class ValidationInterceptor implements Serializable
{
   private static final long serialVersionUID = -5804986456381504613L;
   private static final ValidateRequest DEFAULT_INTERCEPTOR_BINDING = new ValidateRequest.ValidateLiteral();
   private static final Logger log = Logger.getLogger(ValidationInterceptor.class);

   @Inject
   private Validator validator;
   @Inject
   private ValidationMetadata metadata;

   @AroundInvoke
   public Object intercept(InvocationContext ctx) throws Exception
   {
      log.debugv("Validating {0}", ctx.getMethod().toGenericString()); // TODO
      
      // do scanning only once
      if (! metadata.containsMethodMetadata(ctx.getMethod()))
      {
         scanMethod(ctx.getMethod());
      }
      
      Set<ConstraintViolation<Object>> violations = new HashSet<ConstraintViolation<Object>>();

      ValidatedMethodMetadata method = metadata.getMethodMetadata(ctx.getMethod());
      ValidateRequest interceptorBinding = method.getInterceptorBinding();
      Class<?>[] groups = interceptorBinding.groups();
      
      // validate JAX-RS resource fields
      if (interceptorBinding.validateResourceFields())
      {
         log.debugv("Validating JAX-RS resource {0}", ctx.getTarget()); // TODO
         violations.addAll(validator.validate(ctx.getTarget(), groups));
      }
      
      // validate message body
      if (interceptorBinding.validateMessageBody() && (method.getMessageBody() != null))
      {
         Object parameter = ctx.getParameters()[method.getMessageBody()];
         log.debugv("Validating HTTP message body {0}", parameter); // TODO
         violations.addAll(validator.validate(parameter, groups));
      }
      
      // validate parameter objects
      if (interceptorBinding.validateParameterObjects())
      {
         for (Integer parameterIndex : method.getParameterObjects())
         {
            Object parameter = ctx.getParameters()[parameterIndex];
            log.debugv("Validating parameter object {0}", parameter); // TODO
            violations.addAll(validator.validate(parameter, groups));
         }
      }
      
      if (violations.isEmpty())
      {
         log.info("Validation completed. No violations found."); // TODO
         return ctx.proceed();
      }
      else
      {
         log.debugv("Validation completed. {0} violations found.", violations.size()); // TODO
         throw new ValidationException(violations);
      }
   }
   
   private void scanMethod(Method method)
   {
      Integer messageBodyIndex = null;
      Set<Integer> parameterObjects = new HashSet<Integer>();
      ValidateRequest interceptorBinding = getInterceptorBinding(method);
      
      log.debugv("This is the first time {0} is invoked. Scanning.", method); // TODO
      
      Annotation[][] parameterAnnotations = method.getParameterAnnotations();
      for (int i = 0; i < parameterAnnotations.length; i++)
      {
         if (parameterAnnotations[i].length == 0)
         {
            log.debugv("{0} identified as the message body.", method.getParameterTypes()[i]); // TODO
            messageBodyIndex = i;
            continue;
         }

         if (isParameterObject(method.getParameterTypes()[i], method.getParameterAnnotations()[i]))
         {
            log.debugv("{0} identified as the parameter object.", method.getParameterTypes()[i]);
            parameterObjects.add(i);
         }
      }
      metadata.addMethodMetadata(new ValidatedMethodMetadata(method, messageBodyIndex, parameterObjects, interceptorBinding));
   }

   private ValidateRequest getInterceptorBinding(Method method)
   {
      ValidateRequest interceptorBinding = Annotations.getAnnotation(method, ValidateRequest.class);
      if (interceptorBinding == null)
      {
         log.debugv("Unable to find @ValidateRequest interceptor binding for {0}", method.toGenericString());
         // There is no @Validate on the method
         // The interceptor is probably bound to the bean by @Interceptors
         // annotation
         return DEFAULT_INTERCEPTOR_BINDING;
      }
      else
      {
         return interceptorBinding;
      }
   }

   private boolean isParameterObject(Class<?> parameterType, Annotation[] annotations)
   {
      // the not annotated parameter is the message body and thus was validated before
      if (annotations.length == 0)
      {
         return false; 
      }

      // primitive type parameters are definitely not form objects
      if (parameterType.isPrimitive() || isPrimitiveWrapper(parameterType) || String.class.isAssignableFrom(parameterType))
      {
         return false;
      }

      // @Context parameters are not form objects
      for (Annotation annotation : annotations)
      {
         if (annotation instanceof Context)
         {
            return false;
         }
      }
      return true;
   }
}
