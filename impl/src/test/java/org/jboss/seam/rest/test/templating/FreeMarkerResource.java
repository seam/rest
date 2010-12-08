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
package org.jboss.seam.rest.test.templating;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.seam.rest.templating.ResponseTemplate;
import org.jboss.seam.rest.test.Student;

@Path("/freemarker")
public class FreeMarkerResource
{
   @Path("hello")
   @GET
   @Produces("text/student")
   @ResponseTemplate("/hello.ftl")
   public Student hello()
   {
      return new Student("Jozef Hartinger");
   }
   
   @Path("students")
   @GET
   @Produces("application/university+xml")
   @ResponseTemplate("/university.ftl")
   public Student students()
   {
      return new Student("Jozef Hartinger");
   }
   
   @Path("greeting")
   @GET
   @Produces({"text/greeting-informal", "text/greeting-formal"})
   @ResponseTemplate.List({
      @ResponseTemplate(value = "/informal.ftl", produces = "text/greeting-informal"),
      @ResponseTemplate(value = "/formal.ftl", produces = "text/greeting-formal")
   })
   public Student greeting()
   {
      return new Student("Jozef Hartinger");
   }
   
}
