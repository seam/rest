package org.jboss.seam.rest.example.tasks.statistics.geo;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.jboss.seam.rest.client.RestClient;

@Named
@RequestScoped
public class SearchAction
{
   private static final Logger log = Logger.getLogger(SearchAction.class);
   
   @Inject
   @RestClient("http://api.geonames.org")
   private GeonamesService api;

   private String query;
   private SearchResult locations;

   public String search()
   {
      try
      {
         locations = api.searchZip(query, 20, "demo");
      }
      catch (Throwable e)
      {
         log.warn(e.getMessage());
         // stay on the same page - a faces message is added by ErrorInterceptor
         return null;
      }
      return "search";
   }

   public String getQuery()
   {
      return query;
   }

   public void setQuery(String query)
   {
      this.query = query;
   }

   @Produces
   @RequestScoped
   @Named
   public SearchResult getLocations()
   {
      return locations;
   }

}
