package org.jboss.seam.rest.templating;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.AmbiguousResolutionException;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.UnsatisfiedResolutionException;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.jboss.solder.logging.Logger;
import org.jboss.seam.rest.SeamRestConfiguration;
import org.jboss.seam.rest.exceptions.RestResource;

/**
 * TemplatingMessageBodyWriter is enabled for every JAX-RS method annotated with <code>@ResponseTemplate</code> annotation and
 * delegates response production to the {@link TemplatingProvider}.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@Provider
@ApplicationScoped
public class TemplatingMessageBodyWriter implements MessageBodyWriter<Object> {
    private static final Logger log = Logger.getLogger(TemplatingMessageBodyWriter.class);
    private TemplatingProvider provider;

    /**
     * Initializes TemplatingMessageBodyWriter. <code>TemplatingProvider</code> is selected.
     *
     * @throws UnsatisfiedResolutionException if the preferred <code>TemplatingProvider</code> is selected but is not available
     *                                        for injection.
     * @throws AmbiguousResolutionException   if there are multiple <code>TemplatingProviders<code> available.
     */
    @Inject
    public void init(Instance<TemplatingProvider> providerInstance, Instance<SeamRestConfiguration> configuration) {
        log.debugv("Initializing {0}", getClass().getSimpleName());

        Instance<? extends TemplatingProvider> instance = providerInstance;
        Class<? extends TemplatingProvider> preferedTemplatingProvider = null;

        // because of SEAMREST-15, we need to check if SeamRestConfiguration is available
        if (!configuration.isAmbiguous() && !configuration.isUnsatisfied()) {
            preferedTemplatingProvider = configuration.get().getPreferedTemplatingProvider();
        }
        if (preferedTemplatingProvider != null) {
            log.debugv("Prefered templating provider specified. Selecting {0}", preferedTemplatingProvider.getName());
            instance = providerInstance.select(preferedTemplatingProvider);
        }

        // no templating engines found
        if (instance.isUnsatisfied()) {
            if (preferedTemplatingProvider == null) {
                log.debug("No TemplateProvider found. Templating support disabled.");
                return;
            } else {
                throw new UnsatisfiedResolutionException("Unable to load prefered TemplateProvider "
                        + preferedTemplatingProvider.getName());
            }
        }

        // multiple templating engines found
        if (instance.isAmbiguous()) {
            throw new AmbiguousResolutionException("Multiple TemplatingProviders found on classpath. Select the prefered one.");
        }

        provider = instance.get();
    }

    /**
     * Initializes the <code>TemplatingProvider</code> if available.
     */
    public void init(@Observes @RestResource ServletContext context) {
        if (provider != null) {
            log.debugv("Initializing templating provider.");
            provider.init(context);
        }
    }

    /**
     * Returns true if and only if the templating extension is enabled and the method contains the
     * <code>@ResponseTemplate</code> annotation.
     */
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        if (provider == null) {
            return false;
        }
        ResponseTemplate annotation = findAnnotation(annotations, mediaType);
        return annotation != null;
    }

    public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        ResponseTemplate annotation = findAnnotation(annotations, mediaType);
        provider.writeTo(t, annotation, annotations, mediaType, httpHeaders, entityStream);
    }

    private ResponseTemplate findAnnotation(Annotation[] annotations, MediaType type) {
        for (Annotation a : annotations) {
            /*
             * multiple @ResponseTemplate annotations
             *
             * @ResponseTemplate.List({
             *
             * @ResponseTemplate(...)
             *
             * @ResponseTemplate(...) })
             */
            if (ResponseTemplate.List.class.isAssignableFrom(a.annotationType())) {
                ResponseTemplate.List list = (ResponseTemplate.List) a;
                return findAnnotation(list.value(), type);
            }
            /*
             * single @ResponseTemplate
             */
            if (ResponseTemplate.class.isAssignableFrom(a.annotationType())) {
                ResponseTemplate annotation = (ResponseTemplate) a;
                MediaType producedType = MediaType.valueOf(annotation.produces());
                if (producedType.isCompatible(type)) {
                    return annotation;
                }
            }
        }
        return null;
    }

    public TemplatingProvider getProvider() {
        return provider;
    }
}
