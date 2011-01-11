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
package org.jboss.seam.rest.test.validation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jboss.seam.rest.validation.ValidateRequest;

@SuppressWarnings("unused")
@Valid
public class PersonResource
{
   /*
    * These fields simulate JAX-RS @Query parameters
    */
   @NotNull
   @Size(min = 1, max = 30)
   private String query;
   @Min(0)
   private int start;
   @Min(0) @Max(50)
   private int limit;
   
   public PersonResource()
   {
      this("Jozef", 0, 20);
   }

   public PersonResource(String query, int start, int limit)
   {
      this.query = query;
      this.start = start;
      this.limit = limit;
   }

   @ValidateRequest(groups = PartialValidation.class)
   public void partiallyValidatedOperation(Person person)
   {
   }
   
   @ValidateRequest
   public void completelyValidatedOperation(Person person)
   {
   }
   
   @ValidateRequest(validateMessageBody = false)
   public void notValidatedOperation(Person person)
   {
   }
   
   @ValidateRequest
   public void formOperation(@javax.validation.Valid @FormObject FormBean form1, @javax.validation.Valid @FormObject FormBean form2)
   {
   }
}
