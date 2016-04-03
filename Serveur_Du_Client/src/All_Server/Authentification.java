package All_Server;

import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.io.*;

public class Authentification implements Runnable {

	private Socket socket_client;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String loginpass_serealise;
	public  Thread traitement_requete;
	private String message = null;
	
	
	public Authentification(Socket socket_temp){
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
				
				out.println("connect_ok");
				out.flush();
				Interface_Serveur.changeTextLog(ConnexionClient.getnom() +" est maintenant connecter");
				user_connect=true;
			}
			else {
				out.println("erreur");
				out.flush();
				Interface_Serveur.changeTextLog(ConnexionClient.getnom() + " erreur authentification");
				user_connect=false;
			}
			
		} catch (IOException e) {
			
			Interface_Serveur.changeTextLog(ConnexionClient.getnom() +" ne répond pas !");
		}
		
			while(user_connect==true){
				try {
					message=in.readLine();
					Interface_Serveur.changeTextLog(ConnexionClient.getnom() +" dis " + message);
					out.println("Bien recu");
				    out.flush();
				} catch (IOException e) {
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
		
		if(login.equals("thibault") || login.equals("amandine"))	{
			if(pass.equals("aaa"))
				connexion=true;
		}
		
		return connexion;
	}
	
	public static void getConnection()
	{
		Connection conn = null;
		
		try{       
            Class.forName("org.postgresql.Driver");       
        }catch(ClassNotFoundException e){       
        	javax.swing.JOptionPane.showMessageDialog(null,"Driver intouvable", "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE);    
            System.exit(1);       
        }       
        Properties props = new Properties();       
        props.setProperty("user","ncna");       
        props.setProperty("password","root");       
  
        String bd="base_pds";          
        String url = "jdbc:postgresql://192.168.192.132/" + bd;       
   
        try{                   
            conn = DriverManager.getConnection(url, props);       
        }catch(SQLException e){       
        	javax.swing.JOptionPane.showMessageDialog(null,"La connexion a échoué -- " + e.getMessage(), "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE);    
            System.exit(1);       
        }       
        try{       
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);         
            ResultSet rs = st.executeQuery("SELECT * FROM t_conseiller");  
            // par exemple pour afficher nom et prenom de tous les ouvriers  
            while(rs.next()){       
                javax.swing.JOptionPane.showMessageDialog(null,"Pseudo: " + rs.getString("pseudo"));
            }       
            rs.close();       
            st.close();       
        }catch(SQLException e){       
        	//LeNom = e.getMessage();
        	javax.swing.JOptionPane.showMessageDialog(null,"problème requete", "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE);
        }      
	}

}
