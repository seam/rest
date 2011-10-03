package org.jboss.seam.rest;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;

import org.jboss.solder.logging.Logger;

/**
 * We also extend HttpServlet so that Seam REST can be bootstrapped by eagerly-loaded Servlet on Tomcat 7, where 
 * weld-servlet does not support CDI injection into Servlet Listeners.
 * 
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 *
 */
@WebListener
public class SeamRestStartupListener extends HttpServlet implements ServletContextListener {

    private static final Logger log = Logger.getLogger(SeamRestStartupListener.class);

    private static final long serialVersionUID = -3618026329655575903L;

    @Inject
    private SeamRestStartup startup;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        startup.init(config.getServletContext());
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (startup == null) {
            log.warn("Listener injection does not work. You are probably running within a Servlet container. Make sure to configure Seam REST to use Servlet bootstrap instead.");
            return;
        }
        startup.init(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
