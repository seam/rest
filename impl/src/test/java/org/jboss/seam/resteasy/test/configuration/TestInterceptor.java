package org.jboss.seam.resteasy.test.configuration;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@TestInterceptorBinding
public class TestInterceptor
{
   @AroundInvoke
   public Object intercept(InvocationContext ctx) throws Exception
   {
      return ctx.proceed();
   }
}
