package interest_rate;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MainFrame extends JFrame {

	public MainFrame (){
		
		setLocationRelativeTo(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Déterminer les taux d'intérêts de l'agence");
		setSize(550, 450);
		JPanel container = new JPanel();
		setContentPane(container) ;
		container.revalidate();
		container.repaint();
		
	// will create a JTable with the different profils
		JButton actionInterestRate = new JButton("Afficher les taux d'intérêts");
		actionInterestRate.setBounds(100,50,100,20);
		container.add(actionInterestRate) ;
		
		actionInterestRate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				// Creating a new board
			    ClassifyingProfils tab = new ClassifyingProfils ();
			    tab.setVisible(true);
			}
		});

	JButton actionGainLoss = new JButton("Afficher les gains et les pertes");
	container.add(actionGainLoss) ;
	actionGainLoss.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e1){
			GainLoss tab = new GainLoss() ;
			tab.setVisible(true);
			
		}
	});
	
	
	
	//JButton actionCalculateGrade = new JButton("Calculer la note d'un client");

	}}

