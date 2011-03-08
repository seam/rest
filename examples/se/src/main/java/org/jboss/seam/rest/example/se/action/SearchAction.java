package org.jboss.seam.rest.example.se.action;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.seam.rest.client.RestClient;
import org.jboss.seam.rest.example.se.ConnectionException;
import org.jboss.seam.rest.example.se.StatusException;
import org.jboss.seam.rest.example.se.model.SearchResult;

@Singleton
public class SearchAction
{
   @Inject
   @RestClient("http://api.geonames.org")
   private GeonamesService api;
   
   private static final String ZIP_PATTERN = "^\\d{5}([\\-]\\d{4})?$"; 
   private SearchResult locations = new SearchResult();

   public void search(String query) throws ConnectionException
   {
      if (!query.matches(ZIP_PATTERN))
      {
         throw new IllegalArgumentException();
      }
      
      try
      {
         locations = api.searchZip(query, 20, "demo");
         
         if (locations.getStatus() != null)
         {
            throw new StatusException(locations.getStatus().getMessage());
         }
      }
      catch (Throwable e)
      {
         e.printStackTrace();
         throw new ConnectionException(e);
      }
   }

   public SearchResult getLocations()
   {
      return locations;
   }
}
