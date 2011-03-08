package org.jboss.seam.rest.example.se;

import java.awt.EventQueue;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.seam.rest.example.se.ui.MainWindow;
import org.jboss.weld.environment.se.events.ContainerInitialized;

@Singleton
public class Main
{
   @Inject
   private MainWindow window;
   
   public void init(@Observes ContainerInitialized event)
   {
      EventQueue.invokeLater(new Runnable()
      {
         public void run()
         {
            try
            {
               window.getFrame().setVisible(true);
            }
            catch (Exception e)
            {
               e.printStackTrace();
            }
         }
      });
   }
}
