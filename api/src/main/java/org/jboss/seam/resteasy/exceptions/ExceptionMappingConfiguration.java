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

package org.jboss.seam.resteasy.exceptions;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.seam.resteasy.exceptions.ExceptionMapping;

/**
 * Configuration class for exception mappings.
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
@ApplicationScoped
public class ExceptionMappingConfiguration {
	private Set<ExceptionMapping> exceptionMappings = new HashSet<ExceptionMapping>();
	private Set<Class<? extends Throwable>> unwrappedExceptions = new HashSet<Class<? extends Throwable>>();
	private Set<String> unwrappedExceptionsNames = new HashSet<String>();
	
	public Set<ExceptionMapping> getExceptionMappings() {
		return exceptionMappings;
	}

	public void setExceptionMappings(Set<ExceptionMapping> exceptionMappings) {
		this.exceptionMappings = exceptionMappings;
	}
	
	public void addExceptionMapping(ExceptionMapping exceptionMapping)
	{
		this.exceptionMappings.add(exceptionMapping);
	}

	public Set<Class<? extends Throwable>> getUnwrappedExceptions() {
		return unwrappedExceptions;
	}

	public void setUnwrappedExceptions(Set<Class<? extends Throwable>> unwrappedExceptions) {
		this.unwrappedExceptions = unwrappedExceptions;
	}
	
	public void addUnwrappedException(Class<? extends Throwable> unwrappedException)
	{
		unwrappedExceptions.add(unwrappedException);
	}
	
	public Set<String> getUnwrappedExceptionsNames() {
		return unwrappedExceptionsNames;
	}

	public void setUnwrappedExceptionsNames(Set<String> unwrappedExceptionsNames) {
		this.unwrappedExceptionsNames = unwrappedExceptionsNames;
	}

	public void addUnwrappedExceptionName(String unwrappedException)
	{
		this.unwrappedExceptionsNames.add(unwrappedException);
	}
}
