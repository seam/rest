package org.jboss.seam.rest.examples.client.tasks.analyzer;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;

import org.jboss.seam.rest.examples.client.tasks.Task;
import org.jboss.seam.rest.examples.client.tasks.spi.ReportResultEvent;

/**
 * This analyzer identifies the oldest unresolved task.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@Singleton
public class HistoryAnalyzer {
    private Task oldestUnresolvedTask;

    public void processTask(@Observes Task task) {
        if (!task.isResolved() && (oldestUnresolvedTask == null || task.getUpdated().before(oldestUnresolvedTask.getUpdated()))) {
            oldestUnresolvedTask = task;
        }
    }

    public void reportResult(@Observes ReportResultEvent result) {
        if (oldestUnresolvedTask == null)
        {
            result.addResult("The oldest unresolved task:", "there are not tasks");
        }
        else
        {
            result.addResult("The oldest unresolved task:", oldestUnresolvedTask.getName());
        }
        oldestUnresolvedTask = null;
    }
}
