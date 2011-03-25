package org.jboss.seam.rest.test.compat.interceptor;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.rest.test.SeamRestClientTest;
import org.jboss.seam.rest.test.compat.MyApplication;
import org.jboss.seam.rest.test.compat.Resource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunAsClient
@RunWith(Arquillian.class)
public class InterceptedResourceTest extends SeamRestClientTest {
    @Deployment(testable = false)
    public static WebArchive getDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war").addClasses(MyApplication.class, Resource.class)
                .addAsWebInfResource("org/jboss/seam/rest/test/compat/interceptor/beans.xml", "beans.xml")
                .setWebXML("WEB-INF/web.xml").addAsLibrary(getJar());
    }

    public static JavaArchive getJar() {
        return ShrinkWrap.create(JavaArchive.class, "test.jar").addClasses(Valid.class, ValidationInterceptor.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testCdiResourceIsIntercepted() throws Exception {
        test("http://localhost:8080/test/api/test/ping", 200, "Validated pong");
    }
}
