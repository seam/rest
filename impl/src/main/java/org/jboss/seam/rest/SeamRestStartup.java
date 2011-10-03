package org.jboss.seam.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.jboss.solder.logging.Logger;
import org.jboss.seam.rest.client.RestClientExtension;
import org.jboss.seam.rest.exceptions.ExceptionMappingExtension;
import org.jboss.seam.rest.exceptions.RestResource;
import org.jboss.seam.rest.exceptions.SeamExceptionMapper;
import org.jboss.seam.rest.templating.TemplatingMessageBodyWriter;

/**
 * TODO: This listener will be replaced with Seam Servlet. Do not observe the event fired by this listener as it will be removed
 * in future releases.
 *
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 */
@ApplicationScoped
public class SeamRestStartup {
    
    private static final Logger log = Logger.getLogger(SeamRestStartup.class);
    
    @Inject
    private RestClientExtension restClientExtension;
    @Inject
    private ExceptionMappingExtension exceptionMappingExtension;
    @Inject
    private SeamExceptionMapper exceptionMapper;
    @Inject
    private TemplatingMessageBodyWriter templating;

    @Inject
    @RestResource
    private Event<ServletContext> event;
    
    private boolean initialized = false;

    public synchronized void init(ServletContext sc)
    {
        if (initialized)
        {
            return;
        }
        
        event.fire(sc);

        log.infov(
                "Seam REST {0} (Client integration: {1}, Catch integration: {2}, {3} exception mapping rules, Templating provider: {4})",
                this.getClass().getPackage().getSpecificationVersion(),
                restClientExtension.isClientIntegrationEnabled() ? "enabled" : "disabled", exceptionMappingExtension
                        .isCatchIntegrationEnabled() ? "enabled" : "disabled", exceptionMapper.getMappings().size(), templating
                        .getProvider() == null ? "null" : templating.getProvider().toString());
        initialized = true;
    }
}
