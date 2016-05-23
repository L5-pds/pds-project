package interest_rate;

import java.util.ArrayList;

public class ClientsData {
	
	public static void main(String[] args) { 
		// client
		Rate_calculation c1 = new Rate_calculation(1,"BOUZID",25, 20000, 20000,48,"AUTOMOBILE",2,2,1,1,1,1) ;
		// creating a list of client 
		ArrayList<Rate_calculation> listdata = new ArrayList<Rate_calculation>();
		
		listdata.add(c1);
		
		// System.out.println("Client number " + listdata.get(0).getId_client() + ": " + listdata.get(0).getname());
		
		
		System.out.println(c1.grade());
		
		/*
		c1.setId_client() ;
		c1.setNom();
		c1.setAge();
		c1.setSalaire();
		c1.setMontant_pret();
		c1.setDuree_pret_mois();
		c1.setType_pret();
		c1.setSmoker();
		c1.setAlcohol();
		c1.setDisease();
		c1.setCorpulence();
		c1.setJob_risk();
		*/
	}

}
