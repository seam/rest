package org.jboss.seam.resteasy.test.util;

import static org.testng.Assert.assertEquals;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.seam.resteasy.test.Fox;
import org.jboss.seam.resteasy.util.Interpolator;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.weld.extensions.el.Expressions;
import org.testng.annotations.Test;

public class InterpolatorTest extends Arquillian
{
   @Inject
   private Interpolator interpolator;
   
   @Deployment
   public static JavaArchive createDeployment()
   {
      JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "test.jar");
      jar.addManifestResource("META-INF/beans.xml", ArchivePaths.create("beans.xml"));
      jar.addPackage(Interpolator.class.getPackage());
      jar.addPackage(Expressions.class.getPackage());
      jar.addClass(Fox.class);
      return jar;
   }
   
   @Test
   public void testStringInterpolation()
   {
      String template = "The quick #{fox.color} #{fox.count == 1 ? 'fox' : 'foxes'} jumps over the lazy dog";
      String expected = "The quick brown fox jumps over the lazy dog";
      assertEquals(interpolator.interpolate(template), expected);
   }
}
