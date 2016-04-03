package Testing;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
 public static ServerSocket socket_ecoute = null;
 public static Thread thread_launch_ecoute;
public static Connexion_BDD[] pool_connexion;
 
	public void Launch() {
		
		try {
			socket_ecoute = new ServerSocket(1234);
			Interface_Serveur.changeTextLog("Server on-ligne.");
			Interface_Serveur.changeTextLog("Le serveur est à l'écoute du port "+socket_ecoute.getLocalPort());
			thread_launch_ecoute = new Thread(new Accepter_connexion(socket_ecoute));
			thread_launch_ecoute.start();
			
			Interface_Serveur.changeTextLog("Création du pool:");
			creer_pool(1);
			
			Interface_Serveur.changeTextLog(pool_connexion.length + " connexion de crées");
			
			
		} catch (IOException e) {
			Interface_Serveur.changeTextLog("WARNING : Problème ouverture du port (" + e.getMessage() + ")");
		}
	}
	
	private void creer_pool(int nb_connection){
		
		pool_connexion    = new Connexion_BDD[nb_connection];
		
		for(int i=0;i<nb_connection;i++)	{
			
			pool_connexion[i] = new Connexion_BDD(i);
			Interface_Serveur.changeTextLog("Connexion " + i + " créé");
		}
		
	}
	
	public static Connection getConnection() throws SQLException 
	{
        String drivers = "org.postgresql.Driver";
        System.setProperty("jdbc.drivers",drivers);
        String url = "jdbc:postgresql://192.168.192.132/base_pds";
        String username = "ncna";
        String password = "root";
        
        return DriverManager.getConnection(url, username, password);
	}
	
}
