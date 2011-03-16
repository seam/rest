package org.jboss.seam.rest.example.jaxrs.exceptions.ftest;

import org.testng.annotations.Test;

public class ProgrammaticExceptionHandlingTest extends AbstractExceptionHandlingTest {
    @Test
    public void testStatusCode() throws Exception {
        checkResponse("/exceptions?exception=java.lang.ArithmeticException", 500,
                "Cannot divide by zero. Want to divide by two instead?");
    }
}
