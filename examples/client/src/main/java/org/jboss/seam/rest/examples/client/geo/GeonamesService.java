package org.jboss.seam.rest.examples.client.geo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * Very very simplified interface of <a
 * href="http://www.geonames.org/export/web-services.html">http://www.geonames.org/export/web-services.html#postalCodeSearch</a>
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@Produces("application/xml")
public interface GeonamesService {
    @Path("/postalCodeSearch")
    @GET
    SearchResult searchZip(@QueryParam("postalcode") String postalCode, @QueryParam("maxRows") int maxRows,
                           @QueryParam("username") String username);
}
