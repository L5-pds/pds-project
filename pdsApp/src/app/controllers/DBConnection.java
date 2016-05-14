package app.controllers;

import app.listeners.*;
import app.models.*;
import app.helpers.*;

import java.util.HashMap;
import java.lang.Object;

import java.io.*;
import java.net.*;
import java.sql.*;

public class DBConnection implements Runnable {
  private Socket socket;
  private WelcomeListenerServer listener;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private String serializedUser;
  private String message = null;
  private int poolIndex=-1;

  public DBConnection(Socket socket, WelcomeListenerServer l){
    this.socket = socket;
    this.listener = l;
  }

  public void run() {
    User user = null;
    Serialization s = new Serialization();

    while(!socket.isClosed()){
      try {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());

        String query = in.readLine();

        String[] splitedQuery = query.split("/");

        String method = splitedQuery[0];
        String type = splitedQuery[1];
        String object = splitedQuery[2];

        if (type.equals("User")){
          if(method.equals("GET")){
            user = s.unserializeUser(object);
            getConnection(user.getLogin(), user.getPwd());
          }
        }
      } catch (Exception e) {
    	Server.connectionPool[poolIndex].use(false);
        listener.updateInfoLabel();
        listener.changeTextLog("Le client a quitté.");
        if (poolIndex != -1){
          Server.connectionPool[poolIndex].unUse();
          listener.updateInfoLabel();
        }
        try {
          socket.close();
        } catch (IOException e1) {
          listener.changeTextLog("WARNING - Probleme de fermeture de la socket pour l'utilisateur : " + user.getLogin());
        }
      }
    }//end of while loop
  }

  public void getConnection(String login, String pwd){
    boolean userConnected = false;
    if(authentication(login, pwd)){
      for(int i=0 ; i<Server.poolSize ; i++) {
        if(Server.connectionPool[i].isUsed() == false) {
          Server.connectionPool[i].use(true);
          listener.updateInfoLabel();
          poolIndex= i;
          out.println("authentic");
          out.flush();
          listener.changeTextLog(login + " est maintenant connecté");
          userConnected = true;
          break;
        }
      }
      if(userConnected == false) {
        out.println("Aucune connexion disponible (ressayer ultérieurement)");
        out.flush();
        listener.changeTextLog(login + " plus de connexion disponible");
        userConnected = false;
      }
    }
    else {
      out.println("Authentification incorrecte");
      out.flush();
      listener.changeTextLog(login + " erreur authentification");
      userConnected = false;
    }
  }

  private boolean authentication(String login, String pass) {
    boolean authentic = false;
    Connection conn = null;
    Statement stat = null;
    ResultSet results = null;

    try {
      conn = Server.getConnection();
      stat = conn.createStatement();
      results = stat.executeQuery("SELECT * FROM t_advisor WHERE login = '" + login + "' AND password = '" + pass + "';");

      results.next();

      if(results.getRow() != 0)
        authentic = true;

      results.close();
      stat.close();
      conn.close();
    }
    catch (SQLException e) {
      listener.changeTextLog("ERREUR (SQL) pour l'authentification de " + login);
      authentic = false;
    }

    return authentic;
  }
}

