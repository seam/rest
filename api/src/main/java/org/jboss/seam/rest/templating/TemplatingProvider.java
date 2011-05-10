package org.jboss.seam.rest.templating;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;

import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Implementations of this interface represent integration points for various templating engines.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public interface TemplatingProvider {
    /**
     * Initialization callback. The method is guaranteed to be invoked before calling the writeTo() method.
     *
     * @param servletContext
     */
    void init(ServletContext servletContext);

    /**
     * Produces the response.
     *
     * @param result      object returned by the JAX-RS method
     * @param annotation  @ResponseTemplate annotation instance
     * @param annotations annotations available on the JAX-RS method
     * @param mediaType   requested media type
     * @param httpHeaders request HTTP headers
     * @param os          HTTP response output stream
     * @throws IOException
     */
    void writeTo(Object result, ResponseTemplate annotation, Annotation[] annotations, MediaType mediaType,
                 MultivaluedMap<String, Object> httpHeaders, OutputStream os) throws IOException;
}
