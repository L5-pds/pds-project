package All_Server;

public class New_client {
	
	public String nom;
	public String prenom;
	public String mail;

	public String adresse_num;
	public String adresse_voie;
	public String adresse_cp;
	
	New_client(String temps_nom, 
				String temps_prenom, 
				String temps_mail, 
				String temps_anum, 
				String temps_avoie, 
				String temps_acp){
		
		nom=temps_nom;
		prenom=temps_prenom;
		mail=temps_mail;
		adresse_num=temps_anum;
		adresse_voie=temps_avoie;
		adresse_cp=temps_acp;
		
	}
	
}