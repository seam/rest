package org.jboss.seam.rest.examples.tasks.ftest;

import java.net.URL;

import org.jboss.arquillian.ajocado.framework.AjaxSelenium;
import org.jboss.arquillian.ajocado.locator.JQueryLocator;

import static org.jboss.arquillian.ajocado.Ajocado.waitForXhr;
import static org.jboss.arquillian.ajocado.locator.LocatorFactory.jq;

/**
 * Page object for the resolved tasks page (resolved.html)
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public class ResolvedPage extends AbstractPage {
    public static final JQueryLocator NEXT_LINK = jq(".next");
    public static final JQueryLocator PREVIOUS_LINK = jq(".previous");
    public static final JQueryLocator TASK_UNDO = jq("#{0} td:first img:first");

    public ResolvedPage(AjaxSelenium selenium, URL contextPath) {
        super(selenium, contextPath);
        reload();
    }

    @Override
    public String getPageSuffix() {
        return "resolved.html";
    }

    public void undoTask(int id) {
        waitForXhr(selenium).click(TASK_UNDO.format(id));
    }

    public void next() {
        waitForXhr(selenium).click(NEXT_LINK);
        waitForJQuery();
    }

    public void previous() {
        waitForXhr(selenium).click(PREVIOUS_LINK);
        waitForJQuery();
    }

    public boolean isNextAvailable() {
        return selenium.isVisible(NEXT_LINK);
    }

    public boolean isPreviousAvailable() {
        return selenium.isVisible(PREVIOUS_LINK);
    }
}
