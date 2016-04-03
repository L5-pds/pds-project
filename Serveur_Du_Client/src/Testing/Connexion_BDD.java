package Testing;

import java.sql.Connection;
import java.sql.SQLException;

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
	}
	
}
