package app;

public class User {

  private String login;
  private String pwd;

  public User(String n, String a) {
      login = n;
      pwd = a;
  }

  public String getLogin()  {
    return login;
  }

  public String getPwd()  {
    return pwd;
  }

}
