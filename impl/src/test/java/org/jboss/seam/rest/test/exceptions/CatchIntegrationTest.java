package org.jboss.seam.rest.test.exceptions;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CatchIntegrationTest extends BuiltinExceptionMappingTest {
    @Deployment
    public static WebArchive createDeploymentWithCatch() {
        WebArchive war = createDeployment();
        war.addLibraries(LIBRARY_SEAM_CATCH);
        return war;
    }

    @Test
    public void testSpecializedExceptionHandlerGetsCalled() throws Exception {
        test("http://localhost:8080/test/exceptions/ie", 415, null);
    }
}
