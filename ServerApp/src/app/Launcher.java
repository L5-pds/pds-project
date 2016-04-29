package app;

import app.models.*;
import app.controllers.*;
import app.views.welcome.*;

import java.util.ResourceBundle;

public class Launcher {
  private static int connectionPoolSize;

  public static void main(String[] args) {
    ResourceBundle bundle = ResourceBundle.getBundle("domaine.properties.config");
    connectionPoolSize = Integer.parseInt(bundle.getString("connectionPoolSize"));

    Server server = new Server(2);
    WelcomeController wc = new WelcomeController(server);
    WelcomeView serverIhm = new WelcomeView(wc);

    wc.addListener(serverIhm);
    serverIhm.setVisible(true);
  }
}
