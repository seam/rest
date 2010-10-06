package org.jboss.seam.resteasy.test.configuration;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("ejb")
@Stateless
@Default
public class EjbResource
{
   @GET
   @Path("/ping")
   public void ping()
   {
      throw new NullPointerException("ping");
   }

   @GET
   @Path("pong")
   @TestInterceptorBinding
   public void pong()
   {
      throw new NullPointerException("pong");
   }
}
