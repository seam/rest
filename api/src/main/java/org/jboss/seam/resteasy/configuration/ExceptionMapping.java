package org.jboss.seam.resteasy.configuration;

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
}
