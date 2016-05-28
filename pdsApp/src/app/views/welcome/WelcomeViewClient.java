package app.views.welcome;

import app.controllers.*;
import app.listeners.*;
import app.models.component.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    header.setBackground(new Color(215,203,233,255));

    body = new JPanel();
    body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
    body.setBackground(new Color(215,203,233,255));

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    contentPane = this.getContentPane();
    contentPane.add(body, BorderLayout.CENTER);
    contentPane.add(header, BorderLayout.NORTH);
    pack();

    setExtendedState(MAXIMIZED_BOTH);
    this.setMinimumSize(new Dimension(1000, 900));
    setVisible(true);
  }

    /**
     *
     */
    @Override
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
    loginField.setText("LBOUZID");

    labelPass.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
    labelPass.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    labelPass.setText("Mot de passe");

    passField.setFont(new java.awt.Font("Verdana", 0, 30)); // NOI18N
    passField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    passField.setText("ing");

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
    buttonvalide.setCursor(new Cursor(Cursor.HAND_CURSOR));

    buttonvalide.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
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

    /**
     *
     */
    @Override
  public void setMenu(){

    body.removeAll();

    JPanel panelGlobal = new JPanel();
    panelGlobal.setLayout(new GridLayout(7, 1, 0, 0));
    panelGlobal.setOpaque(false);

    JLabel labelTitle = new JLabel();
    labelTitle.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
    labelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    labelTitle.setText("     Menu du programme     ");
    panelGlobal.add(labelTitle);

    JButton button1 = new javax.swing.JButton();
    button1.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    button1.setText("Indicateur de prÃªt");
    button1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            wc.goIndicator();
        }
    });
    panelGlobal.add(button1);

    JButton button2 = new javax.swing.JButton();
    button2.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    button2.setText("Taux d'intérêt de l'agence");
    button2.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            wc.goLinda();
        }
    });
    panelGlobal.add(button2);

    JButton button3 = new javax.swing.JButton();
    button3.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    button3.setText("Ruben");
    button3.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.goRuben();
        }
    });
    panelGlobal.add(button3);

    JButton button4 = new javax.swing.JButton();
    button4.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    button4.setText("Comparer des simulations");
    button4.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.goCompareSimulation();
        }
    });
    panelGlobal.add(button4);

    JButton button5 = new javax.swing.JButton();
    button5.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    button5.setText("Mariam");
    button5.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.goMariam();
        }
    });
    panelGlobal.add(button5);

    JButton button6 = new javax.swing.JButton();
    button6.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
    button6.setText("Alexandre");
    button6.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            wc.goAlexandre();
        }
    });
    panelGlobal.add(button6);


    body.setLayout(new FlowLayout());
    body.add(panelGlobal);

    this.revalidate();
    this.repaint();
  }

    /**
     *
     */
    @Override
  public void setButtonBackMenu()   {
    ImageIcon trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconHome.png"));
    Image im = trait.getImage();
    im  = im.getScaledInstance(80,80,1);
    JLabel buttonBackMenu = new JLabel(new ImageIcon(im));
    buttonBackMenu.setToolTipText("Retour Ã  l'accueil");
    buttonBackMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
    buttonBackMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wc.menuBack();
            }
            public void mouseEntered(MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconHomeHover.png")).getImage().getScaledInstance(80, 80, 1);
                buttonBackMenu.setIcon(new ImageIcon(im));
            }

            public void mouseExited(MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconHome.png")).getImage().getScaledInstance(80, 80, 1);
                buttonBackMenu.setIcon(new ImageIcon(im));
            }
        });

    header.add(buttonBackMenu);
    this.revalidate();
    this.repaint();
  }

    /**
     *
     */
    @Override
  public void updateAnswerLabel(String answer){
    this.answerLabel.setText(answer);
    this.revalidate();
    this.repaint();
  }

    /**
     *
     * @return
     */
    @Override
  public JPanel getBody()   {
    return this.body;
  }

    /**
     *
     * @return
     */
    @Override
  public Container getContainer()    {
    return this.getContentPane();
  }

    /**
     *
     */
    @Override
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
