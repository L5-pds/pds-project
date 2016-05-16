package app.controllers;

import app.models.*;
import app.helpers.Serialization;
import app.listeners.WelcomeListenerClient;

import java.net.Socket;
import java.net.UnknownHostException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

public class WelcomeControllerClient {
  private WelcomeListenerClient listener;

  private String url;
  private int port;
  private static Socket socket;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private Serialization s;

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
      if (e instanceof UnknownHostException)
        answer = "Impossible de se connecter à l'adresse " + socket.getLocalAddress();
      else
        answer = "Aucun serveur à l'écoute du port";

      listener.updateAnswerLabel(answer);
    }
  }

  public void getConnection(String login, String pwd) {
    
    String serverAnswer;
    try {
      User user = new User(login, pwd);
      //Send information in Json format to server
      out.println("AUTH/User/Send connection information/" + s.serializeUser(user));
      out.flush();

      //Waiting for the answer (answer = "authentic" if success)
      serverAnswer = in.readLine();
      if (serverAnswer.equals("authentic")) {
        listener.setMenu();
      } else {
        listener.updateAnswerLabel(serverAnswer);
      }
    } catch (Exception e) {
      javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus");
    }
  }
  
  public void getAllCustomer()  {
      try {
      Adress newAdress = new Adress(55555, 10, "route de chabanais", "CHASSENON", "16150");
      out.println("INSERT/Adress/" + s.serializeAdress(newAdress));
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
  }
  
}
