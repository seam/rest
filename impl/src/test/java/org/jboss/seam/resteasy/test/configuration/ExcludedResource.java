package org.jboss.seam.resteasy.test.configuration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.seam.resteasy.configuration.SeamResteasyConfiguration;

/**
 * This resource is registered by auto-scanning and is later on excluded via {@link SeamResteasyConfiguration}
 * @author Jozef Hartinger
 *
 */
@Path("/excluded")
@Produces("text/plain")
public class ExcludedResource
{
   @GET
   public void echo()
   {
   }
}
