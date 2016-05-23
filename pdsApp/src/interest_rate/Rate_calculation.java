package interest_rate;
import java.time.*;
import java.util.*;

public class Rate_calculation {

	// client's parameters
	public int id_client;
	public String name ;
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

	public Rate_calculation(int id_client, 
			String name,
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
		this.id_client = id_client ;
		this.name = name ;
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

		double rate = 0 ; // need to be retrieved from the database
		double monthly_payment = 0 ; // in each case, generate a coefficient that will be added to the grade
		double grade = 0 ;
		double coeff_salary = 0 ;

	// treatment of the client and the loan : the more the client earns, the smallest the coefficient will be => the grade too
		switch(type_loan){
		case "AUTOMOBILE" :
			rate = 4.21 ;
			break ;

		case "IMMOBILIER" :
			rate = 1.84 ;
			break ;
			
		case "CONSOMMATION" :
			rate = 7.61 ; 
			break ;
			
		default : 
			System.out.println("Erreur : vérifier que le client possède un prêt");
		}
		
		monthly_payment = ((loan_amount*rate - loan_amount)/loan_duration) ;
		coeff_salary = monthly_payment / salary;
		grade = grade + coeff_salary ;
		
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
		return grade ;
	}
	

}


