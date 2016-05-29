package interest_rate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculateGrade extends JFrame {

	JTextField age = new JTextField() ;
	JTextField salary = new JTextField();
	JFormattedTextField type = new JFormattedTextField();
	JTextField amount = new JTextField();
	JTextField duration = new JTextField();
	JTextField disease = new JTextField();
	JTextField smoker = new JTextField();
	JTextField alcohol = new JTextField();
	JTextField corpulence = new JTextField();
	JTextField job_risk = new JTextField();
	JTextField disease_risk = new JTextField();

	public CalculateGrade() {

		setTitle("Calculer la note d'un client");
		JPanel panel = new JPanel() ;
		this.setSize(350, 350);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(12,11,5,5));

		this.getContentPane().add(new JLabel("Age"));
		this.getContentPane().add(age);

		this.getContentPane().add(new JLabel("Salaire"));
		this.getContentPane().add(salary);

		this.getContentPane().add(new JLabel("Type de prêt"));
		this.getContentPane().add(type);

		this.getContentPane().add(new JLabel("Montant du prêt"));
		this.getContentPane().add(amount);

		this.getContentPane().add(new JLabel("Durée du prêt"));
		this.getContentPane().add(duration);

		this.getContentPane().add(new JLabel("Fumeur"));
		this.getContentPane().add(smoker);

		this.getContentPane().add(new JLabel("Maladie"));
		this.getContentPane().add(disease);

		this.getContentPane().add(new JLabel("Dépendance à l'alcool"));
		this.getContentPane().add(alcohol);

		this.getContentPane().add(new JLabel("Obésité"));
		this.getContentPane().add(corpulence);

		this.getContentPane().add(new JLabel("Risque au travail"));
		this.getContentPane().add(job_risk);

		this.getContentPane().add(new JLabel("Prédisposition génétique"));
		this.getContentPane().add(disease_risk);

		JButton buttonValidate = new JButton("Valider");

		this.getContentPane().add(buttonValidate);

		buttonValidate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){

			//	frame.setTitle("Note du client");
				JFrame frame = new JFrame() ;
				frame.setSize(100, 100);
				frame.setLocationRelativeTo(null);
				frame.add(new JLabel("Note du client : " + grade()));
				grade();
				frame.setVisible(true);

			}
		});





		this.setVisible(true);
	}




	// Each client will have a grade according to his personal information
	// for the moment it only takes one client

	public double grade(){

		double grade = 0 ;
		double rate = 0 ; // need to be retrieved from the database?
		double monthly_payment = 0 ;
		double debt_ratio = 0 ;

		switch(Integer.parseInt(smoker.getText())) {
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

		switch(Integer.parseInt(corpulence.getText())) {
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

		switch(Integer.parseInt(alcohol.getText())) {

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

		switch(Integer.parseInt(corpulence.getText())) {
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


		switch(Integer.parseInt(disease_risk.getText())) {
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

		switch(Integer.parseInt(job_risk.getText())) {

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
		switch(type.getText()){

		case "AUTOMOBILE" :
			rate = 0.0421 ;
			// creating gaps for the Integer.parseInt(duration.getText()) and the amount of the loan
			if (Integer.parseInt(duration.getText()) < 36){
				if ((Integer.parseInt(amount.getText()) >= 3000) && (Integer.parseInt(amount.getText())<15000)){
					grade = grade - 1 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 15000) && (Integer.parseInt(amount.getText())<30000)) {
					grade = grade - 1 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 30000) && (Integer.parseInt(amount.getText())<45000)){
					grade = grade - 1 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 45000) && (Integer.parseInt(amount.getText()) <= 60000)){
					grade = grade - 1 ;
				}
				else {

					System.out.println("Le montant n'est pas défini pour ce type du prêt 1-1") ;
				}
			}
			else if ((Integer.parseInt(duration.getText())>=36) && (Integer.parseInt(duration.getText()) <60)){
				if ((Integer.parseInt(amount.getText()) >= 3000) && (Integer.parseInt(amount.getText())<15000)){
					grade = grade ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 15000) && (Integer.parseInt(amount.getText()) < 30000)) {
					grade = grade  ;
				}

				else if ((Integer.parseInt(amount.getText()) >= 30000) && (Integer.parseInt(amount.getText()) < 45000)){
					grade = grade + 0.5 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 45000) && (Integer.parseInt(amount.getText()) <= 60000)){
					grade = grade - 0.5 ;
				}
				else {
					System.out.println(("Le montant n'est pas défini pour ce type de prêt 1-2"));
				}
			}
			else if ((Integer.parseInt(duration.getText())>=60) && (Integer.parseInt(duration.getText()) <= 84) ){
				if ((Integer.parseInt(amount.getText()) >= 3000) && (Integer.parseInt(amount.getText())<15000)){
					grade = grade ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 15000) && (Integer.parseInt(amount.getText()) < 30000)) {
					grade = grade - 1 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 30000) && (Integer.parseInt(amount.getText()) < 45000)){
					grade = grade + 0.5 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 45000) && (Integer.parseInt(amount.getText())<=60000)){
					grade = grade +1 ;
				}
				else {
					System.out.println("Le montant n'est pas défini pour ce type de prêt 1-3");
				}

			}
			else System.out.println("La durée n'est pas définie pour ce type de prêt");

			System.out.println("grade after adding Integer.parseInt(duration.getText()) and amount parameters : " + grade) ;
			break ;

		case "IMMOBILIER" :
			rate = 0.0184 ;
			// creating gaps for the Integer.parseInt(duration.getText()) and the amount of the loan
			if ((Integer.parseInt(duration.getText()) >= 60) && (Integer.parseInt(duration.getText()) <180)){
				if ((Integer.parseInt(amount.getText()) >= 50000) && (Integer.parseInt(amount.getText()) < 250000)){
					grade = grade - 1 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 250000) && (Integer.parseInt(amount.getText())<450000)) {
					grade = grade - 1 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 450000) && (Integer.parseInt(amount.getText())<650000)){
					grade = grade - 1 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 650000) && (Integer.parseInt(amount.getText()) <= 750000)){
					grade = grade - 1 ;
				}
				else {

					System.out.println("Le montant n'est pas défini pour ce type du prêt 2-1") ;
				}
			}
			else if ((Integer.parseInt(duration.getText())>=180) && (Integer.parseInt(duration.getText()) <240)){
				if ((Integer.parseInt(amount.getText()) >= 50000) && (Integer.parseInt(amount.getText()) < 250000)){
					grade = grade ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 250000) && (Integer.parseInt(amount.getText())<450000)) {
					grade = grade  ;
				}

				else if ((Integer.parseInt(amount.getText()) >= 450000) && (Integer.parseInt(amount.getText())<650000)){
					grade = grade + 0.5 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 650000) && (Integer.parseInt(amount.getText()) <= 750000)){
					grade = grade - 0.5 ;
				}
				else {
					System.out.println(("Le montant n'est pas défini pour ce type de prêt 2-2"));
				}
			}
			else if ((Integer.parseInt(duration.getText())>=240) && (Integer.parseInt(duration.getText()) <= 360) ){
				if ((Integer.parseInt(amount.getText()) >= 50000) && (Integer.parseInt(amount.getText()) < 250000)){
					grade = grade ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 250000) && (Integer.parseInt(amount.getText())<450000)) {
					grade = grade - 1 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 450000) && (Integer.parseInt(amount.getText())<650000)){
					grade = grade + 0.5 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 650000) && (Integer.parseInt(amount.getText()) <= 750000)){
					grade = grade +1 ;
				}
				else {
					System.out.println("Le montant n'est pas défini pour ce type de prêt 2-3");
				}

			}
			else System.out.println("La durée n'est pas définie pour ce type de prêt");

			System.out.println("grade after adding Integer.parseInt(duration.getText()) and amount parameters : " + grade) ;
			break ;

		case "DIVERS" :
			rate = 0.761 ;
			// creating gaps for the Integer.parseInt(duration.getText()) and the amount of the loan
			if ((Integer.parseInt(duration.getText()) >= 3) && (Integer.parseInt(duration.getText()) <24)){
				if ((Integer.parseInt(amount.getText()) >= 200) && (Integer.parseInt(amount.getText()) < 20000)){
					grade = grade - 1 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 20000) && (Integer.parseInt(amount.getText())<40000)) {
					grade = grade - 1 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 40000) && (Integer.parseInt(amount.getText())<60000)){
					grade = grade - 1 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 60000) && (Integer.parseInt(amount.getText()) <= 75000)){
					grade = grade - 1 ;
				}
				else {

					System.out.println("Le montant n'est pas défini pour ce type du prêt 3-1") ;
				}
			}
			else if ((Integer.parseInt(duration.getText())>=24) && (Integer.parseInt(duration.getText()) <48)){
				if ((Integer.parseInt(amount.getText()) >= 200) && (Integer.parseInt(amount.getText()) < 20000)){
					grade = grade ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 20000) && (Integer.parseInt(amount.getText())<40000)) {
					grade = grade  ;
				}

				else if ((Integer.parseInt(amount.getText()) >= 40000) && (Integer.parseInt(amount.getText())<60000)){
					grade = grade + 0.5 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 60000) && (Integer.parseInt(amount.getText()) <= 75000)){
					grade = grade - 0.5 ;
				}
				else {
					System.out.println(("Le montant n'est pas défini pour ce type de prêt 3-2"));
				}
			}
			else if ((Integer.parseInt(duration.getText())>=48) && (Integer.parseInt(duration.getText()) <= 84)){
				if ((Integer.parseInt(amount.getText()) >= 200) && (Integer.parseInt(amount.getText()) < 20000)){
					grade = grade ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 20000) && (Integer.parseInt(amount.getText())<40000)) {
					grade = grade - 1 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 40000) && (Integer.parseInt(amount.getText())<60000)){
					grade = grade + 0.5 ;
				}
				else if ((Integer.parseInt(amount.getText()) >= 60000) && (Integer.parseInt(amount.getText()) <= 75000)){
					grade = grade +1 ;
				}
				else {
					System.out.println("Le montant n'est pas défini pour ce type de prêt 3-3");
				}

			}
			else System.out.println("La durée n'est pas définie pour ce type de prêt");

			System.out.println("grade after adding Integer.parseInt(duration.getText()) and amount parameters : " + grade) ;
			break ;

		default :
			System.out.println("Erreur : vérifier que le client possède un prêt");
		}

		// calculating the debt ratio
		monthly_payment = (Integer.parseInt(amount.getText())*(rate/12))/(1-(Math.pow(1+(rate/12),-Integer.parseInt(duration.getText())))) ;
		debt_ratio = monthly_payment / ((Integer.parseInt(salary.getText())/12)) ;

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














