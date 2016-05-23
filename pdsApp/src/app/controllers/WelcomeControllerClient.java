package app.controllers;

import app.models.*;
import app.helpers.Serialization;
import app.listeners.*;
import app.views.indicator.*;
import app.views.linda.*;
import app.views.ruben.*;
import app.views.tarik.*;
import app.views.simulations.*;
import app.views.alexandre.*;

import java.net.*;

import java.io.*;

public class WelcomeControllerClient {
  private WelcomeListenerClient listener;

  private final String url;
  private final int port;
  private static Socket socket;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private final Serialization s;

  private Advisor userConnect;

  public WelcomeControllerClient(String url, int port) {
    this.url = url;
    this.port = port;
    this.s = new Serialization();
  }

  public void addListener(WelcomeListenerClient l) {
    listener = l;
  }

  public void createSocket(){
    try {
      socket = new Socket(url, port);
      out = new PrintWriter(socket.getOutputStream());
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      listener.authenticationIhm();
    } catch (Exception e) {
      listener.setErrorSocket();
    }
  }

  public void getConnection(String login, String pwd) {

    String serverAnswer;
    try {
      Advisor user = new Advisor(login, pwd);
      //Send information in Json format to server
      out.println("AUTH/User/" + s.serializeUser(user));
      out.flush();

      //Waiting for the answer (answer = "authentic" if success)
      serverAnswer = in.readLine();
      String[] splitedAnswer = serverAnswer.split("/");
      String response = splitedAnswer[0];
      String other = splitedAnswer[1];

      if (response.equals("Success")) {
        userConnect = s.unserializeUser(other);
        listener.setButtonBackMenu();
        listener.setMenu();
      } else {
        listener.updateAnswerLabel(other);
      }
    } catch (Exception e) {
      javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus");
    }
  }

  public void menuBack()    {
      listener.setMenu();
  }

  public void goIndicator() {
    ControllerIndicator cci = new ControllerIndicator(socket);
    ViewIndicatorAll ihm = new ViewIndicatorAll(cci, listener.getBody(), listener.getContainer(), this.userConnect);
    cci.addListener(ihm);
  }

  public void goLinda() {
    ControllerLinda cci = new ControllerLinda(socket);
    ViewLinda ihm = new ViewLinda(cci, listener.getBody(), listener.getContainer());
    cci.addListener(ihm);
  }

  public void goRuben() {
    ControllerRuben cci = new ControllerRuben(socket);
    ViewRuben ihm = new ViewRuben(cci, listener.getBody(), listener.getContainer());
    cci.addListener(ihm);
  }

  public void goTarik() {
    ControllerTarik cci = new ControllerTarik(socket);
    ViewTarik ihm = new ViewTarik(cci, listener.getBody(), listener.getContainer());
    cci.addListener(ihm);
  }

  public void goMariam() {
    //ControllerMariam cci = new ControllerMariam(socket);
    //ViewMariam ihm = new ViewMariam(cci, listener.getBody(), listener.getContainer());
    //cci.addListener(ihm);
    VariableRateSimulationController c = new VariableRateSimulationController();
    VariableRateSimulationView ihm = new VariableRateSimulationView(c);
    c.addListener(ihm);
  }

  public void goAlexandre() {
    ControllerAlexandre cci = new ControllerAlexandre(socket);
    ViewAlexandre ihm = new ViewAlexandre(cci, listener.getBody(), listener.getContainer());
    cci.addListener(ihm);
  }

}
