/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.seam.rest.example.tasks.db;

import java.util.Date;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.rest.example.tasks.entity.Category;
import org.jboss.seam.rest.example.tasks.entity.Task;


/**
 * Database import
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
//@Singleton
@Stateless
public class ImportBean
{

   @PersistenceContext
   private EntityManager em;

   public void clearDatabase()
   {
      em.createQuery("delete from Task").executeUpdate();
      em.createQuery("delete from Category").executeUpdate();
   }
   
   public void feedDatabase()
   {
      // School
      Category school = createCategory("School");
      addTask(school, "Build the Turing machine");
      addTask(school, "Finish the RESTEasy-Seam integration example");
      addTask(school, "Learn new vocab for English conversations");
      addTask(school, "Prepare a presentation for webdesign seminar");
      addTask(school, "Print study materials", true);
      em.persist(school);
      
      // Work
      Category work = createCategory("Work");
      addTask(work, "Pick up meal tickets");
      
      // Buy
      em.persist(work);
      Category buy = createCategory("Buy");
      addTask(buy, "Buy milk");
      addTask(buy, "Buy an infinite tape");
      addTask(buy, "Order books");
      addTask(buy, "Buy a turtle", true);
      addTask(buy, "Buy new shoes", true);
      addTask(buy, "Order camera", true);
      em.persist(buy);
      
      // Other stuff
      Category other_stuff = createCategory("Other Stuff");
      addTask(other_stuff, "Learn to fly", true);
      addTask(other_stuff, "Visit grandma");
      addTask(other_stuff, "Extend passport");
      addTask(other_stuff, "Get a haircut");
      addTask(other_stuff, "Pay bills", true);
      addTask(other_stuff, "Tidy up", true);
      em.persist(other_stuff);
   }
   
   private Category createCategory(String name)
   {
      Category c = new Category(name);
      c.setTasks(new LinkedList<Task>());
      return c;
   }
   
   private void addTask(Category category, String name)
   {
      addTask(category, name, false);
   }
   
   private void addTask(Category category, String name, boolean resolved)
   {
      Task task = new Task(name, resolved, new Date(), new Date(), category);
      category.getTasks().add(task);
   }
}
