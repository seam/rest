package org.jboss.seam.rest.examples.client.tasks;

import java.util.List;
import java.util.Map;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.solder.logging.Logger;
import org.jboss.seam.rest.client.RestClient;
import org.jboss.seam.rest.examples.client.ConnectionException;
import org.jboss.seam.rest.examples.client.tasks.spi.ReportResultEvent;

/**
 * Loads Seam Tasks's Categories and Tasks and fires an event for each of these objects for analyzers to be able to process
 * statistics.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */

@Singleton
public class SeamTasksAction {
    private static final Logger log = Logger.getLogger(SeamTasksAction.class);

    @Inject
    @RestClient("http://localhost:8080/rest-tasks")
    private SeamTasksService service;
    @Inject
    private BeanManager manager;

    private ReportResultEvent reportResultEvent = new ReportResultEvent();

    public void loadStatistics() {
        reportResultEvent.clear();

        List<Category> categories;
        List<Task> tasksResponse;

        try {
            categories = service.getCategories(0, 0);
            tasksResponse = service.getTasks("all", 0, 0);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ConnectionException(e);
        }

        for (Category category : categories) {
            manager.fireEvent(category);
        }

        for (Task task : tasksResponse) {
            manager.fireEvent(task);
        }

        manager.fireEvent(reportResultEvent);
    }

    public String[][] getResultArray() {
        Map<String, String> result = reportResultEvent.getResults();
        String[][] resultArray = new String[result.size()][2];

        int i = 0;
        for (Map.Entry<String, String> entry : result.entrySet()) {
            resultArray[i] = new String[]{entry.getKey(), entry.getValue()};
        }

        return resultArray;
    }

    public Map<String, String> getResult() {
        return reportResultEvent.getResults();
    }
}
