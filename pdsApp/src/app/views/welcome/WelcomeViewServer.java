package app.views.welcome;

import app.listeners.WelcomeListenerServer;
import app.controllers.WelcomeControllerServer;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WelcomeViewServer extends JFrame implements WelcomeListenerServer{
  private WelcomeControllerServer controller;
  private final long serialVersionUID = 1L;
  private JPanel contentPane;
  private TextArea lblLogServer = new TextArea();
  private JButton btnStartServer = new JButton("Start server");
  private JLabel userNbLabel;
  private JLabel availableNbLabel;

  public WelcomeViewServer(WelcomeControllerServer wc) {
    this.controller = wc;
    printIHM();
  }

  public void changeTextLog(String addText) {
    Locale locale = Locale.getDefault();
    Date actuelle = new Date();
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à HH:mm:ss | ");
    lblLogServer.setText(lblLogServer.getText() + dateFormat.format(actuelle) + addText + System.getProperty("line.separator"));
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

    userNbLabel.setText(connected + " used connection");
    availableNbLabel.setText(available + " available");
  }

  private void printIHM() {
    setTitle("Serveur");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 1600, 800);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(new BorderLayout());
    
    setMinimumSize(new Dimension(800, 400));
    setIconImage(new ImageIcon(this.getClass().getResource("/pictures/iconLogoAppServer.png")).getImage());
    
    JPanel header = new JPanel();
    header.setLayout(new GridLayout(0,2,5,5));
    
    JPanel h1 = new JPanel();
    h1.setLayout(new GridLayout(1,0,5,5));
    btnStartServer.setFont(new Font("Verdana", Font.PLAIN, 40));
    btnStartServer.setBorder(null);
    btnStartServer.setBackground(Color.RED);
    btnStartServer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
          controller.launch();
          btnStartServer.setText("Server online");
          btnStartServer.setBackground(Color.GREEN);
      }
    });
    h1.add(btnStartServer);
    
    JPanel h2 = new JPanel();
    h2.setLayout(new BoxLayout(h2, BoxLayout.Y_AXIS));
    userNbLabel = new JLabel("0 used connection");
    userNbLabel.setFont(new Font("Verdana", Font.PLAIN, 25));
    userNbLabel.setHorizontalAlignment(SwingConstants.CENTER);
    userNbLabel.setAlignmentX(CENTER_ALIGNMENT);
    availableNbLabel = new JLabel("0 available");
    availableNbLabel.setFont(new Font("Verdana", Font.PLAIN, 25));
    availableNbLabel.setHorizontalAlignment(SwingConstants.CENTER);
    availableNbLabel.setAlignmentX(CENTER_ALIGNMENT);
    h2.add(userNbLabel);
    h2.add(availableNbLabel);
    
    header.add(h1);
    header.add(h2);
    
    lblLogServer.setFont(new Font("Verdana", Font.CENTER_BASELINE, 15));

    contentPane.add(header, BorderLayout.NORTH);
    contentPane.add(lblLogServer, BorderLayout.CENTER);

    
  }
}
