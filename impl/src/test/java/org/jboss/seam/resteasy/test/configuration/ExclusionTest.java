package org.jboss.seam.resteasy.test.configuration;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.api.Run;
import org.jboss.arquillian.api.RunModeType;
import org.jboss.seam.resteasy.test.SeamResteasyClientTest;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.testng.annotations.Test;

@Run(RunModeType.AS_CLIENT)
public class ExclusionTest extends SeamResteasyClientTest
{
   @Deployment
   public static WebArchive createDeployment()
   {
      JavaArchive jar = createSeamResteasyLibrary();
      WebArchive war = createTestApplication();
      war.setWebXML("org/jboss/seam/resteasy/test/configuration/excluded-web.xml");
      war.addLibrary(jar);
      return war;
   }
   
   @Test
   public void testExcludedResourceNotRegistered() throws Exception
   {
      // the resource should be added by auto-scanning and then removed by seam-resteasy configuration 
      test("http://localhost:8080/test/excluded", 404, null);
   }
}
