package org.jboss.seam.rest.exceptions;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB wrapper class for the error message.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@XmlRootElement(name = "error")
public class ErrorMessageWrapper {
    private String message;

    public ErrorMessageWrapper() {
        // JAXB requires no-arg constructor
    }

    public ErrorMessageWrapper(String message) {
        this.message = message;
    }

    @XmlElement(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
