package org.jboss.seam.rest.test.client;

public class PingServiceImpl implements PingService {
    public String ping() {
        return "pong";
    }
}
