package interest_rate;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GainLoss extends JFrame {
	
	public GainLoss(){

		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Gains et pertes");
		this.setSize(500, 500);
		JPanel container = new JPanel() ;
		setContentPane(container) ;
		//container.revalidate();
		//container.repaint();
		
		
		JButton carLoan = new JButton ("Automobile") ;
		carLoan.setBounds(20,100,100,40);
		container.add(carLoan);
		
		carLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				 CarLoan tab = new CarLoan ();
				 tab.setVisible(true);
			}
		});
		
		JButton homeLoan = new JButton ("Immobilier") ;
		homeLoan.setBounds(20,200,100,40);
		container.add(homeLoan);
		
		homeLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				 HomeLoan tab = new HomeLoan ();
				 tab.setVisible(true);
		
			}
			});
		JButton otherLoan = new JButton ("Divers") ;
		otherLoan.setBounds(20,300,100,40);
		container.add(otherLoan);
		
	}
	

		}
