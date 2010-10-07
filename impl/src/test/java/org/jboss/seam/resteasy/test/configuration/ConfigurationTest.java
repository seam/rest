/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
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
