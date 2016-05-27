package interest_rate;
import javax.swing.*;
import java.awt.*;

public class HomeLoan extends JFrame{

	public HomeLoan(){

		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Gains et pertes pour les prêts immobilier");
		this.setSize(550, 150);

		// Data of the board : rate according to the grade and the type of loan
		Object[][] data = {
				{"Taux proposable", "0,84", "1,44", "1,84", "2,54", "3,04"},
				{"Gains ou pertes", "- 32171,77", "535,07", "", "23404,51", "40564,45"}
		};

		// Title of the columns
		String  title[] = {	"", 
				"Très bon client",
				"Bon client",
				"Taux de la maison mère",
				"Client risqué",
		"Client très risqué"};
		JTable board = new JTable(data, title) ;


		//Nous ajoutons notre tableau à notre contentPane dans un scroll
		//Sinon les titres des colonnes ne s'afficheront pas 
		this.getContentPane().add(new JScrollPane(board));

	}   

}


