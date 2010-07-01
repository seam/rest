package org.jboss.seam.resteasy.configuration;

import javax.ws.rs.core.MediaType;

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
   private String messageBody;
   private MediaType mediaType = MediaType.TEXT_PLAIN_TYPE;

   public ExceptionMapping()
   {
   }
   
   public ExceptionMapping(Class<? extends Throwable> exceptionType, int statusCode)
   {
      this.exceptionType = exceptionType;
      this.statusCode = statusCode;
   }

   public ExceptionMapping(Class<? extends Throwable> exceptionType, int statusCode, String messageBody, MediaType mediaType)
   {
      this(exceptionType, statusCode);
      this.messageBody = messageBody;
      this.mediaType = mediaType;
   }
   
   public ExceptionMapping(Class<? extends Throwable> exceptionType, int statusCode, String messageBody, String mediaType)
   {
      this(exceptionType, statusCode, messageBody, MediaType.valueOf(mediaType));
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

   public String getMessageBody()
   {
      return messageBody;
   }

   public void setMessageBody(String messageBody)
   {
      this.messageBody = messageBody;
   }

   public MediaType getMediaType()
   {
      return mediaType;
   }

   public void setMediaType(MediaType mediaType)
   {
      this.mediaType = mediaType;
   }
}
