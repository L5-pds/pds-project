package interest_rate ;

public class RecupDonnees {

	// client's parameters
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
	
	// Each client will have a grade according to his personal information 
		// for the moment it only takes one client
	
	public static void grade(RecupDonnees client){
		int grade = 0 ;
		switch(client.getSmoker()) {
		case 1 : // client has nothing to report
			grade = grade + 1 ;
			break ;
		case 2 : // client in moderate risk 
			grade = grade + 2 ;
			break ;
		case 3 : // client in high risk
			grade = grade + 3 ;
			break ;
		default : 
			grade = 1 ;
		}
		
		switch(client.getCorpulence()) {
		case 1 :
			grade = grade + 1 ;
			break ;
		case 2 : 
			grade = grade + 2 ;
			break ;
		case 3 :
			grade = grade + 3 ;
			break ;
		default : 
			grade = 1 ;
		}
		
		switch(client.getAlcohol()) {
		case 1 :
			grade = grade + 1 ;
			break ;
		case 2 : 
			grade = grade + 2 ;
			break ;
		case 3 :
			grade = grade + 3 ;
			break ;
		default : 
			grade = 1 ;
		}
		
		switch(client.getDisease_risk()) {
		case 1 :
			grade = grade + 1 ;
			break ;
		case 2 : 
			grade = grade + 2 ;
			break ;
		case 3 :
			grade = grade + 3 ;
			break ;
		default : 
			grade = 1 ;
		}
		
		switch(client.getJob_risk()) {
		case 1 :
			grade = grade + 1 ;
			break ;
		case 2 : 
			grade = grade + 2 ;
			break ;
		case 3 :
			grade = grade + 3 ;
			break ;
		default : 
			grade = 1 ;
		}
		
		// treatment of the client and the loan : type, duration in function of salary, age
		double rate=0 ;
		double monthly_payment=0 ;
		double coeff_salary = 0 ;
		double coeff_age = 0 ;
		double monthly_salary = client.getSalaire()/12 ;
		
		switch(toUpperCase(client.getType_pret())){
		case "AUTOMOBILE" : 
			rate = 4.21 ;
			monthly_payment = ((client.getMontant_pret()*rate - client.getMontant_pret())/client.getDuree_pret_mois()) ;
		case "IMMOBILIER" :
			rate = 1.84 ;
			monthly_payment = ((client.getMontant_pret()*rate - client.getMontant_pret())/client.getDuree_pret_mois()) ; 
		case "CONSOMMATION" :
			rate = 7.61 ; 
			monthly_payment = ((client.getMontant_pret()*rate - client.getMontant_pret())/client.getDuree_pret_mois()) ;
		default : 
			System.out.println("Erreur : vérifier que le client possède un prêt");
		}
		
		
			
			
		//	(client.getMontant_pret()/client.getDuree_pret_mois()) )
		
				
			
		}


	
	private static String toUpperCase(String type_pret2) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

	
}