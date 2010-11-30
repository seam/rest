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
package org.jboss.seam.rest.test.templating.freemarker;

import org.jboss.arquillian.api.Deployment;
import org.jboss.seam.rest.templating.freemarker.FreeMarkerTemplate;
import org.jboss.seam.rest.test.SeamRestClientTest;
import org.jboss.seam.rest.test.Student;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;

public class FreeMarkerTemplatingTest extends SeamRestClientTest
{

   @Deployment
   public static WebArchive createDeployment()
   {
      WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
      war.addWebResource("beans.xml");
      war.addResource("org/jboss/seam/rest/test/templating/freemarker/hello.ftl", "hello.ftl");
      war.addResource("org/jboss/seam/rest/test/templating/freemarker/students.ftl", "students.ftl");
      war.setWebXML("org/jboss/seam/rest/test/templating/freemarker/web.xml");
      war.addLibraries(LIBRARY_FREEMARKER, LIBRARY_JBOSS_LOGGING, LIBRARY_WELDX, LIBRARY_SLF4J_API, LIBRARY_SLF4J_IMPL);
      war.addClass(Student.class);
      war.addClasses(Resource.class, MyApplication.class, University.class);
      war.addPackage(FreeMarkerTemplate.class.getPackage());
      return war;
   }
   
   @Test
   public void testTemplate() throws Exception
   {
      test("http://localhost:8080/test/test/hello", 200, "Hello Jozef Hartinger", "text/student");
   }
   
   @Test
   public void testExpressionLanguage() throws Exception
   {
      String expectedResponse = "<university name=\"Masaryk University\"><students count=\"3\"><student>A</student><student>B</student><student>C</student><student>Jozef Hartinger</student></students></university>";
      test("http://localhost:8080/test/test/students", 200, expectedResponse, "application/university+xml");
   }
}
