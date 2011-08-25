package org.jboss.seam.rest.test.templating;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.seam.rest.templating.ResponseTemplate;
import org.jboss.seam.rest.test.Student;

@Path("/freemarker")
public class FreeMarkerResource {
    @Path("hello")
    @GET
    @Produces("text/student")
    @ResponseTemplate("/hello.ftl")
    public Student hello() {
        return new Student("Jozef Hartinger");
    }

    @Path("students")
    @GET
    @Produces("application/university+xml")
    @ResponseTemplate("/university.ftl")
    public Student students() {
        return new Student("Jozef Hartinger");
    }

    @Path("greeting")
    @GET
    @Produces({"text/greeting-informal", "text/greeting-formal"})
    @ResponseTemplate.List({@ResponseTemplate(value = "/informal.ftl", produces = "text/greeting-informal"),
            @ResponseTemplate(value = "/formal.ftl", produces = "text/greeting-formal")})
    public Student greeting() {
        return new Student("Jozef Hartinger");
    }
    
    @Path("string")
    @GET
    @Produces("text/plain")
    @ResponseTemplate("/string.ftl")
    public String string()
    {
        return "Jozef";
    }

}
