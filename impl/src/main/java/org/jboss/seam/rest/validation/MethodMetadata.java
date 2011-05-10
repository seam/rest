package org.jboss.seam.rest.validation;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Caches method metadata needed to perform validation of JAX-RS requests.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public class MethodMetadata {
    private final Method method;
    private final Integer messageBody; // position of the messageBody, may be null
    private final Set<Integer> validatedParameters; // positions of other validated parameters
    private final ValidateRequest interceptorBinding;

    public MethodMetadata(Method method, Integer messageBody, Set<Integer> validatedParameters,
                          ValidateRequest interceptorBinding) {
        this.method = method;
        this.messageBody = messageBody;
        this.validatedParameters = validatedParameters;
        this.interceptorBinding = interceptorBinding;
    }

    public Method getMethod() {
        return method;
    }

    /**
     * Returns the index of the message body parameter or null if the method does not contain a message body parameter.
     */
    public Integer getMessageBody() {
        return messageBody;
    }

    /**
     * Returns a set of indexes of the validated parameters.
     */
    public Set<Integer> getValidatedParameters() {
        return validatedParameters;
    }

    public ValidateRequest getInterceptorBinding() {
        return interceptorBinding;
    }
}
