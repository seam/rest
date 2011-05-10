package org.jboss.seam.rest.examples.client.tasks.spi;

import java.util.HashMap;
import java.util.Map;

/**
 * Observe the ReportResultEvent to report results of the statistics calculation.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public class ReportResultEvent {
    private Map<String, String> results = new HashMap<String, String>();

    public void clear() {
        results.clear();
    }

    public Map<String, String> getResults() {
        return results;
    }

    public void addResult(String key, String value) {
        results.put(key, value);
    }
}
