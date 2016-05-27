package interest_rate;
import javax.swing.*;
import java.awt.*;


public class OtherLoan extends JFrame {

	public OtherLoan(){

		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Gains et pertes pour les prêts divers");
		this.setSize(600, 200);

		// Data of the board : rate according to the grade and the type of loan
		Object[][] data = {
				{"Taux proposable", "6,62", "7,22", "7,62", "8,02", "8,82"},
				{"Gains ou pertes", "-576,71", "-231,3", "4263,31", "232,14", "698,88"}
		};

		// Title of the columns
		String  title[] = {	
				"", 
				"Très bon client",
				"Bon client",
				"Maison-mère",
				"Client risqué",
				"Client très risqué"};
		JTable board = new JTable(data, title) ;


		//Nous ajoutons notre tableau à notre contentPane dans un scroll
		//Sinon les titres des colonnes ne s'afficheront pas 
		this.getContentPane().add(new JScrollPane(board));

	}   

}

