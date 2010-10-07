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
package org.jboss.seam.resteasy.example.tasks.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.resteasy.annotations.providers.NoJackson;

/**
 * 
 * @author Jozef Hartinger
 * 
 */
@Entity
@XmlRootElement
@GroupSequence({ Task.class, TaskValidationGroup.class })
@NamedQueries({
   @NamedQuery(name = "taskById", query = "select task from Task task where task.id = :tid"),
   @NamedQuery(name = "taskByCategoryAndId", query = "select task from Task task where task.id = :tid and task.category.name = :category"),
   @NamedQuery(name = "tasks", query = "select task from Task task where task.resolved in (:r1, :r2)"),
   @NamedQuery(name = "tasksByCategory", query = "select task from Task task where task.category.name = :category and task.resolved in (:r1, :r2)")
})
@NoJackson
public class Task
{
   @Id
   @GeneratedValue
   private Long id;
   @NotNull
   @Size(min = 1, max = 100, groups = TaskValidationGroup.class)
   private String name;
   @NotNull
   private Boolean resolved;
   @Temporal(TemporalType.TIMESTAMP)
   @NotNull
   private Date created;
   @Temporal(TemporalType.TIMESTAMP)
   @NotNull
   private Date updated;
   @ManyToOne
   @NotNull
   private Category category;

   public Task()
   {
   }

   public Task(Long id, String name, Boolean resolved, Date created, Date updated, Category category)
   {
      this.id = id;
      this.name = name;
      this.resolved = resolved;
      this.created = created;
      this.updated = updated;
      this.category = category;
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

   @Transient
   @XmlElement(name = "category")
   public String getCategoryName()
   {
      return category.getName();
   }
}
