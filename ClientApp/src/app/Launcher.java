package app;

import app.views.welcome.Authentication;
import app.controllers.*;
import app.models.*;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Launcher {
  private static Socket socket = null;
  private static JFrame frame;
  private static Container contentPane;
  private static JPanel header;
  private static JPanel body;
  private static ImageIcon trait;
  private static Image im;
  private static JLabel image;
  private static JLabel exception;

  public static void main(String[] args) {
    //Get properties information
    ResourceBundle bundle = ResourceBundle.getBundle("domaine.properties.config");
    String serverAddress = bundle.getString("server");
    int port = Integer.parseInt(bundle.getString("port"));

    //create empty IHM with L5 logo
    template();

    //try to connect to the server
    getConnection(serverAddress, port);
  }

  private static void getConnection(String url, int port){
    try {
      socket = new Socket(url, port);
      WelcomeController ws = new WelcomeController(socket);
      JPanel ihm = new Authentication(ws);
      contentPane.remove(body);
      contentPane.add(ihm);
    } catch (Exception e) {
      if (e instanceof UnknownHostException)
        exception = new JLabel("Impossible de se connecter à l'adresse "+socket.getLocalAddress());
      else
        exception = new JLabel("Aucun serveur à l'écoute du port");

      exception.setAlignmentX(Component.CENTER_ALIGNMENT);
      body.add(exception);
    }

    frame.revalidate();
    frame.repaint();
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
}
