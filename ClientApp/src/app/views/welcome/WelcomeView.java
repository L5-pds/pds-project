package app.views.welcome;

import app.controllers.*;
import app.listeners.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class WelcomeView extends JFrame implements WelcomeListener {
  private static JFrame frame;
  private static Container contentPane;
  private static JPanel header;
  private static JPanel body;
  private static ImageIcon trait;
  private static Image im;
  private static JLabel image;
  private static JLabel exception;


  WelcomeController wc;
  private String answer;
  private JLabel answerLabel;

  private JTextField loginField;
  private JTextField pwdField;
  private JLabel peudoLabel = new JLabel("Pseudo");
  private JLabel passwordLabel = new JLabel("Mot de passe");
  private JButton validate = new JButton("Valider");

  public WelcomeView(WelcomeController wc) {
    this.wc = wc;
    template();
  }

  private void template(){
    //answer.setAlignmentX(Component.CENTER_ALIGNMENT);
    setTitle("L5 Simulator");
    contentPane = this.getContentPane();
    header = new JPanel();
    body = new JPanel();

    trait = new ImageIcon(WelcomeView.class.getResource("/pictures/LogoL5.png"));
    im = trait.getImage();
    im  = im.getScaledInstance(150,91,1);
    image = new JLabel( new ImageIcon(im));
    header.setLayout(new FlowLayout(FlowLayout.LEFT));
    header.add(image);

    body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

    contentPane.add(header, BorderLayout.NORTH);
    contentPane.add(body, BorderLayout.CENTER);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 500);
    setVisible(true);
  }

  public void authenticationIhm() {
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

  public void testOK(){
    answerLabel.setText("Bienvenu " + loginField.getText());
    body.removeAll();
    body.add(answerLabel);

    this.revalidate();
    this.repaint();
  }

  public void updateAnswerLabel(String answer){
    this.answerLabel.setText(answer);
    this.revalidate();
    this.repaint();
  }
}
