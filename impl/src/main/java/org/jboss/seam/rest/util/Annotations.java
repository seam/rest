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
package org.jboss.seam.rest.util;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;

public class Annotations
{
   /**
    * Find an instance of the annotationClass in an annotation arrays including the first
    * level of meta-annotation.
    * 
    *  @return the annotationClass instance if present, null otherwise
    */
   @SuppressWarnings("unchecked")
   public static <T extends Annotation> T getAnnotation(Collection<? extends Annotation> annotations, Class<T> annotationClass)
   {
      for (Annotation annotation : annotations)
      {
         if (annotation.annotationType().equals(annotationClass))
         {
            return (T) annotation;
         }
         for (Annotation innerAnnotation : annotation.annotationType().getAnnotations())
         {
            if (innerAnnotation.annotationType().equals(annotationClass))
            {
               return (T) innerAnnotation;
            }
         }
      }
      return null;
   }
   
   /**
    * Find an instance of the annotationClass in an annotation arrays including the first
    * level of meta-annotation.
    * 
    *  @return the annotationClass instance if present, null otherwise
    */
   public static <T extends Annotation> T getAnnotation(Annotation[] annotations, Class<T> annotationClass)
   {
      return getAnnotation(Arrays.asList(annotations), annotationClass);
   }
}
