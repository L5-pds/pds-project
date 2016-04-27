package app;

import java.util.ResourceBundle;

import app.views.welcome.*;
import app.controllers.*;

public class Launcher {
  public static void main(String[] args) {
    //Get properties information
    ResourceBundle bundle = ResourceBundle.getBundle("domaine.properties.config");
    String serverAddress = bundle.getString("server");
    int port = Integer.parseInt(bundle.getString("port"));

    WelcomeController wc = new WelcomeController(serverAddress, port);
    WelcomeView ihm = new WelcomeView(wc);
    wc.addListener(ihm);
    wc.createSocket();
  }
}
