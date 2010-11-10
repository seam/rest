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
package org.jboss.seam.rest.tasks.statistics.analyzers;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;

import org.jboss.seam.rest.tasks.statistics.ReportResultEvent;
import org.jboss.seam.rest.tasks.statistics.entity.Category;
import org.jboss.seam.rest.tasks.statistics.entity.Task;

@RequestScoped
public class SimpleAnalyzer
{
   private long resolvedTasks = 0l;
   private long unresolvedTasks = 0l;
   private long categories = 0l;
   
   public void processTask(@Observes Task task)
   {
      if (task.isResolved())
      {
         resolvedTasks++;
      }
      else
      {
         unresolvedTasks++;
      }
   }
   
   public void processCategory(@Observes Category category)
   {
      categories++;
   }
   
   public void processResult(@Observes ReportResultEvent result)
   {
      result.addResult("Number of resolved tasks", String.valueOf(resolvedTasks));
      result.addResult("Number of unresolved tasks", String.valueOf(unresolvedTasks));
      result.addResult("Number of categories", String.valueOf(categories));
      result.addResult("Tasks per category", String.valueOf(categories));
   }
}
