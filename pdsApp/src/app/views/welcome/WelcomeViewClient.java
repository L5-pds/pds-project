package app.views.welcome;

import app.controllers.*;
import app.listeners.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import javax.swing.GroupLayout.Alignment;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.MatteBorder;

public class WelcomeViewClient extends JFrame implements WelcomeListenerClient {
  //private JFrame frame;
  private Container contentPane;
  private JPanel header;
  private JPanel body;
  private JLabel answerLabel;
  private JButton buttonBackMenu;

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
    
    setVisible(true);
  }

  public void authenticationIhm() {
    body.removeAll();
            
	JButton buttonValide = new JButton();
    	JLabel labelPass = new JLabel();
   	JLabel labelTitle = new JLabel();
    	JLabel labelLogin = new JLabel();
    	JPanel panelGlobal = new JPanel();
    	JTextField loginField = new JTextField();
    	JPasswordField passField = new JPasswordField();
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
        

        labelTitle.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        labelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitle.setText("Authentification");

        labelLogin.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
        labelLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelLogin.setText("Nom d'utilisateur");

        answerLabel.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
        answerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        answerLabel.setText("");
        
        buttonValide.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
        buttonValide.setText("Valider");
        
        panelGlobal.setLayout(new GridLayout(7, 1, 0, 0));
        panelGlobal.add(labelTitle);
        panelGlobal.add(labelLogin);
        panelGlobal.add(loginField);
        panelGlobal.add(labelPass);
        panelGlobal.add(passField);
        panelGlobal.add(answerLabel);
        panelGlobal.add(buttonValide);
        panelGlobal.setBackground(new Color(215,203,233,200));
        
    buttonValide.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        wc.getConnection(loginField.getText(), String.valueOf(passField.getPassword()));
      }
    });
        
    body.setLayout(new FlowLayout());
    body.add(panelGlobal);
        
    this.revalidate();
    this.repaint();
  }

  public void setMenu(){
          
    body.removeAll();
    
    JPanel panelGlobal = new JPanel();
    panelGlobal.setLayout(new GridLayout(6, 1, 0, 0));
    panelGlobal.setBackground(new Color(215,203,233,200));
    
    JLabel labelTitle = new JLabel();
    labelTitle.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
    labelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    labelTitle.setText("     Menu du programme     ");
    panelGlobal.add(labelTitle);
    
    JButton button1 = new javax.swing.JButton();
    button1.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    button1.setText("test_1");
    button1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.goIndicator();
        }
    });
    panelGlobal.add(button1);
    
    JButton button2 = new javax.swing.JButton();
    button2.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    button2.setText("test_2");
    button2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAdress();
        }
    });
    panelGlobal.add(button2);
    
    JButton button3 = new javax.swing.JButton();
    button3.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    button3.setText("test_3");
    button3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAdress();
        }
    });
    panelGlobal.add(button3);
    
    JButton button4 = new javax.swing.JButton();
    button4.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    button4.setText("test_4");
    button4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAdress();
        }
    });
    panelGlobal.add(button4);
    
    JButton button5 = new javax.swing.JButton();
    button5.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    button5.setText("test_5");
    button5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAdress();
        }
    });
    panelGlobal.add(button5);

    body.add(panelGlobal);
    
    this.revalidate();
    this.repaint();
  }

  public void setButtonBackMenu()   {
    ImageIcon trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/Home.png"));
    Image im = trait.getImage();
    im  = im.getScaledInstance(80,80,1);
    JButton buttonBackMenu = new JButton(new ImageIcon(im));
    //buttonBackMenu.setBackground(new Color(215,203,233,200));
    buttonBackMenu.setBorder(null);
    
    buttonBackMenu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
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
}
