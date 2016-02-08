package Tools;

public class Client {

	private int id;
	private String nom;
    private String prenom;
    private String mail;
    private double salaire;
    private String mdp;
    private int id_agence;
    private int id_adresse;
 
    public Client(int id, String nom, String prenom, String mail, double salaire, String mdp, int id_agence, int id_adresse) {
        super();

        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.salaire = salaire;
        this.mdp = mdp;
        this.id_agence = id_agence;
        this.id_adresse = id_adresse;
    }
    
    public Client(Client ClientCreer)	{
    	super();
    	
    	this.id = ClientCreer.id;
        this.nom = ClientCreer.nom;
        this.prenom = ClientCreer.prenom;
        this.mail = ClientCreer.mail;
        this.salaire = ClientCreer.salaire;
        this.mdp = ClientCreer.mdp;
        this.id_agence = ClientCreer.id_agence;
        this.id_adresse = ClientCreer.id_adresse;
    }
    
    public String GetNom()	{
    	return this.nom;
    }
    
    public String GetPrenom()	{
    	return this.prenom;
    }
	
}
