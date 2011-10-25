package org.jboss.seam.rest.examples.exceptions.ftest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public abstract class AbstractExceptionHandlingTest {
    private HttpClient client = new HttpClient();
    
    @ArquillianResource
    URL contextPath;
    
    public static final String ARCHIVE_NAME = "rest-exceptions.war";
    public static final String CONTEXT_PATH = "/rest-exceptions/";
    public static final String BUILD_DIRECTORY = "target";
    
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(ZipImporter.class, ARCHIVE_NAME).importFrom(new File(BUILD_DIRECTORY + '/' + ARCHIVE_NAME))
                .as(WebArchive.class);
    }

    protected void checkResponse(String url, int expectedStatus, String expectedBody) throws Exception {
        checkResponse(url, "application/xml", expectedStatus, expectedBody);
    }

    protected void checkResponse(String urlSuffix, String accept, int expectedStatus, String expectedBody) throws Exception {
        String url = new URL(contextPath, CONTEXT_PATH  + urlSuffix).toString();
        GetMethod get = new GetMethod(url);
        get.setRequestHeader("Accept", accept);
        assertEquals(expectedStatus, client.executeMethod(get));
        if (expectedBody != null) {
            String responseBody = get.getResponseBodyAsString();
            assertTrue("Unexpected response: " + responseBody, responseBody.contains(expectedBody));
        }
    }
}
