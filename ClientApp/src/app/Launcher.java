package app;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class Launcher {

  private static Socket socket = null;

  public static void main(String[] args) {
    //Get properties information
    ResourceBundle bundle = ResourceBundle.getBundle("domaine.properties.config");
    String serverAddress = bundle.getString("server");
    int port = Integer.parseInt(bundle.getString("port"));

    //Try to create a new socket with IP and PORT
    //If success then load IHM with socket in parameter
    try {
      socket = new Socket(serverAddress, port);
      ClientInterface clientI = new ClientInterface(socket);
      clientI.setVisible(true);

    } catch (UnknownHostException e) {
      javax.swing.JOptionPane.showMessageDialog(null,"Impossible de se connecter à l'adresse "+socket.getLocalAddress(), "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE);
    } catch (IOException e) {
      javax.swing.JOptionPane.showMessageDialog(null,"Aucun serveur à l'écoute du port ", "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
  }
}
