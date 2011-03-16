package org.jboss.seam.rest.validation;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
public class ValidationErrorMessageWrapper {
    private List<String> messages = new ArrayList<String>();

    public ValidationErrorMessageWrapper() {
    }

    @XmlElementWrapper(name = "messages")
    @XmlElement(name = "message")
    public List<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < messages.size(); i++) {
            if (i > 0) {
                // get rid of trailing newline
                builder.append("\n");
            }
            builder.append(messages.get(i));
        }
        return builder.toString();
    }

}