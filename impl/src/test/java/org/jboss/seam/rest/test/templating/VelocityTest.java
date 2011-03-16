package org.jboss.seam.rest.test.templating;

import org.jboss.arquillian.api.Deployment;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;

public class VelocityTest extends AbstractTemplatingTest {
    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = createTestApplication();
        war.addLibraries(LIBRARY_VELOCITY, LIBRARY_VELOCITY_TOOLS, LIBRARY_COMMONS_LANG, LIBRARY_COMMONS_COLLECTIONS);
        war.addLibrary(getSeamRest());
        return war;
    }

    @Test
    public void testTemplate() throws Exception {
        test("http://localhost:8080/test/velocity/hello", 200, "Hello Jozef Hartinger", "text/student");
    }

    @Test
    public void testExpressionLanguage() throws Exception {
        String expectedResponse = "<university name=\"Masaryk University\"><students count=\"3\"><student>A</student><student>B</student><student>C</student><student>Jozef Hartinger</student></students></university>";
        test("http://localhost:8080/test/velocity/students", 200, expectedResponse, "application/university+xml");
    }
}
