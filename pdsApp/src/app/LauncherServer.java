package app;

import app.models.*;
import app.controllers.*;
import app.views.welcome.*;

import java.util.ResourceBundle;

public class LauncherServer {
  private static int connectionPoolSize;

  public static void main(String[] args) {
    ResourceBundle bundle = ResourceBundle.getBundle("domaine.properties.config");
    connectionPoolSize = Integer.parseInt(bundle.getString("connectionPoolSize"));

    Server server = new Server(connectionPoolSize);
    WelcomeControllerServer wc = new WelcomeControllerServer(server);
    WelcomeViewServer serverIhm = new WelcomeViewServer(wc);

    wc.addListener(serverIhm);
    serverIhm.setVisible(true);
  }
}
