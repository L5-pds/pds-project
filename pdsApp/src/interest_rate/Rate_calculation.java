package interest_rate;
import java.time.*;
import java.util.Date;

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

	public Rate_calculation(int id_client, String name,int age,int salary,int loan_amount,int loan_duration, String type_loan, int smoker, int disease, int corpulence, int alcohol, int disease_risk, int job_risk) {

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

	public int getId_client(){
		return id_client ; 
	}
	public int setId_client(){
		return id_client ; 
	}

	public String getname(){
		return name ; 
	}
	public String setname(){
		return name ; 
	}

	public int getAge(){
		return age ;
	}
	public int setAge(){
		return age ;
	}

	public int getSalary(){
		return salary ; 
	}
	public int setSalary(){
		return salary ; 
	}

	public int getDisease(){
		return disease ; 
	}
	public int setDisease(){
		return disease ; 
	}

	public int getLoan_amount(){
		return loan_amount ;
	}
	public int setLoan_amount(){
		return loan_amount ;
	}

	public int getLoan_duration(){
		return loan_duration ; 
	}
	public int setLoan_duration(){
		return loan_duration ; 
	}

	public String getType_loan(){
		return type_loan ;
	}
	public String setType_loan(){
		return type_loan ;
	}

	public int getSmoker(){
		return smoker ; 
	}
	public int setSmoker(){
		return smoker ; 
	}

	public int getCorpulence(){
		return corpulence ; 
	}
	public int setCorpulence(){
		return corpulence ; 
	}

	public int getAlcohol(){
		return alcohol ;
	}
	public int setAlcohol(){
		return alcohol ;
	}

	public int getDisease_risk(){
		return disease_risk ;
	}
	public int setDisease_risk(){
		return disease_risk ;
	}

	public int getJob_risk(){
		return job_risk ;
	}
	public int setJob_risk(){
		return job_risk ;
	}

	// Each client will have a grade according to his personal information 
	// for the moment it only takes one client

	public static double grade(Rate_calculation client){

		double rate = 0 ; // need to be retrieved from the database
		double monthly_payment = 0 ; // in each case, generate a coefficient that will be added to the grade
		double grade = 0 ;
		double coeff_salary = 0 ;

	// treatment of the client and the loan : the more the client earns, the smallest the coefficient will be => the grade too
		switch(client.getType_loan()){
		
		case "AUTOMOBILE" :
			rate = 4.21 ;
			monthly_payment = ((client.getLoan_amount()*rate - client.getLoan_amount())/client.getLoan_duration()) ;
			coeff_salary = monthly_payment / client.getSalary();
			grade = grade + coeff_salary ;

		case "IMMOBILIER" :
			rate = 1.84 ;
			monthly_payment = ((client.getLoan_amount()*rate - client.getLoan_amount())/client.getLoan_duration()) ; 
			coeff_salary = monthly_payment / client.getSalary();
			grade = grade + coeff_salary ;

		case "CONSOMMATION" :
			rate = 7.61 ; 
			monthly_payment = ((client.getLoan_amount()*rate - client.getLoan_amount())/client.getLoan_duration()) ;
			coeff_salary = monthly_payment / client.getSalary();
			grade = grade + coeff_salary ;

		default : 
			System.out.println("Erreur : vérifier que le client possède un prêt");
		}
		
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
			
		default : // the client didn't give the information to the bank
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
	return grade ;
	}

}


