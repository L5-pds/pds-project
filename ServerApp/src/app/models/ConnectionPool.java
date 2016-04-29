package app.models;

import java.sql.*;

public class ConnectionPool {

  private Connection connection;
  private int id;
  private boolean used;

  public ConnectionPool(int i){
    try {
      this.connection = Server.getConnection();
      this.id = i;
      this.used = false;
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public boolean isUsed() {
    return used;
  }

  public void use() {
    this.used = true;
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
