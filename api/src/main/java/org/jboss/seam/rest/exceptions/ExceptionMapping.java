package org.jboss.seam.rest.exceptions;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface ExceptionMapping {
    Class<? extends Throwable> exceptionType();

    int status();

    String message() default "";

    boolean useExceptionMessage() default false;

    boolean interpolateMessage() default true;

    boolean useJaxb() default true;

    /**
     * Defines several <code>@DeclarativeExceptionMapping</code> annotations on the same element
     */
    @Target(TYPE)
    @Retention(RUNTIME)
    @Documented
            @interface List {
        ExceptionMapping[] value();
    }
}
