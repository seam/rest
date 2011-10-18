package org.jboss.seam.rest.test.templating.multiple;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.seam.rest.test.Dependencies;
import org.jboss.seam.rest.test.templating.AbstractTemplatingTest;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;

/**
 * This test verifies that a prefered TemplatingProvider can be selected.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public class PreferedTemplatingProviderTest extends AbstractTemplatingTest {
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        WebArchive war = createTestApplication();
        war.addAsLibraries(Dependencies.VELOCITY);
        war.addAsLibraries(Dependencies.VELOCITY_TOOLS);
        war.addAsLibrary(getSeamRest());
        return war;
    }

    public static JavaArchive getSeamRest() {
        JavaArchive jar = AbstractTemplatingTest.getSeamRest();
        jar.addClass(CustomSeamRestConfiguration.class);
        jar.addClasses(MockTemplatingProvider.class, CustomSeamRestConfiguration.class);
        return jar;
    }

    @Test
    public void testPreferedTemplateIsSelected() throws Exception {
        test("http://localhost:8080/test/freemarker/hello", 200, "Hello world!", "text/student");
    }
}
