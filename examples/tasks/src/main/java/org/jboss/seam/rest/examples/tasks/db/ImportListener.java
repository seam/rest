package org.jboss.seam.rest.examples.tasks.db;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * This listener replaces the import.sql script - it feeds the database with sample data
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public class ImportListener implements ServletContextListener {
    @Inject
    private ImportBean bean;

    public void contextInitialized(ServletContextEvent sce) {
        bean.clearDatabase();
        bean.feedDatabase();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        bean.clearDatabase();
    }
}
