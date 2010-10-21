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
package org.jboss.seam.resteasy.test.exceptions;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Specializes;

import org.jboss.seam.resteasy.exceptions.ExceptionMapping;
import org.jboss.seam.resteasy.exceptions.ExceptionMappingConfiguration;

@Specializes
public class CustomExceptionMappings extends ExceptionMappingConfiguration {

	@PostConstruct
	public void setup()
	{
		addExceptionMapping(new ExceptionMapping(IllegalAccessException.class, 410));
		addExceptionMapping(new ExceptionMapping(ArrayIndexOutOfBoundsException.class, 411));
		addExceptionMapping(new ExceptionMapping(NullPointerException.class, 412));
		addExceptionMapping(new ExceptionMapping(UnsupportedOperationException.class, 413, "The quick #{fox.color} #{fox.count == 1 ? 'fox' : 'foxes'} jumps over the lazy dog"));
		addExceptionMapping(new ExceptionMapping(NoSuchMethodError.class, 414, "The quick #{fox.color} #{fox.count == 1 ? 'fox' : 'foxes'} jumps over the lazy dog", false));
		addExceptionMapping(new ExceptionMapping(Exception2.class, 400));
		addUnwrappedException(Exception1.class);
		
		addExceptionMapping(new ExceptionMapping(Exception3.class, 415));
		addUnwrappedException(Exception3.class);
	}
}
