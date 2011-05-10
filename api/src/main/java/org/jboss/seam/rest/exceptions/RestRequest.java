package org.jboss.seam.rest.exceptions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;

/**
 * A qualifier used to distinguish Seam Catch handler methods that are appropriate for a REST resource request.
 *
 * @author <a href="http://community.jboss.org/people/dan.j.allen">Dan Allen</a>
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface RestRequest {

    /**
     * Annotation literal for {@link RestRequest} qualifier.
     */
    @SuppressWarnings("all")
    static class RestRequestLiteral extends AnnotationLiteral<RestRequest> implements RestRequest {
        private static final long serialVersionUID = 2990603884151025895L;

        public static final RestRequest INSTANCE = new RestRequestLiteral();
    }
}
