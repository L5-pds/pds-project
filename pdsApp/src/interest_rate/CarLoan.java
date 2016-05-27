package interest_rate;
import javax.swing.*;
import java.awt.*;


	public class CarLoan extends JFrame {

	public CarLoan(){
		  
	    this.setLocationRelativeTo(null);
	    //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setTitle("Gains et pertes pour les prêts automobile");
	    this.setSize(600, 200);

	    // Data of the board : rate according to the grade and the type of loan
	    Object[][] data = {
	      {"Taux proposable", "3,21", "3,81", "4,21", "4,91", "5,41"},
	      {"Gains ou pertes", "-535,07", "-214,82", "2 207,78", "378,48", "650,8"}
	    };

	    // Title of the columns
	    String  title[] = {	"", 
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
	
