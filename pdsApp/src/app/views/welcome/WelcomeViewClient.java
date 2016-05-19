package app.views.welcome;

import app.controllers.*;
import app.listeners.*;
import app.models.component.*;

import javax.swing.*;
import java.awt.*;

public class WelcomeViewClient extends JFrame implements WelcomeListenerClient {
  //private JFrame frame;
  private Container contentPane;
  private JPanel header;
  private JPanel body;
  private JLabel answerLabel;

  WelcomeControllerClient wc;

  public WelcomeViewClient(WelcomeControllerClient wc) {
    this.wc = wc;
    template();
  }

  private void template(){
    setTitle("Gestion de banque");

    ImageIcon trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/LogoL5.png"));
    Image im = trait.getImage();
    im  = im.getScaledInstance(330,200,1);
    JLabel image = new JLabel( new ImageIcon(im));

    header = new JPanel();

    header.add(image);
    header.setBackground(new Color(215,203,233,200));

    body = new JPanel();
    body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
    body.setBackground(new Color(215,203,233,200));

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    contentPane = this.getContentPane();
    contentPane.add(body, BorderLayout.CENTER);
    contentPane.add(header, BorderLayout.NORTH);
    pack();

    setExtendedState(MAXIMIZED_BOTH);
    this.setMinimumSize(new Dimension(1000, 900));
    setVisible(true);
  }

  public void authenticationIhm() {
    body.removeAll();

    	JLabel labelPass = new JLabel();
    JLabel labelLogin = new JLabel();
    JPanel panelGlobal = new JPanel();
    RoundJTextField loginField = new RoundJTextField(20);
    RoundJPasswordField passField = new RoundJPasswordField(20);
    answerLabel = new JLabel();

    loginField.setFont(new java.awt.Font("Verdana", 0, 30)); // NOI18N
    loginField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    loginField.setText("");

    labelPass.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
    labelPass.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    labelPass.setText("Mot de passe");

    passField.setFont(new java.awt.Font("Verdana", 0, 30)); // NOI18N
    passField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    passField.setText("");

    labelLogin.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
    labelLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    labelLogin.setText("Nom d'utilisateur");

    answerLabel.setFont(new java.awt.Font("Verdana", 0, 15)); // NOI18N
    answerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    answerLabel.setText("");

    ImageIcon trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconConnection.png"));
    Image im = trait.getImage();
    im  = im.getScaledInstance(296,66,1);
    JLabel buttonvalide = new JLabel(new ImageIcon(im));

    buttonvalide.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            wc.getConnection(loginField.getText(), String.valueOf(passField.getPassword()));
        }
    });

    panelGlobal.setLayout(new GridLayout(7, 1, 0, 0));
    panelGlobal.add(labelLogin);
    panelGlobal.add(loginField);
    panelGlobal.add(labelPass);
    panelGlobal.add(passField);
    panelGlobal.add(answerLabel);
    panelGlobal.add(buttonvalide);
    panelGlobal.setOpaque(false);

    body.setLayout(new FlowLayout());
    body.add(panelGlobal);

    this.revalidate();
    this.repaint();
  }

  public void setMenu(){

    body.removeAll();

    JPanel panelGlobal = new JPanel();
    panelGlobal.setLayout(new GridLayout(6, 1, 0, 0));
    panelGlobal.setOpaque(false);

    JLabel labelTitle = new JLabel();
    labelTitle.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
    labelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    labelTitle.setText("     Menu du programme     ");
    panelGlobal.add(labelTitle);

    ImageIcon trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconSpeed.png"));
    Image im = trait.getImage();
    im  = im.getScaledInstance(50,50,1);
    JLabel buttonGoIndicator = new JLabel(new ImageIcon(im));
    buttonGoIndicator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wc.goIndicator();
            }
        });
    panelGlobal.add(buttonGoIndicator);

    JButton button2 = new javax.swing.JButton();
    button2.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    button2.setText("test_2");
    button2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAddress();
        }
    });
    panelGlobal.add(button2);

    JButton button3 = new javax.swing.JButton();
    button3.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    button3.setText("test_3");
    button3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAddress();
        }
    });
    panelGlobal.add(button3);

    JButton button4 = new javax.swing.JButton();
    button4.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    button4.setText("test_4");
    button4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAddress();
        }
    });
    panelGlobal.add(button4);

    JButton button5 = new javax.swing.JButton();
    button5.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    button5.setText("test_5");
    button5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAddress();
        }
    });
    panelGlobal.add(button5);


    body.setLayout(new FlowLayout());
    body.add(panelGlobal);

    this.revalidate();
    this.repaint();
  }

  public void setButtonBackMenu()   {
    ImageIcon trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/Home.png"));
    Image im = trait.getImage();
    im  = im.getScaledInstance(80,80,1);
    JLabel buttonBackMenu = new JLabel(new ImageIcon(im));
    buttonBackMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wc.menuBack();
            }
        });

    header.add(buttonBackMenu);
    this.revalidate();
    this.repaint();
  }

  public void updateAnswerLabel(String answer){
    this.answerLabel.setText(answer);
    this.revalidate();
    this.repaint();
  }

  public JPanel getBody()   {
    return this.body;
  }

  public Container getContainer()    {
    return this.getContentPane();
  }

  public void setErrorSocket()  {
    body.removeAll();
    JPanel panelGlobal = new JPanel();

    ImageIcon trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/errorSocket.png"));
    Image im = trait.getImage();
    im  = im.getScaledInstance(945,567,1);
    JLabel lblError = new JLabel(new ImageIcon(im));

    panelGlobal.add(lblError);
    panelGlobal.setOpaque(false);

    body.setLayout(new FlowLayout());
    body.add(panelGlobal);

    this.revalidate();
    this.repaint();
  }

}
