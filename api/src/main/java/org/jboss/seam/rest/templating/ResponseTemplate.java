package org.jboss.seam.rest.templating;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates that the HTTP response is produced using a template.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@Retention(RUNTIME)
@Target(METHOD)
@Documented
public @interface ResponseTemplate {
    /**
     * Path to the template to be used.
     */
    String value();

    /**
     * Media type produced by the template.
     */
    String produces() default "*/*";

    /**
     * Name under which the returned value is available in the template.
     */
    String responseName() default "response";

    /**
     * Defines several <code>@ResponseTemplate</code> annotations on the same element
     *
     * @see ResponseTemplate
     */
    @Target(METHOD)
    @Retention(RUNTIME)
    @Documented
            @interface List {
        ResponseTemplate[] value();
    }
}
