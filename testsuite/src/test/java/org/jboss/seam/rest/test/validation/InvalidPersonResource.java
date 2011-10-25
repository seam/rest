package org.jboss.seam.rest.test.validation;

@Invalid
public class InvalidPersonResource extends PersonResource {
    public InvalidPersonResource() {
        super("", -1, 100);
    }
}
