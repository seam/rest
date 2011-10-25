package org.jboss.seam.rest.client;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * Qualifier for injecting REST client and <code>ClientRequest</code> instances.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@Target({TYPE, METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Qualifier
public @interface RestClient {
    /**
     * URL of the web service
     */
    @Nonbinding String value();

    /**
     * Annotation literal for @{link RestClient} qualifier.
     */
    @SuppressWarnings("all")
    public static class RestClientLiteral extends AnnotationLiteral<RestClient> implements RestClient {
        private static final long serialVersionUID = -8456396489504116441L;

        private final String value;

        public RestClientLiteral() {
            this("");
        }

        public RestClientLiteral(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }
}
