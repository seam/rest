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
package org.jboss.seam.resteasy.test.exceptions;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.api.Run;
import org.jboss.arquillian.api.RunModeType;
import org.jboss.seam.resteasy.exceptions.ExceptionMapping;
import org.jboss.seam.resteasy.test.Fox;
import org.jboss.seam.resteasy.test.SeamResteasyClientTest;
import org.jboss.seam.resteasy.util.Interpolator;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

@Run(RunModeType.AS_CLIENT)
public class ExceptionMappingTest extends SeamResteasyClientTest
{

   // TODO simulate war/jar structure

   @Deployment
   public static WebArchive createDeployment()
   {
      WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
      war.addWebResource("META-INF/beans.xml", "beans.xml");
      war.setWebXML("WEB-INF/web.xml");
      war.addPackage(ExceptionMapping.class.getPackage());
      war.addClasses(CustomExceptionMappingConfiguration.class, TestResource.class, Fox.class, MoreSpecificExceptionMapper.class, MyApplication.class);
      war.addClasses(Exception1.class, Exception2.class);
      war.addClasses(Interpolator.class, MockInterpolator.class);
      // SLF4J - needed at GlassFish
      war.addPackages(true, Logger.class.getPackage());
      return war;
   }

   @Test
   public void testException() throws Exception
   {
      test("http://localhost:8080/test/exceptions/iae", 410, null);
   }

   @Test
   public void testSpecializedExceptionMapperGetsCalled() throws Exception
   {
      test("http://localhost:8080/test/exceptions/aioobe", 421, null);
   }

   @Test
   public void testRuntimeException() throws Exception
   {
      test("http://localhost:8080/test/exceptions/npe", 412, "Null reference.");
   }

   @Test
   public void testExceptionUnwrapping() throws Exception
   {
      test("http://localhost:8080/test/exceptions/e1", 400, null);
   }

   @Test
   public void testExceptionUnwrappingEjb() throws Exception
   {
      test("http://localhost:8080/test/exceptions/ejb", 400, null);
   }

   @Test
   public void testErrorMessageInterpolation() throws Exception
   {
      test("http://localhost:8080/test/exceptions/uoe", 413, "The quick brown fox jumps over the lazy dog");
   }

   @Test
   public void testErrorMessageInterpolationSwitch() throws Exception
   {
      test("http://localhost:8080/test/exceptions/nsme", 414, "The quick #{fox.color} #{fox.count == 1 ? 'fox' : 'foxes'} jumps over the lazy dog");
   }
}