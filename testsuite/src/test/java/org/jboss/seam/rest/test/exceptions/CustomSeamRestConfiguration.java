package org.jboss.seam.rest.test.exceptions;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Specializes;

import org.jboss.seam.rest.SeamRestConfiguration;
import org.jboss.seam.rest.exceptions.Mapping;

@Specializes
public class CustomSeamRestConfiguration extends SeamRestConfiguration {

    @PostConstruct
    public void setup() {
        addMapping(new Mapping(IllegalAccessException.class, 410));
        addMapping(new Mapping(ArrayIndexOutOfBoundsException.class, 411));
        addMapping(new Mapping(NullPointerException.class, 412, "Null reference.", false, false, false));
        addMapping(new Mapping(UnsupportedOperationException.class, 413,
                "The quick #{fox.color} #{fox.count == 1 ? 'fox' : 'foxes'} jumps over the lazy dog", false, true, false));
        addMapping(new Mapping(NoSuchMethodError.class, 414,
                "The quick #{fox.color} #{fox.count == 1 ? 'fox' : 'foxes'} jumps over the lazy dog", false, false, false));
        addMapping(new Mapping(Exception2.class, 400));
    }
}
