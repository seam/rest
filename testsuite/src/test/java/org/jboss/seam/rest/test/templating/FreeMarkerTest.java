package org.jboss.seam.rest.test.templating;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.seam.rest.test.Dependencies;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;

public class FreeMarkerTest extends AbstractTemplatingTest {
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        WebArchive war = createTestApplication();
        war.addAsLibraries(Dependencies.FREEMARKER);
        war.addAsLibrary(getSeamRest());
        return war;
    }
    @Test
    public void testTemplate() throws Exception {
        test("http://localhost:8080/test/freemarker/hello", 200, "Hello Jozef Hartinger", "text/student");
    }
    @Test
    public void testExpressionLanguage() throws Exception {
        String expectedResponse = "<university name=\"Masaryk University\"><students count=\"3\"><student>A</student><student>B</student><student>C</student><student>Jozef Hartinger</student></students></university>";
        test("http://localhost:8080/test/freemarker/students", 200, expectedResponse, "application/university+xml");
    }
    @Test
    public void testCorrectTemplateSelected() throws Exception {
        test("http://localhost:8080/test/freemarker/greeting", 200, "Hi Jozef Hartinger", "text/greeting-informal");
        test("http://localhost:8080/test/freemarker/greeting", 200, "Good morning Mr. Jozef Hartinger", "text/greeting-formal");
    }
    
    @Test
    @Ignore
    public void testString() throws Exception {
        test("http://localhost:8080/test/freemarker/string", 200, "Hello Jozef!", "text/plain");
    }
}
