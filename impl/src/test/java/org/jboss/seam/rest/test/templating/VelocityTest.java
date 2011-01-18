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
package org.jboss.seam.rest.test.templating;

import org.jboss.arquillian.api.Deployment;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;

public class VelocityTest extends AbstractTemplatingTest
{
   @Deployment
   public static WebArchive createDeployment()
   {
      WebArchive war = createTestApplication();
      war.addLibraries(LIBRARY_VELOCITY, LIBRARY_VELOCITY_TOOLS, LIBRARY_COMMONS_LANG, LIBRARY_COMMONS_COLLECTIONS);
      war.addLibrary(getSeamRest());
      return war;
   }
   
   @Test
   public void testTemplate() throws Exception
   {
      test("http://localhost:8080/test/velocity/hello", 200, "Hello Jozef Hartinger", "text/student");
   }
   
   @Test
   public void testExpressionLanguage() throws Exception
   {
      String expectedResponse = "<university name=\"Masaryk University\"><students count=\"3\"><student>A</student><student>B</student><student>C</student><student>Jozef Hartinger</student></students></university>";
      test("http://localhost:8080/test/velocity/students", 200, expectedResponse, "application/university+xml");
   }
}
