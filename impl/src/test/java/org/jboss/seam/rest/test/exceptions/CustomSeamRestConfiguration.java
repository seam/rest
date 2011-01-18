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

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Specializes;

import org.jboss.seam.rest.SeamRestConfiguration;
import org.jboss.seam.rest.exceptions.Mapping;

@Specializes
public class CustomSeamRestConfiguration extends SeamRestConfiguration {

	@PostConstruct
	public void setup()
	{
		addMapping(new Mapping(IllegalAccessException.class, 410));
		addMapping(new Mapping(ArrayIndexOutOfBoundsException.class, 411));
		addMapping(new Mapping(NullPointerException.class, 412, "Null reference.", false, false, false));
		addMapping(new Mapping(UnsupportedOperationException.class, 413, "The quick #{fox.color} #{fox.count == 1 ? 'fox' : 'foxes'} jumps over the lazy dog", false, true, false));
		addMapping(new Mapping(NoSuchMethodError.class, 414, "The quick #{fox.color} #{fox.count == 1 ? 'fox' : 'foxes'} jumps over the lazy dog", false, false, false));
		addMapping(new Mapping(Exception2.class, 400));
	}
}
