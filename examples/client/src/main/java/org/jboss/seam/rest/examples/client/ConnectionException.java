package org.jboss.seam.rest.examples.client;

public class ConnectionException extends RuntimeException {
    private static final long serialVersionUID = 8479013669413477666L;

    public ConnectionException() {
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(Throwable cause) {
        super(cause);
    }

}
