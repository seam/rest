package org.jboss.seam.resteasy.test.configuration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.seam.resteasy.test.Student;

@Path("foo")
@Produces("text/plain")
public class TestResource
{
   @GET
   public String foo()
   {
      return "foo";
   }
   
   @GET
   @Produces("application/xml")
   public String xmlFoo()
   {
      return "<foo/>";
   }
   
   @GET
   @Path("student")
   @Produces("foo/bar")
   public Student bar()
   {
      return new Student("Jozef");
   }
   
   @GET
   @Path("notFound")
   public void notFound()
   {
      throw new EntityNotFoundException();
   }
}
