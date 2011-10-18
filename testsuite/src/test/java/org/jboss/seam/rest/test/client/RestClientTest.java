package org.jboss.seam.rest.test.client;

import static org.jboss.seam.rest.test.Dependencies.addDependencyToManifest;
import static org.junit.Assert.assertEquals;

import javax.enterprise.inject.spi.Extension;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.seam.rest.client.RestClientExtension;
import org.jboss.seam.rest.test.Dependencies;
import org.jboss.seam.rest.test.SeamRestClientTest;
import org.jboss.solder.bean.Beans;
import org.jboss.solder.el.Expressions;
import org.jboss.solder.literal.DefaultLiteral;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class RestClientTest {
    @Inject
    private InjectedBean bean;

    @Deployment
    public static WebArchive getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
        war.addPackage(RestClientTest.class.getPackage()); // test classes
        war.addPackage(Beans.class.getPackage());
        war.addClass(DefaultLiteral.class);
        war.addClass(Dependencies.class);
        war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        war.setWebXML("WEB-INF/web.xml");
        war.addAsLibrary(getSeamRest());
        war.addAsLibraries(Dependencies.SEAM_SOLDER);
        addDependencyToManifest(war, "org.apache.httpcomponents"); // JBoss AS 7
        return war;
    }

    public static JavaArchive getSeamRest() {
        JavaArchive jar = SeamRestClientTest.createSeamRest();
        jar.addAsServiceProvider(Extension.class, RestClientExtension.class);
        return jar;
    }

    @Test
    public void testClientRequest() throws Exception {
        ClientRequest request = bean.getRequest();
        request.accept(MediaType.TEXT_PLAIN_TYPE);
        ClientResponse<String> response = request.get(String.class);
        assertEquals("pong", response.getEntity());
    }

    @Test
    public void testRestClientPost() {
        assertEquals(200, bean.createTask());
    }

    @Test
    public void testRestClientGet() {
        Task task = bean.getTask();

        assertEquals(2, task.getId());
        assertEquals("alpha", task.getName());
        assertEquals("bravo", task.getDescription());
        assertEquals("pong", bean.ping());
    }

    @Test
    public void testUriInterpolation() throws Exception {
        assertEquals("http://example.com:8080/service/ping", bean.getRequestWithEl().getUri());
    }
}
