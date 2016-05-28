package app.controllers;

import app.models.*; 
import app.helpers.Serialization;
import app.listeners.*;
import app.views.indicator.*;
import app.views.linda.*;
//import app.views.mariam.ViewMariam;
import app.views.ruben.*;
import app.views.simulations.*;
import app.views.alexandre.*;
import app.views.welcome.WelcomeViewClient;
import java.awt.Image;

import java.net.*;

import java.io.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

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
      Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconError.png")).getImage().getScaledInstance(75, 75, 1);
          JOptionPane.showMessageDialog(null, 
            "Le serveur ne répond plus", 
            "Erreur", 
            JOptionPane.WARNING_MESSAGE,
            new ImageIcon(im));
    }
  }

  public void menuBack()    {
      listener.setMenu();
  }

  public void goIndicator() {
    
      if(userConnect.isDirector())  {
          ControllerIndicator cci = new ControllerIndicator(socket);
          ViewIndicatorAll ihm = new ViewIndicatorAll(cci, listener.getBody(), listener.getContainer(), this.userConnect);
          cci.addListener(ihm);
      }else {
          Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconAlarm.png")).getImage().getScaledInstance(75, 75, 1);
          JOptionPane.showMessageDialog(null, 
            "Fonctionnalité réservé", 
            "Accès interdit", 
            JOptionPane.WARNING_MESSAGE,
            new ImageIcon(im));
      }
      
    
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

  public void goCompareSimulation() {
    CompareSimulationController cci = new CompareSimulationController(socket);
    CompareSimulationView ihm = new CompareSimulationView(cci, listener.getBody(), listener.getContainer());
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

  public void goFixedRateSimulation() {
    //ControllerAlexandre cci = new ControllerAlexandre(socket);
    //ViewAlexandre ihm = new ViewAlexandre(cci, listener.getBody(), listener.getContainer());
    //cci.addListener(ihm);
      
      app.models.FixedRateSimulation m = new app.models.FixedRateSimulation();
      FixedRateSimulationControllerClient c = new FixedRateSimulationControllerClient(m,socket);
      FixedRateSimulationView v = new FixedRateSimulationView(c,listener.getBody());
  }
  
  
    public void openManual() throws IOException    {
        java.awt.Desktop.getDesktop().open(new File(WelcomeViewClient.class.getResource("/document/Manual.pdf").getFile()));
    }
    

}
