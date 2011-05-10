package org.jboss.seam.rest.examples.client.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.jboss.seam.rest.examples.client.tasks.SeamTasksAction;

@Singleton
public class TasksPanel extends JPanel {
    private static final long serialVersionUID = -4386402265087492737L;
    private JPanel header;
    private JTable resultTable;
    private JButton button;
    private JLabel messageLabel;

    @Inject
    private SeamTasksAction action;
    @Inject
    private TasksResultTableModel model;

    @Inject
    public void initialize() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));

        header = new JPanel();
        add(header, BorderLayout.NORTH);
        header.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        button = new JButton("Load seam-tasks statistics");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonAction();
            }
        });

        header.add(button);

        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        header.add(messageLabel);

        resultTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        resultTable.setFillsViewportHeight(true);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void buttonAction() {
        try {
            action.loadStatistics();
            model.fireTableDataChanged();
            messageLabel.setText("");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            messageLabel.setText("Unable to connect to Seam Tasks");
        }
    }
}
