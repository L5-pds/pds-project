package app.views.simulations;

import app.controllers.*;
import app.helpers.*;
import app.listeners.*;
import app.models.component.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.*;

public class CompareSimulationView implements CompareSimulationListener{

  private CompareSimulationController cci;
  private JPanel body;
  private Container cont;
  private JComboBox cbClient;
  private JComboBox cbType;
  private ArrayList<String[]> clients;
  private ArrayList<String[]> simulations;
  private JLabel nameLabel;
  private JLabel selectClientLabel;
  private JLabel selectTypeLabel;
  private RoundJTextField nameField;
  private RoundJButton searchButton;
  private RoundJButton validateButton;
  private RoundJButton compareButton;
  private JPanel globalPanel;

  public CompareSimulationView(CompareSimulationController cci, JPanel body, Container cont) {
    this.cci = cci;
    this.body = body;
    this.cont = cont;
  }

  public void setIHM() {
    body.removeAll();

    nameLabel = new JLabel();
    selectClientLabel = new JLabel();
    selectTypeLabel = new JLabel();
    nameField = new RoundJTextField(20);
    searchButton = new RoundJButton();
    compareButton = new RoundJButton();
    validateButton = new RoundJButton();
    globalPanel = new JPanel();

    nameLabel.setFont(new java.awt.Font("Verdana", 0, 25));
    nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    nameLabel.setText("Nom du client");

    selectClientLabel.setFont(new java.awt.Font("Verdana", 0, 25));
    selectClientLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    selectClientLabel.setText("Selectionnez un client");

    selectTypeLabel.setFont(new java.awt.Font("Verdana", 0, 25));
    selectTypeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    selectTypeLabel.setText("Selectionnez un type de prêt");

    nameField.setFont(new java.awt.Font("Verdana", 0, 30));
    nameField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

    searchButton.setText("Chercher");
    searchButton.addActionListener(new ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        nextStep("name");
      }
    });

    validateButton.setText("Valider");
    validateButton.addActionListener(new ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        nextStep("list");
      }
    });

    compareButton.setText("Comparer");
    compareButton.addActionListener(new ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Click !");
      }
    });

    cbClient = new JComboBox();

    String[] loanTypes = {"AUTOMOBILE", "CONSOMMATION","IMMOBILIER"};
    cbType = new JComboBox(loanTypes);

    globalPanel.setLayout(new GridLayout(8, 1, 0, 0));
    globalPanel.add(nameLabel);
    globalPanel.add(nameField);
    globalPanel.add(searchButton);

    globalPanel.setOpaque(false);

    body.setLayout(new FlowLayout());
    body.add(globalPanel);

    cont.revalidate();
    cont.repaint();
  }

  public void nextStep(String step){
    switch(step) {
      case "name" :
        globalPanel.remove(selectTypeLabel);
        globalPanel.remove(cbType);
        globalPanel.remove(validateButton);

        clients = cci.getCustomers(nameField.getText());
        for( ActionListener al : cbClient.getActionListeners() )
          cbClient.removeActionListener( al );

        cbClient.removeAllItems();
        for(int i=0; i< clients.size(); i++){
          String[] tmp = clients.get(i);
          cbClient.addItem(new Item(Integer.parseInt(tmp[0]), tmp[1] +" "+tmp[2]));
        }

        nextStep("client");
        globalPanel.add(selectClientLabel);
        globalPanel.add(cbClient);
        cont.revalidate();
        cont.repaint();
        break;

      case "client" :
        cbClient.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            globalPanel.add(selectTypeLabel);
            globalPanel.add(cbType);
            globalPanel.add(validateButton);
            cont.revalidate();
            cont.repaint();
          }
        });
      break;

      case "list" :
        Item selected = (Item)cbClient.getSelectedItem();
        simulations = cci.getSimulations(selected.getId(), cbType.getSelectedItem().toString());
        Object[][] data =  new Object[simulations.size()][6];;
        for(int i=0; i< simulations.size(); i++){
          String[] tmp = simulations.get(i);
          data[i] = tmp;
        }

        globalPanel.removeAll();
        JTable tab = table(data);
        JScrollPane scrollPane = new JScrollPane(tab);
        globalPanel.add(scrollPane);
        globalPanel.add(compareButton);
        cont.revalidate();
        cont.repaint();
      break;

      case "finish" :

      break;
    }
  }

  public JTable table(Object[][] data){
    Object[] columnNames = {"Id", "Date", "Label", "Montant", "Durée", ""};
    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    JTable table = new JTable(model) {
      private final long serialVersionUID = 1L;
      public Class getColumnClass(int column) {
        switch (column) {
          case 0:
            return String.class;
          case 1:
            return String.class;
          case 2:
            return String.class;
          case 3:
            return String.class;
          case 4:
            return String.class;
          default:
            return Boolean.class;
        }
      }
    };
    table.setPreferredScrollableViewportSize(table.getPreferredSize());

    return table;
  }
}
