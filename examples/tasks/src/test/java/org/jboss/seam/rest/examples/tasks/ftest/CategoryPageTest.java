package org.jboss.seam.rest.examples.tasks.ftest;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.jboss.arquillian.ajocado.framework.AjaxSelenium;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for the category page (categories.html)
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@RunWith(Arquillian.class)
public class CategoryPageTest extends AbstractPageTest {
    private CategoryPage page;
    
    @Drone
    AjaxSelenium selenium;

    @ArquillianResource
    URL contextPath;

    @Before
    public void openCategoryPage() {
        if (page == null) {
            page = new CategoryPage(selenium, contextPath);
        }
        page.reload();
    }

    @Test
    public void testContent() {
        assertTrue(page.isCategoryPresent("School"));
        assertTrue(page.isCategoryPresent("Buy"));
    }

    @Test
    public void testAddingCategory() {
        String name = "Home";
        page.newCategory(name);
        assertTrue(page.isCategoryPresent(name));
        page.reload(); // verify changes are stored on the server
        assertTrue(page.isCategoryPresent(name));
        assertTrue(page.goToTaskPage().isCategoryPresent(name));
    }

    @Test
    public void testRemovingCategory() {
        String name = "Work";
        assertTrue(page.isCategoryPresent(name));
        page.deleteCategory(name);
        assertFalse(page.isCategoryPresent(name));
        page.reload(); // verify changes are stored on the server
        assertFalse(page.isCategoryPresent(name));
        assertFalse(page.goToTaskPage().isCategoryPresent(name));
        assertFalse(page.goToTaskPage().isTaskPresent(6)); // cascaded delete
    }
}
