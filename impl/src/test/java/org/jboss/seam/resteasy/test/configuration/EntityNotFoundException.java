package org.jboss.seam.resteasy.test.configuration;

public class EntityNotFoundException extends RuntimeException
{
   private static final long serialVersionUID = 1L;

   public EntityNotFoundException()
   {
   }

   public EntityNotFoundException(String message, Throwable cause)
   {
      super(message, cause);
   }

   public EntityNotFoundException(String message)
   {
      super(message);
   }

   public EntityNotFoundException(Throwable cause)
   {
      super(cause);
   }
}
