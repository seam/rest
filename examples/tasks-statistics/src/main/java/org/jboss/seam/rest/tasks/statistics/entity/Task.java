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
package org.jboss.seam.rest.tasks.statistics.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author Jozef Hartinger
 * 
 */
@XmlRootElement
public class Task
{
   private Long id;
   private String name;
   private Boolean resolved;
   private Date created;
   private Date updated;
   private Category category;

   public Task()
   {
   }

   public Task(String name, Boolean resolved, Date created, Date updated, Category category)
   {
      this.name = name;
      this.resolved = resolved;
      this.created = created;
      this.updated = updated;
      this.category = category;
   }

   public Task(Long id, String name, Boolean resolved, Date created, Date updated, Category category)
   {
      this(name, resolved, created, updated, category);
      this.id = id;
   }

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public Boolean isResolved()
   {
      return resolved;
   }

   public void setResolved(Boolean resolved)
   {
      this.resolved = resolved;
   }

   @XmlElement(name = "created")
   public Date getCreated()
   {
      return created;
   }

   public void setCreated(Date created)
   {
      this.created = created;
   }

   @XmlElement(name = "updated")
   public Date getUpdated()
   {
      return updated;
   }

   public void setUpdated(Date updated)
   {
      this.updated = updated;
   }

   @XmlTransient
   public Category getCategory()
   {
      return category;
   }

   public void setCategory(Category category)
   {
      this.category = category;
   }

   @XmlElement(name = "category")
   public String getCategoryName()
   {
      return category.getName();
   }
}
