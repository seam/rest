package org.jboss.seam.resteasy.test.configuration;

import static org.testng.Assert.assertEquals;

import org.apache.commons.httpclient.methods.GetMethod;
import org.jboss.arquillian.api.Run;
import org.jboss.arquillian.api.RunModeType;
import org.jboss.seam.resteasy.test.SeamResteasyClientTest;
import org.testng.annotations.Test;

@Run(RunModeType.AS_CLIENT)
public class ConfigurationTest extends SeamResteasyClientTest
{
   @Test
   public void testResource() throws Exception
   {
      test("http://localhost:8080/test/foo", 200, "foo");
   }

   @Test
   public void testProvider() throws Exception
   {
      GetMethod get = new GetMethod("http://localhost:8080/test/foo/student");
      get.setRequestHeader("Accept", "foo/bar");
      assertEquals(client.executeMethod(get), 200);
      assertEquals(get.getResponseBodyAsString(), "Jozef Hartinger");
   }

   @Test
   public void testExceptionMapping1() throws Exception
   {
      test("http://localhost:8080/test/foo/exception1", 410, null);
   }
   
   @Test
   public void testExceptionMapping2() throws Exception
   {
      test("http://localhost:8080/test/foo/exception2", 410, "You should not call methods on a null reference.");
   }
   
   @Test
   public void testEjbExceptionMapping() throws Exception
   {
      test("http://localhost:8080/test/ejb/ping", 410, "You should not call methods on a null reference.");
   }
   
   @Test
   public void testEjbWithWeldInterceptorExceptionMapping() throws Exception
   {
      test("http://localhost:8080/test/ejb/pong", 410, "You should not call methods on a null reference.");
   }
   
   @Test
   public void testExceptionMessageInterpolation() throws Exception
   {
      test("http://localhost:8080/test/foo/interpolatedException", 405, "The quick brown fox jumps over the lazy dog");
   }
   
   @Test
   public void testExceptionMessageInterpolationSwitch() throws Exception
   {
      test("http://localhost:8080/test/foo/interpolatedExceptionSwitch", 405, "#{fox.color}");
   }
   
   @Test
   public void testExceptionMessageInterpolationXml() throws Exception
   {
      test("http://localhost:8080/test/foo/interpolatedException", 405, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><error><message>The quick brown fox jumps over the lazy dog</message></error>", "application/xml");
   }
   
   @Test
   public void testBasicExceptionMapping() throws Exception
   {
      test("http://localhost:8080/test/foo/exception3", 410, null);
   }

   @Test
   public void testMediaTypeMapping() throws Exception
   {
      test("http://localhost:8080/test/foo.xml", 200, "<foo/>");
   }
}
