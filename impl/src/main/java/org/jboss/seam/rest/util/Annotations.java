package org.jboss.seam.rest.util;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;

public class Annotations {
    /**
     * Find an instance of the annotationClass in an annotation arrays including the first level of meta-annotation.
     *
     * @return the annotationClass instance if present, null otherwise
     */
    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T getAnnotation(Collection<? extends Annotation> annotations, Class<T> annotationClass) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(annotationClass)) {
                return (T) annotation;
            }
            for (Annotation innerAnnotation : annotation.annotationType().getAnnotations()) {
                if (innerAnnotation.annotationType().equals(annotationClass)) {
                    return (T) innerAnnotation;
                }
            }
        }
        return null;
    }

    /**
     * Find an instance of the annotationClass in an annotation arrays including the first level of meta-annotation.
     *
     * @return the annotationClass instance if present, null otherwise
     */
    public static <T extends Annotation> T getAnnotation(Annotation[] annotations, Class<T> annotationClass) {
        return getAnnotation(Arrays.asList(annotations), annotationClass);
    }
}
