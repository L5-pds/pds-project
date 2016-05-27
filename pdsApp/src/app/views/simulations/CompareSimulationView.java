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
import java.text.DecimalFormat;

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
  private JPanel tablePanel;
  private JTable tab;

  private Double rate;
  private Double wage;
  private DecimalFormat df = new DecimalFormat("#.00");


  public CompareSimulationView(CompareSimulationController cci, JPanel body, Container cont) {
    df.setMaximumFractionDigits(2);
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
    simulationLabel.setText("Selectionnez les simulations à comparer (2 à 5 simulations)");

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
          Object[] columnNames = {"Id", "Date", "Label", "Montant", "Durée", ""};
          tab = table(data, columnNames, true);
          JScrollPane scrollPane = new JScrollPane(tab);
          globalPanel.add(scrollPane);
          globalPanel.add(compareButton);
        }
        else{
          errLabel.setText("Pas de simulations trouvées.");
          globalPanel.add(errLabel);
        }
      break;

      case "compare" :
        int simIndex=0;
        Boolean goCompare=true;
        String[][] checked = new String[5][3];
        for (int i = 0; i < tab.getRowCount(); i++) {
          Boolean isChecked = (Boolean)tab.getValueAt(i, 5);
          if (isChecked){
            if (simIndex < 5){
              String[] r = {tab.getValueAt(i, 0).toString(), tab.getValueAt(i, 3).toString(), tab.getValueAt(i, 4).toString()};
              checked[simIndex] =r;
              simIndex++;
            }
            else{
              goCompare = false;
              errLabel.setText("Vous ne pouvez pas comparer plus de 5 simulations");
              globalPanel.add(errLabel);
            }
          }
        }
        if(simIndex < 2){
          goCompare = false;
          errLabel.setText("Au moins deux simulations doivent être selectionnées");
          globalPanel.add(errLabel);
        }
        if(goCompare)
          compare(simIndex, checked);
      break;
    }
    cont.revalidate();
    cont.repaint();
  }

  public JTable table(Object[][] data, Object[] columnNames, Boolean bool){
    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    JTable table = new JTable(model) {
      private final long serialVersionUID = 1L;
      public Class getColumnClass(int column) {
        if(bool){
          switch (column) {
            case 5:
              return Boolean.class;
            default:
              return String.class;
          }
        }else{
          return String.class;
        }
      }
    };
    table.setPreferredScrollableViewportSize(table.getPreferredSize());
    if(bool){
      for (int i = 0; i < table.getRowCount(); i++) {
        table.setValueAt(false, i, 5);
      }
    }

    return table;
  }

  public void compare(int simIndex,String[][] checked){
    globalPanel.removeAll();
    body.remove(globalPanel);

    tablePanel = new JPanel();
    tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));

    Object[][] data =  new Object[simIndex][8];
    for (int i=0; i<simIndex; i++){
      String rowId = checked[i][0];
      String rowAmount = checked[i][1];
      String rowRem = String.valueOf(Double.parseDouble(rowAmount) * (1 + rate/100));
      String rowPeriod = checked[i][2];
      String rowMonthly = String.valueOf(Double.parseDouble(rowRem) / Double.parseDouble(rowPeriod));
      String rowRate = String.valueOf(rate);
      String rowInterest = String.valueOf(Double.parseDouble(rowAmount) * rate/100);
      String rowRatio = String.valueOf(Double.parseDouble(rowMonthly) / wage);
      String[] row = {rowId, rowAmount, df.format(Double.parseDouble(rowRem)), df.format(Double.parseDouble(rowMonthly)), rowPeriod, rowRate, df.format(Double.parseDouble(rowInterest)), new DecimalFormat("0.##").format(Double.parseDouble(rowRatio))};
      data[i] = row;
    }

    simulationLabel.setText("Resultats");
    Object[] columnNames = {"Id", "Montant total", "Total à rembourser", "Mensualité", "Durée (en mois)", "Taux d'intérêt", "Total des intérêts", "Niveau d'endettement"};
    tab = table(data, columnNames, false);
    JScrollPane scrollPane = new JScrollPane(tab);

    tablePanel.add(simulationLabel);
    tablePanel.add(scrollPane);
    tablePanel.setPreferredSize(new Dimension(1200,130));
    tablePanel.setOpaque(false);
    scrollPane.setOpaque(false);
    body.setOpaque(false);

    body.add(tablePanel);
  }
}
