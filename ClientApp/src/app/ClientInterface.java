package app;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;

public class ClientInterface extends JFrame {

  private static final long serialVersionUID = 1L;
  private JPanel contentPane;
  private JTextField loginField;
  private JTextField pwdField;
  private static Socket socket;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private boolean connection;
  private JTextField lastName;
  private JTextField firstName;
  private JTextField mail;
  private JTextField addressNumber;
  private JTextField street;
  private JTextField zipCode;
  private JLabel serverInfo;

  //Create the frame.
  public ClientInterface(Socket sck) {

    socket = sck;

    connection=false;

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 529, 233);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    authenticationIhm();

    //Event Click on the button "OK"
    JButton connectionButton = new JButton("OK");
    connectionButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        try {
          out = new PrintWriter(socket.getOutputStream());
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

          User userConnection = new User(loginField.getText(), pwdField.getText());

          //Convert Class to Json format
          Serialization s = new Serialization();

          //Send information in Json format to server
          out.println(s.serialize(userConnection));
          out.flush();

          //Waiting the answer
          String serverAnswer=in.readLine();

          if(serverAnswer.equals("connect_ok")){
            //if success then load IHM for add new client
            loginField.setBackground(new Color(0, 204, 102));
            pwdField.setBackground(new Color(0, 204, 102));
            connection = true;
            loginField.setEnabled(false);
            pwdField.setEnabled(false);
            connectionButton.setEnabled(false);
            createCustomerIhm();
            setBounds(100, 100, 529, 431);
          }
          else {
            //else informe unsucess authentification and close the app
            javax.swing.JOptionPane.showMessageDialog(null,"Erreur: " + serverAnswer, "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.exit(0);
          }
        } catch (IOException e) {
          javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus", "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE);
        }


      }
    });
    connectionButton.setBounds(436, 56, 56, 77);
    contentPane.add(connectionButton);

    //Event Click on the button "Valider"
    JButton sendCustomerButton = new JButton("Valider");
    sendCustomerButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        //Convert new client information to Json format
        Serialization s = new Serialization();
        Customer newCustomer = new Customer(
          lastName.getText(),
          firstName.getText(),
          mail.getText(),
          addressNumber.getText(),
          street.getText(),
          zipCode.getText()
        );

        //Send new client to server
        out.println(s.serialize(newCustomer));
        out.flush();

        try {
          //Waiting the anwser
          String serverAnswer=in.readLine();

          if(serverAnswer.equals("OK")){
            //if the answer is "OK" then informe client app and clear all composant
            serverInfo.setText("Client " + lastName.getText() + " " + firstName.getText() + " est ajouté");
            lastName.setText("");
            firstName.setText("");
            mail.setText("");
            addressNumber.setText("");
            street.setText("");
            zipCode.setText("");
          }
          else {
            //else inform client app with the server answer
            serverInfo.setText("Erreur: " + serverAnswer);
          }

        } catch (IOException e) {
          javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus", "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE);
          System.exit(0);
        }

      }
    });
    sendCustomerButton.setBounds(362, 328, 130, 29);
    contentPane.add(sendCustomerButton);
  }

  private void createCustomerIhm(){
    JLabel lastNameLabel = new JLabel("Nom");
    lastNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    lastNameLabel.setBounds(25, 184, 69, 20);
    contentPane.add(lastNameLabel);

    JLabel firstNameLabel = new JLabel("Prénom");
    firstNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    firstNameLabel.setBounds(25, 220, 69, 20);
    contentPane.add(firstNameLabel);

    JLabel mailLabel = new JLabel("Mail");
    mailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    mailLabel.setBounds(25, 256, 69, 20);
    contentPane.add(mailLabel);

    lastName = new JTextField();
    lastName.setText("");
    lastName.setBounds(98, 181, 394, 26);
    contentPane.add(lastName);
    lastName.setColumns(10);

    firstName = new JTextField();
    firstName.setText("");
    firstName.setBounds(98, 217, 394, 26);
    contentPane.add(firstName);
    firstName.setColumns(10);

    mail = new JTextField();
    mail.setText("");
    mail.setBounds(98, 253, 394, 26);
    contentPane.add(mail);
    mail.setColumns(10);

    JLabel addressLabel = new JLabel("Adresse");
    addressLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    addressLabel.setBounds(25, 289, 69, 20);
    contentPane.add(addressLabel);

    addressNumber = new JTextField();
    addressNumber.setText("");
    addressNumber.setBounds(98, 286, 56, 26);
    contentPane.add(addressNumber);
    addressNumber.setColumns(10);

    street = new JTextField();
    street.setText("");
    street.setBounds(169, 286, 220, 26);
    contentPane.add(street);
    street.setColumns(10);

    zipCode = new JTextField();
    zipCode.setText("");
    zipCode.setBounds(404, 286, 88, 26);
    contentPane.add(zipCode);
    zipCode.setColumns(10);

    serverInfo = new JLabel("");
    serverInfo.setBounds(98, 332, 251, 20);
    contentPane.add(serverInfo);
  }

  private void authenticationIhm() {
    ImageIcon trait = new ImageIcon(this.getClass().getResource("/pictures/LogoL5.png"));
    Image im = trait.getImage();
    int width = 250;
    int height = 152;
    im  = im.getScaledInstance(width,height,1);
    JLabel image = new JLabel(  new ImageIcon(im));
    image.setBounds(15, 16, 250, 152);
    contentPane.add(image);

    pwdField = new JPasswordField();
    pwdField.setBounds(280, 107, 146, 26);
    contentPane.add(pwdField);
    pwdField.setColumns(10);

    loginField = new JTextField();
    loginField.setColumns(10);
    loginField.setBounds(280, 56, 146, 26);
    contentPane.add(loginField);

    JLabel peudoLabel = new JLabel("Pseudo");
    peudoLabel.setBounds(280, 37, 69, 20);
    contentPane.add(peudoLabel);

    JLabel passwordLabel = new JLabel("Password");
    passwordLabel.setBounds(280, 88, 69, 20);
    contentPane.add(passwordLabel);
  }
}
