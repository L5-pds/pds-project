package app;

public class User {

  private String nom;
  private String mdp;

  public User(String n, String a) {
      nom = n;
      mdp = a;
  }

  public String getnom()  {
    return nom;
  }

  public String getmdp()  {
    return mdp;
  }

}
