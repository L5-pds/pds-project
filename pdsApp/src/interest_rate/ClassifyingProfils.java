package interest_rate;
//import java.sql.*;
import javax.swing.*;
import java.awt.*;

	
	// creating a board of profils  - 
//to do : register rates in the database

	public class ClassifyingProfils extends JFrame {

	  public ClassifyingProfils(){
		  
	    this.setLocationRelativeTo(null);
	    //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setTitle("Profils client");
	    this.setSize(600, 150);

	    // Data of the board : rate according to the grade and the type of loan
	    Object[][] data = {
	      {"Automobile", "3,21", "3,81", "4,21", "4,91", "5,41"},
	      {"Immobilier", "0,84", "1,44", "1,84", "2,54", "3,04"},
	      {"Divers", "6,62", "7,22", "7,62", "8,02", "8,82"}
	    };

	    // Title of the columns
	    String  title[] = {	"Type de prêt", 
	    					"Très bon client",
	    					"Bon client",
	    					"Maison-mère",
	    					"Client risqué",
	    					"Client très risqué"};
	    JTable board = new JTable(data, title)	;
	    
	   
	    //Nous ajoutons notre tableau à notre contentPane dans un scroll
	    //Sinon les titres des colonnes ne s'afficheront pas 
	    this.getContentPane().add(new JScrollPane(board));

	  }   

	}
	
