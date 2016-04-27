package app;
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
    boolean userConnected = false;

    while(!socket.isClosed()){
      if(userConnected == false){
        try {
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          out = new PrintWriter(socket.getOutputStream());

          serializedUser = in.readLine();
          user = s.unserializeUser(serializedUser);

          if(authentication(user.getLogin(), user.getPwd())){
            for(int i=0 ; i<Server.connectionPoolSize ; i++) {
              if(Server.connectionPool[i].isUsed() == false) {
                Server.connectionPool[i].setUsed(true);
                poolIndex= i;
                out.println("authentic");
                out.flush();
                ServerInterface.changeTextLog(user.getLogin() + " est maintenant connecté");
                userConnected = true;
                break;
              }
            }
            if(userConnected == false) {
              out.println("Aucune connexion disponible (ressayer ultérieurement)");
              out.flush();
              ServerInterface.changeTextLog(user.getLogin() + " plus de connexion disponible");
              userConnected = false;
            }
          }
          else {
            out.println("Authentification incorrecte");
            out.flush();
            ServerInterface.changeTextLog(user.getLogin() + " erreur authentification");
            userConnected = false;
          }

        } catch (Exception e) {
          ServerInterface.changeTextLog("Le client a quitté.");
          try {
            socket.close();
          } catch (IOException e1) {
            ServerInterface.changeTextLog("WARNING - Probleme de fermeture de la socket pour l'utilisateur : " + user.getLogin());
          }
        }
      }
      else{
        try {
          String creationReturn=null;
          message = in.readLine();
          Customer newCustomer = s.unserializeCustomer(message);
          int addressId = Server.connectionPool[poolIndex].createAddress("INSERT INTO T_ADRESSE_CLIENT (nume_rue, nom_rue, code_postal) VALUES('"
                        + newCustomer.addressNumber + "', '"
                        + newCustomer.street + "', '"
                        + newCustomer.zipCode + "')",
                        "SELECT * FROM T_ADRESSE_CLIENT WHERE "
                              + "nume_rue = '" + newCustomer.addressNumber + "' AND "
                              + "nom_rue = '" + newCustomer.street + "' AND "
                              + "code_postal = '" + newCustomer.zipCode + "'");

          creationReturn = Server.connectionPool[poolIndex].createClient("INSERT INTO T_CLIENT (nom_client, prenom_client, mail_client, id_agence, id_adresse) VALUES('"
                      + newCustomer.lastName + "', '"
                      + newCustomer.firstName + "', '"
                      + newCustomer.mail + "', 2, "
                      + addressId + ")",
                      "SELECT COUNT(*) AS Result FROM T_CLIENT WHERE "
                              + "nom_client = '" + newCustomer.lastName + "' AND "
                              + "prenom_client = '" + newCustomer.firstName + "' AND "
                              + "mail_client = '" + newCustomer.mail + "' AND "
                              + "id_adresse = '" + addressId + "'");

          ServerInterface.changeTextLog("AJOUT --> " + newCustomer.lastName + " " + newCustomer.firstName + " (" + creationReturn + ")" );
          out.println(creationReturn);
          out.flush();
        } catch (Exception e) {
          Server.connectionPool[poolIndex].setUsed(false);
          ServerInterface.changeTextLog(user.getLogin() +" s'est déconnecté");

          userConnected = false;
          try {
            socket.close();
          } catch (IOException e1) {
            ServerInterface.changeTextLog("WARNING - Problème de fermeture de la socket pour l'utilisateur : " + user.getLogin());
          }
        }
      }
    }//end of while loop
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

