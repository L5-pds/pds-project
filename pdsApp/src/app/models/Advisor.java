package app.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Advisor {

  private int id;
  private String last_name;
  private String first_name;
  private boolean director;
  private int id_agency;
  private String password;
  private String login;
  private String mail;
  private Address agencyAddress;
  private boolean error;
  private String errorMessage;

  public Advisor() {
    this.id = -1;
    this.last_name = "";
    this.first_name = "";
    this.director = false;
    this.id_agency = -1;
    this.password = "";
    this.login = "";
    this.mail = "";
    this.error = true;
  }

  public Advisor(String login, String password) {
      this.login = login;
      this.password = password;
  }

  public Advisor(int id,
          String last_name,
          String first_name,
          boolean director,
          int id_agency,
          String password,
          String login,
          String mail) {
    this.id = id;
    this.last_name = last_name;
    this.first_name = first_name;
    this.director = director;
    this.id_agency = id_agency;
    this.password = password;
    this.login = login;
    this.mail = mail;
  }

  public void setAgencyAddress(Address agencyAddress)  {
      this.agencyAddress = agencyAddress;
  }

  public void setError(boolean error)   {
      this.error = error;
  }

  public void setErrorMessage(String errorMessage)   {
      this.errorMessage = errorMessage;
  }

  public int getId() {
    return id;
  }

  public String getLastName() {
    return last_name;
  }

  public String getFirstName() {
    return first_name;
  }

  public boolean isDirector() {
    return director;
  }

  public int getAgency() {
    return id_agency;
  }

  public String getLogin() {
    return login;
  }

  public String getPassword() {
    return password;
  }

  public String getMail() {
    return mail;
  }

  public Address getAddress() {
    return agencyAddress;
  }

  public boolean getError() {
    return error;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void getAdvisor() {
    Connection conn = null;
    Statement stat = null;
    ResultSet results = null;

    try {
      conn = Server.getConnection();
      stat = conn.createStatement();
      results = stat.executeQuery("SELECT * FROM t_advisor WHERE login = '" + this.login + "' AND password = '" + this.password + "';");

      results.next();

      this.id = results.getInt("id_advisor");
      this.last_name = results.getString("last_name");
      this.first_name = results.getString("first_name");
      this.director = results.getBoolean("director");
      this.id_agency = results.getInt("id_agency");
      this.password = results.getString("password");
      this.login = results.getString("login");
      this.mail = results.getString("mail");

      results.close();

      results = stat.executeQuery("SELECT t_adress.id_adress, t_adress.street_nb, t_adress.street_name, t_adress.city_name, t_adress.zip_code " +
                                  "FROM t_adress, t_advisor " +
                                  "WHERE t_adress.id_adress = t_advisor.id_agency AND t_advisor.login = '" + this.login + "' AND t_advisor.password = '" + this.password + "';");

      results.next();

      this.agencyAddress = new Address(results.getInt("id_adress"),
                                results.getInt("street_nb"),
                                results.getString("street_name"),
                                results.getString("city_name"),
                                results.getString("zip_code"));

      stat.close();
      conn.close();
      this.error = false;
    }
    catch (SQLException e) {
      this.errorMessage = "ERREUR (SQL) pour récupérer les données de l'utilisateur" + this.login;
      this.error = true;
    }
  }
}
