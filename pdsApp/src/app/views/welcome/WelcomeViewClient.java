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
  private JFrame frame;
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
            
	javax.swing.JButton jButton1;
    	javax.swing.JLabel jLabel1;
   	javax.swing.JLabel jLabel2;
    	javax.swing.JLabel jLabel3;
    	javax.swing.JPanel jPanel1;
    	javax.swing.JTextField loginField;
    	javax.swing.JTextField passField;

	loginField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        passField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        answerLabel = new JLabel("");
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        loginField.setFont(new java.awt.Font("Verdana", 0, 30)); // NOI18N
        loginField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        loginField.setText("");

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Mot de passe");

        passField.setFont(new java.awt.Font("Verdana", 0, 30)); // NOI18N
        passField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passField.setText("");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Authentification");

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Nom d'utilisateur");

        answerLabel.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
        answerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        jButton1.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
        jButton1.setText("Valider");
        
        jPanel1.setLayout(new GridLayout(7, 1, 0, 0));
        jPanel1.add(jLabel2);
        jPanel1.add(jLabel3);
        jPanel1.add(loginField);
        jPanel1.add(jLabel1);
        jPanel1.add(passField);
        jPanel1.add(answerLabel);
        jPanel1.add(jButton1);
        jPanel1.setBackground(new Color(215,203,233,200));
        
        jButton1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        wc.getConnection(loginField.getText(), passField.getText());
      }
    });
        
        body.setLayout(new FlowLayout());
        body.add(jPanel1);
    /*
    body.setLayout(new GridLayout(3, 2));
    pwdField = new JPasswordField();
    loginField = new JTextField();
    pwdField.setColumns(10);
    loginField.setColumns(10);
    body.add(peudoLabel);
    body.add(passwordLabel);
    body.add(loginField);
    body.add(pwdField);
    body.add(validate);
    body.add(answerLabel);

    validate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        wc.getConnection(loginField.getText(), pwdField.getText());
      }
    });
    */
    this.revalidate();
    this.repaint();
  }

  public void setMenu(){
          
    body.removeAll();
    
    JPanel jPanel1 = new javax.swing.JPanel();
    jPanel1.setLayout(new GridLayout(6, 1, 0, 0));
    jPanel1.setBackground(new Color(215,203,233,200));
    
    JLabel jLabel2 = new JLabel();
    jLabel2.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
    jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel2.setText("     Menu du programme     ");
    jPanel1.add(jLabel2);
    
    JButton jButton1 = new javax.swing.JButton();
    jButton1.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    jButton1.setText("test_1");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAdress();
        }
    });
    jPanel1.add(jButton1);
    
    JButton jButton2 = new javax.swing.JButton();
    jButton2.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    jButton2.setText("test_2");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAdress();
        }
    });
    jPanel1.add(jButton2);
    
    JButton jButton3 = new javax.swing.JButton();
    jButton3.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    jButton3.setText("test_3");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAdress();
        }
    });
    jPanel1.add(jButton3);
    
    JButton jButton4 = new javax.swing.JButton();
    jButton4.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    jButton4.setText("test_4");
    jButton4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAdress();
        }
    });
    jPanel1.add(jButton4);
    
    JButton jButton5 = new javax.swing.JButton();
    jButton5.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    jButton5.setText("test_5");
    jButton5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAdress();
        }
    });
    jPanel1.add(jButton5);

    body.add(jPanel1);
    
    this.revalidate();
    this.repaint();
  }

  public void setButtonBackMenu()   {
      ImageIcon trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/Home.png"));
    Image im = trait.getImage();
    im  = im.getScaledInstance(80,80,1);
    JButton buttonBackMenu = new JButton(new ImageIcon(im));
    buttonBackMenu.setBackground(new Color(215,203,233,200));
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
}
