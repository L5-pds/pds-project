package interest_rate;

import java.util.ArrayList;

public class ClientsData {
	
	public static void main(String[] args) { 
		// client
		RecupDonnees c1 = new RecupDonnees(1,"BOUZID","25", 2000, 20000,48,"Automobile",2,2,1,1,1,1) ;
		// creating a list of client 
		ArrayList<RecupDonnees> listdata = new ArrayList<RecupDonnees>();
		listdata.add(c1);
		
		System.out.println("Client number " + listdata.get(0).getId_client() + ": " + listdata.get(0).getNom());
		
		/*
		c1.getId_client() ;
		c1.getNom();
		c1.getAge();
		c1.getSalaire();
		c1.getMontant_pret();
		c1.getDuree_pret_mois();
		c1.getType_pret();
		c1.getSmoker();
		c1.getAlcohol();
		c1.getDisease();
		c1.getCorpulence();
		c1.getJob_risk();
		*/
	}
	
	
	
	

}
