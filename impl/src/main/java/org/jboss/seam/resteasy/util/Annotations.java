package org.jboss.seam.resteasy.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Annotations
{
   /**
    * Looks for annotation on a given class including stereotypes.
    * TODO only the first level of stereotypes is implemented currently (class -> stereotype -> annotation)
    * if the annotation is placed deeper in the stereotype hierarchy, it won't be found
    * 
    * @param clazz class to be inspected
    * @param annotationClass annotation to be looked for
    * @return The annotation instance or null if the annotation is not present
    */
   public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotationClass)
   {
      if (clazz.isAnnotationPresent(annotationClass))
      {
         return clazz.getAnnotation(annotationClass);
      }
      for (Annotation annotation : clazz.getAnnotations())
      {
         if (annotation.annotationType().isAnnotationPresent(annotationClass))
         {
            return annotation.annotationType().getAnnotation(annotationClass);
         }
      }
      return null;
   }
   
   /**
    * Looks for annotation on a given method, declaring class of the method and its stereotypes.
    * 
    * @param method method to be inspected
    * @param annotationClass annotation to be looked for
    * @return The annotation instance or null if the annotation is not present
    */
   public static <T extends Annotation> T getAnnotation(Method method, Class<T> annotationClass)
   {
      if (method.isAnnotationPresent(annotationClass))
      {
         return method.getAnnotation(annotationClass);
      }
      else
      {
         return getAnnotation(method.getDeclaringClass(), annotationClass);
      }
   }
}
