package org.jboss.seam.rest.example.tasks.statistics.analyzers;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;

import org.jboss.seam.rest.example.tasks.statistics.ReportResultEvent;
import org.jboss.seam.rest.example.tasks.statistics.entity.Category;
import org.jboss.seam.rest.example.tasks.statistics.entity.Task;

@RequestScoped
public class SimpleAnalyzer
{
   private long resolvedTasks = 0l;
   private long unresolvedTasks = 0l;
   private long categories = 0l;
   
   public void processTask(@Observes Task task)
   {
      if (task.isResolved())
      {
         resolvedTasks++;
      }
      else
      {
         unresolvedTasks++;
      }
   }
   
   public void processCategory(@Observes Category category)
   {
      categories++;
   }
   
   public void processResult(@Observes ReportResultEvent result)
   {
      result.addResult("Number of resolved tasks", String.valueOf(resolvedTasks));
      result.addResult("Number of unresolved tasks", String.valueOf(unresolvedTasks));
      result.addResult("Number of categories", String.valueOf(categories));
      result.addResult("Tasks per category", String.valueOf(categories));
   }
}
