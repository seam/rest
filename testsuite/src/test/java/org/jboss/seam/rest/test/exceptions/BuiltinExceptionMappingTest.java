package org.jboss.seam.rest.test.exceptions;

import javax.enterprise.inject.spi.Extension;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.seam.rest.exceptions.ExceptionMappingExtension;
import org.jboss.seam.rest.exceptions.SeamExceptionMapper;
import org.jboss.seam.rest.exceptions.integration.CatchExceptionMapper;
import org.jboss.seam.rest.test.Dependencies;
import org.jboss.seam.rest.test.Fox;
import org.jboss.seam.rest.test.SeamRestClientTest;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;

public class BuiltinExceptionMappingTest extends SeamRestClientTest {
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
        war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        war.setWebXML("WEB-INF/web.xml");
        war.addClasses(Resource.class, Fox.class, MoreSpecificExceptionMapper.class, MoreSpecificExceptionHandler.class,
                MyApplication.class);
        war.addClasses(Exception1.class, Exception2.class);
        war.addAsLibraries(Dependencies.SEAM_SOLDER);
        war.addAsLibraries(getSeamRest());
        return war;
    }

    public static JavaArchive getSeamRest() {
        JavaArchive jar = SeamRestClientTest.createSeamRest();
        jar.addClass(CustomSeamRestConfiguration.class);
        jar.addClasses(SeamExceptionMapper.class, CatchExceptionMapper.class);
        jar.addAsServiceProvider(Extension.class, ExceptionMappingExtension.class);
        return jar;
    }

    @Test
    public void testException() throws Exception {
        test("http://localhost:8080/test/exceptions/iae", 410, null);
    }

    @Test
    public void testSpecializedExceptionMapperGetsCalled() throws Exception {
        test("http://localhost:8080/test/exceptions/aioobe", 421, null);
    }

    @Test
    public void testRuntimeException() throws Exception {
        test("http://localhost:8080/test/exceptions/npe", 412, "Null reference.");
    }

    @Test
    public void testExceptionUnwrapping() throws Exception {
        test("http://localhost:8080/test/exceptions/e1", 400, null);
    }

    @Test
    public void testExceptionUnwrapping2() throws Exception {
        test("http://localhost:8080/test/exceptions/e2", 400, null);
    }

    @Test
    public void testExceptionUnwrappingEjb() throws Exception {
        test("http://localhost:8080/test/exceptions/ejb", 400, null);
    }

    @Test
    public void testErrorMessageInterpolation() throws Exception {
        test("http://localhost:8080/test/exceptions/uoe", 413, "The quick brown fox jumps over the lazy dog");
    }

    @Test
    public void testErrorMessageInterpolationSwitch() throws Exception {
        test("http://localhost:8080/test/exceptions/nsme", 414,
                "The quick #{fox.color} #{fox.count == 1 ? 'fox' : 'foxes'} jumps over the lazy dog");
    }

    @Test
    public void testUnhandledExceptionRethrown() throws Exception {
        test("http://localhost:8080/test/exceptions/imse", 500, null);
    }

    @Test
    public void testAnnotationConfiguredMapping1() throws Exception {
        test("http://localhost:8080/test/exceptions/itse", 415, null);
    }

    @Test
    public void testAnnotationConfiguredMapping2() throws Exception {
        test("http://localhost:8080/test/exceptions/nsfe", 416, "NoSuchField");
    }

    @Test
    public void testAnnotationConfiguredMapping3() throws Exception {
        test("http://localhost:8080/test/exceptions/nfe", 417, "incorrect number format");
    }

    @Test
    public void testAnnotationConfiguredMapping4() throws Exception {
        test("http://localhost:8080/test/exceptions/sioobe", 418, "The quick brown fox jumps over the lazy dog");
    }
}
