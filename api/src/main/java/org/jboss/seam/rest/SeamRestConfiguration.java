package org.jboss.seam.rest;

import java.util.HashSet;
import java.util.Set;

import org.jboss.seam.rest.exceptions.Mapping;
import org.jboss.seam.rest.templating.TemplatingProvider;

/**
 * This class is used by the config module for alternative setup.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public class SeamRestConfiguration {

    private Set<Mapping> mappings = new HashSet<Mapping>();
    private Class<? extends TemplatingProvider> preferedTemplatingProvider;

    public Set<Mapping> getMappings() {
        return mappings;
    }

    public void setMappings(Set<Mapping> Mappings) {
        this.mappings = Mappings;
    }

    public void addMapping(Mapping mapping) {
        this.mappings.add(mapping);
    }

    public Class<? extends TemplatingProvider> getPreferedTemplatingProvider() {
        return preferedTemplatingProvider;
    }

    public void setPreferedTemplatingProvider(Class<? extends TemplatingProvider> preferedTemplatingProvider) {
        this.preferedTemplatingProvider = preferedTemplatingProvider;
    }
}
