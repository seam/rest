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

import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.waitXhr;
import static org.jboss.test.selenium.locator.LocatorFactory.jq;

import java.net.URL;

import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.locator.JQueryLocator;

/**
 * Page object for the resolved tasks page (resolved.html)
 * @author Jozef Hartinger
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
