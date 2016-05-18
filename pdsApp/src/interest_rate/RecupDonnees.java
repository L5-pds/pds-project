package interest_rate ;

public class RecupDonnees {

	// client's data 
	public int id_client;
	public String nom ;
	public String age ;
	public int salaire ; 
	public int montant_pret ; 
	public int duree_pret_mois ; 
	public String type_pret;
	public int smoker ;
	public int disease ;
	public int corpulence ;
	public int alcohol ;
	public int disease_risk ;
	public int job_risk ; 
	
	public RecupDonnees(int id_client, String nom,String age,int salaire,int montant_pret,int duree_pret_mois, String type_pret, int smoker, int disease, int corpulence, int alcohol, int disease_risk, int job_risk) {

		this.id_client = id_client ;
		this.nom = nom ;
		this.age = age;
		this.salaire = salaire;
		this.montant_pret = montant_pret;
		this.duree_pret_mois = duree_pret_mois;
		this.type_pret = type_pret;
		this.smoker = smoker;
		this.disease = disease;
		this.corpulence = corpulence;
		this.alcohol = alcohol;
		this.disease_risk = disease_risk;
		this.job_risk = job_risk;
	}

	public int getId_client(){
		return id_client ; 
	}

	public String getNom(){
		return nom ; 
	}

	public String getAge(){
		return age ;
	}

	public int getSalaire(){
		return salaire ; 
	}
	
	public int getDisease(){
		return disease ; 
	}


	public int getMontant_pret(){
		return montant_pret ;
	}

	public int getDuree_pret_mois(){
		return duree_pret_mois ; 
	}

	public String getType_pret(){
		return type_pret ;
	}

	public int getSmoker(){
		return smoker ; 
	}

	public int getCorpulence(){
		return corpulence ; 
	}

	public int getAlcohol(){
		return alcohol ;
	}

	public int getDisease_risk(){
		return disease_risk ;
	}

	public int getJob_risk(){
		return job_risk ;
	}
	
}