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
package org.jboss.seam.rest.example.tasks.noxml;

import javax.enterprise.inject.Alternative;
import javax.persistence.NoResultException;

import org.jboss.seam.rest.SeamRestConfiguration;
import org.jboss.seam.rest.exceptions.ExceptionMapping;

/**
 * This is a configuration for Seam REST exception mapping. Activate this alternative if the XML
 * configuration cannot be used.
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 *
 */
@Alternative
@ExceptionMapping.List({
   @ExceptionMapping(exceptionType = NoResultException.class, status = 404, message = "Requested resource does not exist."),
   @ExceptionMapping(exceptionType = IllegalArgumentException.class, status = 400, message = "Illegal parameter value.")
})
public class CustomExceptionMappingConfiguration extends SeamRestConfiguration {
}
