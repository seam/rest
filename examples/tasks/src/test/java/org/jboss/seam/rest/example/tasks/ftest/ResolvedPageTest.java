package org.jboss.seam.rest.examples.tasks.ftest;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import org.jboss.test.selenium.AbstractTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for the resolved tasks page (resolved.html)
 * 
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 * 
 */
public class ResolvedPageTest extends AbstractTestCase {
    private ResolvedPage page;

    @BeforeMethod
    public void openResolvedPage() {
        if (page == null) {
            page = new ResolvedPage(selenium, contextPath);
        }
        page.reload();
    }

    @Test
    public void contentTest() {
        assertTrue(page.isTaskPresent(6, "Print study materials"));
        assertTrue(page.isTaskPresent(13, "Buy a turtle"));
        assertTrue(page.isTaskPresent(14, "Buy new shoes"));
    }

    @Test
    public void paginationTest() {
        assertTrue(page.isNextAvailable());
        assertFalse(page.isPreviousAvailable());
        page.next();
        assertTrue(page.isTaskPresent(22, "Tidy up"));
        assertFalse(page.isNextAvailable());
        page.previous();
        assertTrue(page.isNextAvailable());
        assertFalse(page.isPreviousAvailable());
    }

    @Test
    public void testUndo() {
        int taskId = 15;

        assertTrue(page.isTaskPresent(taskId));
        page.undoTask(taskId);

        assertFalse(page.isTaskPresent(taskId));
        page.reload(); // verify changes are stored on the server
        assertFalse(page.isTaskPresent(taskId));

        assertTrue(page.goToTaskPage().isTaskPresent(taskId));
    }
}
