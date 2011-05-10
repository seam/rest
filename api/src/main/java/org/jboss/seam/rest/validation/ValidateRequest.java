package org.jboss.seam.rest.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import javax.validation.groups.Default;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Triggers validation of incomming HTTP requests. The {@link Default} group is validated if not specified otherwise. By
 * default, the following elements are validated: - message body parameter - parameter objects - fields of the JAX-RS resource
 * <p/>
 * This behaviour can be altered using {@link #validateMessageBody()}, {@link #validateFormParameters()} and
 * {@link #validateResourceFields()} attributes.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
@Documented
@Inherited
@InterceptorBinding
public @interface ValidateRequest {
    /**
     * Validation group that will be used during validation process.
     */
    @Nonbinding Class<?>[] groups() default Default.class;

    /**
     * If set to false, the message body parameter will not be validated.
     */
    @Nonbinding boolean validateMessageBody() default true;

    /**
     * If set to false, the JAX-RS resource fields will not be validated.
     */
    @Nonbinding boolean validateResourceFields() default true;

    /**
     * Annotation literal for {@link ValidateRequest} interceptor binding.
     */
    @SuppressWarnings("all")
    static class ValidateLiteral extends AnnotationLiteral<ValidateRequest> implements ValidateRequest {
        private static final long serialVersionUID = 6404662043744038090L;

        private final Class<?>[] groups;
        private final boolean validateMessageBody;
        private final boolean validateResourceFields;

        public ValidateLiteral() {
            this(new Class<?>[]{Default.class}, true, true);
        }

        public ValidateLiteral(Class<?>[] groups, boolean validateMessageBody, boolean validateResourceFields) {
            this.groups = groups;
            this.validateMessageBody = validateMessageBody;
            this.validateResourceFields = validateResourceFields;
        }

        public Class<?>[] groups() {
            return groups;
        }

        public boolean validateMessageBody() {
            return validateMessageBody;
        }

        public boolean validateResourceFields() {
            return validateResourceFields;
        }
    }
}
