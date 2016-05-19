package app.models;

public class Advisor {

  private int id;
  private String last_name;
  private String first_name;
  private boolean director;
  private int id_agency;
  private String password;
  private String login;
  private String mail;
  private Adress adressAgency;
  private boolean error;
  
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
  
  public void setAdressAgency(Adress adressAgency)  {
      this.adressAgency = adressAgency;
  }
  
  public void setError(boolean error)   {
      this.error = error;
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

  public Adress getAdress() {
    return adressAgency;
  }

  public boolean getError() {
    return error;
  }

}
