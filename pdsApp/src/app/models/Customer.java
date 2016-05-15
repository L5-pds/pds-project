package app.models;

import java.sql.*;

public class Customer {
  private int id;
  private String lastName;
  private String firstName;
  private String mail;

  private String addressNumber;
  private String street;
  private String zipCode;
  
  private ResultSet responseRequest;

  public Customer()   {
    this.id = -1;
    this.lastName = "";
    this.firstName = "";
    this.mail = "";
    this.addressNumber = "";
    this.street = "";
    this.zipCode = "";
  }
  
  public Customer(int id,
        String lastName,
        String firstName,
        String mail,
        String addressNumber,
        String street,
        String zipCode){
    this.id = id;
    this.lastName = lastName;
    this.firstName = firstName;
    this.mail = mail;
    this.addressNumber = addressNumber;
    this.street = street;
    this.zipCode = zipCode;
  }

  public void serverGetAllUser(int indexPool) {
      responseRequest = Server.connectionPool[indexPool].selectWithRespons("SELECT * FROM t_client WHERE id_client < 2000;");
  }

  public void serverGetOnlyUser(int id) {
      
  }
  
  public int getCustomerCount() {
      int result = -1;
      try   {
          responseRequest.last();
          result = responseRequest.getRow();
      } catch (SQLException e) {
          
      }
      
      return result;
  }
  
}
