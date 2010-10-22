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
package org.jboss.seam.resteasy.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;

//import org.jboss.weld.extensions.el.Expressions;

@ApplicationScoped
public class ExpressionLanguageInterpolator implements Interpolator
{
   public static final Pattern elPattern = Pattern.compile("(#\\{.*?\\})");
//   @Inject
//   private Expressions el;

   public String interpolate(String input)
   {
      Matcher matcher = elPattern.matcher(input);
      StringBuffer buffer = new StringBuffer();

      while (matcher.find())
      {
//         matcher.appendReplacement(buffer, String.valueOf(el.evaluateValueExpression(matcher.group())));
         matcher.appendReplacement(buffer, "Interpolator disabled.");
      }
      matcher.appendTail(buffer);

      return buffer.toString();
   }
}
