package org.jboss.seam.rest.example.tasks.ftest;

import java.net.URL;

import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.locator.option.OptionLocator;
import org.jboss.test.selenium.locator.option.OptionValueLocator;

import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.waitXhr;
import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.jboss.test.selenium.locator.option.OptionLocatorFactory.optionValue;

/**
 * Page object for the tasks page (tasks.html)
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 *
 */
public class TaskPage extends AbstractPage
{
   public static final JQueryLocator NEW_TASK_NAME_FIELD = jq("#editTaskName");
   public static final JQueryLocator NEW_TASK_CATEGORY_SELECT = jq("#editTaskCategory");
   public static final JQueryLocator NEW_TASK_SUBMIT = jq("#editTaskSubmit");
   public static final JQueryLocator TASK_BY_NAME = jq(".name:contains('{0}')");
   public static final JQueryLocator TASK_BY_ID_AND_CATEGORY = jq("#{0} ~ #{1}:first .name");

   public static final JQueryLocator TASK_DONE_BUTTON = jq("#{0} img:first");
   public static final JQueryLocator TASK_DELETE_BUTTON = jq("#{0} img:last");
   public static final JQueryLocator TASK_EDIT_BUTTON = jq("#{0} img:nth-child(2)");
   public static final JQueryLocator TASK_EDIT_FORM = jq("#{0} .updateTask");
   public static final JQueryLocator TASK_EDIT_NAME_FIELD = jq("#{0} .nameField");
   public static final JQueryLocator TASK_EDIT_CATEGORY_SELECT = jq("#{0} #editTaskCategory");
   public static final OptionLocator<OptionValueLocator> TASK_CATEGORY_OPTION = optionValue("{0}");

   public static final JQueryLocator TASK_EDIT_UPDATE_BUTTON = jq("#{0} #update");

   public TaskPage(AjaxSelenium selenium, URL contextPath)
   {
      super(selenium, contextPath);
      reload();
   }

   @Override
   public String getPageSuffix()
   {
      return "tasks.html";
   }

   public void newTask(String name, String category)
   {
      selenium.type(NEW_TASK_NAME_FIELD, name);
      selenium.select(NEW_TASK_CATEGORY_SELECT, TASK_CATEGORY_OPTION.format(category));
      waitXhr(selenium).click(NEW_TASK_SUBMIT);
   }

   public boolean isTaskPresent(String taskName)
   {
      return selenium.isElementPresent(TASK_BY_NAME.format(taskName));
   }

   public boolean isTaskPresent(String categoryName, int taskId)
   {
      return selenium.isElementPresent(TASK_BY_ID_AND_CATEGORY.format(categoryName, taskId));
   }

   public boolean isTaskPresent(String categoryName, int taskId, String taskName)
   {
      return isTaskPresent(categoryName, taskId) && selenium.getText(TASK_BY_ID_AND_CATEGORY.format(categoryName, taskId)).equals(taskName);
   }

   public void resolveTask(int id)
   {
      waitXhr(selenium).click(TASK_DONE_BUTTON.format(id));
   }

   public void editTask(int id, String taskName, String categoryName)
   {
      selenium.click(TASK_EDIT_BUTTON.format(id));
      waitForJQuery();
      selenium.type(TASK_EDIT_NAME_FIELD.format(id), taskName);
      // for some reason we need to hit the select first
      selenium.isElementPresent(TASK_EDIT_CATEGORY_SELECT.format(id));
      selenium.select(TASK_EDIT_CATEGORY_SELECT.format(id), TASK_CATEGORY_OPTION.format(categoryName));
      waitXhr(selenium).click(TASK_EDIT_UPDATE_BUTTON.format(id));
   }

   public void removeTask(int id)
   {
      waitXhr(selenium).click(TASK_DELETE_BUTTON.format(id));
   }

   public boolean isEditFormPresent(int id)
   {
      return selenium.isElementPresent(TASK_EDIT_FORM.format(id));
   }
}
