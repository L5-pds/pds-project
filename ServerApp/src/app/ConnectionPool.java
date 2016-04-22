package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPool {

  private Connection connection;
  @SuppressWarnings("unused")
  private int id;
  private boolean used;

  ConnectionPool(int i){
    try {
      connection = Server.getConnection();
      id=i;
      used=false;
    } catch (SQLException e) {
      System.out.println("Erreur");
    }
  }

  public boolean isUsed() {
    return used;
  }

  public void setUsed(boolean used) {
    this.used = used;
    Server.updateConnectionAvailability();
  }

  public String createClient(String query1, String query2){
    String queryReturn=null;
    Statement stat;
    try {
      stat = connection.createStatement();
      ResultSet isExisting = stat.executeQuery(query2);

      isExisting.next();
      if(isExisting.getInt("Result")==0) {
        stat = connection.createStatement();
        stat.executeUpdate(query1);
        queryReturn="OK";
      }
      else  {
        queryReturn="Utilisateur déja présent dans la base";
      }

    } catch (SQLException e) {
      e.printStackTrace();
      queryReturn="Erreur SQL";
    }

    return queryReturn;
  }

  public int createAddress(String query1, String query2){
    int queryReturn=0;
    Statement stat;
    try {

      stat = connection.createStatement();
      ResultSet queryResults = stat.executeQuery(query2);
      queryResults.next();

      if(queryResults.getRow()==0)  {
        stat = connection.createStatement();
        stat.executeUpdate(query1);

        stat = connection.createStatement();
        queryResults = stat.executeQuery(query2);

        queryResults.next();
        queryReturn=queryResults.getInt("id_adresse");
      }
      else  {
        queryReturn=queryResults.getInt("id_adresse");
      }

    } catch (SQLException e) {
      e.printStackTrace();
      queryReturn=0;
    }

    return queryReturn;
  }

}
