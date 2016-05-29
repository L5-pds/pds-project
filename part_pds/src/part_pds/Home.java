package part_pds;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home extends JFrame {

	private JPanel contentPane;
	private	JButton btnAfficherGraphiques = new JButton("Graphiques");
	private	JButton btnAfficherTableau = new JButton("Tableau d'amortissement");
	private	JButton btnNewButton = new JButton("Tableau et graphiques");

	/**
	 * Launch the application.
	 */

	public Home() { // Changer le nom de la classe et de la methode -> A mettre en anglais.
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnAfficherGraphiques.setBounds(22, 24, 167, 29);		
		btnAfficherTableau.setBounds(201, 24, 192, 29);
		btnNewButton.setBounds(107, 78, 214, 24);

		contentPane.add(btnAfficherGraphiques);	
		contentPane.add(btnAfficherTableau);
		contentPane.add(btnNewButton);
	}
	
	public static void main(String[] args) {
		
					Home home = new Home();
					home.setVisible(true);
				
		
	}
}
