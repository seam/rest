package org.jboss.seam.rest.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.solder.logging.Logger;
import org.jboss.seam.rest.SeamRestConfiguration;
import org.jboss.seam.rest.validation.ValidationException;
import org.jboss.seam.rest.validation.ValidationExceptionHandler;
import org.jboss.solder.el.Expressions;

/**
 * This {@link ExceptionMapper} implementation converts caught exceptions to HTTP responses based on exception mapping rules.
 * <p/>
 * <p>
 * If there is no matching rule for an exception, the exception is rethrown wrapped within {@link UnhandledException}. Note that
 * this implementation is replaced by CatchExceptionMapper in environments where Seam Catch is available.
 * </p>
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 * @see ExceptionMappingConfiguration
 */
@Provider
@ApplicationScoped
public class SeamExceptionMapper implements ExceptionMapper<Throwable> {
    @Inject
    @RestResource
    private ResponseBuilder responseBuilder;
    @Inject
    @RestResource
    private Instance<Response> response;
    @Inject
    private Expressions expressions;
    @Inject
    private ValidationExceptionHandler validationExceptionHandler;

    private Map<Class<? extends Throwable>, Mapping> mappings = new HashMap<Class<? extends Throwable>, Mapping>();

    private static final Logger log = Logger.getLogger(SeamExceptionMapper.class);

    /**
     * Mappings are stored in a Map so that we can find them by the exception type.
     */
    @Inject
    public void init(Instance<SeamRestConfiguration> configuration, ExceptionMappingExtension extension) {
        log.info("Processing exception mapping configuration.");

        // XML-configured mappings
        if (!configuration.isAmbiguous() && !configuration.isUnsatisfied()) {
            Set<Mapping> exceptionMappings = configuration.get().getMappings();
            for (Mapping mapping : exceptionMappings) {
                addExceptionMapping(mapping);
            }
        }
        // annotation-configured mappings
        for (Mapping mapping : extension.getExceptionMappings()) {
            addExceptionMapping(mapping);
        }
    }

    protected void addExceptionMapping(Mapping mapping) {
        this.mappings.put(mapping.getExceptionType(), mapping);
        log.infov("Registered {0}", mapping);
    }

    /**
     * This observer method triggers {@link #init(ExceptionMappingConfiguration)} on bootstrap.
     */
    public void init(@Observes @RestResource ServletContext ctx) {
    }

    public Response toResponse(Throwable e) {
        log.debugv("Handling {0}", e.getClass());

        Throwable exception = e;

        while (exception != null) // iterate over cause chain
        {
            Class<? extends Throwable> exceptionType = exception.getClass();

            if (mappings.containsKey(exceptionType)) {
                produceResponse(exception, responseBuilder);
                return response.get();
            }

            if (exception instanceof ValidationException) {
                validationExceptionHandler.handleValidationException((ValidationException) exception, responseBuilder);
                return response.get();
            }

            log.debugv("Unwrapping {0}", exception.getClass());
            exception = exception.getCause();
        }

        // No ExceptionMapper/ExceptionMapping, rethrow the exception
        throw new UnhandledException(e);
    }

    protected void produceResponse(Throwable exception, ResponseBuilder builder) {
        Mapping mapping = mappings.get(exception.getClass());
        log.debugv("Found exception mapping {0} for {1}", mapping, exception.getClass());

        builder.status(mapping.getStatusCode());

        String message = createMessage(mapping.getMessage(), mapping.isInterpolateMessageBody(),
                mapping.isUseExceptionMessage(), exception, expressions);
        if (message != null) {
            builder.entity(createEntityBody(mapping, message));
        }
    }

    protected String createMessage(String message, boolean interpolate, boolean useExceptionMessage, Throwable e,
                                   Expressions expressions) {
        String msg = message;
        if (msg == null || msg.length() == 0) {
            if (useExceptionMessage) {
                msg = e.getMessage();
            } else {
                return null; // empty body is acceptable
            }
        }

        if (interpolate && msg != null) {
            return expressions.evaluateValueExpression(msg, String.class);
        }
        return msg;
    }

    protected Object createEntityBody(Mapping mapping, String message) {
        if (mapping.isUseJaxb()) {
            return new ErrorMessageWrapper(message);
        }
        return message;
    }

    public Map<Class<? extends Throwable>, Mapping> getMappings() {
        return mappings;
    }
}
