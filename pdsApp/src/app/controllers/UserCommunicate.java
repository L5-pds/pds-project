package app.controllers;

import app.listeners.*;
import app.models.*;
import app.helpers.*;

import java.util.HashMap;
import java.lang.Object;

import java.io.*;
import java.net.*;
import java.sql.*;

public class UserCommunicate implements Runnable {
  private Socket socket;
  private WelcomeListenerServer listener;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private Serialization gsonSerial;
  private String message = null;
  private int poolIndex=-1;

  public UserCommunicate(Socket socket, WelcomeListenerServer l){
    this.socket = socket;
    this.listener = l;
    this.gsonSerial = new Serialization();
  }

  public void run() {
    User user = null;
    String query;
    String[] splitedQuery;
    String method;
    String typeObject;
    String information;
    String object;
    boolean userConnected = false;

    try {
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    out = new PrintWriter(socket.getOutputStream());
    } catch (Exception e1) {
        try {
        socket.close();
        } catch (Exception e2) {
            listener.changeTextLog("WARNING - Erreur de fermeture de la socket");
        }
        listener.changeTextLog("WARNING - Erreur de déclaration des variables de communications (in/out)");
    }

    //Authentication process
    while((!socket.isClosed()) && (!userConnected)){
      try {
        query = in.readLine();
        splitedQuery = query.split("/");

        method = splitedQuery[0];
        typeObject = splitedQuery[1];
        information = splitedQuery[2];
        object = splitedQuery[3];

        if (method.equals("AUTH")){
          if(typeObject.equals("User")){
            user = gsonSerial.unserializeUser(object);
            userConnected = getConnection(user.getLogin(), user.getPwd());
          }
        }
      } catch (Exception e) {
        listener.changeTextLog("CONNECT_WARNING - gone");
        if (poolIndex != -1){
          Server.connectionPool[poolIndex].use(false);
          listener.updateInfoLabel();
        }
        try {
          socket.close();
        } catch (IOException e1) {
          listener.changeTextLog("SERVER_WARNING - closing socket error");
        }
      }
    }//end of while loop
      
    //Communicate process
    while((!socket.isClosed()) && (userConnected)){
      try {
        query = in.readLine();
        splitedQuery = query.split("/");

        method = splitedQuery[0];
        typeObject = splitedQuery[1];
        object = splitedQuery[2];
        
        switch (method) {
            case "INSERT":
                switch (typeObject) {
                    case "Adress":
                        Adress newAdress = gsonSerial.unserializeAdress(object);
                        String request = "INSERT INTO t_adress VALUES (" 
                                + newAdress.getId() + ", " 
                                + newAdress.getStreetNb() + ", " 
                                + "'" + newAdress.getStreetName() + "'" + ", " 
                                + "'" + newAdress.getCityName() + "'" + ", " 
                                + "'" + newAdress.getZipCode() + "'"
                                + ");";
                        String response = Server.connectionPool[poolIndex].requestWithoutRespons(request);
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - add new adress - " + response);
                        out.println(response);
                        out.flush();
                        break;
                    default:
                        break;
                }
                break;
            case "UPDATE":
                switch (typeObject) {
                    case "Adress":
                        Adress newAdress = gsonSerial.unserializeAdress(object);
                        String request = "INSERT INTO t_adress VALUES (" 
                                + newAdress.getId() + ", " 
                                + newAdress.getStreetNb() + ", " 
                                + "'" + newAdress.getStreetName() + "'" + ", " 
                                + "'" + newAdress.getCityName() + "'" + ", " 
                                + "'" + newAdress.getZipCode() + "'"
                                + ");";
                        String response = Server.connectionPool[poolIndex].requestWithoutRespons(request);
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - add new adress - " + response);
                        out.println(response);
                        out.flush();
                        break;
                    default:
                        break;
                }
                break;
            case "DELETE":
                switch (typeObject) {
                    case "Adress":
                        Adress newAdress = gsonSerial.unserializeAdress(object);
                        String request = "INSERT INTO t_adress VALUES (" 
                                + newAdress.getId() + ", " 
                                + newAdress.getStreetNb() + ", " 
                                + "'" + newAdress.getStreetName() + "'" + ", " 
                                + "'" + newAdress.getCityName() + "'" + ", " 
                                + "'" + newAdress.getZipCode() + "'"
                                + ");";
                        String response = Server.connectionPool[poolIndex].requestWithoutRespons(request);
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - add new adress - " + response);
                        out.println(response);
                        out.flush();
                        break;
                    default:
                        break;
                }
                break;
            case "SPECIF_1":
                switch (typeObject) {
                    case "Adress":
                        //Coding
                    default:
                        break;
                }
                break;
            case "SPECIF_2":
                switch (typeObject) {
                    case "Adress":
                        //Coding
                    default:
                        break;
                }
                break;
            case "SPECIF_3":
                switch (typeObject) {
                    case "Adress":
                        //Coding
                    default:
                        break;
                }
                break;
            case "SPECIF_4":
                switch (typeObject) {
                    case "Adress":
                        //Coding
                    default:
                        break;
                }
                break;
            case "SPECIF_5":
                switch (typeObject) {
                    case "Adress":
                        //Coding
                    default:
                        break;
                }
                break;
            case "SPECIF_6":
                switch (typeObject) {
                    case "Adress":
                        //Coding
                    default:
                        break;
                }
                break;
            default:
                //coding
                break;
        }
                
        
      } catch (Exception e) {
        listener.changeTextLog("CONNECT_WARNING - " + user.getLogin() + " - gone");
        if (poolIndex != -1){
          Server.connectionPool[poolIndex].use(false);
          listener.updateInfoLabel();
        }
        try {
          socket.close();
        } catch (IOException e1) {
          listener.changeTextLog("SERVER_WARNING - closing socket error");
        }
      }
    }//end of while loop
  }

  public boolean getConnection(String login, String pwd){
    boolean userConnected = false;
    boolean userAuth = false;
    boolean userAlreadyUse = false;
    boolean userNoPool = true;
    
    userAuth=authentication(login, pwd);
    if(userAuth == false)   {
        out.println("Authentification incorrecte");
        out.flush();
        listener.changeTextLog("CONNECT_WARNING - " + login + " - dismissed");
        return false;
    }
    
    for (int i = 0; i < Server.poolSize; i++) {
        if (Server.connectionPool[i].getUser().equals(login)) {
            out.println("Cet utilisateur est déja connecté (ressayer ultérieurement)");
            out.flush();
            listener.changeTextLog("CONNECT_WARNING - " + login + " - already connect");
            userAlreadyUse = true;
            return false;
        }
    }
    
    for (int i = 0; i < Server.poolSize; i++) {
        if (Server.connectionPool[i].isUsed() == false) {
            Server.connectionPool[i].use(true);
            Server.connectionPool[i].setUser(login);
            listener.updateInfoLabel();
            poolIndex = i;
            out.println("authentic");
            out.flush();
            listener.changeTextLog("CONNECT_WARNING - " + login + " - connected");
            userNoPool = false;
            userConnected = true;
            return true;
        }
    }
    
    if (userNoPool == true) {
        out.println("Aucune connexion disponible (ressayer ultérieurement)");
        out.flush();
        listener.changeTextLog("CONNECT_WARNING - " + login + " - no connection available");
        return false;
    }
    
    return userConnected;
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