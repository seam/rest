package org.jboss.seam.resteasy.validation;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.jboss.resteasy.core.PropertyInjectorImpl;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.PropertyInjector;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

/**
 * This interceptor workarounds https://jira.jboss.org/browse/CDI-6. Enable it if you experience problems with JAX-RS injection into CDI-intercepted beans.
 */
@ValidateRequest
@Interceptor
public class InjectionInterceptor implements Serializable
{
   private static final long serialVersionUID = -4805100726530802448L;

   @PostConstruct
   public void performJaxrsInjection(InvocationContext ctx)
   {
      HttpRequest request = ResteasyProviderFactory.getContextData(HttpRequest.class);
      HttpResponse response = ResteasyProviderFactory.getContextData(HttpResponse.class);

      PropertyInjector propertyInjector = new PropertyInjectorImpl(ctx.getTarget().getClass(), ResteasyProviderFactory.getInstance());
      
      if ((request != null) && (response != null))
      {
         propertyInjector.inject(request, response, ctx.getTarget());
      }
      else
      {
         throw new IllegalStateException("Trying to instantiate a resource outside of HTTP request.");
      }
   }
}
