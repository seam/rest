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
package org.jboss.seam.resteasy.validation;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.annotations.providers.jaxb.IgnoreMediaTypes;

@XmlRootElement(name = "error")
@IgnoreMediaTypes("text/plain")
// TODO
public class ValidationErrorMessageWrapper
{
   private List<String> messages = new ArrayList<String>();

   public ValidationErrorMessageWrapper()
   {
   }

   @XmlElementWrapper(name="messages")
   @XmlElement(name = "message")
   public List<String> getMessages()
   {
      return messages;
   }

   public void addMessage(String message)
   {
      messages.add(message);
   }

   @Override
   public String toString()
   {
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < messages.size(); i++)
      {
         if (i > 0)
         {
            // get rid of trailing newline
            builder.append("\n");
         }
         builder.append(messages.get(i));
      }
      return builder.toString();
   }

}