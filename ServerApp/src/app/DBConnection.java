package app;

import java.util.HashMap;
import java.lang.Object;

import java.net.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;

public class DBConnection implements Runnable {
  private Socket socket;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private String serializedUser;
  private String message = null;
  private int poolIndex=-1;

  public DBConnection(Socket socket){
    this.socket = socket;
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
        ServerInterface.changeTextLog("Le client a quitté.");
        try {
          socket.close();
        } catch (IOException e1) {
          ServerInterface.changeTextLog("WARNING - Probleme de fermeture de la socket pour l'utilisateur : " + user.getLogin());
        }
      }
    }//end of while loop
  }

  public void getConnection(String login, String pwd){
    boolean userConnected = false;
    if(authentication(login, pwd)){
      for(int i=0 ; i<Server.connectionPoolSize ; i++) {
        if(Server.connectionPool[i].isUsed() == false) {
          Server.connectionPool[i].setUsed(true);
          poolIndex= i;
          out.println("authentic");
          out.flush();
          ServerInterface.changeTextLog(login + " est maintenant connecté");
          userConnected = true;
          break;
        }
      }
      if(userConnected == false) {
        out.println("Aucune connexion disponible (ressayer ultérieurement)");
        out.flush();
        ServerInterface.changeTextLog(login + " plus de connexion disponible");
        userConnected = false;
      }
    }
    else {
      out.println("Authentification incorrecte");
      out.flush();
      ServerInterface.changeTextLog(login + " erreur authentification");
      userConnected = false;
    }
  }

  private static boolean authentication(String login, String pass) {
    boolean authentic = false;
    Connection conn = null;
    Statement stat = null;
    ResultSet resultat = null;

    try {
      conn = Server.getConnection();
      stat=conn.createStatement();
      resultat=stat.executeQuery("SELECT * FROM T_CONSEILLER WHERE pseudo = '" + login + "' AND password = '" + pass + "';");

      resultat.next();

      if(resultat.getRow() != 0) {
        authentic = true;
      }
      resultat.close();
      stat.close();
      conn.close();
    }
    catch (SQLException e) {
      ServerInterface.changeTextLog("ERREUR (SQL) pour l'authentification de " + login);
      authentic=false;
    }

    return authentic;
  }
}

