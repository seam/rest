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

import java.net.URL;

import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.locator.JQueryLocator;
import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.waitXhr;

/**
 * Page object for the category page (categories.html)
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 *
 */
public class CategoryPage extends AbstractPage
{
   public static final JQueryLocator NEW_CATEGORY_NAME = jq("#editCategoryName");
   public static final JQueryLocator NEW_CATEGORY_SUBMIT = jq("#editCategorySubmit");
   public static final JQueryLocator CATEGORY_DELETE_BUTTON = jq("#{0} img:first");
   
   public CategoryPage(AjaxSelenium selenium, URL contextPath)
   {
      super(selenium, contextPath);
      reload();
   }

   @Override
   public String getPageSuffix()
   {
      return "categories.html";
   }
   
   public void newCategory(String name)
   {
      selenium.type(NEW_CATEGORY_NAME, name);
      waitXhr(selenium).click(NEW_CATEGORY_SUBMIT);
   }
   
   public void deleteCategory(String name)
   {
      waitXhr(selenium).click(CATEGORY_DELETE_BUTTON.format(name));
   }
}
