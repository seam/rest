package org.jboss.seam.resteasy.test;

import javax.inject.Named;

@Named
public class Fox
{
   public int getCount()
   {
      return 1;
   }
   
   public String getColor()
   {
      return "brown";
   }
}
