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

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jboss.arquillian.api.Run;
import org.jboss.arquillian.api.RunModeType;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

@Run(RunModeType.AS_CLIENT)
@RunWith(Arquillian.class)
public abstract class SeamResteasyClientTest
{
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
}
