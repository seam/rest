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
package org.jboss.seam.rest.client;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Qualifier for injecting REST client and <code>ClientRequest</code> instances.
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
@Target({ FIELD, ANNOTATION_TYPE, METHOD, PARAMETER })
@Retention(RUNTIME)
@Documented
@Qualifier
public @interface RestClient
{
   /**
    * URL of the web service
    */
   @Nonbinding
   String value();
   
   /**
    * Annotation literal for @{link RestClient} qualifier.
    */
   @SuppressWarnings("all")
   public static class RestClientLiteral extends AnnotationLiteral<RestClient> implements RestClient
   {
      private static final long serialVersionUID = -8456396489504116441L;
      
      private final String value;
      
      public RestClientLiteral()
      {
         this("");
      }
      
      public RestClientLiteral(String value)
      {
         this.value = value;
      }

      public String value()
      {
         return value;
      }
   }
}
