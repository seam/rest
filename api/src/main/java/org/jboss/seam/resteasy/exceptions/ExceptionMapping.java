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

/**
 * Represents a mapping of an exception to an HTTP status code and response body.
 * 
 * @author Jozef Hartinger
 *
 */
public class ExceptionMapping
{
   private Class<? extends Throwable> exceptionType;
   private int statusCode;
   private String message;
   private boolean interpolateMessageBody = true;

   public ExceptionMapping()
   {
   }
   
   public ExceptionMapping(Class<? extends Throwable> exceptionType, int statusCode)
   {
      this.exceptionType = exceptionType;
      this.statusCode = statusCode;
   }

   public ExceptionMapping(Class<? extends Throwable> exceptionType, int statusCode, String message)
   {
      this(exceptionType, statusCode);
      this.message = message;
   }
   
   public ExceptionMapping(Class<? extends Throwable> exceptionType, int statusCode, String message, boolean interpolateMessageBody)
   {
      this(exceptionType, statusCode, message);
      this.interpolateMessageBody = interpolateMessageBody;
   }

   public Class<? extends Throwable> getExceptionType()
   {
      return exceptionType;
   }

   public void setExceptionType(Class<? extends Throwable> exceptionType)
   {
      this.exceptionType = exceptionType;
   }

   public int getStatusCode()
   {
      return statusCode;
   }

   public void setStatusCode(int statusCode)
   {
      this.statusCode = statusCode;
   }

   public String getMessage()
   {
      return message;
   }

   public void setMessage(String message)
   {
      this.message = message;
   }

   public boolean isInterpolateMessageBody()
   {
      return interpolateMessageBody;
   }

   public void setInterpolateMessageBody(boolean interpolateMessageBody)
   {
      this.interpolateMessageBody = interpolateMessageBody;
   }

   @Override
   public String toString()
   {
      return "ExceptionMapping: " + exceptionType.getCanonicalName() + " --> (" + statusCode + ", " + message + ")";
   }
}
