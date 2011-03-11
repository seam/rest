package org.jboss.seam.rest.example.client.tasks.analyzer;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;

import org.jboss.seam.rest.example.client.tasks.Task;
import org.jboss.seam.rest.example.client.tasks.spi.ReportResultEvent;

/**
 * This analyzer identifies the oldest unresolved task.
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 *
 */
@Singleton
public class HistoryAnalyzer
{
   private Task oldestUnresolvedTask;

   @PostConstruct
   public void init()
   {
      // just in case there are no tasks
      oldestUnresolvedTask = new Task();
      oldestUnresolvedTask.setUpdated(new Date());
      oldestUnresolvedTask.setName("No tasks");
   }
   
   public void processTask(@Observes Task task)
   {
      if (!task.isResolved() && task.getUpdated().before(oldestUnresolvedTask.getUpdated()))
      {
         oldestUnresolvedTask = task;
      }
   }
   
   public void reportResult(@Observes ReportResultEvent result)
   {
      result.addResult("The oldest unresolved task:", oldestUnresolvedTask.getName());
   }
}
