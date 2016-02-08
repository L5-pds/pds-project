package BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;

public class BDD_Tools {

public static Connection conn = null;
	
	public BDD_Tools() {
		try{       
            Class.forName("org.postgresql.Driver");       
        }catch(ClassNotFoundException e){     
        	javax.swing.JOptionPane.showMessageDialog(null,"Driver introuvable", "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.exit(1);       
        }       
        Properties props = new Properties();     
  
        ResourceBundle bundle = ResourceBundle.getBundle("domaine.properties.config");
        
        props.setProperty("user",bundle.getString("login"));       
        props.setProperty("password",bundle.getString("password"));       
        String url = "jdbc:postgresql://" + bundle.getString("server") + "/" + bundle.getString("bdd");       
   
        try{                   
            conn = DriverManager.getConnection(url, props);       
        }catch(SQLException e){     
        	javax.swing.JOptionPane.showMessageDialog(null,"La connexion a �chou�", "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE); 
            System.exit(1);       
        }       	
	}
	
	public void RequeteSansReponse(String Requete)	{
     
            Statement st;
			try {
				st = conn.createStatement();
				st.executeUpdate(Requete);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}             
        
	}
	
	public ResultSet RequeteReponse(String Requete)	{
		
		/*try{       
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);         
            ResultSet rs = st.executeQuery("SELECT * FROM latable");
            while(rs.next()){       
                LeNom = rs.getString("nom");
            }       
            rs.close();       
            st.close();       
        }catch(SQLException e){
        	javax.swing.JOptionPane.showMessageDialog(null,"Problème lors de l'execution de la requête: " + e.getMessage(), "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE); 
        	System.exit(1);      
        }*/

		ResultSet LeRetour=null;
		try{       
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);         
            ResultSet rs = st.executeQuery(Requete);
            LeRetour=rs;
            //rs.close();       
            //st.close();       
        }catch(SQLException e){
        	javax.swing.JOptionPane.showMessageDialog(null,"Problème lors de l'execution de la requête: " + e.getMessage(), "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE); 
        	System.exit(1);      
        }
		
		return LeRetour;
		
	}
	
}