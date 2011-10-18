package org.jboss.seam.rest.test.templating;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.seam.rest.templating.ResponseTemplate;
import org.jboss.seam.rest.test.Student;

@Path("/velocity")
public class VelocityResource {
    @Path("hello")
    @GET
    @Produces("text/student")
    @ResponseTemplate("/hello.vm")
    public Student hello() {
        return new Student("Jozef Hartinger");
    }

    @Path("students")
    @GET
    @Produces("application/university+xml")
    @ResponseTemplate("/university.vm")
    public Student students() {
        return new Student("Jozef Hartinger");
    }
    
    @Path("string")
    @GET
    @Produces("text/plain")
    @ResponseTemplate("/string.vm")
    public String string()
    {
        return "Jozef";
    }
}
