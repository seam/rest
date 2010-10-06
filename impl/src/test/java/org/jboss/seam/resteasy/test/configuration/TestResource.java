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
   @Path("exception1")
   public void exception1()
   {
      throw new EntityNotFoundException("Entity is gone.");
   }

   @GET
   @Path("exception2")
   public void exception2()
   {
      throw new NullPointerException("null");
   }

   @GET
   @Path("exception3")
   public void exception3()
   {
      throw new IllegalArgumentException();
   }

   @GET
   @Path("interpolatedException")
   @Produces({ "text/plain", "application/xml" })
   public void interpolatedException()
   {
      throw new UnsupportedOperationException();
   }

   @GET
   @Path("interpolatedExceptionSwitch")
   public void interpolatedExceptionSwitch()
   {
      throw new NoSuchMethodError();
   }
}
