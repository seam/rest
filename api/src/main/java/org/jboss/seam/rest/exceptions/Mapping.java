package org.jboss.seam.rest.exceptions;

/**
 * Represents a mapping of an exception to an HTTP status code and response body.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public class Mapping {
    private Class<? extends Throwable> exceptionType;
    private int statusCode;
    private String message;
    private boolean interpolateMessageBody = true;
    private boolean useExceptionMessage = false;
    private boolean useJaxb = true;

    public Mapping() {
    }

    public Mapping(Class<? extends Throwable> exceptionType, int statusCode) {
        this.exceptionType = exceptionType;
        this.statusCode = statusCode;
    }

    public Mapping(Class<? extends Throwable> exceptionType, int statusCode, String message, boolean useExceptionMessage,
                   boolean interpolateMessageBody, boolean useJaxb) {
        this(exceptionType, statusCode);
        this.message = message;
        this.useExceptionMessage = useExceptionMessage;
        this.interpolateMessageBody = interpolateMessageBody;
        this.useJaxb = useJaxb;
    }

    public Class<? extends Throwable> getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(Class<? extends Throwable> exceptionType) {
        this.exceptionType = exceptionType;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isInterpolateMessageBody() {
        return interpolateMessageBody;
    }

    public void setInterpolateMessageBody(boolean interpolateMessageBody) {
        this.interpolateMessageBody = interpolateMessageBody;
    }

    public boolean isUseExceptionMessage() {
        return useExceptionMessage;
    }

    public void setUseExceptionMessage(boolean useExceptionMessage) {
        this.useExceptionMessage = useExceptionMessage;
    }

    public boolean isUseJaxb() {
        return useJaxb;
    }

    public void setUseJaxb(boolean useJaxb) {
        this.useJaxb = useJaxb;
    }

    @Override
    public String toString() {
        return "ExceptionMapping: " + exceptionType.getCanonicalName() + " --> (" + statusCode + ", " + message + ")";
    }
}
