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
package org.jboss.seam.rest.test;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.seam.rest.util.Interpolator;

@Mock
@ApplicationScoped
public class MockInterpolator implements Interpolator
{
   public String interpolate(String input)
   {
      if ("The quick #{fox.color} #{fox.count == 1 ? 'fox' : 'foxes'} jumps over the lazy dog".equals(input))
      {
         return "The quick brown fox jumps over the lazy dog";
      }
      else if ("http://#{service.host}:8080/#{service.context.path}/ping".equals(input))
      {
         return "http://example.com:8080/service/ping";
      }
      return input;
   }
}
