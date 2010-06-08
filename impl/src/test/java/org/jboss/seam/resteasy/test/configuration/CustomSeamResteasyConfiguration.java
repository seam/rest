package org.jboss.seam.resteasy.test.configuration;

import javax.enterprise.inject.Specializes;

import org.jboss.seam.resteasy.configuration.SeamResteasyConfiguration;

@Specializes
public class CustomSeamResteasyConfiguration extends SeamResteasyConfiguration
{

   public CustomSeamResteasyConfiguration()
   {
      getResources().add(TestResource.class);
      getProviders().add(TestProvider.class);
      getExceptionMappings().put(EntityNotFoundException.class, 404);
      getMediaTypeMappings().put("xml", "application/xml");
   }
}
