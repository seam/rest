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
package org.jboss.seam.rest.test.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.seam.rest.client.RestClient;
import org.jboss.seam.rest.util.Annotations;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AnnotationsTest
{
   @Test
   public void testAnnotationSetSearch() throws SecurityException, NoSuchFieldException
   {
      Field field1 = Resource.class.getDeclaredField("field1");
      Field field2 = Resource.class.getDeclaredField("field2");
      Field field3 = Resource.class.getDeclaredField("field3");
      Set<Annotation> annotations1 = new HashSet<Annotation>((List<? extends Annotation>) Arrays.asList(field1.getAnnotations()));
      Set<Annotation> annotations2 = new HashSet<Annotation>((List<? extends Annotation>) Arrays.asList(field2.getAnnotations()));
      Set<Annotation> annotations3 = new HashSet<Annotation>((List<? extends Annotation>) Arrays.asList(field3.getAnnotations()));

      assertEquals("http://example.com", Annotations.getAnnotation(annotations1, RestClient.class).value());
      assertEquals("http://foo.bar", Annotations.getAnnotation(annotations2, RestClient.class).value());
      assertNull(Annotations.getAnnotation(annotations3, RestClient.class));
   }
}
