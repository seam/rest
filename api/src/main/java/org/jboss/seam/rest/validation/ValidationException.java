package org.jboss.seam.rest.validation;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;

public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = -2779809222298578247L;

    private Set<ConstraintViolation<Object>> violations;

    public ValidationException(Set<ConstraintViolation<Object>> violations) {
        this.violations = violations;
    }

    public Set<ConstraintViolation<Object>> getViolations() {
        return violations;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder("[");
        for (Iterator<ConstraintViolation<Object>> iterator = violations.iterator(); iterator.hasNext();) {
            builder.append(iterator.next().getMessage());
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
