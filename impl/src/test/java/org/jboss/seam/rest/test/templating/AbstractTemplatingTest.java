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

import java.io.File;

import org.jboss.seam.rest.templating.ResponseTemplate;
import org.jboss.seam.rest.templating.TemplatingExtension;
import org.jboss.seam.rest.templating.freemarker.FreeMarkerProvider;
import org.jboss.seam.rest.templating.velocity.VelocityProvider;
import org.jboss.seam.rest.test.SeamRestClientTest;
import org.jboss.seam.rest.test.Student;
import org.jboss.seam.rest.util.Utils;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * This test class contains common methods for creating testing artifacts.
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
public abstract class AbstractTemplatingTest extends SeamRestClientTest
{
   public static final File LIBRARY_FREEMARKER = new File("target/lib/freemarker.jar");
   public static final File LIBRARY_VELOCITY = new File("target/lib/velocity.jar");
   public static final File LIBRARY_VELOCITY_TOOLS = new File("target/lib/velocity-tools.jar");
   public static final File LIBRARY_COMMONS_LANG = new File("target/lib/commons-lang.jar");
   
   public static WebArchive createTestApplication()
   {
      WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
      war.addWebResource("beans.xml");
      war.addResource("org/jboss/seam/rest/test/templating/hello.ftl", "hello.ftl");
      war.addResource("org/jboss/seam/rest/test/templating/hello.vm", "hello.vm");
      war.addResource("org/jboss/seam/rest/test/templating/university.ftl", "university.ftl");
      war.addResource("org/jboss/seam/rest/test/templating/university.vm", "university.vm");
      war.addResource("org/jboss/seam/rest/test/templating/formal.ftl", "formal.ftl");
      war.addResource("org/jboss/seam/rest/test/templating/informal.ftl", "informal.ftl");
      war.setWebXML("org/jboss/seam/rest/test/templating/web.xml");
      war.addLibraries(LIBRARY_WELDX);
      war.addLibraries(LIBRARY_JBOSS_LOGGING, LIBRARY_SLF4J_API, LIBRARY_SLF4J_IMPL);
      war.addLibraries(LIBRARY_SEAM_SERVLET_API, LIBRARY_SEAM_SERVLET_IMPL);
      war.addClasses(Student.class, University.class);
      war.addClasses(FreeMarkerResource.class, VelocityResource.class, MyApplication.class);
      war.addLibrary(createSeamRest());
      return war;
   }
   
   public static JavaArchive createSeamRest()
   {
      JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "seam-rest.jar");
      jar.addManifestResource("org/jboss/seam/rest/test/templating/javax.enterprise.inject.spi.Extension", "services/javax.enterprise.inject.spi.Extension");
      jar.addClass(Utils.class);
      jar.addPackage(ResponseTemplate.class.getPackage());
      jar.addPackage(TemplatingExtension.class.getPackage());
      jar.addPackage(FreeMarkerProvider.class.getPackage());
      jar.addPackage(VelocityProvider.class.getPackage());
      return jar;
   }
}
