package org.jboss.seam.rest.examples.client.tasks.analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;

import org.jboss.seam.rest.examples.client.tasks.Task;
import org.jboss.seam.rest.examples.client.tasks.spi.ReportResultEvent;

/**
 * This analyzer identifies the most often used word.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */

@Singleton
public class LexicalAnalyzer {
    private Map<String, Long> words = new HashMap<String, Long>();

    public void processTask(@Observes Task task) {
        for (String word : task.getName().split(" ")) {
            Long occurence = words.get(word);
            if (occurence == null) {
                words.put(word, 1l);
            } else {
                words.put(word, ++occurence);
            }
        }
    }

    public void reportResult(@Observes ReportResultEvent result) {
        List<Map.Entry<String, Long>> wordChart = new ArrayList<Map.Entry<String, Long>>(words.entrySet());
        Collections.sort(wordChart, new MapEntryValueComparator<String, Long>());
        if (wordChart.isEmpty())
        {
            result.addResult("Most often used word:", "there are not tasks");
        }
        else
        {
            result.addResult("Most often used word:", wordChart.get(0).getKey());
        }
        words.clear();
    }

    /**
     * Compares Map entries based on their values in ascending order.
     *
     * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
     */
    public class MapEntryValueComparator<K, V extends Comparable<V>> implements Comparator<Entry<K, V>> {
        public int compare(Entry<K, V> o1, Entry<K, V> o2) {
            return o2.getValue().compareTo(o1.getValue());
        }
    }
}
