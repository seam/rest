package org.jboss.seam.rest.example.tasks.statistics;

import java.util.HashMap;
import java.util.Map;

public class ReportResultEvent
{
   private Map<String, String> results = new HashMap<String, String>();

   public Map<String, String> getResults()
   {
      return results;
   }

   public void addResult(String key, String value)
   {
      results.put(key, value);
   }
}
