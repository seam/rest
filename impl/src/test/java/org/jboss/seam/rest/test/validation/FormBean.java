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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

public class FormBean
{
   @FormParam("foo")
   @NotNull
   private String foo;
   @FormParam("bar")
   @Size(min = 2)
   private String bar;

   public FormBean(String foo, String bar)
   {
      this.foo = foo;
      this.bar = bar;
   }

   public String getFoo()
   {
      return foo;
   }

   public void setFoo(String foo)
   {
      this.foo = foo;
   }

   public String getBar()
   {
      return bar;
   }

   public void setBar(String bar)
   {
      this.bar = bar;
   }
}
