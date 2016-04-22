package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPool {

  private Connection connection;
  private int id;
  private boolean used;

  ConnectionPool(int i){
    try {
      connection = Server.getConnection();
      id  =i;
      used = false;
    } catch (SQLException e) {
      System.out.println("Erreur CoonectionPool cannot getConnection");
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
        queryReturn="Utilisateur d�ja pr�sent dans la base";
      }

    } catch (SQLException e) {
      e.printStackTrace();
      queryReturn="Erreur SQL";
    }

    return queryReturn;
  }

  public int createAddress(String insertQuery, String selectQuery){
    int addressId = 0;
    Statement stat;
    try {
      stat = connection.createStatement();
      ResultSet queryResults = stat.executeQuery(selectQuery);
      System.out.println(queryResults);
      queryResults.next();
      System.out.println(queryResults);


      if(queryResults.getRow() == 0) {
        stat = connection.createStatement();
        stat.executeUpdate(insertQuery);

        stat = connection.createStatement();
        queryResults = stat.executeQuery(selectQuery);

        queryResults.next();
        addressId = queryResults.getInt("id_adresse");
      }
      else  {
        addressId = queryResults.getInt("id_adresse");
      }

    } catch (SQLException e) {
      e.printStackTrace();
      addressId=0;
    }

    return addressId;
  }

}
