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
package org.jboss.seam.rest.example.jaxrs.exceptions.resource;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.jboss.seam.solder.reflection.Reflections;

@Path("exceptions")
@Produces("application/xml")
public class ExceptionResource
{
   @GET
   public void throwException(@QueryParam("exception") @DefaultValue("java.lang.Exception") String exceptionType) throws Throwable
   {
      Object object = Reflections.classForName(exceptionType).newInstance();
      
      if (!(object instanceof Exception))
      {
         throw new RuntimeException(exceptionType + " is not an exception");
      }
      
      throw (Throwable) object;
   }
}
