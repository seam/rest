package org.jboss.seam.rest.examples.exceptions.ftest;

import org.junit.Test;

public class ProgrammaticExceptionHandlingTest extends AbstractExceptionHandlingTest {
    @Test
    public void testStatusCode() throws Exception {
        checkResponse("api/exceptions?exception=java.lang.ArithmeticException", 500,
                "Cannot divide by zero. Want to divide by two instead?");
    }
}
