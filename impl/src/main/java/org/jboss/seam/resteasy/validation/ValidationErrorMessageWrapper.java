package org.jboss.seam.resteasy.validation;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
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

   @XmlElement(name = "messages")
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