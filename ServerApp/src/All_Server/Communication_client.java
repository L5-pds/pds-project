package All_Server;
import java.net.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;

public class Communication_client implements Runnable {

	private Socket socket_client;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String loginpass_serealise;
	private String message = null;
	private int index_pool=-1;
	
	
	public Communication_client(Socket socket_temp){
		socket_client = socket_temp;
		}
	
	public void run() {
	
		Personne ConnexionClient = null;
		SerialisationPersonne sp = new SerialisationPersonne();
		boolean user_connect=false;
		
		try {
			
			in = new BufferedReader(new InputStreamReader(socket_client.getInputStream()));
			out = new PrintWriter(socket_client.getOutputStream());
			
			loginpass_serealise = in.readLine();
			
			ConnexionClient = sp.deserialiserp(loginpass_serealise);
			
			if(isValid(ConnexionClient.getnom(), ConnexionClient.getmdp())){
				for(int i=0 ; i<Server.Nb_max_connect_bdd ; i++)	{
					if(Server.pool_connexion[i].isUse()==false)	{
						Server.pool_connexion[i].setUse(true);
						index_pool=i;
						out.println("connect_ok");
						out.flush();
						Interface_Serveur.changeTextLog(ConnexionClient.getnom() +" est maintenant connecter");
						user_connect=true;
						break;
					}
				}
				if(user_connect==false)	{
					out.println("Aucune connexion disponible (ressayer ultérieurement)");
					out.flush();
					Interface_Serveur.changeTextLog(ConnexionClient.getnom() + " plus de connexion disponible");
					user_connect=false;
				}
			}
			else {
				out.println("Authentification incorrect");
				out.flush();
				Interface_Serveur.changeTextLog(ConnexionClient.getnom() + " erreur authentification");
				user_connect=false;
			}
			
		} catch (IOException e) {
			
			Interface_Serveur.changeTextLog("Problème de connexion...");
		}
		
			while(user_connect==true){
				try {
					message=in.readLine();
					Interface_Serveur.changeTextLog(ConnexionClient.getnom() +" dis " + message);
					
					New_client add_client = sp.deserialiserc(message);
					
					String Retour_Function=null;
					
					int id_adresse = Server.pool_connexion[index_pool].Add_Adresse("INSERT INTO T_ADRESSE_CLIENT (nume_rue, nom_rue, code_postal) VALUES('" 
							+ add_client.adresse_num + "', '"
							+ add_client.adresse_voie + "', '"
							+ add_client.adresse_cp + "')",
							"SELECT * FROM T_ADRESSE_CLIENT WHERE " 
									+ "nume_rue = '" + add_client.adresse_num + "' AND "
									+ "nom_rue = '" + add_client.adresse_voie + "' AND "
									+ "code_postal = '" + add_client.adresse_cp + "'");
						
		
					
					Retour_Function=Server.pool_connexion[index_pool].Add_Client("INSERT INTO T_CLIENT (nom_client, prenom_client, mail_client, id_agence, id_adresse) VALUES('" 
							+ add_client.nom + "', '"
							+ add_client.prenom + "', '"
							+ add_client.mail + "', 2, " 
							+ id_adresse + ")",
							"SELECT COUNT(*) AS Result FROM T_CLIENT WHERE " 
									+ "nom_client = '" + add_client.nom + "' AND "
									+ "prenom_client = '" + add_client.prenom + "' AND "
									+ "mail_client = '" + add_client.mail + "' AND "
									+ "id_adresse = '" + id_adresse + "'");
							
					Interface_Serveur.changeTextLog("AJOUT --> " + add_client.nom + " " + add_client.prenom + " (" + Retour_Function + ")" );
					out.println(Retour_Function);
				    out.flush();
				} catch (IOException e) {
					Server.pool_connexion[index_pool].setUse(false);
					Interface_Serveur.changeTextLog(ConnexionClient.getnom() +" s'est déconnecté");
					
					user_connect=false;
					try {
						socket_client.close();
					} catch (IOException e1) {
						Interface_Serveur.changeTextLog("WARNING - Problème de fermeture de la socket pour l'utilisateur : " + ConnexionClient.getnom());
					}
				}
			}
		
	}
	
	private static boolean isValid(String login, String pass) {
		boolean connexion = false;		
		Connection conn = null;
		Statement stat = null;
 	   ResultSet resultat = null;
		
		
		try {
			conn = Server.getConnection();
			stat=conn.createStatement();
			resultat=stat.executeQuery("SELECT * FROM T_CONSEILLER WHERE pseudo = '" + login + "' AND password = '" + pass + "';");
			
			resultat.next();
			
			if(resultat.getRow()==0)	{
				connexion=false;
			}else	{
				connexion=true;
			}
			resultat.close();
            stat.close();
	    	conn.close();
			
		} catch (SQLException e) {
			Interface_Serveur.changeTextLog("ERREUR (SQL) pour l'authentification de " + login);
			connexion=false;
		}
		
		return connexion;
	}

}

