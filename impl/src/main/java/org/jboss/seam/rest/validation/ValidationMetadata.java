package org.jboss.seam.rest.validation;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

/**
 * Container for {@link MethodMetadata}.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@ApplicationScoped
public class ValidationMetadata implements Serializable {
    private static final long serialVersionUID = 9094847250956854536L;

    private Map<Method, MethodMetadata> methods = new HashMap<Method, MethodMetadata>();

    public MethodMetadata getMethodMetadata(Method method) {
        return methods.get(method);
    }

    void addMethodMetadata(MethodMetadata method) {
        methods.put(method.getMethod(), method);
    }

    public boolean containsMethodMetadata(Method method) {
        return methods.containsKey(method);
    }
}
