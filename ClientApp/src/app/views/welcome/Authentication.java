package app.views.welcome;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Authentication extends JPanel {
  private static Socket socket;
  private JTextField loginField;
  private JTextField pwdField;
  private JLabel peudoLabel = new JLabel("Pseudo");
  private JLabel passwordLabel = new JLabel("Mot de passe");
  private JButton validate = new JButton("Valider");

  public Authentication(Socket sck) {
    this.socket = sck;
    authenticationIhm();
  }

  private void authenticationIhm() {
    this.setLayout(new GridLayout(3, 2));
    pwdField = new JPasswordField();
    loginField = new JTextField();
    pwdField.setColumns(10);
    loginField.setColumns(10);
    this.add(peudoLabel);
    this.add(passwordLabel);
    this.add(pwdField);
    this.add(loginField);
    this.add(validate);
  }
}
