package org.jboss.seam.rest.example.tasks.ftest;

import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.waitXhr;
import static org.jboss.test.selenium.locator.LocatorFactory.jq;

import java.net.URL;

import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.locator.JQueryLocator;

/**
 * Page object for the resolved tasks page (resolved.html)
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 *
 */
public class ResolvedPage extends AbstractPage
{
   public static final JQueryLocator NEXT_LINK = jq(".next");
   public static final JQueryLocator PREVIOUS_LINK = jq(".previous");
   public static final JQueryLocator TASK_UNDO = jq("#{0} td:first img:first");

   public ResolvedPage(AjaxSelenium selenium, URL contextPath)
   {
      super(selenium, contextPath);
      reload();
   }

   @Override
   public String getPageSuffix()
   {
      return "resolved.html";
   }
   
   public void undoTask(int id)
   {
      waitXhr(selenium).click(TASK_UNDO.format(id));
   }

   public void next()
   {
      waitXhr(selenium).click(NEXT_LINK);
      waitForJQuery();
   }

   public void previous()
   {
      waitXhr(selenium).click(PREVIOUS_LINK);
      waitForJQuery();
   }

   public boolean isNextAvailable()
   {
      return selenium.isVisible(NEXT_LINK);
   }

   public boolean isPreviousAvailable()
   {
      return selenium.isVisible(PREVIOUS_LINK);
   }
}
