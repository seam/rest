/*
 * JBoss, Home of Professional Open Source
 * Copyright [2010], Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.seam.rest.exceptions;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A qualifier used to distinguish Seam Catch handler methods that are appropriate for a REST resource request.
 *
 * @author <a href="http://community.jboss.org/people/dan.j.allen">Dan Allen</a>
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface RestRequest
{

   /**
    * Annotation literal for {@link RestRequest} qualifier.
    */
   @SuppressWarnings("all")
   static class RestRequestLiteral extends AnnotationLiteral<RestRequest> implements RestRequest
   {
      private static final long serialVersionUID = 2990603884151025895L;
      
      public static final RestRequest INSTANCE = new RestRequestLiteral();
   }
}
