package org.jboss.seam.rest.examples.exceptions.resource;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.jboss.solder.reflection.Reflections;

@Path("exceptions")
@Produces("application/xml")
public class ExceptionResource {
    @GET
    public void throwException(@QueryParam("exception") @DefaultValue("java.lang.Exception") String exceptionType)
            throws Throwable {
        Object object = Reflections.classForName(exceptionType).newInstance();

        if (!(object instanceof Exception)) {
            throw new RuntimeException(exceptionType + " is not an exception");
        }

        throw (Throwable) object;
    }
}
