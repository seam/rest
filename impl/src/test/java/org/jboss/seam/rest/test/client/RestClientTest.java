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
package org.jboss.seam.rest.test.client;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.seam.rest.client.RestClientExtension;
import org.jboss.seam.rest.test.SeamRestClientTest;
import org.jboss.seam.rest.util.Annotations;
import org.jboss.seam.rest.util.ExpressionLanguageInterpolator;
import org.jboss.seam.rest.util.Interpolator;
import org.jboss.seam.rest.util.Utils;
import org.jboss.seam.solder.bean.Beans;
import org.jboss.seam.solder.literal.DefaultLiteral;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class RestClientTest
{

   @Inject
   private InjectedBean bean;

   @Deployment
   public static WebArchive getDeployment()
   {
      WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
      war.addPackage(RestClientTest.class.getPackage()); // test classes
      war.addPackage(Beans.class.getPackage());
      war.addClass(DefaultLiteral.class);
      war.addWebResource("beans.xml", "classes/META-INF/beans.xml");
      war.addWebResource("org/jboss/seam/rest/test/client/web.xml", "web.xml");
      war.addLibrary(getSeamRest());
      war.addLibraries(SeamRestClientTest.LIBRARY_SEAM_SOLDER_API, SeamRestClientTest.LIBRARY_SEAM_SOLDER_IMPL);
      return war;
   }

   public static JavaArchive getSeamRest()
   {
      JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "test.jar");
      jar.addPackage(RestClientExtension.class.getPackage());
      jar.addClasses(Utils.class, Annotations.class, Interpolator.class, ExpressionLanguageInterpolator.class);
      jar.addManifestResource("org/jboss/seam/rest/test/client/javax.enterprise.inject.spi.Extension", "services/javax.enterprise.inject.spi.Extension");
      return jar;
   }

   @Test
   public void testClientRequest() throws Exception
   {
      ClientRequest request = bean.getRequest();
      request.accept(MediaType.TEXT_PLAIN_TYPE);
      ClientResponse<String> response = request.get(String.class);
      assertEquals("pong", response.getEntity());
   }
   
   @Test
   public void testRestClientPost()
   {
      assertEquals(200, bean.createTask());
   }
   
   @Test
   public void testRestClientGet()
   {
      Task task = bean.getTask();
      
      assertEquals(2, task.getId());
      assertEquals("alpha", task.getName());
      assertEquals("bravo", task.getDescription());
      assertEquals("pong", bean.ping());
   }
   
   @Test
   public void testUriInterpolation() throws Exception
   {
      assertEquals("http://example.com:8080/service/ping", bean.getRequestWithEl().getUri());
   }
}
