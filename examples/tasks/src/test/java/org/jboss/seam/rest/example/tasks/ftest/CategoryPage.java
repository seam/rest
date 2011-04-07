package org.jboss.seam.rest.examples.tasks.ftest;

import java.net.URL;

import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.locator.JQueryLocator;
import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.waitXhr;

/**
 * Page object for the category page (categories.html)
 * 
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 * 
 */
public class CategoryPage extends AbstractPage {
    public static final JQueryLocator NEW_CATEGORY_NAME = jq("#editCategoryName");
    public static final JQueryLocator NEW_CATEGORY_SUBMIT = jq("#editCategorySubmit");
    public static final JQueryLocator CATEGORY_DELETE_BUTTON = jq("#{0} img:first");

    public CategoryPage(AjaxSelenium selenium, URL contextPath) {
        super(selenium, contextPath);
        reload();
    }

    @Override
    public String getPageSuffix() {
        return "categories.html";
    }

    public void newCategory(String name) {
        selenium.type(NEW_CATEGORY_NAME, name);
        waitXhr(selenium).click(NEW_CATEGORY_SUBMIT);
    }

    public void deleteCategory(String name) {
        waitXhr(selenium).click(CATEGORY_DELETE_BUTTON.format(name));
    }
}
