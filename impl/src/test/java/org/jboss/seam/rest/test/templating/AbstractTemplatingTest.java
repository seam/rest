package org.jboss.seam.rest.test.templating;

import java.io.File;

import org.jboss.seam.rest.templating.TemplatingProvider;
import org.jboss.seam.rest.templating.freemarker.FreeMarkerProvider;
import org.jboss.seam.rest.templating.velocity.VelocityProvider;
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
 * 
 */
public abstract class AbstractTemplatingTest extends SeamRestClientTest {
    public static final File LIBRARY_FREEMARKER = new File("target/lib/freemarker.jar");
    public static final File LIBRARY_VELOCITY = new File("target/lib/velocity.jar");
    public static final File LIBRARY_VELOCITY_TOOLS = new File("target/lib/velocity-tools.jar");
    public static final File LIBRARY_COMMONS_LANG = new File("target/lib/commons-lang.jar");
    public static final File LIBRARY_COMMONS_COLLECTIONS = new File("target/lib/commons-collections.jar");

    public static WebArchive createTestApplication() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
        war.addWebResource(EmptyAsset.INSTANCE, "beans.xml");
        war.setWebXML("org/jboss/seam/rest/test/templating/web.xml");
        war.addResource("org/jboss/seam/rest/test/templating/hello.ftl", "hello.ftl");
        war.addResource("org/jboss/seam/rest/test/templating/hello.vm", "hello.vm");
        war.addResource("org/jboss/seam/rest/test/templating/university.ftl", "university.ftl");
        war.addResource("org/jboss/seam/rest/test/templating/university.vm", "university.vm");
        war.addResource("org/jboss/seam/rest/test/templating/formal.ftl", "formal.ftl");
        war.addResource("org/jboss/seam/rest/test/templating/informal.ftl", "informal.ftl");
        war.addLibrary(LIBRARY_SEAM_SOLDER);
        war.addLibraries(LIBRARY_SLF4J_API, LIBRARY_SLF4J_IMPL);
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
