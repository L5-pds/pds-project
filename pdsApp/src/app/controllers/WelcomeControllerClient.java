package app.controllers;

import app.models.*; 
import app.helpers.Serialization;
import app.listeners.*;
import app.views.indicator.*;
import app.views.linda.*;
//import app.views.mariam.ViewMariam;
import app.views.ruben.*;
import app.views.tarik.*;
import app.views.simulations.*;
import app.views.alexandre.*;

import java.net.*;

import java.io.*;

public class WelcomeControllerClient {
  private WelcomeListenerClient listener;

  private String url;
  private int port;
  private static Socket socket;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private Serialization s;

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
    String answer;
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

  public void testAddNewAddress()  {
    /*
    try {
      Address newAddress = new Address(55555, 10, "route de chabanais", "CHASSENON", "16150");
      out.println("INSERT/Address/" + s.serializeAddress(newAddress));
      out.flush();
      String response = in.readLine();
      if(response.equals("success")) {
          javax.swing.JOptionPane.showMessageDialog(null,"Adresse ajoutée avec succes");
      }else {
          javax.swing.JOptionPane.showMessageDialog(null,"Erreur : " + response);
      }
    } catch (Exception e) {
      javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus");
    }
    */
    javax.swing.JOptionPane.showMessageDialog(null,"Utilisateur connecté : " + userConnect.getFirstName() + " " + userConnect.getLastName() + " de l'agence '" + userConnect.getAgencyInfo().getName() + "'");
  }

  public void menuBack()    {
      listener.setMenu();
  }

  public void goIndicator() {
    ControllerClientIndicator cci = new ControllerClientIndicator(socket);
    IndicatorView ihm = new IndicatorView(cci, listener.getBody(), listener.getContainer());
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
	VariableRateSimulationController cci = new VariableRateSimulationController(socket);
	VariableRateSimulationView ihm = new VariableRateSimulationView(cci, listener.getBody(), listener.getContainer());
    cci.addListener(ihm);
    //VariableRateSimulationController c = new VariableRateSimulationController();
    //VariableRateSimulationView ihm = new VariableRateSimulationView(c);
    //c.addListener(ihm);
  }

  public void goAlexandre() {
    ControllerAlexandre cci = new ControllerAlexandre(socket);
    ViewAlexandre ihm = new ViewAlexandre(cci, listener.getBody(), listener.getContainer());
    cci.addListener(ihm);
  }

}
