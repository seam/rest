package org.jboss.seam.rest.example.tasks.statistics;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.jboss.seam.rest.client.RestClient;
import org.jboss.seam.rest.example.tasks.statistics.entity.Category;
import org.jboss.seam.rest.example.tasks.statistics.entity.Task;

@RequestScoped
@Named("client")
public class SeamTasksAction
{
   private static final Logger log = Logger.getLogger(SeamTasksAction.class);
   
   @Inject
   @RestClient("http://localhost:8080/seam-tasks")
   private SeamTasksService service;
   @Inject
   private Event<Task> taskEvent;
   @Inject
   private Event<Category> categoryEvent;
   @Inject
   private Event<ReportResultEvent> resultEvent;
   private ReportResultEvent result = new ReportResultEvent();

   public String loadStatistics()
   {
      List<Category> categories;
      List<Task> tasksResponse;
      
      try
      {
         categories = service.getCategories(0, 0);
         tasksResponse = service.getTasks("all", 0, 0);
      }
      catch (Exception e)
      {
         log.warn(e.getMessage());
         return null; // faces messages are added by the ErrorInterceptor
      }

      for (Category category : categories)
      {
         categoryEvent.fire(category);
      }

      for (Task task : tasksResponse)
      {
         taskEvent.fire(task);
      }

      resultEvent.fire(result);
      return "result";
   }

   @Produces
   @Named("result")
   public List<Object> getResult()
   {
      return new ArrayList<Object>(result.getResults().entrySet());
   }
}
