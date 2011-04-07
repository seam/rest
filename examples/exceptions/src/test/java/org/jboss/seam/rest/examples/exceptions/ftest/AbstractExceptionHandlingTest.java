package org.jboss.seam.rest.examples.exceptions.ftest;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public abstract class AbstractExceptionHandlingTest {
    protected String contextPath = "http://localhost:8080/rest-exceptions/api";
    private HttpClient client = new HttpClient();

    @BeforeClass
    @Parameters("context.path")
    public void setup(@Optional String contextPath) {
        if (contextPath != null) {
            this.contextPath = contextPath;
        }
    }

    protected void checkResponse(String url, int expectedStatus, String expectedBody) throws Exception {
        checkResponse(url, "application/xml", expectedStatus, expectedBody);
    }

    protected void checkResponse(String urlSuffix, String accept, int expectedStatus, String expectedBody) throws Exception {
        GetMethod get = new GetMethod(contextPath + urlSuffix);
        get.setRequestHeader("Accept", accept);
        assertEquals(expectedStatus, client.executeMethod(get));
        if (expectedBody != null) {
            String responseBody = get.getResponseBodyAsString();
            assertTrue("Unexpected response: " + responseBody, responseBody.contains(expectedBody));
        }
    }
}
