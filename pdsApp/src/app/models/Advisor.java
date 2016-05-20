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
  private Agency agencyInfo;
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

  public void setAgency(Agency agencyInfo)  {
      this.agencyInfo = agencyInfo;
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

  public Agency getAgencyInfo() {
    return agencyInfo;
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
      results = stat.executeQuery("SELECT * FROM t_advisor, t_agency WHERE login = '" + this.login + "' AND t_agency.id_agency = t_advisor.id_agency;");

      results.next();

      this.id = results.getInt("id_advisor");
      this.last_name = results.getString("last_name");
      this.first_name = results.getString("first_name");
      this.director = results.getBoolean("director");
      this.id_agency = results.getInt("id_agency");
      this.password = results.getString("password");
      this.login = results.getString("login");
      this.mail = results.getString("mail");

      this.agencyInfo = new Agency(results.getInt("id_agency"),
                                results.getString("name"),
                                results.getInt("id_adress"),
                                results.getString("phone_nb"),
                                results.getString("fax_nb"));

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
