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
package org.jboss.seam.rest.validation;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

/**
 * Container for {@link ValidatedMethodMetadata}.
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
@ApplicationScoped
public class ValidationMetadata implements Serializable
{
   private static final long serialVersionUID = 9094847250956854536L;
   
   private Map<Method, ValidatedMethodMetadata> methods = new HashMap<Method, ValidatedMethodMetadata>();
   
   public ValidatedMethodMetadata getMethodMetadata(Method method)
   {
      return methods.get(method);
   }
   
   void addMethodMetadata(ValidatedMethodMetadata method)
   {
      methods.put(method.getMethod(), method);
   }
   
   public boolean containsMethodMetadata(Method method)
   {
      return methods.containsKey(method);
   }
}
