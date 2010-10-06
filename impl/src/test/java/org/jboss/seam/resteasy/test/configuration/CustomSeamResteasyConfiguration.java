
package org.jboss.seam.resteasy.test.configuration;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Specializes;

import org.jboss.seam.resteasy.configuration.ExceptionMapping;
import org.jboss.seam.resteasy.configuration.SeamResteasyConfiguration;

@Specializes
public class CustomSeamResteasyConfiguration extends SeamResteasyConfiguration
{
   @PostConstruct
   public void setup()
   {
      addResource(TestResource.class);
      addResource(EjbResource.class);
      addExcludedResource(ExcludedResource.class);
      addProvider(TestProvider.class);
      addBasicExceptionMapping(IllegalArgumentException.class, 410);
      addExceptionMapping(new ExceptionMapping(EntityNotFoundException.class, 410));
      addExceptionMapping(new ExceptionMapping(NullPointerException.class, 410, "You should not call methods on a null reference."));
      addExceptionMapping(new ExceptionMapping(UnsupportedOperationException.class, 405, "The quick #{fox.color} #{fox.count == 1 ? 'fox' : 'foxes'} jumps over the lazy dog"));
      addExceptionMapping(new ExceptionMapping(NoSuchMethodError.class, 405, "#{fox.color}", false));
      addMediaTypeMapping("xml", "application/xml");
   }
}
