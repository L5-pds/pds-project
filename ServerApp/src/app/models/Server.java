package app.models;

import java.sql.*;
import java.util.ResourceBundle;

public class Server {
  public static ConnectionPool[] connectionPool;
  public static int poolSize;

  public Server (int size){
    this.poolSize = size;
    this.connectionPool = new ConnectionPool[size];
  }

  public void createConnectionPool(){
    for (int i = 0; i < poolSize ; i++) {
      this.connectionPool[i] = new ConnectionPool(i);
    }
  }

  public static Connection getConnection() throws SQLException{
    ResourceBundle bundle = ResourceBundle.getBundle("domaine.properties.config");

    String drivers = "org.postgresql.Driver";
    System.setProperty("jdbc.drivers",drivers);
    String url = "jdbc:postgresql://" + bundle.getString("server") + "/" + bundle.getString("dbName");
    String username = bundle.getString("login");
    String password = bundle.getString("password");

    return DriverManager.getConnection(url, username, password);
  }
}
