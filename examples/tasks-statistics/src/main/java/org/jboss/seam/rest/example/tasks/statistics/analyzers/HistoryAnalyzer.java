package org.jboss.seam.rest.example.tasks.statistics.analyzers;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;

import org.jboss.seam.rest.example.tasks.statistics.ReportResultEvent;
import org.jboss.seam.rest.example.tasks.statistics.entity.Task;

@RequestScoped
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
