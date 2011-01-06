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
import javax.validation.Valid;
import javax.validation.Validator;

import org.jboss.logging.Logger;
import org.jboss.seam.rest.util.Annotations;
import org.jboss.seam.rest.util.Utils;
import org.jboss.seam.rest.validation.ValidateRequest;
import org.jboss.seam.rest.validation.ValidationException;

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

   /**
    * Intercepts method invocations to <code>@ValidateRequest</code> annotated methods.
    * <p>
    * On the first run, the method is scanned for message body parameters and parameter object parameters
    * and the metadata is stored within {@link ValidationMetadata}.
    * </p>
    * <p>
    * On subsequent runs, method parameters and the declaring instance are validated using {@link Validator}.
    * {@link ValidationException} is thrown if validation fails.
    *  
    * @throws ValidationException
    *
    */
   @AroundInvoke
   public Object intercept(InvocationContext ctx) throws Exception
   {
      log.debugv("Validating {0}", ctx.getMethod().toGenericString());
      
      // do scanning only once
      if (! metadata.containsMethodMetadata(ctx.getMethod()))
      {
         scanMethod(ctx.getMethod());
      }
      
      Set<ConstraintViolation<Object>> violations = new HashSet<ConstraintViolation<Object>>();

      MethodMetadata method = metadata.getMethodMetadata(ctx.getMethod());
      ValidateRequest interceptorBinding = method.getInterceptorBinding();
      Class<?>[] groups = interceptorBinding.groups();
      
      // validate JAX-RS resource fields
      if (interceptorBinding.validateResourceFields())
      {
         log.debugv("Validating JAX-RS resource {0}", ctx.getTarget());
         violations.addAll(validator.validate(ctx.getTarget(), groups));
      }
      
      // validate message body
      if (interceptorBinding.validateMessageBody() && (method.getMessageBody() != null))
      {
         Object parameter = ctx.getParameters()[method.getMessageBody()];
         log.debugv("Validating HTTP message body {0}", parameter);
         violations.addAll(validator.validate(parameter, groups));
      }
      
      // validate other parameters
     for (Integer parameterIndex : method.getValidatedParameters())
     {
        Object parameter = ctx.getParameters()[parameterIndex];
        log.debugv("Validating parameter {0}", parameter);
        violations.addAll(validator.validate(parameter, groups));
      }
      
      if (violations.isEmpty())
      {
         log.debug("Validation completed. No violations found.");
         return ctx.proceed();
      }
      else
      {
         log.debugv("Validation completed. {0} violations found.", violations.size());
         throw new ValidationException(violations);
      }
   }
   
   private void scanMethod(Method method)
   {
      Integer messageBodyIndex = null;
      Set<Integer> otherValidatedParameters = new HashSet<Integer>();
      ValidateRequest interceptorBinding = getInterceptorBinding(method);
      
      log.debugv("This is the first time {0} is invoked. Scanning.", method);
      
      Annotation[][] parameterAnnotations = method.getParameterAnnotations();
      for (int i = 0; i < parameterAnnotations.length; i++)
      {
         if (parameterAnnotations[i].length == 0) // message body
         {
            log.debugv("{0} identified as the message body.", method.getParameterTypes()[i]);
            messageBodyIndex = i;
            continue;
         }

         if (isValidatedParameter(method.getParameterTypes()[i], method.getParameterAnnotations()[i]))
         {
            log.debugv("{0} identified as a validated parameter.", method.getParameterTypes()[i]);
            otherValidatedParameters.add(i);
         }
      }
      metadata.addMethodMetadata(new MethodMetadata(method, messageBodyIndex, otherValidatedParameters, interceptorBinding));
   }

   private ValidateRequest getInterceptorBinding(Method method)
   {
      ValidateRequest interceptorBinding = Annotations.getAnnotation(method, ValidateRequest.class);
      if (interceptorBinding == null)
      {
         log.debugv("Unable to find @ValidateRequest interceptor binding for {0}", method.toGenericString());
         // There is no @ValidateRequest on the method
         // The interceptor is probably bound to the bean by @Interceptors
         // annotation
         return DEFAULT_INTERCEPTOR_BINDING;
      }
      else
      {
         return interceptorBinding;
      }
   }

   private boolean isValidatedParameter(Class<?> parameterType, Annotation[] annotations)
   {
      if (Annotations.getAnnotation(annotations, Valid.class) == null)
      {
         return false; // we only validate the message body and @Valid annotated parameters
      }
      if (Utils.isPrimitiveWrapper(parameterType) || String.class.isAssignableFrom(parameterType))
      {
         log.warnv("Parameter {0} will not be validated as it is not a JavaBean.", parameterType);
         return false;
      }
      return true;
   }
}
