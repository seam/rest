package org.jboss.seam.rest.test.compat.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Valid
public class ValidationInterceptor {
    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        return "Validated " + ctx.proceed();
    }
}
