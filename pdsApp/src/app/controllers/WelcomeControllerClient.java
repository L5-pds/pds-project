package app.controllers;

import app.models.User;
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

  public WelcomeControllerClient(String url, int port) {
    this.url = url;
    this.port = port;
  }

  public void addListener(WelcomeListenerClient l) {
    listener = l;
  }

  public void createSocket(){
    String answer;
    try {
      socket = new Socket(url, port);
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
      out = new PrintWriter(socket.getOutputStream());
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      User user = new User(login, pwd);
      Serialization s = new Serialization();
      //Send information in Json format to server
      out.println("GET/User/" + s.serialize(user));
      out.flush();

      //Waiting for the answer (answer = "authentic" if success)
      serverAnswer = in.readLine();
      if (serverAnswer.equals("authentic")) {
        listener.testOK();
      } else {
        listener.updateAnswerLabel(serverAnswer);
      }
    } catch (Exception e) {
      listener.updateAnswerLabel("Le serveur ne répond plus");
    }
  }
}
