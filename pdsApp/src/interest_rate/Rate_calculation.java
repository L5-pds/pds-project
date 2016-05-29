package interest_rate;
import java.time.*;
import java.util.*;

public class Rate_calculation {

	// client's parameters
	
	//public int id_client;
	//public String name ;
	public int age ;
	public int salary ; 
	public int loan_amount ; 
	public int loan_duration ; 
	public String type_loan;
	public int smoker ;
	public int disease ;
	public int corpulence ;
	public int alcohol ;
	public int disease_risk ;
	public int job_risk ; 

	public Rate_calculation(
			int age,
			int salary,
			int loan_amount,
			int loan_duration, 
			String type_loan, 
			int smoker,
			int disease,
			int corpulence, 
			int alcohol, 
			int disease_risk, 
			int job_risk) 
	{
		//this.id_client = id_client ;
		//this.name = name ;
		this.age = age;
		this.salary = salary;
		this.loan_amount = loan_amount;
		this.loan_duration = loan_duration;
		this.type_loan = type_loan;
		this.smoker = smoker;
		this.disease = disease;
		this.corpulence = corpulence;
		this.alcohol = alcohol;
		this.disease_risk = disease_risk;
		this.job_risk = job_risk;
	}

	// Each client will have a grade according to his personal information 
	// for the moment it only takes one client

	public double grade(){

		double grade = 0 ;
		double rate = 0 ; // need to be retrieved from the database?
		double monthly_payment = 0 ; 
		double debt_ratio = 0 ;

		switch(smoker) {
		case 1 : // client has nothing to report
			grade = grade + 1 ;
			break ;

		case 2 : // client in moderate risk 
			grade = grade + 2 ;
			break ;

		case 3 : // client in high risk
			grade = grade + 3 ;
			break ;

		default : // the client didn't give the information to the bank
			grade = 1 ;
		}

		switch(corpulence) {
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
	
		switch(alcohol) {
	
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

		switch(corpulence) {
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


		switch(disease_risk) {
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

		switch(job_risk) {

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
		System.out.println("grade only on health parameters : " +grade);

		// treatment of the client and the loan : the more the client earns, the smallest the coefficient will be => the grade too
		switch(type_loan){

		case "AUTOMOBILE" :
			rate = 0.0421 ;
			// creating gaps for the duration and the amount of the loan
			if (loan_duration < 36){
				if ((loan_amount >= 3000) && (loan_amount<15000)){
					grade = grade - 1 ;	
				}
				else if ((loan_amount >= 15000) && (loan_amount<30000)) {
					grade = grade - 1 ;
				}
				else if ((loan_amount >= 30000) && (loan_amount<45000)){
					grade = grade - 1 ;
				}
				else if ((loan_amount >= 45000) && (loan_amount <= 60000)){
					grade = grade - 1 ;
				}
				else {

					System.out.println("Le montant n'est pas défini pour ce type du prêt 1-1") ;
				}
			}
			else if ((loan_duration>=36) && (loan_duration <60)){
				if ((loan_amount >= 3000) && (loan_amount<15000)){
					grade = grade ;	
				}
				else if ((loan_amount >= 15000) && (loan_amount < 30000)) {
					grade = grade  ;
				}

				else if ((loan_amount >= 30000) && (loan_amount < 45000)){
					grade = grade + 0.5 ;
				}
				else if ((loan_amount >= 45000) && (loan_amount <= 60000)){
					grade = grade - 0.5 ;
				}
				else {
					System.out.println(("Le montant n'est pas défini pour ce type de prêt 1-2"));
				}
			}
			else if ((loan_duration>=60) && (loan_duration <= 84) ){
				if ((loan_amount >= 3000) && (loan_amount<15000)){
					grade = grade ;	
				}
				else if ((loan_amount >= 15000) && (loan_amount < 30000)) {
					grade = grade - 1 ;
				}
				else if ((loan_amount >= 30000) && (loan_amount < 45000)){
					grade = grade + 0.5 ;
				}
				else if ((loan_amount >= 45000) && (loan_amount<=60000)){
					grade = grade +1 ;
				}
				else {
					System.out.println("Le montant n'est pas défini pour ce type de prêt 1-3");
				}

			}
			else System.out.println("La dur�e n'est pas définie pour ce type de prêt");

			System.out.println("grade after adding duration and amount parameters : " + grade) ;
			break ;

		case "IMMOBILIER" :
			rate = 0.0184 ;
			// creating gaps for the duration and the amount of the loan
			if ((loan_duration >= 60) && (loan_duration <180)){
				if ((loan_amount >= 50000) && (loan_amount < 250000)){
					grade = grade - 1 ;	
				}
				else if ((loan_amount >= 250000) && (loan_amount<450000)) {
					grade = grade - 1 ;
				}
				else if ((loan_amount >= 450000) && (loan_amount<650000)){
					grade = grade - 1 ;
				}
				else if ((loan_amount >= 650000) && (loan_amount <= 750000)){
					grade = grade - 1 ;
				}
				else {

					System.out.println("Le montant n'est pas défini pour ce type du prêt 2-1") ;
				}
			}
			else if ((loan_duration>=180) && (loan_duration <240)){
				if ((loan_amount >= 50000) && (loan_amount < 250000)){
					grade = grade ;	
				}
				else if ((loan_amount >= 250000) && (loan_amount<450000)) {
					grade = grade  ;
				}

				else if ((loan_amount >= 450000) && (loan_amount<650000)){
					grade = grade + 0.5 ;
				}
				else if ((loan_amount >= 650000) && (loan_amount <= 750000)){
					grade = grade - 0.5 ;
				}
				else {
					System.out.println(("Le montant n'est pas défini pour ce type de prêt 2-2"));
				}
			}
			else if ((loan_duration>=240) && (loan_duration <= 360) ){
				if ((loan_amount >= 50000) && (loan_amount < 250000)){
					grade = grade ;	
				}
				else if ((loan_amount >= 250000) && (loan_amount<450000)) {
					grade = grade - 1 ;
				}
				else if ((loan_amount >= 450000) && (loan_amount<650000)){
					grade = grade + 0.5 ;
				}
				else if ((loan_amount >= 650000) && (loan_amount <= 750000)){
					grade = grade +1 ;
				}
				else {
					System.out.println("Le montant n'est pas défini pour ce type de prêt 2-3");
				}

			}
			else System.out.println("La durée n'est pas défini pour ce type de prêt");

			System.out.println("grade after adding duration and amount parameters : " + grade) ;
			break ;

		case "DIVERS" :
			rate = 0.761 ; 
			// creating gaps for the duration and the amount of the loan
			if ((loan_duration >= 3) && (loan_duration <24)){
				if ((loan_amount >= 200) && (loan_amount < 20000)){
					grade = grade - 1 ;	
				}
				else if ((loan_amount >= 20000) && (loan_amount<40000)) {
					grade = grade - 1 ;
				}
				else if ((loan_amount >= 40000) && (loan_amount<60000)){
					grade = grade - 1 ;
				}
				else if ((loan_amount >= 60000) && (loan_amount <= 75000)){
					grade = grade - 1 ;
				}
				else {

					System.out.println("Le montant n'est pas défini pour ce type de prêt 3-1") ;
				}
			}
			else if ((loan_duration>=24) && (loan_duration <48)){
				if ((loan_amount >= 200) && (loan_amount < 20000)){
					grade = grade ;	
				}
				else if ((loan_amount >= 20000) && (loan_amount<40000)) {
					grade = grade  ;
				}

				else if ((loan_amount >= 40000) && (loan_amount<60000)){
					grade = grade + 0.5 ;
				}
				else if ((loan_amount >= 60000) && (loan_amount <= 75000)){
					grade = grade - 0.5 ;
				}
				else {
					System.out.println(("Le montant n'est pas défini pour ce type de prêt 3-2"));
				}
			}
			else if ((loan_duration>=48) && (loan_duration <= 84)){
				if ((loan_amount >= 200) && (loan_amount < 20000)){
					grade = grade ;	
				}
				else if ((loan_amount >= 20000) && (loan_amount<40000)) {
					grade = grade - 1 ;
				}
				else if ((loan_amount >= 40000) && (loan_amount<60000)){
					grade = grade + 0.5 ;
				}
				else if ((loan_amount >= 60000) && (loan_amount <= 75000)){
					grade = grade +1 ;
				}
				else {
					System.out.println("Le montant n'est pas défini pour ce type de prêt 3-3");
				}

			}
			else System.out.println("La durée n'est pas définie pour ce type de prêt");

			System.out.println("grade after adding duration and amount parameters : " + grade) ;
			break ;

		default :
			System.out.println("Erreur : vérifier que le client possède un prêt");
		}

		// calculating the debt ratio
		monthly_payment = (loan_amount*(rate/12))/(1-(Math.pow(1+(rate/12),-loan_duration))) ;
		debt_ratio = monthly_payment / (salary/12) ;

		//if it's over 1/3 the debt is to high so high risk for the bank

		if ((debt_ratio > 0.33) && (debt_ratio  < 0.4) ) {	
			grade = grade + 2 ;
			System.out.println("TEST1 : " +grade) ; // possible but with high risk
		}
		else if (debt_ratio>=0.4){ // dangerous for the bank
			grade = grade + 4 ;
			System.out.println("TEST2 : " +grade) ;
		}
		else {
			grade = grade ; // debt rate is ok
		}
		System.out.println("Monthly_payment : " + monthly_payment);
		System.out.println("debt_ratio : " + debt_ratio);
		return grade;
	}

}


