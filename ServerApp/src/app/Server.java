package app;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Server {

  public static ServerSocket socket_ecoute = null;
  public static Thread thread_launch_ecoute;
  public static Connexion_BDD[] pool_connexion;
  public static int Nb_max_connect_bdd;

  public void launch() {

    try {
      socket_ecoute = new ServerSocket(1234);
      Interface_Serveur.changeTextLog("Server on-ligne.");
      Interface_Serveur.changeTextLog("Le serveur est à l'écoute du port "+socket_ecoute.getLocalPort());
      thread_launch_ecoute = new Thread(new Accepter_connexion(socket_ecoute));
      thread_launch_ecoute.start();

      ResourceBundle bundle = ResourceBundle.getBundle("domaine.properties.config");

      Nb_max_connect_bdd = Integer.parseInt(bundle.getString("nb_max_conect_bdd"));

      Interface_Serveur.changeTextLog("Création du pool:");
      creer_pool(Nb_max_connect_bdd);

      MAJ_IHM_Connection();

      Interface_Serveur.changeTextLog(pool_connexion.length + " connexion de crées");


    } catch (IOException e) {
      Interface_Serveur.changeTextLog("WARNING : Problème ouverture du port (" + e.getMessage() + ")");
    }
  }

  private void creer_pool(int nb_connection){

    pool_connexion    = new Connexion_BDD[nb_connection];

    for(int i=0;i<nb_connection;i++)  {

      pool_connexion[i] = new Connexion_BDD(i);
      Interface_Serveur.changeTextLog("Connexion " + i + " créé");
    }

  }

  public static Connection getConnection() throws SQLException
  {
    ResourceBundle bundle = ResourceBundle.getBundle("domaine.properties.config");

        String drivers = "org.postgresql.Driver";
        System.setProperty("jdbc.drivers",drivers);
        String url = "jdbc:postgresql://" + bundle.getString("server") + "/" + bundle.getString("bdd");
        String username = bundle.getString("login");
        String password = bundle.getString("password");

        return DriverManager.getConnection(url, username, password);
  }

  public static void MAJ_IHM_Connection() {
    int connect=0;
    int dispo=0;

    for(int i=0;i<pool_connexion.length;i++)  {
      if(pool_connexion[i].isUse()==true) {
        connect++;
      }else {
        dispo++;
      }

      Interface_Serveur.updateInfoLabel(connect, dispo);
    }
  }
}
