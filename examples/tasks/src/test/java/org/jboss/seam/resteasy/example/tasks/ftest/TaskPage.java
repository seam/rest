/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.resteasy.example.tasks.ftest;

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
 * @author Jozef Hartinger
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
