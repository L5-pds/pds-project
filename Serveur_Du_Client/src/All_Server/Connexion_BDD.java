package All_Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Connexion_BDD {

	private Connection conn;
	private int        Numero_conn;
	private boolean    use;
	
	Connexion_BDD(int i){
		try {
			conn = Server.getConnection();
			Numero_conn=i;
			use=false;
		} catch (SQLException e) {
			System.out.println("Erreur");
		}
	}
	
	public boolean isUse()	{
		return use;
	}
	
	public void setUse(boolean used)	{
		use=used;
		Server.MAJ_IHM_Connection();
	}
	
	public void Add_Client(String Requete){
		
		Statement stat;
		try {
			stat = conn.createStatement();
	 	   	int rowadd = stat.executeUpdate(Requete);
	 	   System.out.println("Ajout de la ligne" + rowadd);
	 	   	
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
}
