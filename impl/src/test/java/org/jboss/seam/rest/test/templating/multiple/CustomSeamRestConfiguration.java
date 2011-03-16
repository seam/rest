package org.jboss.seam.rest.test.templating.multiple;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Specializes;

import org.jboss.seam.rest.SeamRestConfiguration;

@Specializes
public class CustomSeamRestConfiguration extends SeamRestConfiguration {
    @PostConstruct
    public void init() {
        setPreferedTemplatingProvider(MockTemplatingProvider.class);
    }
}
