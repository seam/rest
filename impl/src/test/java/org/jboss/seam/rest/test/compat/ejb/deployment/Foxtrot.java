package org.jboss.seam.rest.test.compat.ejb.deployment;

import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;

@Singleton
@ApplicationScoped
public class Foxtrot {
    public boolean ping() {
        return true;
    }
}
