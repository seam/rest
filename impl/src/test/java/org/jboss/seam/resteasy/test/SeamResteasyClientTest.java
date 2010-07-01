package org.jboss.seam.resteasy.test;

import static org.testng.Assert.assertEquals;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.api.Run;
import org.jboss.arquillian.api.RunModeType;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.seam.resteasy.configuration.SeamResteasyConfiguration;
import org.jboss.seam.resteasy.test.configuration.CustomSeamResteasyConfiguration;
import org.jboss.seam.resteasy.test.configuration.EntityNotFoundException;
import org.jboss.seam.resteasy.test.configuration.ExcludedResource;
import org.jboss.seam.resteasy.test.configuration.TestProvider;
import org.jboss.seam.resteasy.test.configuration.TestResource;
import org.jboss.seam.resteasy.validation.ValidateRequest;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

@Run(RunModeType.AS_CLIENT)
public abstract class SeamResteasyClientTest extends Arquillian
{
   protected HttpClient client = new HttpClient();
   
   @Deployment
   public static WebArchive createDeployment()
   {
      JavaArchive jar = createSeamResteasyLibrary();
      WebArchive war = createTestApplication();
      war.addLibrary(jar);
      return war;
   }
   
   public static JavaArchive createSeamResteasyLibrary()
   {
      JavaArchive jar = ShrinkWrap.create("seam-resteasy.jar", JavaArchive.class);
      jar.addManifestResource("META-INF/web-fragment.xml", "web-fragment.xml");
      jar.addManifestResource("META-INF/beans.xml", ArchivePaths.create("beans.xml"));
      jar.addPackage(SeamResteasyConfiguration.class.getPackage());
      jar.addPackage(ValidateRequest.class.getPackage());
      jar.addPackage(SeamResteasyClientTest.class.getPackage());
      return jar;
   }
   
   public static WebArchive createTestApplication()
   {
      WebArchive war = ShrinkWrap.create("test.war", WebArchive.class);
      war.addClass(CustomSeamResteasyConfiguration.class);
      war.addClass(EntityNotFoundException.class);
      war.addClass(TestProvider.class);
      war.addClass(TestResource.class);
      war.addClass(ExcludedResource.class);
      war.addClass(Student.class);
      war.addResource("META-INF/beans.xml", ArchivePaths.create("WEB-INF/beans.xml"));
      war.setWebXML("org/jboss/seam/resteasy/test/configuration/web.xml");
      return war;
   }
   
   protected void test(String url, int expectedStatus, String expectedBody) throws Exception
   {
      GetMethod get = new GetMethod(url);
      get.setRequestHeader("Accept", "text/plain");
      assertEquals(client.executeMethod(get), expectedStatus);
      if (expectedBody != null)
      {
         assertEquals(get.getResponseBodyAsString(), expectedBody);
      }
   }
}
