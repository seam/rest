package org.jboss.seam.rest.templating;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;

/**
 * Holds objects used for rendering. Populate the underlying data map with objects to be accessible within a template.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@RequestScoped
public class TemplatingModel {
    private Map<String, Object> data = new HashMap<String, Object>();

    /**
     * Returns the underlying map of objects. The objects can be referenced within a template by their names (map keys).
     *
     * @return
     */
    public Map<String, Object> getData() {
        return data;
    }
}
