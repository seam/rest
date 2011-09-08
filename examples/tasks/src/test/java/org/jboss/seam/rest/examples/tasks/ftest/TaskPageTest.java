package org.jboss.seam.rest.examples.tasks.ftest;


import java.net.URL;

import org.jboss.arquillian.ajocado.framework.AjaxSelenium;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the tasks page (tasks.html)
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public class TaskPageTest extends AbstractPageTest {
    private TaskPage page;

    @Drone
    AjaxSelenium selenium;

    @ArquillianResource
    URL contextPath;
    
    @Before
    public void openTaskPage() {
        if (page == null) {
            page = new TaskPage(selenium, contextPath);
        }
        page.reload();
    }

    @Test
    public void testNewTask() {
        String task = "Learn GIT";
        page.newTask(task, "School");
        assertTrue(page.isTaskPresent(task));
        page.reload();
        assertTrue(page.isTaskPresent(task));
    }

    @Test
    public void testIllegalTaskName() {
        page.newTask("", "School");
        assertTrue(selenium.isAlertPresent());
        assertEquals(selenium.getAlert().trim(), "size must be between 1 and 100");
    }

    @Test
    public void testRenamingTask() {
        int id = 10;
        String task = "Buy yoghurt";
        String category = "Buy";
        page.editTask(id, task, category);
        assertFalse(page.isEditFormPresent(id));
        assertTrue(page.isTaskPresent(category, id, task));
        assertFalse(page.isTaskPresent("Buy milk")); // the old name
        page.reload();
        assertTrue(page.isTaskPresent(category, id, task));
        assertFalse(page.isTaskPresent("Buy milk"));
    }

    @Test
    public void testMovingTask() {
        int id = 11;
        String name = "Buy an infinite tape";
        String newCategory = "School";
        assertTrue(page.isTaskPresent(id, name));
        page.editTask(id, name, newCategory);
        assertTrue(page.isTaskPresent(newCategory, id, name));
        page.reload(); // verify changes are stored on the server
        assertTrue(page.isTaskPresent(newCategory, id, name));
    }

    @Test
    // SEAMREST-13
    public void testRemovingTask() {
        assertTrue(page.isTaskPresent(4, "Learn new vocab for English conversations"));
        page.removeTask(4);
        assertFalse(page.isTaskPresent(4));
        page.reload(); // verify changes are stored on the server
        assertFalse(page.isTaskPresent(4));
    }

    @Test
    public void testContent() {
        assertTrue(page.isCategoryPresent("School"));
        assertTrue(page.isCategoryPresent("Buy"));
        assertTrue(page.isTaskPresent("School", 5));
        assertTrue(page.isTaskPresent("School", 5, "Prepare a presentation for webdesign seminar"));
        assertTrue(page.isTaskPresent("School", 3, "Finish the RESTEasy-Seam integration example"));
        assertTrue(page.isTaskPresent("School", 2, "Build the Turing machine"));
        assertTrue(page.isTaskPresent(20, "Get a haircut"));
        assertTrue(page.isTaskPresent(19, "Extend passport"));
        assertTrue(page.isTaskPresent(18, "Visit grandma"));
    }

    @Test
    public void testResolvingTask() {
        int taskId = 12;
        assertTrue(page.isTaskPresent(taskId, "Order books"));
        page.resolveTask(taskId);
        assertFalse(page.isTaskPresent(taskId));
        page.reload(); // verify changes are stored on the server
        assertFalse(page.isTaskPresent(taskId));
        assertTrue(page.goToResolvedPage().isTaskPresent(taskId));
    }
}
