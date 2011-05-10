package org.jboss.seam.rest.test.util;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.rest.test.Fox;
import org.jboss.seam.rest.test.SeamRestClientTest;
import org.jboss.seam.rest.util.Interpolator;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class InterpolatorTest {
    @Inject
    private Interpolator interpolator;

    @Deployment
    public static WebArchive getDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibrary(getJar()).addAsLibrary(SeamRestClientTest.LIBRARY_SEAM_SOLDER);
    }

    public static JavaArchive getJar() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "test.jar");
        jar.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        jar.addClasses(Interpolator.class);
        jar.addClass(Fox.class);
        return jar;
    }

    @Test
    public void testStringInterpolation() {
        String template = "The quick #{fox.color} #{fox.count == 1 ? 'fox' : 'foxes'} jumps over the lazy dog";
        String expected = "The quick brown fox jumps over the lazy dog";
        assertEquals(expected, interpolator.interpolate(template));
    }
}
