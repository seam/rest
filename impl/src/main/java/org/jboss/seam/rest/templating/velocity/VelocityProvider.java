package org.jboss.seam.rest.templating.velocity;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.jboss.seam.rest.templating.ModelWrapper;
import org.jboss.seam.rest.templating.ResponseTemplate;
import org.jboss.seam.rest.templating.TemplatingModel;
import org.jboss.seam.rest.templating.TemplatingProvider;

/**
 * Renders response using Apache Velocity.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@ApplicationScoped
public class VelocityProvider implements TemplatingProvider {
    @Inject
    private TemplatingModel model;
    @Inject
    private BeanManager manager;

    // Instance is only needed because of a bug in GF

    public void init(ServletContext servletContext) {
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "servlet");
        properties.setProperty("servlet.resource.loader.class", "org.apache.velocity.tools.view.WebappResourceLoader");
        Velocity.setApplicationAttribute("javax.servlet.ServletContext", servletContext);
        Velocity.init(properties);
    }

    public void writeTo(Object o, ResponseTemplate annotation, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream os) throws IOException {
        ModelWrapper model = new ModelWrapper(this.model.getData(), manager);
        model.put(annotation.responseName(), o);

        Template template = null;
        template = Velocity.getTemplate(annotation.value());

        OutputStreamWriter writer = new OutputStreamWriter(os);
        template.merge(new VelocityContext(model), writer);
        writer.flush();
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}
