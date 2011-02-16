/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.seam.rest.tasks.statistics.geo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB class used for parsing http://geonames.org XML response.
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 *
 */
@XmlRootElement(name = "geonames")
public class SearchResult
{
   private Integer resultCount;
   private List<Location> locations;

   @XmlElement(name = "totalResultsCount")
   public Integer getResultCount()
   {
      return resultCount;
   }

   public void setResultCount(Integer resultCount)
   {
      this.resultCount = resultCount;
   }

   @XmlElement(name = "code")
   public List<Location> getLocations()
   {
      return locations;
   }

   public void setLocations(List<Location> locations)
   {
      this.locations = locations;
   }
   
   public static class Location
   {
      private String postalCode;
      private String name;
      private String countryCode;
      private Double latitude;
      private Double longitude;

      @XmlElement(name = "postalcode")
      public String getPostalCode()
      {
         return postalCode;
      }

      public void setPostalCode(String postalCode)
      {
         this.postalCode = postalCode;
      }

      @XmlElement
      public String getName()
      {
         return name;
      }

      public void setName(String name)
      {
         this.name = name;
      }

      @XmlElement
      public String getCountryCode()
      {
         return countryCode;
      }

      public void setCountryCode(String countryCode)
      {
         this.countryCode = countryCode;
      }

      @XmlElement(name = "lat")
      public Double getLatitude()
      {
         return latitude;
      }

      public void setLatitude(Double latitude)
      {
         this.latitude = latitude;
      }

      @XmlElement(name = "lng")
      public Double getLongitude()
      {
         return longitude;
      }

      public void setLongitude(Double longitude)
      {
         this.longitude = longitude;
      }
   }
}
