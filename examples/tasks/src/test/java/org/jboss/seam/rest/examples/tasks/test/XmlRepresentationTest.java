package org.jboss.seam.rest.examples.tasks.test;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * This test verifies XML representations created by FreeMarker or Apache Velocity.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public class XmlRepresentationTest {
    private HttpClient client = new HttpClient();

    private String getRepresentation(String url, String accept) throws Exception {
        GetMethod get = new GetMethod(url);
        get.setRequestHeader("Accept", accept);
        assertEquals(200, client.executeMethod(get));
        return get.getResponseBodyAsString();
    }

    @Test
    public void testTask() throws Exception {
        String representation = getRepresentation("http://localhost:8080/rest-tasks/api/task/2", "application/task+xml");
        assertTrue(representation.contains("<name>Build the Turing machine</name>"));
        assertTrue(representation.contains("<link rel=\"self\" href=\"http://localhost:8080/rest-tasks/api/task/2\"/>"));
        assertTrue(representation
                .contains("<link rel=\"http://sfwk.org/rest/tasks/move\" href=\"http://localhost:8080/rest-tasks/api/task/2/move?category={category-name}\"/>"));
        assertTrue(representation.contains("<link rel=\"edit\" href=\"http://localhost:8080/rest-tasks/api/task/2\"/>"));
        assertTrue(representation.contains("<resolved>false</resolved>"));
    }

    @Test
    public void testTasks() throws Exception {
        String representation = getRepresentation("http://localhost:8080/rest-tasks/api/task?start=4&limit=1",
                "application/tasks+xml");
        assertTrue(representation
                .contains("<link href=\"self\" href=\"http://localhost:8080/rest-tasks/api/task?start=4&limit=1\"/>"));
        assertTrue(representation
                .contains("<link href=\"next\" href=\"http://localhost:8080/rest-tasks/api/task?start=5&limit=1\"/>"));
        assertTrue(representation
                .contains("<link href=\"previous\" href=\"http://localhost:8080/rest-tasks/api/task?start=3&limit=1\"/>"));
        assertTrue(representation.contains("<name>Pick up meal tickets</name>"));
        assertTrue(representation.contains("<link rel=\"self\" href=\"http://localhost:8080/rest-tasks/api/task/8\"/>"));
        assertTrue(representation
                .contains("<link rel=\"http://sfwk.org/rest/tasks/move\" href=\"http://localhost:8080/rest-tasks/api/task/8/move?category={category-name}\"/>"));
        assertTrue(representation.contains("<link rel=\"edit\" href=\"http://localhost:8080/rest-tasks/api/task/8\"/>"));
    }

    @Test
    public void testCategories() throws Exception {
        String representation = getRepresentation("http://localhost:8080/rest-tasks/api/category?start=1&limit=1",
                "application/categories+xml");
        assertTrue(representation
                .contains("<link href=\"self\" href=\"http://localhost:8080/rest-tasks/api/category?start=1&limit=1\"/>"));
        assertTrue(representation
                .contains("<link href=\"next\" href=\"http://localhost:8080/rest-tasks/api/category?start=2&limit=1\"/>"));
        assertTrue(representation
                .contains("<link href=\"previous\" href=\"http://localhost:8080/rest-tasks/api/category?start=0&limit=1\"/>"));
        assertTrue(representation.contains("<name>Work</name>"));
        assertTrue(representation.contains("<link rel=\"self\" href=\"http://localhost:8080/rest-tasks/api/category/Work\"/>"));
        assertTrue(representation.contains("<name>Pick up meal tickets</name>"));
        assertTrue(representation.contains("<link rel=\"self\" href=\"http://localhost:8080/rest-tasks/api/task/8\"/>"));
        assertTrue(representation
                .contains("<link rel=\"http://sfwk.org/rest/tasks/move\" href=\"http://localhost:8080/rest-tasks/api/task/8/move?category={category-name}\"/>"));
        assertTrue(representation.contains("<link rel=\"edit\" href=\"http://localhost:8080/rest-tasks/api/task/8\"/>"));
        assertTrue(representation.contains("<resolved>false</resolved>"));
    }

    @Test
    public void testCategoriesShort() throws Exception {
        String representation = getRepresentation("http://localhost:8080/rest-tasks/api/category?start=1&limit=1",
                "application/categories-short+xml");
        assertTrue(representation
                .contains("<link href=\"self\" href=\"http://localhost:8080/rest-tasks/api/category?start=1&limit=1\"/>"));
        assertTrue(representation
                .contains("<link href=\"next\" href=\"http://localhost:8080/rest-tasks/api/category?start=2&limit=1\"/>"));
        assertTrue(representation
                .contains("<link href=\"previous\" href=\"http://localhost:8080/rest-tasks/api/category?start=0&limit=1\"/>"));
        assertTrue(representation.contains("<name>Work</name>"));
        assertTrue(representation.contains("<link rel=\"self\" href=\"http://localhost:8080/rest-tasks/api/category/Work\"/>"));
        assertTrue(representation.contains("<name>Pick up meal tickets</name>"));
        assertTrue(representation.contains("<link rel=\"self\" href=\"http://localhost:8080/rest-tasks/api/task/8\"/>"));
    }

}
