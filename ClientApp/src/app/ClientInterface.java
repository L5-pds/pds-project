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
  private JTextField tb_password;
  private JTextField tb_login;
  private static Socket socket;
  private PrintWriter out = null;
  private BufferedReader in = null;
  public static String login = null, pass = null;
  @SuppressWarnings("unused")
  private boolean connect;
  private JTextField txtNom;
  private JTextField txtPrenom;
  private JTextField txtMail;
  private JTextField txtNumero;
  private JTextField txtRue;
  private JTextField txtCodepostal;
  private JLabel lblInfoserver;

  //Create the frame.
  public ClientInterface(Socket socket_temp) {

    socket = socket_temp;

    connect=false;

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 529, 233);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    create_IHM();

    //Event Click on the button "OK"
    JButton TB_Confirm_Connect = new JButton("OK");
    TB_Confirm_Connect.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {

        try {

        out = new PrintWriter(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        login = tb_login.getText();
        pass = tb_password.getText();

        //Convert Class to Json format
        Serialization sp = new Serialization();
        User userConnection = new User(login,pass);

        //Send information in Json format to server
        out.println(sp.serialiser(userConnection));
        out.flush();

        //Waiting the answer
        String Reponse_Connect=in.readLine();

        if(Reponse_Connect.equals("connect_ok")){
          //if success then load IHM for add new client
          tb_login.setBackground(new Color(0, 204, 102));
          tb_password.setBackground(new Color(0, 204, 102));
          connect = true;
          tb_login.setEnabled(false);
          tb_password.setEnabled(false);
          TB_Confirm_Connect.setEnabled(false);
          create_IHM_new();
          setBounds(100, 100, 529, 431);
            }

        else {
          //else informe unsucess authentification and close the app
            javax.swing.JOptionPane.showMessageDialog(null,"Erreur: " + Reponse_Connect, "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            }

        } catch (IOException e) {
          javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus", "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE);
        }


      }
    });
    TB_Confirm_Connect.setBounds(436, 56, 56, 77);
    contentPane.add(TB_Confirm_Connect);

    //Event Click on the button "Valider"
    JButton TB_Valider = new JButton("Valider");
    TB_Valider.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {

        //Convert new client information to Json format
        Serialization sp = new Serialization();
        Customer newCustomer = new Customer(txtNom.getText(),
            txtPrenom.getText(),
            txtMail.getText(),
            txtNumero.getText(),
            txtRue.getText(),
            txtCodepostal.getText());

        //Send new client to server
        out.println(sp.serialiser(newCustomer));
        out.flush();

        try {
          //Waiting the anwser
        String Reponse_Add=in.readLine();

        if(Reponse_Add.equals("OK")){
          //if answer is "add_ok" then informe client app and clear all composant
          lblInfoserver.setText("Client " + txtNom.getText() + " " + txtPrenom.getText() + " est ajouté");
          txtNom.setText("");
          txtPrenom.setText("");
          txtMail.setText("");
          txtNumero.setText("");
          txtRue.setText("");
          txtCodepostal.setText("");
            }

        else {
          //else informe client app whith the server answer
          lblInfoserver.setText("Erreur: " + Reponse_Add);
            }

        } catch (IOException e) {
          javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus", "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE);
          System.exit(0);
        }

      }
    });
    TB_Valider.setBounds(362, 328, 130, 29);
    contentPane.add(TB_Valider);
  }

  private void create_IHM_new(){
    JLabel lblNom = new JLabel("Nom");
    lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
    lblNom.setBounds(25, 184, 69, 20);
    contentPane.add(lblNom);

    JLabel lblPrnom = new JLabel("Prénom");
    lblPrnom.setHorizontalAlignment(SwingConstants.RIGHT);
    lblPrnom.setBounds(25, 220, 69, 20);
    contentPane.add(lblPrnom);

    JLabel lblMail = new JLabel("Mail");
    lblMail.setHorizontalAlignment(SwingConstants.RIGHT);
    lblMail.setBounds(25, 256, 69, 20);
    contentPane.add(lblMail);

    txtNom = new JTextField();
    txtNom.setText("");
    txtNom.setBounds(98, 181, 394, 26);
    contentPane.add(txtNom);
    txtNom.setColumns(10);

    txtPrenom = new JTextField();
    txtPrenom.setText("");
    txtPrenom.setBounds(98, 217, 394, 26);
    contentPane.add(txtPrenom);
    txtPrenom.setColumns(10);

    txtMail = new JTextField();
    txtMail.setText("");
    txtMail.setBounds(98, 253, 394, 26);
    contentPane.add(txtMail);
    txtMail.setColumns(10);

    JLabel lblAdresse = new JLabel("Adresse");
    lblAdresse.setHorizontalAlignment(SwingConstants.RIGHT);
    lblAdresse.setBounds(25, 289, 69, 20);
    contentPane.add(lblAdresse);

    txtNumero = new JTextField();
    txtNumero.setText("");
    txtNumero.setBounds(98, 286, 56, 26);
    contentPane.add(txtNumero);
    txtNumero.setColumns(10);

    txtRue = new JTextField();
    txtRue.setText("");
    txtRue.setBounds(169, 286, 220, 26);
    contentPane.add(txtRue);
    txtRue.setColumns(10);

    txtCodepostal = new JTextField();
    txtCodepostal.setText("");
    txtCodepostal.setBounds(404, 286, 88, 26);
    contentPane.add(txtCodepostal);
    txtCodepostal.setColumns(10);

    lblInfoserver = new JLabel("");
    lblInfoserver.setBounds(98, 332, 251, 20);
    contentPane.add(lblInfoserver);
  }

  private void create_IHM() {
    ImageIcon   trait = new ImageIcon(this.getClass().getResource("/pictures/LogoL5.png"));
    Image         im  =  trait.getImage();
    int hauteur = 152;
    int largeur = 250;
    im  = im.getScaledInstance(largeur,hauteur,1);
    JLabel image = new JLabel(  new ImageIcon(im));
    image.setBounds(15, 16, 250, 152);
    contentPane.add(image);

    tb_password = new JPasswordField();
    tb_password.setBounds(280, 107, 146, 26);
    contentPane.add(tb_password);
    tb_password.setColumns(10);

    tb_login = new JTextField();
    tb_login.setColumns(10);
    tb_login.setBounds(280, 56, 146, 26);
    contentPane.add(tb_login);

    JLabel lblPseudo = new JLabel("Pseudo");
    lblPseudo.setBounds(280, 37, 69, 20);
    contentPane.add(lblPseudo);

    JLabel lblPassword = new JLabel("Password");
    lblPassword.setBounds(280, 88, 69, 20);
    contentPane.add(lblPassword);
  }
}
