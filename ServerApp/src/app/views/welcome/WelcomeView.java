package app.views.welcome;

import app.listeners.WelcomeListener;
import app.controllers.WelcomeController;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomeView extends JFrame implements WelcomeListener{
  private WelcomeController controller;
  private final long serialVersionUID = 1L;
  private JPanel contentPane;
  private TextArea lblLogServer = new TextArea();
  private JButton btnStartServer = new JButton("Start server");
  private JLabel userNbLabel;
  private JLabel availableNbLabel;

  public WelcomeView(WelcomeController wc) {
    this.controller = wc;
    printIHM();
  }

  public void changeTextLog(String addText) {
    lblLogServer.setText(lblLogServer.getText() + addText + System.getProperty("line.separator"));
  }

  public void updateInfoLabel() {
    int connected = 0;
    int available = 0;
    for(int i=0; i< controller.server.connectionPool.length; i++) {
      if (controller.server.connectionPool[i].isUsed() == true)
        connected++;
      else
        available++;
    }

    userNbLabel.setText(connected + " connexions");
    availableNbLabel.setText(available + " disponibles");
  }

  private void printIHM() {
    setTitle("Serveur");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 800, 800);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);
    lblLogServer.setBounds(10, 91, 760, 645);
    lblLogServer.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 15));
    contentPane.add(lblLogServer);

    btnStartServer.setFont(new Font("Tahoma", Font.PLAIN, 25));
    btnStartServer.setBorder(null);
    btnStartServer.setBackground(Color.LIGHT_GRAY);
    btnStartServer.setBounds(10, 11, 549, 69);
    btnStartServer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
          controller.launch();
      }
    });
    contentPane.add(btnStartServer);

    userNbLabel = new JLabel("0 utilis\u00E9e");
    userNbLabel.setHorizontalAlignment(SwingConstants.CENTER);
    userNbLabel.setBounds(574, 24, 189, 20);
    contentPane.add(userNbLabel);

    availableNbLabel = new JLabel("0 disponible");
    availableNbLabel.setHorizontalAlignment(SwingConstants.CENTER);
    availableNbLabel.setBounds(574, 45, 189, 20);
    contentPane.add(availableNbLabel);
  }
}
