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
package org.jboss.seam.rest.test.exceptions;

import javax.ejb.EJBException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/exceptions")
public class Resource {

	@GET
	@Path("/iae")
	public void throwIae() throws Exception
	{
		throw new IllegalAccessException();
	}
	
	@GET
	@Path("/aioobe")
	public void throwAioobe()
	{
		throw new ArrayIndexOutOfBoundsException();
	}
	
	@GET
	@Path("/npe")
	public void throwNpe()
	{
		throw new NullPointerException();
	}
	
	@GET
	@Path("/e1")
	public void e1()
	{
		throw new Exception1(new Exception2());
	}
	
	@GET
	@Path("/ejb")
	public void ejb()
	{
		throw new EJBException(new Exception2());
	}
	
	@GET
	@Path("/uoe")
	public void throwUoe()
	{
		throw new UnsupportedOperationException();
	}
	
	@GET
	@Path("/nsme")
	public void throwNsme()
	{
		throw new NoSuchMethodError();
	}
}
