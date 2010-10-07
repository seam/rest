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
import org.jboss.seam.resteasy.test.configuration.EjbResource;
import org.jboss.seam.resteasy.test.configuration.EntityNotFoundException;
import org.jboss.seam.resteasy.test.configuration.ExcludedResource;
import org.jboss.seam.resteasy.test.configuration.TestInterceptor;
import org.jboss.seam.resteasy.test.configuration.TestInterceptorBinding;
import org.jboss.seam.resteasy.test.configuration.TestProvider;
import org.jboss.seam.resteasy.test.configuration.TestResource;
import org.jboss.seam.resteasy.util.Interpolator;
import org.jboss.seam.resteasy.validation.ValidateRequest;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.weld.extensions.el.Expressions;

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
      JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "seam-resteasy.jar");
      jar.addManifestResource("META-INF/web-fragment.xml", "web-fragment.xml");
      jar.addManifestResource("META-INF/beans.xml", ArchivePaths.create("beans.xml"));
      jar.addPackage(SeamResteasyConfiguration.class.getPackage());
      jar.addPackage(ValidateRequest.class.getPackage());
      jar.addPackage(Interpolator.class.getPackage());
      jar.addPackage(Expressions.class.getPackage());
      return jar;
   }
   
   public static WebArchive createTestApplication()
   {
      WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
      war.addClass(CustomSeamResteasyConfiguration.class);
      war.addClass(EntityNotFoundException.class);
      war.addClass(TestProvider.class);
      war.addClass(TestResource.class);
      war.addClass(TestInterceptorBinding.class);
      war.addClass(TestInterceptor.class);
      war.addClass(EjbResource.class);
      war.addClass(ExcludedResource.class);
      war.addClass(Student.class);
      war.addClass(Fox.class);
      war.addResource("org/jboss/seam/resteasy/test/configuration/beans.xml", ArchivePaths.create("WEB-INF/beans.xml"));
      war.setWebXML("org/jboss/seam/resteasy/test/configuration/web.xml");
      return war;
   }
   
   protected void test(String url, int expectedStatus, String expectedBody, String accept) throws Exception
   {
      GetMethod get = new GetMethod(url);
      get.setRequestHeader("Accept", accept);
      assertEquals(client.executeMethod(get), expectedStatus);
      if (expectedBody != null)
      {
         assertEquals(get.getResponseBodyAsString(), expectedBody);
      }
   }
   
   protected void test(String url, int expectedStatus, String expectedBody) throws Exception
   {
      test(url, expectedStatus, expectedBody, "text/plain");
   }
}
