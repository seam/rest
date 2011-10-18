package org.jboss.seam.rest.test;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.jboss.seam.rest.SeamRestStartup;
import org.jboss.seam.rest.exceptions.RestResource;

/**
 * Same as {@link SeamRestStartup} but does not have external dependencies.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@WebListener
public class MockStartup implements ServletContextListener {
    @Inject
    @RestResource
    private Event<ServletContext> event;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        event.fire(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
