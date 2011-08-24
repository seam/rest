package org.jboss.seam.rest.test.templating;

import org.jboss.seam.rest.templating.TemplatingProvider;
import org.jboss.seam.rest.templating.freemarker.FreeMarkerProvider;
import org.jboss.seam.rest.templating.velocity.VelocityProvider;
import org.jboss.seam.rest.test.Dependencies;
import org.jboss.seam.rest.test.SeamRestClientTest;
import org.jboss.seam.rest.test.Student;
import org.jboss.seam.rest.test.University;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * This test class contains common methods for creating testing artifacts.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public abstract class AbstractTemplatingTest extends SeamRestClientTest {

    public static WebArchive createTestApplication() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
        war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        war.setWebXML("WEB-INF/web.xml");
        war.addAsWebResource("org/jboss/seam/rest/test/templating/hello.ftl", "hello.ftl");
        war.addAsWebResource("org/jboss/seam/rest/test/templating/hello.vm", "hello.vm");
        war.addAsWebResource("org/jboss/seam/rest/test/templating/university.ftl", "university.ftl");
        war.addAsWebResource("org/jboss/seam/rest/test/templating/university.vm", "university.vm");
        war.addAsWebResource("org/jboss/seam/rest/test/templating/formal.ftl", "formal.ftl");
        war.addAsWebResource("org/jboss/seam/rest/test/templating/informal.ftl", "informal.ftl");
        war.addAsWebResource("org/jboss/seam/rest/test/templating/string.ftl", "string.ftl");
        war.addAsWebResource("org/jboss/seam/rest/test/templating/string.vm", "string.vm");
        war.addAsLibraries(Dependencies.SEAM_SOLDER);
        war.addClasses(FreeMarkerResource.class, VelocityResource.class, MyApplication.class);
        return war;
    }

    public static JavaArchive getSeamRest() {
        JavaArchive jar = SeamRestClientTest.createSeamRest();
        jar.addPackage(TemplatingProvider.class.getPackage());
        jar.addPackage(FreeMarkerProvider.class.getPackage());
        jar.addPackage(VelocityProvider.class.getPackage());
        jar.addClasses(Student.class, University.class);
        return jar;
    }
}
