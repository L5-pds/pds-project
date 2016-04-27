package app.controllers;

import app.models.User;
import app.helpers.Serialization;
import app.listeners.WelcomeListener;

import java.util.ResourceBundle;

import java.net.Socket;
import java.net.UnknownHostException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

public class WelcomeController {
  private WelcomeListener listener;

  private String url;
  private int port;
  private static Socket socket;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private String serverAnswer;

  //answer.setAlignmentX(Component.CENTER_ALIGNMENT);

  public WelcomeController(String url, int port) {
    this.url = url;
    this.port = port;
  }

  public void addListener(WelcomeListener l) {
    listener = l;
  }

  private void createSocket(){
    String answer;
    try {
      socket = new Socket(url, port);
      listener.authenticationIhm();
    } catch (Exception e) {
      if (e instanceof UnknownHostException)
        answer = "Impossible de se connecter à l'adresse " + socket.getLocalAddress();
      else
        answer = "Aucun serveur à l'écoute du port";

      l.updateAnswerLabel(answer);
    }
  }

  public void getConnection(String login, String pwd) {
    Strign serverAnswer;
    try {
      out = new PrintWriter(socket.getOutputStream());
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      User user = new User(login, pwd);
      Serialization s = new Serialization();
      //Send information in Json format to server
      out.println(s.serialize(user));
      out.flush();

      //Waiting for the answer (answer = "authentic" if success)
      serverAnswer = in.readLine();
      if (serverAnswer.equals("authentic")) {
        l.testOK();
      } else {
        l.updateAnswerLabel(serverAnswer);
      }
    } catch (Exception e) {
      l.updateAnswerLabel("Le serveur ne répond plus");
    }
  }
}
