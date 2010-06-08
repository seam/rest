package org.jboss.seam.resteasy.test.configuration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
   @Path("bar")
   @Produces("foo/bar")
   public String bar()
   {
      return "bar";
   }
   
   @GET
   @Path("notFound")
   public void notFound()
   {
      throw new EntityNotFoundException();
   }
}
