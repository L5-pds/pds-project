package app.views.welcome;

import app.controllers.WelcomeController;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Authentication extends JPanel {
  WelcomeController wc;
  private String answer="";
  private JLabel answerLabel;

  private JTextField loginField;
  private JTextField pwdField;
  private JLabel peudoLabel = new JLabel("Pseudo");
  private JLabel passwordLabel = new JLabel("Mot de passe");
  private JButton validate = new JButton("Valider");

  public Authentication(WelcomeController wc) {
    this.wc = wc;
    authenticationIhm();
    validate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        answer = wc.connection(loginField.getText(), pwdField.getText());
        update(answer);
      }
    });
  }

  private void authenticationIhm() {
    this.setLayout(new GridLayout(3, 2));
    pwdField = new JPasswordField();
    loginField = new JTextField();
    answerLabel = new JLabel("");
    pwdField.setColumns(10);
    loginField.setColumns(10);
    this.add(peudoLabel);
    this.add(passwordLabel);
    this.add(loginField);
    this.add(pwdField);
    this.add(validate);
    this.add(answerLabel);
  }

  private void update(String answer){
    if (answer.equals("authentic")){
      this.removeAll();
      answerLabel.setText("Bienvenu " + loginField.getText());
      this.add(answerLabel);
    }
    else if(answer.equals("disconnected")){
      this.removeAll();
      answerLabel.setText("Le serveur ne répond plus");
      this.add(answerLabel);
    }
    else{
      answerLabel.setText(answer);
    }

    this.revalidate();
    this.repaint();
  }
}
