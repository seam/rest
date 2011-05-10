package org.jboss.seam.rest.test.templating.multiple;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.jboss.seam.rest.templating.ResponseTemplate;
import org.jboss.seam.rest.templating.TemplatingProvider;

@ApplicationScoped
public class MockTemplatingProvider implements TemplatingProvider {
    public void init(ServletContext servletContext) {
        // noop
    }

    public void writeTo(Object o, ResponseTemplate annotation, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream os) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
        writer.write("Hello world!");
        writer.flush();
    }
}
