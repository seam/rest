package org.jboss.seam.rest.examples.client;

public class StatusException extends RuntimeException {
    private static final long serialVersionUID = 717053681736345905L;

    public StatusException(String message) {
        super(message);
    }
}
