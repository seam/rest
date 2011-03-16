package org.jboss.seam.rest.test.compat.provider.application;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.api.Run;
import org.jboss.arquillian.api.RunModeType;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.rest.test.SeamRestClientTest;
import org.jboss.seam.rest.test.compat.MyApplication;
import org.jboss.seam.rest.test.compat.Resource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Verifies that an Application subclass can be injected into a JAX-RS provider.
 * 
 * https://issues.jboss.org/browse/RESTEASY-506
 * 
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 * 
 */

@Run(RunModeType.AS_CLIENT)
@RunWith(Arquillian.class)
public class ApplicationInjectedIntoProviderTest extends SeamRestClientTest {
    @Deployment
    public static WebArchive getDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(MyApplication.class, Resource.class, MyExceptionMapper.class).setWebXML("WEB-INF/web.xml");
    }

    @Test
    public void testProviderInjected() throws Exception {
        test("http://localhost:8080/test/api/test/exception", 200, "Application:true");
    }
}
