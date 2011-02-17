package org.jboss.seam.rest.example.tasks.statistics;

public class Table
{
   private final String header;
   private final Object[][] values;

   public Table(String header, Object[][] values)
   {
      this.header = header;
      this.values = values;
   }

   public String getHeader()
   {
      return header;
   }

   public Object[][] getValues()
   {
      return values;
   }
}
