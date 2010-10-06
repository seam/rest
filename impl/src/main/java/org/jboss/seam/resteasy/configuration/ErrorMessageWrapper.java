package org.jboss.seam.resteasy.configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.annotations.providers.jaxb.IgnoreMediaTypes;

@XmlRootElement(name = "error")
@IgnoreMediaTypes("text/*")
// TODO
public class ErrorMessageWrapper
{
   private String message;

   public ErrorMessageWrapper()
   {
      // JAXB requires no-arg constructor
   }

   public ErrorMessageWrapper(String message)
   {
      this.message = message;
   }

   @XmlElement(name = "message")
   public String getMessage()
   {
      return message;
   }

   public void setMessage(String message)
   {
      this.message = message;
   }

   @Override
   public String toString()
   {
      return message;
   }
}