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

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Caches method metadata needed to perform validation of JAX-RS requests.
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
public class MethodMetadata
{
   private final Method method;
   private final Integer messageBody; // position of the messageBody, may be null
   private final Set<Integer> validatedParameters; // positions of other validated parameters
   private final ValidateRequest interceptorBinding;
   
   public MethodMetadata(Method method, Integer messageBody, Set<Integer> validatedParameters, ValidateRequest interceptorBinding)
   {
      this.method = method;
      this.messageBody = messageBody;
      this.validatedParameters = validatedParameters;
      this.interceptorBinding = interceptorBinding;
   }

   public Method getMethod()
   {
      return method;
   }

   /**
    * Returns the index of the message body parameter or null if the method 
    * does not contain a message body parameter.
    */
   public Integer getMessageBody()
   {
      return messageBody;
   }

   /**
    * Returns a set of indexes of the validated parameters.
    */
   public Set<Integer> getValidatedParameters()
   {
      return validatedParameters;
   }

   public ValidateRequest getInterceptorBinding()
   {
      return interceptorBinding;
   }
}
