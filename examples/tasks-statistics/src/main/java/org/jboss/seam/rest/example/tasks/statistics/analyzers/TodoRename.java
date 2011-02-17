package org.jboss.seam.rest.example.tasks.statistics.analyzers;

public class TodoRename<K> implements Comparable<TodoRename<K>>
{
   private final K value;
   private Integer occurences = 1;
   
   public TodoRename(K value)
   {
      this.value = value;
   }

   public K getValue()
   {
      return value;
   }

   public Integer getOccurences()
   {
      return occurences;
   }

   public void inc()
   {
      occurences++;
   }

   public int compareTo(TodoRename<K> o)
   {
      return occurences.compareTo(o.getOccurences());
   }

   @Override
   public int hashCode()
   {
      return value.hashCode();
   }

   @Override
   public boolean equals(Object obj)
   {
      return value.equals(obj);
   }
}
