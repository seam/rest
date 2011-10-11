package org.jboss.seam.rest.examples.tasks.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jboss.arquillian.ajocado.utils.URLUtils;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.seam.rest.examples.tasks.ftest.AbstractPageTest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This test verifies XML representations created by FreeMarker or Apache Velocity.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@RunWith(Arquillian.class)
public class XmlRepresentationTest extends AbstractPageTest {
    
    @ArquillianResource
    private URL contextPath;
    
    private HttpClient client = new HttpClient();

    private String getRepresentation(String segment, String accept) throws Exception {
        GetMethod get = new GetMethod(URLUtils.buildUrl(contextPath, segment).toString());
        get.setRequestHeader("Accept", accept);
        assertEquals(200, client.executeMethod(get));
        return get.getResponseBodyAsString();
    }

    @Test
    public void testTask() throws Exception {
        String representation = getRepresentation("/rest-tasks/api/task/2", "application/task+xml");
        assertTrue(contains(representation, "<name>Build the Turing machine</name>"));
        assertTrue(contains(representation, "<link rel=\"self\" href=\".*/rest-tasks/api/task/2\"/>"));
        assertTrue(contains(representation, "<link rel=\"http://sfwk\\.org/rest/tasks/move\" href=\".*/rest-tasks/api/task/2/move\\?category=\\{category-name\\}\"/>"));
        assertTrue(contains(representation, "<link rel=\"edit\" href=\".*/rest-tasks/api/task/2\"/>"));
        assertTrue(contains(representation, "<resolved>false</resolved>"));
    }
    
    @Test
    public void testTasks() throws Exception {
        String representation = getRepresentation("/rest-tasks/api/task?start=4&limit=1", "application/tasks+xml");
        assertTrue(contains(representation, "<link href=\"self\" href=\".*/rest-tasks/api/task\\?start=4&limit=1\"/>"));
        assertTrue(contains(representation, "<link href=\"next\" href=\".*/rest-tasks/api/task\\?start=5&limit=1\"/>"));
        assertTrue(contains(representation, "<link href=\"previous\" href=\".*/rest-tasks/api/task\\?start=3&limit=1\"/>"));
        assertTrue(contains(representation, "<name>Pick up meal tickets</name>"));
        assertTrue(contains(representation, "<link rel=\"self\" href=\".*/rest-tasks/api/task/8\"/>"));
        assertTrue(contains(representation, "<link rel=\"http://sfwk\\.org/rest/tasks/move\" href=\".*/rest-tasks/api/task/8/move\\?category=\\{category-name\\}\"/>"));
        assertTrue(contains(representation, "<link rel=\"edit\" href=\".*/rest-tasks/api/task/8\"/>"));
    }

    @Test
    public void testCategories() throws Exception {
        String representation = getRepresentation("/rest-tasks/api/category?start=1&limit=1", "application/categories+xml");
        assertTrue(contains(representation, "<link href=\"self\" href=\".*/rest-tasks/api/category\\?start=1&limit=1\"/>"));
        assertTrue(contains(representation, "<link href=\"next\" href=\".*/rest-tasks/api/category\\?start=2&limit=1\"/>"));
        assertTrue(contains(representation, "<link href=\"previous\" href=\".*/rest-tasks/api/category\\?start=0&limit=1\"/>"));
        assertTrue(contains(representation, "<name>Work</name>"));
        assertTrue(contains(representation, "<link rel=\"self\" href=\".*/rest-tasks/api/category/Work\"/>"));
        assertTrue(contains(representation, "<name>Pick up meal tickets</name>"));
        assertTrue(contains(representation, "<link rel=\"self\" href=\".*/rest-tasks/api/task/8\"/>"));
        assertTrue(contains(representation, "<link rel=\"http://sfwk\\.org/rest/tasks/move\" href=\".*/rest-tasks/api/task/8/move\\?category=\\{category-name\\}\"/>"));
        assertTrue(contains(representation, "<link rel=\"edit\" href=\".*/rest-tasks/api/task/8\"/>"));
        assertTrue(contains(representation, "<resolved>false</resolved>"));
    }

    @Test
    public void testCategoriesShort() throws Exception {
        String representation = getRepresentation("/rest-tasks/api/category?start=1&limit=1", "application/categories-short+xml");
        assertTrue(contains(representation, "<link href=\"self\" href=\".*/rest-tasks/api/category\\?start=1&limit=1\"/>"));
        assertTrue(contains(representation, "<link href=\"next\" href=\".*/rest-tasks/api/category\\?start=2&limit=1\"/>"));
        assertTrue(contains(representation, "<link href=\"previous\" href=\".*/rest-tasks/api/category\\?start=0&limit=1\"/>"));
        assertTrue(contains(representation, "<name>Work</name>"));
        assertTrue(contains(representation, "<link rel=\"self\" href=\".*/rest-tasks/api/category/Work\"/>"));
        assertTrue(contains(representation, "<name>Pick up meal tickets</name>"));
        assertTrue(contains(representation, "<link rel=\"self\" href=\".*/rest-tasks/api/task/8\"/>"));
    }

    private boolean contains(String value, String regex)
    {
        return Pattern.compile(regex).matcher(value).find();
    }
}
