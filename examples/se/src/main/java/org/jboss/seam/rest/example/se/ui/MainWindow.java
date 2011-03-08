package org.jboss.seam.rest.example.se.ui;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import org.jboss.seam.rest.example.se.ConnectionException;
import org.jboss.seam.rest.example.se.StatusException;
import org.jboss.seam.rest.example.se.action.SearchAction;
import org.jboss.seam.rest.example.se.model.SearchResultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

@Singleton
public class MainWindow
{

   private JFrame frame;
   private JPanel panel;
   private JTable table;
   private JButton btnNewButton;
   private JTextField textField;
   private JLabel label;
   private JLabel lblEnterZipCode;

   /**
    * Initialize the contents of the frame.
    * @wbp.parser.entryPoint
    */
   @Inject
   public void initialize(final SearchResultTableModel model, final SearchAction action)
   {
      frame = new JFrame();
      frame.setBounds(100, 100, 535, 227);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().setLayout(new BorderLayout(0, 0));
      frame.setTitle("Seam REST SE Example");
      
      panel = new JPanel();
      frame.getContentPane().add(panel, BorderLayout.NORTH);
      panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
      
      lblEnterZipCode = new JLabel("Enter zip code:");
      panel.add(lblEnterZipCode);
      
      textField = new JTextField();
      panel.add(textField);
      textField.setColumns(10);
      
      btnNewButton = new JButton("Search");
      btnNewButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try
            {
               action.search(textField.getText());
               model.fireTableDataChanged();
               label.setText("");
            }
            catch (IllegalArgumentException iae)
            {
               label.setText("Incorrect ZIP format.");
            }
            catch (ConnectionException ce)
            {
               label.setText("Connection problem.");
            }
            catch (StatusException se)
            {
               label.setText(se.getMessage());
            }
         }
      });
      
      label = new JLabel("");
      label.setForeground(Color.RED);
      panel.add(label);
      panel.add(btnNewButton);
      
      table = new JTable(model);
      
      JScrollPane scrollPane = new JScrollPane(table);
      table.setFillsViewportHeight(true);

      frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
   }

   public JFrame getFrame()
   {
      return frame;
   }
}
