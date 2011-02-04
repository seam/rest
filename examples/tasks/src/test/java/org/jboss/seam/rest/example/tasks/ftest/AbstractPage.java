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
package org.jboss.seam.rest.example.tasks.ftest;

import java.net.MalformedURLException;
import java.net.URL;

import org.jboss.test.selenium.encapsulated.JavaScript;
import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.waiting.Wait;
import org.jboss.test.selenium.waiting.conditions.ElementPresent;

import static org.jboss.test.selenium.locator.LocatorFactory.jq;

/**
 * This abstract page class contains common methods for all the pages.
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 *
 */
public abstract class AbstractPage
{
   public static final JQueryLocator TASK_NAME = jq("#{0} .name");
   public static final JQueryLocator CATEGORY_NAME = jq("#{0} .name");
   public static final JavaScript JQUERY_IDLE_CONNECTION = new JavaScript("selenium.browserbot.getCurrentWindow().jQuery.active===0");
   public static final JQueryLocator PAGE_INITIALIZED = jq("body[initialized=true]");

   protected AjaxSelenium selenium;
   protected URL contextPath;

   public AbstractPage(AjaxSelenium selenium, URL contextPath)
   {
      this.selenium = selenium;
      this.contextPath = contextPath;
      reload();
   }

   /**
    * Return url of the page
    */
   public abstract String getPageSuffix();

   public void reload()
   {
      URL url;
      try
      {
         url = new URL(contextPath.toString() + getPageSuffix());
      }
      catch (MalformedURLException e)
      {
         throw new RuntimeException(e);
      }

      // firefox sometimes freezes, if that happens, let's give it two more tries
      for (int i = 0; i < 3; i++)
      {
         try
         {
            selenium.open(url);
            selenium.waitForPageToLoad();
            Wait.waitSelenium().interval(1000).until(ElementPresent.getInstance().locator(PAGE_INITIALIZED));
            break;
         }
         catch (Exception e)
         {
            continue;
         }
      }
   }

   public void waitForJQuery()
   {
      selenium.waitForCondition(JQUERY_IDLE_CONNECTION);
   }

   public JQueryLocator getTaskNameLocator(int id)
   {
      return TASK_NAME.format(id);
   }

   public ResolvedPage goToResolvedPage()
   {
      return new ResolvedPage(selenium, contextPath);
   }

   public TaskPage goToTaskPage()
   {
      return new TaskPage(selenium, contextPath);
   }

   public CategoryPage goToCategoryPage()
   {
      return new CategoryPage(selenium, contextPath);
   }

   public boolean isTaskPresent(int id)
   {
      return selenium.isElementPresent(getTaskNameLocator(id));
   }

   public boolean isTaskPresent(int id, String name)
   {
      return isTaskPresent(id) && selenium.getText(getTaskNameLocator(id)).equals(name);
   }

   public boolean isCategoryPresent(String name)
   {
      return selenium.isElementPresent(CATEGORY_NAME.format(name));
   }
}
