package interest_rate;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GainLoss extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public GainLoss(){

		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Gains et pertes");
		this.setSize(400, 100);
		JPanel container = new JPanel() ;
		setContentPane(container) ;
		//container.revalidate();
		//container.repaint();
		
		



		JButton carLoan = new JButton ("Prêt Automobile") ;
		//carLoan.setBounds(20,100,100,40);
		container.add(carLoan);

		carLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				CarLoan tab = new CarLoan ();
				tab.setVisible(true);
			}
		});

		JButton homeLoan = new JButton ("Prêt Immobilier") ;
		//homeLoan.setBounds(20,200,100,40);
		container.add(homeLoan);
		

		homeLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				HomeLoan tab = new HomeLoan ();
				tab.setVisible(true);

			}
		});
		JButton otherLoan = new JButton ("Prêt Divers") ;
		//otherLoan.setBounds(20,300,100,40);
		container.add(otherLoan);

		otherLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				OtherLoan tab = new OtherLoan ();
				tab.setVisible(true);

			}
		});
	}
}
