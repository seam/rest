package org.jboss.seam.rest.examples.client.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.jboss.seam.rest.examples.client.ConnectionException;
import org.jboss.seam.rest.examples.client.StatusException;
import org.jboss.seam.rest.examples.client.geo.SearchAction;

@Singleton
public class ZipPanel extends JPanel {
    private static final long serialVersionUID = 4668520540534987240L;

    private JPanel header;
    private JTable results;
    private JButton searchButton;
    private JTextField searchQuery;
    private JLabel messageLabel;
    private JLabel searchLabel;

    @Inject
    private ZipSearchResultTableModel model;
    @Inject
    private SearchAction action;

    @Inject
    public void initialize() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));

        header = new JPanel();
        add(header, BorderLayout.NORTH);
        header.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        searchLabel = new JLabel("Enter zip code:");
        header.add(searchLabel);

        searchQuery = new JTextField();
        header.add(searchQuery);
        searchQuery.setColumns(10);

        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonAction();
            }
        });

        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        header.add(messageLabel);
        header.add(searchButton);

        results = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(results);
        results.setFillsViewportHeight(true);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void buttonAction() {
        try {
            action.search(searchQuery.getText());
            model.fireTableDataChanged();
            messageLabel.setText("");
        } catch (IllegalArgumentException iae) {
            messageLabel.setText("Incorrect ZIP format.");
        } catch (ConnectionException ce) {
            messageLabel.setText("Connection problem");
        } catch (StatusException se) {
            messageLabel.setText(se.getMessage());
        }
    }
}
