package org.jboss.seam.rest.test.client;

import java.io.File;
import java.io.InputStream;

import javax.enterprise.inject.spi.Extension;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.osgi.testing.ManifestBuilder;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.seam.rest.client.RestClientExtension;
import org.jboss.seam.rest.test.SeamRestClientTest;
import org.jboss.seam.solder.bean.Beans;
import org.jboss.seam.solder.literal.DefaultLiteral;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

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
        war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        war.setWebXML("WEB-INF/web.xml");
        war.addAsLibrary(getSeamRest());
        war.addAsLibrary(SeamRestClientTest.LIBRARY_SEAM_SOLDER);

        // JBoss AS 7
        war.setManifest(new Asset() {

            @Override
            public InputStream openStream() {
                return ManifestBuilder.newInstance().addManifestHeader("Dependencies", "org.apache.httpcomponents")
                        .openStream();
            }
        });
        war.as(ZipExporter.class).exportTo(new File("target/test.war"));
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
