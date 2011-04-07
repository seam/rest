package org.jboss.seam.rest.examples.tasks.ftest;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import org.jboss.test.selenium.AbstractTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for the category page (categories.html)
 * 
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 * 
 */
public class CategoryPageTest extends AbstractTestCase {
    private CategoryPage page;

    @BeforeMethod
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