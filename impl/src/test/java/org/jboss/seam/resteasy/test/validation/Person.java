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
package org.jboss.seam.resteasy.test.validation;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

public class Person
{

   @Size(min = 2, max = 20, groups = { PartialValidation.class, Default.class })
   private String firstName;
   @Size(min = 2, max = 20, groups = { PartialValidation.class, Default.class })
   private String surname;
   @Min(value = 18, groups = { PartialValidation.class, Default.class })
   private int age;
   @AssertFalse(groups = { PartialValidation.class, Default.class })
   private boolean zombie;
   @NotNull // not validated during partial validation
   private String bio;

   public Person(String firstName, String surname, int age, boolean zombie)
   {
      this.firstName = firstName;
      this.surname = surname;
      this.age = age;
      this.zombie = zombie;
   }
   
   public Person(String firstName, String surname, int age, boolean zombie, String bio)
   {
      this(firstName, surname, age, zombie);
      this.bio = bio;
   }
   
   public Person()
   {
   }

   public String getFirstName()
   {
      return firstName;
   }

   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }

   public String getSurname()
   {
      return surname;
   }

   public void setSurname(String surname)
   {
      this.surname = surname;
   }

   public int getAge()
   {
      return age;
   }

   public void setAge(int age)
   {
      this.age = age;
   }

   public boolean isZombie()
   {
      return zombie;
   }

   public void setZombie(boolean zombie)
   {
      this.zombie = zombie;
   }
   
   public String getBio()
   {
      return bio;
   }

   public void setBio(String bio)
   {
      this.bio = bio;
   }
}
