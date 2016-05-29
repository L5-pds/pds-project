package interest_rate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*; 

public class CalculateGrade extends JFrame {
	
	JTextField age = new JTextField() ;	
	JTextField salary = new JTextField();
	JTextField type = new JTextField();
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
		
		this.getContentPane().add(new JLabel("Obèsité"));
		this.getContentPane().add(corpulence);
		
		this.getContentPane().add(new JLabel("Risque au travail"));
		this.getContentPane().add(job_risk);
		
		this.getContentPane().add(new JLabel("Prédisposition génétique"));
		this.getContentPane().add(disease_risk);
		
		JButton buttonValidate = new JButton("Valider");
		
		this.getContentPane().add(buttonValidate);
		this.setVisible(true);
	}
	
	
	
}






