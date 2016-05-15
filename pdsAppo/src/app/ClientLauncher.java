package app;

import java.util.ResourceBundle;

import app.views.welcome.*;
import app.controllers.*;

public class ClientLauncher {
  public static void main(String[] args) {
    //Get properties information
    ResourceBundle bundle = ResourceBundle.getBundle("domaine.properties.config");
    String serverAddress = bundle.getString("server");
    int port = Integer.parseInt(bundle.getString("port"));

    WelcomeControllerClient wc = new WelcomeControllerClient(serverAddress, port);
    WelcomeViewClient ihm = new WelcomeViewClient(wc);
    wc.addListener(ihm);
    wc.createSocket();
    
  }
}