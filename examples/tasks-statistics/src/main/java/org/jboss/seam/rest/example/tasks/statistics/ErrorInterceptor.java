package org.jboss.seam.rest.example.tasks.statistics;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.ClientErrorInterceptor;

public class ErrorInterceptor implements ClientErrorInterceptor
{
   public static final FacesMessage NOT_FOUND = new FacesMessage("Remote service not found. (404)");
   public static final FacesMessage ERROR = new FacesMessage("Error connecting to remote server.");
   
   public void handle(ClientResponse<?> response) throws RuntimeException
   {
      if (Status.NOT_FOUND.equals(response.getResponseStatus()))
      {
         FacesContext.getCurrentInstance().addMessage(null, NOT_FOUND);
      }
      else
      {
         FacesContext.getCurrentInstance().addMessage(null, ERROR);
      }
   }
}
