package org.jboss.seam.rest.templating.freemarker;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.jboss.seam.rest.templating.ModelWrapper;
import org.jboss.seam.rest.templating.ResponseTemplate;
import org.jboss.seam.rest.templating.TemplatingModel;
import org.jboss.seam.rest.templating.TemplatingProvider;

/**
 * Converts the response object to a rendered FreeMarker template.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@ApplicationScoped
public class FreeMarkerProvider implements TemplatingProvider {
    private Configuration configuration;
    @Inject
    private TemplatingModel model;
    @Inject
    private BeanManager manager;

    public void init(ServletContext servletContext) {
        configuration = new Configuration();
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        configuration.setServletContextForTemplateLoading(servletContext, "/");
    }

    public void writeTo(Object o, ResponseTemplate annotation, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream os) throws IOException {
        Template template = configuration.getTemplate(annotation.value());

        model.getData().put(annotation.responseName(), o);

        try {
            OutputStreamWriter writer = new OutputStreamWriter(os);
            template.process(new ModelWrapper(model.getData(), manager), writer);
            writer.flush();
        } catch (TemplateException e) {
            throw new RuntimeException("Unable to write FreeMarker response.", e);
        }
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}
