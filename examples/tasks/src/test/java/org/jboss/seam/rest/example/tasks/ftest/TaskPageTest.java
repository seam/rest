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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import org.jboss.test.selenium.AbstractTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for the tasks page (tasks.html)
 * @author Jozef Hartinger
 *
 */
public class TaskPageTest extends AbstractTestCase
{
   private TaskPage page;

   @BeforeMethod
   public void openTaskPage()
   {
      if (page == null)
      {
         page = new TaskPage(selenium, contextPath);
      }
      page.reload();
   }

   @Test
   public void testNewTask()
   {
      String task = "Learn GIT";
      page.newTask(task, "School");
      assertTrue(page.isTaskPresent(task));
      page.reload();
      assertTrue(page.isTaskPresent(task));
   }

   @Test
   public void testIllegalTaskName()
   {
      page.newTask("", "School");
      assertTrue(selenium.isAlertPresent());
      assertEquals(selenium.getAlert().trim(), "size must be between 1 and 100");
   }

   @Test
   public void testRenamingTask()
   {
      int id = 10;
      String task = "Buy yoghurt";
      String category = "Buy";
      page.editTask(id, task, category);
      assertFalse(page.isEditFormPresent(id));
      assertTrue(page.isTaskPresent(category, id, task));
      assertFalse(page.isTaskPresent("Buy milk")); // the old name
      page.reload();
      assertTrue(page.isTaskPresent(category, id, task));
      assertFalse(page.isTaskPresent("Buy milk"));
   }

   @Test
   public void testMovingTask()
   {
      int id = 11;
      String name = "Buy an infinite tape";
      String newCategory = "School";
      assertTrue(page.isTaskPresent(id, name));
      page.editTask(id, name, newCategory);
      assertTrue(page.isTaskPresent(newCategory, id, name));
      page.reload(); // verify changes are stored on the server
      assertTrue(page.isTaskPresent(newCategory, id, name));
   }

   @Test
   public void testRemovingTask()
   {
      assertTrue(page.isTaskPresent(4, "Learn new vocab for English conversations"));
      page.removeTask(4);
      assertFalse(page.isTaskPresent(4));
      page.reload(); // verify changes are stored on the server
      assertFalse(page.isTaskPresent(4));
   }

   @Test
   public void testContent()
   {
      assertTrue(page.isCategoryPresent("School"));
      assertTrue(page.isCategoryPresent("Buy"));
      assertTrue(page.isTaskPresent("School", 5, "Prepare a presentation for webdesign seminar"));
      assertTrue(page.isTaskPresent("School", 3, "Finish the RESTEasy-Seam integration example"));
      assertTrue(page.isTaskPresent("School", 2, "Build the Turing machine"));
      assertTrue(page.isTaskPresent(20, "Get a haircut"));
      assertTrue(page.isTaskPresent(19, "Extend passport"));
      assertTrue(page.isTaskPresent(18, "Visit grandma"));
   }

   @Test
   public void testResolvingTask()
   {
      int taskId = 12;
      assertTrue(page.isTaskPresent(taskId, "Order books"));
      page.resolveTask(taskId);
      assertFalse(page.isTaskPresent(taskId));
      page.reload(); // verify changes are stored on the server
      assertFalse(page.isTaskPresent(taskId));
      assertTrue(page.goToResolvedPage().isTaskPresent(taskId));
   }
}
