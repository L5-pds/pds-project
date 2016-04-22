package app;
import java.net.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;

public class Connection implements Runnable {
  private Socket socket;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private String serializedUser;
  private String message = null;
  private int poolIndex=-1;

  public Connection(Socket socket){
    this.socket = socket;
  }

  public void run() {
    User user = null;
    Serialization s = new Serialization();
    boolean userConnected=false;

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
            out.println("connect_ok");
            out.flush();
            ServerInterface.changeTextLog(user.getLogin() + " est maintenant connecter");
            userConnected = true;
            break;
          }
        }
        if(userConnected==false) {
          out.println("Aucune connexion disponible (ressayer ultérieurement)");
          out.flush();
          ServerInterface.changeTextLog(user.getLogin() + " plus de connexion disponible");
          userConnected=false;
        }
      }
      else {
        out.println("Authentification incorrect");
        out.flush();
        ServerInterface.changeTextLog(user.getLogin() + " erreur authentification");
        userConnected=false;
      }

    } catch (IOException e) {
      ServerInterface.changeTextLog("Problème de connexion...");
    }

    while(userConnected == true){
      try {
        message = in.readLine();

        ServerInterface.changeTextLog(user.getLogin() +" dis " + message);

        Customer newCustomer = s.unserializeCustomer(message);

        String creationReturn=null;

        int addressId = Server.connectionPool[poolIndex].createAddress("INSERT INTO T_ADRESSE_CLIENT (nume_rue, nom_rue, code_postal) VALUES('"
                      + newCustomer.adresse_num + "', '"
                      + newCustomer.adresse_voie + "', '"
                      + newCustomer.adresse_cp + "')",
                      "SELECT * FROM T_ADRESSE_CLIENT WHERE "
                            + "nume_rue = '" + newCustomer.adresse_num + "' AND "
                            + "nom_rue = '" + newCustomer.adresse_voie + "' AND "
                            + "code_postal = '" + newCustomer.adresse_cp + "'");

        creationReturn = Server.connectionPool[poolIndex].createClient("INSERT INTO T_CLIENT (nom_client, prenom_client, mail_client, id_agence, id_adresse) VALUES('"
                    + newCustomer.nom + "', '"
                    + newCustomer.prenom + "', '"
                    + newCustomer.mail + "', 2, "
                    + addressId + ")",
                    "SELECT COUNT(*) AS Result FROM T_CLIENT WHERE "
                            + "nom_client = '" + newCustomer.nom + "' AND "
                            + "prenom_client = '" + newCustomer.prenom + "' AND "
                            + "mail_client = '" + newCustomer.mail + "' AND "
                            + "id_adresse = '" + addressId + "'");

        ServerInterface.changeTextLog("AJOUT --> " + newCustomer.nom + " " + newCustomer.prenom + " (" + creationReturn + ")" );
        out.println(creationReturn);
        out.flush();
      } catch (IOException e) {
        Server.connectionPool[poolIndex].setUsed(false);
        ServerInterface.changeTextLog(user.getLogin() +" s'est déconnecté");

        userConnected=false;
        try {
          socket.close();
        } catch (IOException e1) {
          ServerInterface.changeTextLog("WARNING - Problème de fermeture de la socket pour l'utilisateur : " + user.getLogin());
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

      if(resultat.getRow()==0) {
        authentic=false;
      }else {
        authentic=true;
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

