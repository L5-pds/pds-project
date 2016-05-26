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
  private JLabel simulationLabel;
  private JLabel selectClientLabel;
  private JLabel selectTypeLabel;
  private JLabel errLabel;
  private RoundJTextField nameField;
  private RoundJButton searchButton;
  private RoundJButton validateButton;
  private RoundJButton compareButton;
  private JPanel globalPanel;
  private JTable tab;

  private Double rate;
  private Double wage;

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
    simulationLabel = new JLabel();
    errLabel = new JLabel();
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

    simulationLabel.setFont(new java.awt.Font("Verdana", 0, 25));
    simulationLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    simulationLabel.setText("Selectionnez les simulations à comparer");

    errLabel.setFont(new java.awt.Font("Verdana", 0, 25));
    errLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

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
        nextStep("compare");
      }
    });

    cbClient = new JComboBox();

    String[] loanTypes = {"AUTOMOBILE", "CONSOMMATION","IMMOBILIER"};
    cbType = new JComboBox(loanTypes);

    globalPanel.setLayout(new GridLayout(9, 1, 0, 0));
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
        globalPanel.remove(errLabel);

        if (nameField.getText() != null && !nameField.getText().isEmpty())
          clients = cci.getCustomers(nameField.getText());
        else
          clients = null;
        for( ActionListener al : cbClient.getActionListeners() )
          cbClient.removeActionListener(al);

        cbClient.removeAllItems();
        if(clients != null && clients.size() != 0){
          for(int i=0; i< clients.size(); i++){
            String[] tmp = clients.get(i);
            cbClient.addItem(new Item(Integer.parseInt(tmp[0]), tmp[1] +" "+tmp[2]+" - "+tmp[3], Double.parseDouble(tmp[4])));
          }
          nextStep("client");
          globalPanel.add(selectClientLabel);
          globalPanel.add(cbClient);
        }
        else{
          globalPanel.remove(selectClientLabel);
          globalPanel.remove(cbClient);
          errLabel.setText("aucun client trouvé");
          globalPanel.add(errLabel);
        }
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
        System.out.println();
        Item selected = (Item)cbClient.getSelectedItem();
        wage = selected.getWage();
        simulations = cci.getSimulations(selected.getId(), cbType.getSelectedItem().toString());
        Object[][] data =  new Object[simulations.size() - 1][6];
        String[] tmp = simulations.get(simulations.size() -1);
        rate =  Double.parseDouble(tmp[0]);
        for(int i=0; i< simulations.size() - 1; i++){
          String[] tmp2 = simulations.get(i);
          data[i] = tmp2;
        }

        if(data.length != 0){
          globalPanel.removeAll();
          globalPanel.add(simulationLabel);
          tab = table(data);
          JScrollPane scrollPane = new JScrollPane(tab);
          globalPanel.add(scrollPane);
          globalPanel.add(compareButton);
        }
        else{
          errLabel.setText("Pas de simulations trouvées.");
          globalPanel.add(errLabel);
        }
        cont.revalidate();
        cont.repaint();
      break;

      case "compare" :
        int simIndex=0;
        String[] checked = new String[5];
        for (int i = 0; i < tab.getRowCount(); i++) {
          Boolean isChecked = (Boolean)tab.getValueAt(i, 5);
          if (isChecked){
            if (simIndex < 5){
              checked[simIndex]=tab.getValueAt(i, 0).toString();
              simIndex++;
            }
            else{
              System.out.println("Err : 5 simulations max!");
            }
          }
        }
        System.out.println("rate : "+rate);
        System.out.println("wage : "+wage);

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
    for (int i = 0; i < table.getRowCount(); i++) {
      table.setValueAt(false, i, 5);
    }

    return table;
  }
}
