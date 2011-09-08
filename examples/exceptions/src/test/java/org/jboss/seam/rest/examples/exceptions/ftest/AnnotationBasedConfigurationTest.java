package org.jboss.seam.rest.examples.exceptions.ftest;

import org.junit.Test;

public class AnnotationBasedConfigurationTest extends AbstractExceptionHandlingTest {
    @Test
    public void testStatusCode() throws Exception {
        checkResponse("api/exceptions?exception=java.lang.IllegalAccessException", 403, null);
    }

    @Test
    public void testMessageBody() throws Exception {
        checkResponse("api/exceptions?exception=java.lang.NullPointerException", 500,
                "<error><message>NullPointerException was thrown.</message></error>");
    }

    @Test
    public void testExceptionMessage() throws Exception {
        checkResponse("api/exceptions?exception=java.lang.Object", 500,
                "<error><message>java.lang.Object is not an exception</message></error>");
    }
}
