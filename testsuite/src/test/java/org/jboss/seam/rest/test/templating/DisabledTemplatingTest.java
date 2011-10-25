package org.jboss.seam.rest.test.templating;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;

/**
 * This test verifies that seam-rest deploys successfully if there is not templating engine on the classpath.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public class DisabledTemplatingTest extends AbstractTemplatingTest {
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        WebArchive war = createTestApplication().addAsLibrary(getSeamRest());
        return war;
    }

    @Test
    public void testSeamRestDeploysWithNoTemplatingLibraries() {
        // noop - if the deployment is unsuccessful, arquillian fails the test
    }
}
