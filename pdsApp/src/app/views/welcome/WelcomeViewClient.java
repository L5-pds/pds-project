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
  private ImageIcon trait;
  private Image im;
  private JLabel image;
  private JLabel answerLabel;
  private JTextField pwdField;
  private JTextField loginField;


  WelcomeControllerClient wc;

  private JLabel peudoLabel = new JLabel("Pseudo");
  private JLabel passwordLabel = new JLabel("Mot de passe");
  private JButton validate = new JButton("Valider");
  private JPanel panInfoUser;
  private JPanel panInfoUser1;
  private JPanel panInfoUser2;
  private JLabel lblInfoUser11;
  private JLabel lblInfoUser12;
  private JLabel lblInfoUser13;
  private JLabel lblInfoUser21;
  private JLabel lblInfoUser22;
  private JLabel lblInfoUser23;
  private JMenu mnNewMenu;
  private JMenuBar menuBar;
  private JMenuItem mntmNewMenuItem;
  private JMenuItem menuItem;
  private JMenu menu;
  private JMenuItem menuItem_1;
  private JMenuItem menuItem_2;

  public WelcomeViewClient(WelcomeControllerClient wc) {
    this.wc = wc;
    template();
  }

  private void template(){
    setTitle("Gestion de banque");

    trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/LogoL5.png"));
    im = trait.getImage();
    im  = im.getScaledInstance(330,200,1);
    image = new JLabel( new ImageIcon(im));
    lblInfoUser11 = new JLabel("Utilisateur : ");
    lblInfoUser11.setFont(new Font("Verdana", Font.PLAIN, 22));
    lblInfoUser12 = new JLabel("Profil : ");
    lblInfoUser12.setFont(new Font("Verdana", Font.PLAIN, 22));
    lblInfoUser13 = new JLabel("Autre : ");
    lblInfoUser13.setFont(new Font("Verdana", Font.PLAIN, 22));
    panInfoUser1 = new JPanel();
    panInfoUser1.setLayout(new BoxLayout(panInfoUser1, BoxLayout.PAGE_AXIS));
    panInfoUser1.add(lblInfoUser11);
    panInfoUser1.add(lblInfoUser12);
    panInfoUser1.add(lblInfoUser13);
    panInfoUser1.setBackground(new Color(215,203,233,200));
    lblInfoUser21 = new JLabel("reg");
    lblInfoUser21.setFont(new Font("Verdana", Font.PLAIN, 22));
    lblInfoUser22 = new JLabel("erg");
    lblInfoUser22.setFont(new Font("Verdana", Font.PLAIN, 22));
    lblInfoUser23 = new JLabel("erg");
    lblInfoUser23.setFont(new Font("Verdana", Font.PLAIN, 22));
    panInfoUser2 = new JPanel();
    panInfoUser2.setLayout(new BoxLayout(panInfoUser2, BoxLayout.PAGE_AXIS));
    panInfoUser2.add(lblInfoUser21);
    panInfoUser2.add(lblInfoUser22);
    panInfoUser2.add(lblInfoUser23);
    panInfoUser2.setBackground(new Color(215,203,233,200));
    panInfoUser = new JPanel();
    panInfoUser.add(panInfoUser1);
    panInfoUser.add(panInfoUser2);
    panInfoUser.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
    panInfoUser.setBackground(new Color(215,203,233,200));
    panInfoUser.setSize(2000, 1000);
    header = new JPanel();
    
    menuBar = new JMenuBar();
    header.add(menuBar);
    
    mnNewMenu = new JMenu("New menu");
    menuBar.add(mnNewMenu);
    
    mntmNewMenuItem = new JMenuItem("New menu item");
    mnNewMenu.add(mntmNewMenuItem);
    
    menuItem = new JMenuItem("New menu item");
    mnNewMenu.add(menuItem);
    
    menu = new JMenu("New menu");
    menuBar.add(menu);
    
    menuItem_1 = new JMenuItem("New menu item");
    menu.add(menuItem_1);
    
    menuItem_2 = new JMenuItem("New menu item");
    menu.add(menuItem_2);
    header.add(image);
    header.add(panInfoUser);
    header.setBackground(new Color(215,203,233,200));
    
    answerLabel = new JLabel("");
    answerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    body = new JPanel();
    body.add(answerLabel);
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
    body.setLayout(new GridLayout(3, 2));
    pwdField = new JPasswordField();
    loginField = new JTextField();
    answerLabel = new JLabel("");
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

    this.revalidate();
    this.repaint();
  }

  public void setMenu(){
    body.removeAll();
    
    JButton jButton1 = new javax.swing.JButton();
    jButton1.setText("test_1");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAdress();
        }
    });
    body.add(jButton1);
    
    JButton jButton2 = new javax.swing.JButton();
    jButton2.setText("test_2");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAdress();
        }
    });
    body.add(jButton2);
    
    JButton jButton3 = new javax.swing.JButton();
    jButton3.setText("test_3");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAdress();
        }
    });
    body.add(jButton3);
    
    JButton jButton4 = new javax.swing.JButton();
    jButton4.setText("test_4");
    jButton4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAdress();
        }
    });
    body.add(jButton4);
    
    JButton jButton5 = new javax.swing.JButton();
    jButton5.setText("test_5");
    jButton5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.testAddNewAdress();
        }
    });
    body.add(jButton5);

    this.revalidate();
    this.repaint();
  }

  public void updateAnswerLabel(String answer){
    this.answerLabel.setText(answer);
    this.revalidate();
    this.repaint();
  }
}
