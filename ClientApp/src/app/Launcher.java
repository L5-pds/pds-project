package app;

import app.views.welcome.*;
import app.controllers.*;
import app.models.*;

public class Launcher {
  public static void main(String[] args) {
    //Get properties information
    ResourceBundle bundle = ResourceBundle.getBundle("domaine.properties.config");
    String serverAddress = bundle.getString("server");
    int port = Integer.parseInt(bundle.getString("port"));

    WelcomeController ws = new WelcomeController(serverAddress, port);
    JPanel ihm = new WelcomeView(ws);
    ws.addListener(ihm);
  }
}
