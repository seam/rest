package org.jboss.seam.rest.examples.client;

import java.awt.*;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.seam.rest.examples.client.ui.ApplicationFrame;
import org.jboss.weld.environment.se.events.ContainerInitialized;

@Singleton
public class Main {
    @Inject
    private ApplicationFrame frame;

    public void init(@Observes ContainerInitialized event) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
