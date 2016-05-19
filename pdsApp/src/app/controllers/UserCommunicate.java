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
  private Advisor user = null;

  public UserCommunicate(Socket socket, WelcomeListenerServer l){
    this.socket = socket;
    this.listener = l;
    this.gsonSerial = new Serialization();
  }

  public void run() {
    String query;
    String[] splitedQuery;
    String method;
    String typeObject;
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
        object = splitedQuery[2];

        if (method.equals("AUTH")){
          if(typeObject.equals("User")){
            user = gsonSerial.unserializeUser(object);
            userConnected = getConnection(user.getLogin(), user.getPassword());
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
            case "INSERT": //Case for insert into database
                switch (typeObject) {
                    case "Adress": //Case for insert into database new adress
                        Adress newAdress = gsonSerial.unserializeAdress(object);
                        String request = "INSERT INTO t_adress VALUES (" 
                                + newAdress.getId() + ", " 
                                + newAdress.getStreetNb() + ", " 
                                + "'" + newAdress.getStreetName() + "'" + ", " 
                                + "'" + newAdress.getCityName() + "'" + ", " 
                                + "'" + newAdress.getZipCode() + "'"
                                + ");";
                        String response = Server.connectionPool[poolIndex].requestWithoutResult(request);
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - add new adress - " + response);
                        out.println(response);
                        out.flush();
                        break;
                    default:
                        //Coding
                        break;
                }
                break;
            case "UPDATE": //Case for update database
                switch (typeObject) {
                    case "Adress": //Case for update existing adress into database
                        //Coding
                        break;
                    default:
                        //Coding
                        break;
                }
                break;
            case "DELETE": //Case for delete from database
                switch (typeObject) {
                    case "Adress": //Case for delete existing adress from database
                        //Coding
                        break;
                    default:
                        //Coding
                        break;
                }
                break;
            case "SPECIF_1": //Spécifique THIBAULT DON'T TOUCHE !!!
                switch (typeObject) {
                    case "Adress":
                        String request = "SELECT COUNT(*) AS COUNTADRESS FROM t_adress;";
                        ResultSet response = Server.connectionPool[poolIndex].requestWithResult(request);
                        response.next();
                        response.getInt("COUNTADRESS");
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - count adress - " + response.getInt("COUNTADRESS"));
                        out.println("success/" + response.getInt("COUNTADRESS"));
                        out.flush();
                    default:
                        //Coding
                        break;
                }
                break;
            case "SPECIF_2": //Spécifique TARIK DON'T TOUCHE !!!
                switch (typeObject) {
                    case "Adress":
                        //Coding
                    default:
                        //Coding
                        break;
                }
                break;
            case "SPECIF_3": //Spécifique RUBEN
                switch (typeObject) {
                    case "Adress":
                        //Coding
                    default:
                        //Coding
                        break;
                }
                break;
            case "SPECIF_4": //Spécifique ALEXANDRE
                switch (typeObject) {
                    case "Adress":
                        //Coding
                    default:
                        //Coding
                        break;
                }
                break;
            case "SPECIF_5": //Spécifique MARIAM
                switch (typeObject) {
                    case "Adress":
                        //Coding
                    default:
                        //Coding
                        break;
                }
                break;
            case "SPECIF_6": //Spécifique LINDA
                switch (typeObject) {
                    case "Adress":
                        //Coding
                    default:
                        //Coding
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
        out.println("Error/Authentification incorrecte");
        out.flush();
        listener.changeTextLog("CONNECT_WARNING - " + login + " - dismissed");
        return false;
    }
    
    user = getAdvisor(login, pwd);
    getAdressAgency(login, pwd);
    if(user.getError() == true)   {
        out.println("Error/Erreur de récupération des informations");
        out.flush();
        listener.changeTextLog("CONNECT_WARNING - " + login + " - dismissed");
        return false;
    }
    
    for (int i = 0; i < Server.poolSize; i++) {
        if (Server.connectionPool[i].getUser().equals(login)) {
            out.println("Error/Cet utilisateur est déja connecté (ressayer ultérieurement)");
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
            out.println("Success/" + gsonSerial.serializeUser(user));
            out.flush();
            listener.changeTextLog("CONNECT_WARNING - " + login + " - connected");
            userNoPool = false;
            userConnected = true;
            return true;
        }
    }
    
    if (userNoPool == true) {
        out.println("Error/Aucune connexion disponible (ressayer ultérieurement)");
        out.flush();
        listener.changeTextLog("CONNECT_WARNING - " + login + " - no connection available");
        return false;
    }
    
    return userConnected;
  }

  private void getAdressAgency(String login, String pass) {
    Connection conn = null;
    Statement stat = null;
    ResultSet results = null;
    
    try {
      conn = Server.getConnection();
      stat = conn.createStatement();
      results = stat.executeQuery("SELECT t_adress.id_adress, t_adress.street_nb, t_adress.street_name, t_adress.city_name, t_adress.zip_code " + 
                                  "FROM t_adress, t_advisor " + 
                                  "WHERE t_adress.id_adress = t_advisor.id_agency AND t_advisor.login = '" + login + "' AND t_advisor.password = '" + pass + "';");
      
      results.next();

      Adress tmp = new Adress(results.getInt("id_adress"), 
                                results.getInt("street_nb"), 
                                results.getString("street_name"), 
                                results.getString("city_name"), 
                                results.getString("zip_code"));
      
      results.close();
      stat.close();
      conn.close();
      user.setAdressAgency(tmp);
    }
    catch (SQLException e) {
      listener.changeTextLog("ERREUR (SQL) pour récupérer les données de l'utilisateur " + login);
      user.setError(true);
    }
  }
  
  private Advisor getAdvisor(String login, String pass) {
    Connection conn = null;
    Statement stat = null;
    ResultSet results = null;
    Advisor tmp = new Advisor();
    
    try {
      conn = Server.getConnection();
      stat = conn.createStatement();
      results = stat.executeQuery("SELECT * FROM t_advisor WHERE login = '" + login + "' AND password = '" + pass + "';");
      
      results.next();

      tmp = new Advisor(results.getInt("id_advisor"), 
                       results.getString("last_name"), 
                       results.getString("first_name"), 
                       results.getBoolean("director"), 
                       results.getInt("id_agency"), 
                       results.getString("password"), 
                       results.getString("login"), 
                       results.getString("mail"));
      
      results.close();
      stat.close();
      conn.close();
      tmp.setError(false);
    }
    catch (SQLException e) {
      listener.changeTextLog("ERREUR (SQL) pour récupérer les données de l'utilisateur " + login);
      tmp.setError(true);
    }

    return tmp;
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