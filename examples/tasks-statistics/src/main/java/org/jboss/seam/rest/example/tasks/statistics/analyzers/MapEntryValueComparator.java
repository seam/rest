package org.jboss.seam.rest.example.tasks.statistics.analyzers;

import java.util.Comparator;
import java.util.Map.Entry;

/**
 * Compares Map entries based on their values in ascending order.
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 *
 */
public class MapEntryValueComparator<K, V extends Comparable<V>> implements Comparator<Entry<K, V>>
{
   public int compare(Entry<K, V> o1, Entry<K, V> o2)
   {
      return o2.getValue().compareTo(o1.getValue());
   }
}
