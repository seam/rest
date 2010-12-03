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
package org.jboss.seam.rest.test;

import java.io.File;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jboss.arquillian.api.Run;
import org.jboss.arquillian.api.RunModeType;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

@Run(RunModeType.AS_CLIENT)
@RunWith(Arquillian.class)
public abstract class SeamRestClientTest
{
   public static final File LIBRARY_WELDX = new File("target/lib/weld-extensions.jar");
   public static final File LIBRARY_JBOSS_LOGGING = new File("target/lib/jboss-logging.jar");
   public static final File LIBRARY_SLF4J_API = new File("target/lib/slf4j-api.jar");
   public static final File LIBRARY_SLF4J_IMPL = new File("target/lib/slf4j-simple.jar");
   
   protected HttpClient client = new HttpClient();
   
   protected void test(String url, int expectedStatus, String expectedBody, String accept) throws Exception
   {
      GetMethod get = new GetMethod(url);
      get.setRequestHeader("Accept", accept);
      assertEquals(expectedStatus, client.executeMethod(get));
      if (expectedBody != null)
      {
         assertEquals(expectedBody, get.getResponseBodyAsString());
      }
   }
   
   protected void test(String url, int expectedStatus, String expectedBody) throws Exception
   {
      test(url, expectedStatus, expectedBody, "text/plain");
   }
   
   public static JavaArchive getLoggingJar()
   {
      JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "logging.jar");
      jar.addPackage(Logger.class.getPackage());
      return jar;
   }
}
