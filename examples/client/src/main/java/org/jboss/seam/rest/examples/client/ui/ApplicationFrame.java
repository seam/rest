package org.jboss.seam.rest.examples.client.ui;

import java.awt.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * The main application window, composed of {@link TasksPanel} and {@link ZipPanel}.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */

@Singleton
public class ApplicationFrame extends JFrame {
    private static final long serialVersionUID = -421735336400980498L;
    private JPanel contentPane;

    /**
     * Create the frame.
     */
    @Inject
    public ApplicationFrame(TasksPanel tasksPanel, ZipPanel zipPanel) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 300);
        setLocationRelativeTo(null);
        setTitle("Seam REST Client Application");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new GridLayout(1, 2, 20, 0));
        setContentPane(contentPane);
        contentPane.add(zipPanel);
        contentPane.add(tasksPanel);
    }
}
