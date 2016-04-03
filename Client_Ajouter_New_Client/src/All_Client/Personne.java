package All_Client;

public class Personne {
    
    private String nom;
    private String mdp;
    
    public Personne(String n, String a) {
        nom = n;
        mdp = a;
    }

    public String getnom()	{
    	return nom;
    }
    
    public String getmdp()	{
    	return mdp;
    }
    
    /*public String toString() {
        String s = nom + ", " + mdp ;
        return s;
    }*/
}