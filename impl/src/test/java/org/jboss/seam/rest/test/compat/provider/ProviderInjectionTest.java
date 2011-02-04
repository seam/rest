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
package org.jboss.seam.rest.test.compat.provider;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.api.Run;
import org.jboss.arquillian.api.RunModeType;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.rest.test.SeamRestClientTest;
import org.jboss.seam.rest.test.compat.MyApplication;
import org.jboss.seam.rest.test.compat.Resource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Verifies that a JAX-RS provider gets JAX-RS injections when bundled
 * inside a .jar placed in WEB-INF/lib.
 * 
 * http://java.net/jira/browse/GLASSFISH-15794
 * https://issues.jboss.org/browse/RESTEASY-506
 * 
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 *
 */

@Run(RunModeType.AS_CLIENT)
@RunWith(Arquillian.class)
public class ProviderInjectionTest extends SeamRestClientTest
{
   @Deployment
   public static WebArchive getDeployment()
   {
      return ShrinkWrap.create(WebArchive.class, "test.war")
         .addClasses(MyApplication.class, Resource.class)
         .addWebResource(EmptyAsset.INSTANCE, "beans.xml")
         .setWebXML("WEB-INF/web.xml")
         .addLibrary(getJar());
   }
   
   public static JavaArchive getJar()
   {
      return ShrinkWrap.create(JavaArchive.class, "test.jar")
         .addClasses(MyExceptionMapper.class, Foo.class)
         .addManifestResource(EmptyAsset.INSTANCE, "beans.xml");
   }
   
   @Test
   public void testProviderInjected() throws Exception
   {
      test("http://localhost:8080/test/api/test/exception", 200, "SecurityContext:true,Providers:true,ServletConfig:true,ServletContext:true,CDI field injection:true");
   }
}
