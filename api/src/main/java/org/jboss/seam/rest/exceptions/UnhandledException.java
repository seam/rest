package org.jboss.seam.rest.exceptions;

public class UnhandledException extends RuntimeException {
    private static final long serialVersionUID = 2094785191554517720L;

    public UnhandledException() {
    }

    public UnhandledException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnhandledException(String message) {
        super(message);
    }

    public UnhandledException(Throwable cause) {
        super(cause);
    }
}