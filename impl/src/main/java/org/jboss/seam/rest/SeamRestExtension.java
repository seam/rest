package org.jboss.seam.rest;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

/**
 * Currently just vetoes {@link SeamRestConfiguration}.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public class SeamRestExtension implements Extension {
    /**
     * Veto SeamRestConfiguration class from bean discovery since we want it's subclasses to be beans, not the class itself.
     */
    public void vetoSeamRestConfiguration(@Observes ProcessAnnotatedType<SeamRestConfiguration> event) {
        if (event.getAnnotatedType().getJavaClass().equals(SeamRestConfiguration.class)) {
            event.veto();
        }
    }

}
