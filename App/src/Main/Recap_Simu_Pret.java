package Main;
import Tools.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BDD.BDD_Tools;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Recap_Simu_Pret extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JPanel Panelclient;
	private JLabel Ltitreclient;
	private JLabel Lnomprenom;
	private JLabel Lmail;
	private JLabel Ladresse;
	
	private JPanel Panelpret;
	private JLabel lblInformationsDeLemprunt;
	private JLabel Ltypeprettaux;
	private JLabel Lduree;
	private JLabel Lmontant;
	private JLabel Lmensualite;
	
	
	private PretGlobal lepret;
	
	BDD_Tools Client = new BDD_Tools();
	ResultSet LeRetour=null;
	ModelTableau ResultModel;
	
	public Recap_Simu_Pret(PretGlobal pret) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 594, 458);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lepret=pret;

		GenererPanelClient();
		
		GenererPanelPret();
		
		JButton btnNewButton = new JButton("Enregistrer le prêt");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					LeRetour=Client.RequeteReponse("SELECT id_type_pret FROM T_TYPE_PRET WHERE libelle_type_pret = '" + lepret.getTypepret() + "'");
					LeRetour.next();
					
					Client.RequeteSansReponse("INSERT INTO T_SIMU_PRET (montant_pret, duree_pret, type_duree_pret, type_taux_pret, id_type_pret, id_client) VALUES (" + lepret.getMontant() + ", " + lepret.getDuree() + ", 'M', 'F', " + LeRetour.getInt("id_type_pret") + ", " + lepret.getClient() + ")");
					
					javax.swing.JOptionPane.showMessageDialog(null,"Ajouté avec succès", "Information", javax.swing.JOptionPane.INFORMATION_MESSAGE); 
					
					System.exit(1);  
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(15, 343, 542, 48);
		contentPane.add(btnNewButton);
		
		//DecimalFormat df = new DecimalFormat("0.00");
		
		//javax.swing.JOptionPane.showMessageDialog(null,df.format(lepret.CalculeMensualit()));
		
	}
	
	private void GenererPanelClient()	{

		Panelclient = new JPanel();
		Panelclient.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		Panelclient.setBounds(15, 16, 542, 137);
		contentPane.add(Panelclient);
		Panelclient.setLayout(null);
		
		Ltitreclient = new JLabel("Client");
		Ltitreclient.setHorizontalAlignment(SwingConstants.CENTER);
		Ltitreclient.setBounds(0, 7, 542, 25);
		Ltitreclient.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Panelclient.add(Ltitreclient);
		
		Lnomprenom = new JLabel("Nom Prenom");
		Lnomprenom.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Lnomprenom.setHorizontalAlignment(SwingConstants.CENTER);
		Lnomprenom.setBounds(15, 48, 512, 20);
		Panelclient.add(Lnomprenom);
		
		Lmail = new JLabel("Mail");
		Lmail.setHorizontalAlignment(SwingConstants.CENTER);
		Lmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Lmail.setBounds(15, 73, 512, 20);
		Panelclient.add(Lmail);
		
		Ladresse = new JLabel("Adresse");
		Ladresse.setHorizontalAlignment(SwingConstants.CENTER);
		Ladresse.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Ladresse.setBounds(15, 98, 512, 20);
		Panelclient.add(Ladresse);
		
		try {
			
			LeRetour=Client.RequeteReponse("SELECT id_adresse, nom_client, prenom_client, mail_client FROM T_CLIENT WHERE id_client = " + lepret.getClient() );
			LeRetour.next();
			Lnomprenom.setText(LeRetour.getString("nom_client") + " " + LeRetour.getString("prenom_client"));
			Lmail.setText(LeRetour.getString("mail_client"));
			
			LeRetour=Client.RequeteReponse("SELECT * FROM T_ADRESSE_CLIENT WHERE id_adresse = " + LeRetour.getString("id_adresse"));
			LeRetour.next();
			Ladresse.setText(LeRetour.getString("nume_rue") + " " + LeRetour.getString("nom_rue") + ", " + LeRetour.getString("code_postal") + " " + LeRetour.getString("nom_ville"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void GenererPanelPret()	{
		
		Panelpret = new JPanel();
		Panelpret.setLayout(null);
		Panelpret.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		Panelpret.setBounds(15, 169, 542, 167);
		contentPane.add(Panelpret);
		
		lblInformationsDeLemprunt = new JLabel("Informations de l'emprunt");
		lblInformationsDeLemprunt.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformationsDeLemprunt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblInformationsDeLemprunt.setBounds(0, 7, 542, 25);
		Panelpret.add(lblInformationsDeLemprunt);
		
		Ltypeprettaux = new JLabel("<dynamic> <dynamic>");
		Ltypeprettaux.setHorizontalAlignment(SwingConstants.CENTER);
		Ltypeprettaux.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Ltypeprettaux.setBounds(15, 48, 512, 20);
		Panelpret.add(Ltypeprettaux);
		
		Lduree = new JLabel("ezfezfezfez");
		Lduree.setHorizontalAlignment(SwingConstants.CENTER);
		Lduree.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Lduree.setBounds(15, 73, 512, 20);
		Panelpret.add(Lduree);
		
		Lmontant = new JLabel("<dynamic> <dynamic>, <dynamic> <dynamic>");
		Lmontant.setHorizontalAlignment(SwingConstants.CENTER);
		Lmontant.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Lmontant.setBounds(15, 98, 512, 20);
		Panelpret.add(Lmontant);
		
		Lmensualite = new JLabel("Mensualit\u00E9es : 3432.45\u20AC/mois");
		Lmensualite.setHorizontalAlignment(SwingConstants.CENTER);
		Lmensualite.setFont(new Font("Tahoma", Font.PLAIN, 25));
		Lmensualite.setBounds(15, 125, 512, 31);
		Panelpret.add(Lmensualite);
		
		try {
			
			LeRetour=Client.RequeteReponse("SELECT taux_type_pret FROM T_TYPE_PRET WHERE libelle_type_pret = '" + lepret.getTypepret() + "'");
			LeRetour.next();
			
			Ltypeprettaux.setText("Type de prêt: " + lepret.getTypepret() + " (" + LeRetour.getDouble("taux_type_pret") + "%)");
			Lduree.setText("Durée: " + lepret.getDuree() + " mois");
			Lmontant.setText("Montant: " + lepret.getMontant() + "€");
			
			DecimalFormat df = new DecimalFormat("0.00");
			
			Lmensualite.setText("Mensualitées de " + df.format(lepret.CalculeMensualit()) + "€/mois");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
