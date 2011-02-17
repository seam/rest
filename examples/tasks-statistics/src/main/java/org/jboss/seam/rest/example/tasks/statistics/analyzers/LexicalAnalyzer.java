package org.jboss.seam.rest.example.tasks.statistics.analyzers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;

import org.jboss.seam.rest.example.tasks.statistics.ReportResultEvent;
import org.jboss.seam.rest.example.tasks.statistics.entity.Task;

@RequestScoped
public class LexicalAnalyzer
{
   private Map<String, Long> words = new HashMap<String, Long>();
   
   public void processTask(@Observes Task task)
   {
      for (String word : task.getName().split(" "))
      {
         Long occurence = words.get(word);
         if (occurence == null)
         {
            words.put(word, 1l);
         }
         else
         {
            words.put(word, ++occurence);
         }
      }
   }
   
   public void reportResult(@Observes ReportResultEvent result)
   {
      List<Map.Entry<String, Long>> wordChart = new ArrayList<Map.Entry<String, Long>>(words.entrySet());
      Collections.sort(wordChart, new MapEntryValueComparator<String, Long>());
      result.addResult("Most often used word:", wordChart.get(0).getKey());
   }
}
