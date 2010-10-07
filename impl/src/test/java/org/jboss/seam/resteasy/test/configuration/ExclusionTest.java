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

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.api.Run;
import org.jboss.arquillian.api.RunModeType;
import org.jboss.seam.resteasy.test.SeamResteasyClientTest;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.testng.annotations.Test;

@Run(RunModeType.AS_CLIENT)
public class ExclusionTest extends SeamResteasyClientTest
{
   @Deployment
   public static WebArchive createDeployment()
   {
      JavaArchive jar = createSeamResteasyLibrary();
      WebArchive war = createTestApplication();
      war.setWebXML("org/jboss/seam/resteasy/test/configuration/excluded-web.xml");
      war.addLibrary(jar);
      return war;
   }
   
   @Test
   public void testExcludedResourceNotRegistered() throws Exception
   {
      // the resource should be added by auto-scanning and then removed by seam-resteasy configuration 
      test("http://localhost:8080/test/excluded", 404, null);
   }
}
