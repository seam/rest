package org.jboss.seam.rest.exceptions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.jboss.solder.logging.Logger;
import org.jboss.seam.rest.util.Utils;

public class ExceptionMappingExtension implements Extension {
    private static final Logger log = Logger.getLogger(ExceptionMappingExtension.class);
    private static final String SEAM_CATCH_NAME = "org.jboss.seam.exception.control.extension.CatchExtension";

    private Set<Mapping> exceptionMappings = new HashSet<Mapping>();
    private boolean catchIntegrationEnabled = false;

    public void scanForCatch(@Observes BeforeBeanDiscovery event, BeanManager manager) {
        catchIntegrationEnabled = Utils.isClassAvailable(SEAM_CATCH_NAME);
        if (catchIntegrationEnabled) {
            log.info("Catch integration enabled.");
        }
    }

    public <T> void findExceptionMappingDeclaration(@Observes ProcessAnnotatedType<T> event) {
        AnnotatedType<T> type = event.getAnnotatedType();

        ExceptionMapping.List mappings = type.getAnnotation(ExceptionMapping.List.class);
        if (mappings != null) {
            for (ExceptionMapping mapping : mappings.value()) {
                addExceptionMapping(mapping);
            }
        }

        // also, single @DeclarativeExceptionMapping can be used
        ExceptionMapping mapping = type.getAnnotation(ExceptionMapping.class);
        if (mapping != null) {
            addExceptionMapping(mapping);
        }
    }

    private void addExceptionMapping(ExceptionMapping mapping) {
        exceptionMappings.add(new Mapping(mapping.exceptionType(), mapping.status(), mapping.message(), mapping
                .useExceptionMessage(), mapping.interpolateMessage(), mapping.useJaxb()));
    }

    public Set<Mapping> getExceptionMappings() {
        return Collections.unmodifiableSet(exceptionMappings);
    }

    public boolean isCatchIntegrationEnabled() {
        return catchIntegrationEnabled;
    }
}
