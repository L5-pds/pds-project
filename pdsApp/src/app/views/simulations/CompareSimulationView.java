package app.views.simulations;

import app.controllers.*;
import app.helpers.*;
import app.listeners.*;
import app.models.component.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class CompareSimulationView implements CompareSimulationListener{

  private CompareSimulationController cci;
  private JPanel body;
  private Container cont;
  private JComboBox cbClient;
  private JComboBox cbType;
  private ArrayList<String[]> clients;
  private JLabel nameLabel;
  private JLabel selectClientLabel;
  private JLabel selectTypeLabel;
  private RoundJTextField nameField;
  private RoundJButton searchButton;
  private RoundJButton validateButton;
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
        nextStep("finish");
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
        //clients = new ArrayList<String>();
        //clients.add(nameField.getText()+"1");
        //clients.add(nameField.getText()+"2");
        //clients.add(nameField.getText()+"3");

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

      case "finish" :
        String c = cbClient.getSelectedItem().toString();
        String t = cbType.getSelectedItem().toString();
        Item selected = (Item)cbClient.getSelectedItem();
        String id = String.valueOf(selected.getId());
        System.out.println("Finish : "+ id +" - "+ c + " - " + t);
      break;
    }
  }
}
