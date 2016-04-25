package app.controllers;

import app.models.User;
import app.helpers.Serialization;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WelcomeController {
  private static Socket socket;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private String serverAnswer;

  public WelcomeController(Socket sck) {
    this.socket = sck;
  }

  public String connection(String login, String pwd ) {
    try {
      out = new PrintWriter(socket.getOutputStream());
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      User user = new User(login, pwd);
      Serialization s = new Serialization();
      //Send information in Json format to server
      out.println(s.serialize(user));
      out.flush();

      //Waiting for the answer (answer = "authentic" if success)
      serverAnswer=in.readLine();
      if (serverAnswer.equals("authentic")){

      }
    } catch (Exception e) {
      serverAnswer = "disconnected";
    }
    return serverAnswer;
  }
}
