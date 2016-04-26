package app.views.welcome;

import app.controllers.*;

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
    wc.createSocket();
  }

  private static void template(){
    frame = new JFrame("L5 Simulator");
    contentPane = frame.getContentPane();
    header = new JPanel();
    body = new JPanel();

    trait = new ImageIcon(Launcher.class.getResource("/pictures/LogoL5.png"));
    im = trait.getImage();
    im  = im.getScaledInstance(150,91,1);
    image = new JLabel( new ImageIcon(im));
    header.setLayout(new FlowLayout(FlowLayout.LEFT));
    header.add(image);

    body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

    contentPane.add(header, BorderLayout.NORTH);
    contentPane.add(body, BorderLayout.CENTER);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 500);
    frame.setVisible(true);
  }

  private void authenticationIhm() {
    this.setLayout(new GridLayout(3, 2));
    pwdField = new JPasswordField();
    loginField = new JTextField();
    answerLabel = new JLabel("");
    pwdField.setColumns(10);
    loginField.setColumns(10);
    this.add(peudoLabel);
    this.add(passwordLabel);
    this.add(loginField);
    this.add(pwdField);
    this.add(validate);
    this.add(answerLabel);

    validate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        answer = wc.connection(loginField.getText(), pwdField.getText());
        update(answer);
      }
    });
  }

  private void update(String answer){
    if (answer.equals("authentic")){
      this.removeAll();
      answerLabel.setText("Bienvenu " + loginField.getText());
      this.add(answerLabel);
    }
    else if(answer.equals("disconnected")){
      this.removeAll();
      answerLabel.setText("Le serveur ne répond plus");
      this.add(answerLabel);
    }
    else{
      answerLabel.setText(answer);
    }

    this.revalidate();
    this.repaint();
  }

  public void updateAnswerLabel(String, answer){
    this.answerLabel.setText(answer);
  }
}
