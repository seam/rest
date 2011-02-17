package org.jboss.seam.rest.test.templating;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jboss.seam.rest.templating.TemplatingMessageBodyWriter;

@ApplicationPath("/*")
public class MyApplication extends Application
{

   @Override
   public Set<Class<?>> getClasses()
   {
      Set<Class<?>> classes = new HashSet<Class<?>>();
      classes.add(FreeMarkerResource.class);
      classes.add(VelocityResource.class);
      classes.add(TemplatingMessageBodyWriter.class);
      return classes;
   }

}
