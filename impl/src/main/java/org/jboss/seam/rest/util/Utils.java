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
package org.jboss.seam.rest.util;

import java.util.HashSet;
import java.util.Set;

public class Utils
{
   private static Set<Class<?>> WRAPPED_CLASSES = new HashSet<Class<?>>(); 
      
   {
      WRAPPED_CLASSES.add(Boolean.class);
      WRAPPED_CLASSES.add(Character.class);
      WRAPPED_CLASSES.add(Byte.class);
      WRAPPED_CLASSES.add(Short.class);
      WRAPPED_CLASSES.add(Integer.class);
      WRAPPED_CLASSES.add(Long.class);
      WRAPPED_CLASSES.add(Float.class);
      WRAPPED_CLASSES.add(Double.class);
      WRAPPED_CLASSES.add(Void.class);
   }

   /**
    * TODO JAVADOC
    * @param clazz
    * @return
    */
   public static boolean isPrimitiveWrapper(Class<?> clazz)
   {
      return WRAPPED_CLASSES.contains(clazz);
   }
}
