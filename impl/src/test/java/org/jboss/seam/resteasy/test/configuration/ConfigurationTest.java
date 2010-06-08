package org.jboss.seam.resteasy.test.configuration;


import static org.testng.Assert.assertEquals;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.api.Run;
import org.jboss.arquillian.api.RunModeType;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.seam.resteasy.configuration.ConfigurationListener;
import org.jboss.seam.resteasy.configuration.GenericExceptionMapper;
import org.jboss.seam.resteasy.configuration.SeamResteasyConfiguration;
import org.jboss.seam.resteasy.validation.ValidationException;
import org.jboss.seam.resteasy.validation.ValidationExceptionMapper;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.testng.annotations.Test;

@Run(RunModeType.AS_CLIENT)
public class ConfigurationTest 
extends Arquillian
{
   private HttpClient client = new HttpClient();
   
   @Deployment
   public static WebArchive createDeployment()
   {
      JavaArchive jar = ShrinkWrap.create("seam-resteasy.jar", JavaArchive.class);
      jar.addManifestResource("META-INF/web-fragment.xml", "web-fragment.xml");
      jar.addManifestResource("META-INF/beans.xml", ArchivePaths.create("beans.xml"));
      jar.addClass(SeamResteasyConfiguration.class);
      jar.addClass(ConfigurationListener.class);
      jar.addClass(GenericExceptionMapper.class);
      jar.addClass(ValidationExceptionMapper.class);
      jar.addClass(ValidationException.class);
      WebArchive war = ShrinkWrap.create("test.war", WebArchive.class);
      war.addClass(CustomSeamResteasyConfiguration.class);
      war.addClass(EntityNotFoundException.class);
      war.addClass(TestProvider.class);
      war.addClass(TestResource.class);
      war.addLibrary(jar);
      war.addResource("META-INF/beans.xml", ArchivePaths.create("WEB-INF/beans.xml"));
      war.setWebXML("org/jboss/seam/resteasy/test/configuration/web.xml");
      return war;
   }
   
   
   @Test
   public void testResource() throws Exception
   {
      test("http://localhost:8080/test/foo", 200, "foo");
   }
   
   @Test
   public void testProvider() throws Exception
   {
      GetMethod get = new GetMethod("http://localhost:8080/test/foo/bar");
      get.setRequestHeader("Accept", "foo/bar");
      assertEquals(client.executeMethod(get), 200);
      assertEquals(get.getResponseBodyAsString(), "foobar");
   }
   
   @Test
   public void testExceptionMapping() throws Exception
   {
      test("http://localhost:8080/test/foo/notFound", 404, null);
   }
   
   @Test
   public void testMediaTypeMapping() throws Exception
   {
      test("http://localhost:8080/test/foo.xml", 200, "<foo/>");
   }
   
   private void test(String url, int expectedStatus, String expectedBody) throws Exception
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
