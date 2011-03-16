package org.jboss.seam.rest.test.client;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

@SuppressWarnings("unused")
public class ClientConfiguration {
    @Produces
    @Named("service.host")
    private final String host = "example.com";
    @Produces
    @Named("service.context.path")
    private final String contextPath = "service";
}
