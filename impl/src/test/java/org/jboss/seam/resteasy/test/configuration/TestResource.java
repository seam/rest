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
package org.jboss.seam.resteasy.test.configuration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.seam.resteasy.test.Student;

@Path("foo")
@Produces("text/plain")
public class TestResource
{
   @GET
   public String foo()
   {
      return "foo";
   }

   @GET
   @Produces("application/xml")
   public String xmlFoo()
   {
      return "<foo/>";
   }

   @GET
   @Path("student")
   @Produces("foo/bar")
   public Student bar()
   {
      return new Student("Jozef");
   }

   @GET
   @Path("exception1")
   public void exception1()
   {
      throw new EntityNotFoundException("Entity is gone.");
   }

   @GET
   @Path("exception2")
   public void exception2()
   {
      throw new NullPointerException("null");
   }

   @GET
   @Path("exception3")
   public void exception3()
   {
      throw new IllegalArgumentException();
   }

   @GET
   @Path("interpolatedException")
   @Produces({ "text/plain", "application/xml" })
   public void interpolatedException()
   {
      throw new UnsupportedOperationException();
   }

   @GET
   @Path("interpolatedExceptionSwitch")
   public void interpolatedExceptionSwitch()
   {
      throw new NoSuchMethodError();
   }
}
