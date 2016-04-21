package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connexion_BDD {

  private Connection conn;
  @SuppressWarnings("unused")
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
  
  public boolean isUse()  {
    return use;
  }
  
  public void setUse(boolean used)  {
    use=used;
    Server.MAJ_IHM_Connection();
  }
  
  public String Add_Client(String Requete1, String Requete2){
    
    String Retour_Function=null;
    Statement stat;
    try {
      
      stat = conn.createStatement();
      
      ResultSet IfExist = stat.executeQuery(Requete2);
      
      IfExist.next();
      if(IfExist.getInt("Result")==0) {
        stat = conn.createStatement();
        stat.executeUpdate(Requete1);
        Retour_Function="OK";
      }
      else  {
        Retour_Function="Utilisateur déja présent dans la base";
      }
        
    } catch (SQLException e) {
      e.printStackTrace();
      Retour_Function="Erreur SQL";
    }
    
    return Retour_Function;
  }
  
  public int Add_Adresse(String Requete1, String Requete2){
    
    int Retour_Function=0;
    Statement stat;
    try {
      
      stat = conn.createStatement();
      
      ResultSet Response = stat.executeQuery(Requete2);
      
      Response.next();

      if(Response.getRow()==0)  {
        stat = conn.createStatement();
        stat.executeUpdate(Requete1);
        
        stat = conn.createStatement();
        Response = stat.executeQuery(Requete2);
        
        Response.next();
        Retour_Function=Response.getInt("id_adresse");
      }
      else  {
        Retour_Function=Response.getInt("id_adresse");
      }
        
    } catch (SQLException e) {
      e.printStackTrace();
      Retour_Function=0;
    }
    
    return Retour_Function;

  }
  
  public ResultSet All_Client(String Requete){
    
    ResultSet Response = null;
    Statement stat;
    try {
      stat = conn.createStatement();
      Response = stat.executeQuery(Requete);
        
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Response;
  }
  
  
}
