package org.jboss.seam.rest.client;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessManagedBean;

import org.jboss.solder.logging.Logger;
import org.jboss.seam.rest.util.Utils;
import org.jboss.solder.reflection.AnnotationInspector;

/**
 * The Seam REST Client extension provides injection of
 * <ul>
 * <li>org.jboss.resteasy.client.ClientRequest instances</li>
 * <li>REST clients - proxied JAX-RS interfaces capable of invoking client requests</li>
 * </ul>
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public class RestClientExtension implements Extension {
    private static final Logger log = Logger.getLogger(RestClientExtension.class);
    private static final String RESTEASY_PROVIDER_FACTORY_NAME = "org.jboss.resteasy.spi.ResteasyProviderFactory";
    private static final String HTTP_CLIENT_NAME = "org.apache.http.client.HttpClient";
    private boolean enabled;

    private Set<Type> jaxrsInterfaces = new HashSet<Type>();
    private Bean<RestClientProducer> restClientProducerBean;

    public void registerExtension(@Observes BeforeBeanDiscovery event, BeanManager manager) {
        enabled = Utils.isClassAvailable(RESTEASY_PROVIDER_FACTORY_NAME);
        enabled = enabled && Utils.isClassAvailable(HTTP_CLIENT_NAME);
    }

    /**
     * Obtains the <code>Bean</code> instance for the <code>RestClientProducer</code> component. This instance is used later for
     * registering {@link RestClientProducer#produceRestClient} as a producer method.
     *
     * @param event
     */
    public void getRestClientProducerDelegate(@Observes ProcessManagedBean<RestClientProducer> event) {
        this.restClientProducerBean = event.getBean();
    }

    /**
     * Scans a Bean for the following injection points
     * <p/>
     * <code>
     *
     * @Inject @RestClient private T service; </code>
     * <p/>
     * where T is a JAX-RS annotated interface and builds a collection of these types.
     */
    public <T> void scanInjectionPointsForJaxrsInterfaces(@Observes ProcessBean<T> event, BeanManager manager) {
        if (!enabled) {
            return;
        }

        for (InjectionPoint ip : event.getBean().getInjectionPoints()) {
            RestClient qualifier = AnnotationInspector.getAnnotation(ip.getAnnotated(), RestClient.class, manager);

            if (qualifier != null) {
                if (ip.getType() instanceof Class<?>) {
                    Class<?> clazz = (Class<?>) ip.getType();

                    if (clazz.isInterface()) // we only support interfaces
                    {
                        jaxrsInterfaces.add(clazz);
                    }
                }
            }
        }
    }

    /**
     * Registers the RestClientProducer if there is an injection point that requires it
     */
    public void afterBeanDiscovery(@Observes AfterBeanDiscovery event, BeanManager manager) {
        if (enabled && !jaxrsInterfaces.isEmpty()) {
            if (restClientProducerBean == null) {
                log.warn("ProcessProducerMethod<RestClientProducer, Object> not fired. Client extension may not work properly.");
                return;
            }
            // register an additional RestClientProducer that supports all the interfaces
            event.addBean(new RestClientProducerBean(restClientProducerBean, jaxrsInterfaces, manager));
        }
    }

    public boolean isClientIntegrationEnabled() {
        return enabled;
    }
}
