package org.jboss.seam.rest.example.client.tasks.analyzer;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;

import org.jboss.seam.rest.example.client.tasks.Category;
import org.jboss.seam.rest.example.client.tasks.Task;
import org.jboss.seam.rest.example.client.tasks.spi.ReportResultEvent;

/**
 * This analyzer generates simple statistics such as number of categories, tasks, etc.
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 *
 */

@Singleton
public class SimpleAnalyzer
{
   private int resolvedTasks = 0;
   private int unresolvedTasks = 0;
   private int categories = 0;
   
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
      result.addResult("Tasks per category", String.valueOf(( (double) unresolvedTasks ) / categories));
   }
}
