package org.jboss.seam.rest.test.exceptions;

import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@RequestScoped
@Path("/exceptions")
public class Resource {

    @GET
    @Path("/iae")
    public void throwIae() throws Exception {
        throw new IllegalAccessException();
    }

    @GET
    @Path("/aioobe")
    public void throwAioobe() {
        throw new ArrayIndexOutOfBoundsException();
    }

    @GET
    @Path("/npe")
    public void throwNpe() {
        throw new NullPointerException();
    }

    @GET
    @Path("/e1")
    public void e1() {
        throw new Exception1(new Exception2());
    }

    @GET
    @Path("/e2")
    public void e2() {
        throw new Exception2(new Exception1());
    }

    @GET
    @Path("/ejb")
    public void ejb() {
        throw new EJBException(new Exception2());
    }

    @GET
    @Path("/uoe")
    public void throwUoe() {
        throw new UnsupportedOperationException();
    }

    @GET
    @Path("/nsme")
    public void throwNsme() {
        throw new NoSuchMethodError();
    }

    @GET
    @Path("/ie")
    public void throwIe() throws InstantiationException {
        throw new InstantiationException();
    }

    @GET
    @Path("/imse")
    public void throwImse() {
        throw new IllegalMonitorStateException();
    }

    @GET
    @Path("/itse")
    public void throwItse() {
        throw new IllegalThreadStateException();
    }

    @GET
    @Path("/nsfe")
    public void throwNsfe() {
        throw new NoSuchFieldError();
    }

    @GET
    @Path("/nfe")
    public void throwNfe() {
        throw new NumberFormatException("incorrect number format");
    }

    @GET
    @Path("/sioobe")
    public void throwSioobe() {
        throw new StringIndexOutOfBoundsException(
                "The quick #{fox.color} #{fox.count == 1 ? 'fox' : 'foxes'} jumps over the lazy dog");
    }
}
