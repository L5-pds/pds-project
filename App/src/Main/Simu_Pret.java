package Main;

import Tools.*;
import FrameSelect.*;


import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BDD.BDD_Tools;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JSpinner;
import java.awt.Font;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;

public class Simu_Pret extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	BDD_Tools Client = new BDD_Tools();
	ResultSet LeRetour=null;
	
	private PretGlobal lepret;
	
	private JTextField textField;
	private JLabel lblClient;
	private JLabel lblPrt;
	private JComboBox<String> comboBox;
	private JPanel panel;
	private JLabel label;
	private JLabel nbmois;
	private JSlider slider;
	private JPanel panel_1;
	private JLabel label_1;
	private JSpinner spinner;
	private JButton BtValide;
	/**
	 * Create the frame.
	 */
	public Simu_Pret() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 305, 391);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lepret=new PretGlobal();
		
		GenererSelectClient();
		
		GenererSelectTypePret();
		
		RemplirCbTypePret();
		
		GenererMontant();
		
		GenererDureePret();
		
		ecouteurtypepret();
		
		GenererValidation();
		
	}
	
	public Simu_Pret(PretGlobal passagepret) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 305, 391);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lepret=passagepret;
		
		GenererSelectClient();
		
		GenererSelectTypePret();
		
		RemplirCbTypePret();
		
		GenererMontant();
		
		GenererDureePret();
		
		ecouteurtypepret();
		
		GenererValidation();
		
		RecupValPret();
		
	}
	
	public void GenererValidation()		{
		BtValide= new JButton("Calculer le pr\u00EAt");
		BtValide.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if (textField.getText().length() >0)
				{
					
					lepret.setTypepret(comboBox.getItemAt(comboBox.getSelectedIndex()));
					lepret.setDuree(slider.getValue());
					lepret.setMontant(spinner.getValue().hashCode());
					
					Recap_Simu_Pret RecapPret = new Recap_Simu_Pret(lepret);
					RecapPret.setVisible(true);
					
					CacherFram(false);
					
				}else
				{
					javax.swing.JOptionPane.showMessageDialog(null,"Aucun client n'est séléctionné", "Information", javax.swing.JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		BtValide.setBounds(0, 294, 283, 41);
		contentPane.add(BtValide);
	}

	public void ecouteurtypepret()	{
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				
				try{       
					LeRetour=Client.RequeteReponse("SELECT * FROM T_TYPE_PRET WHERE libelle_type_pret = '" + comboBox.getItemAt(comboBox.getSelectedIndex()) + "'");
					while(LeRetour.next()){ 
						
						slider.setValue(LeRetour.getInt("min_duree"));
						slider.setMinimum(LeRetour.getInt("min_duree"));
						slider.setMaximum(LeRetour.getInt("max_duree"));
				 		
						spinner.setModel(new SpinnerNumberModel(LeRetour.getInt("min_montant"), LeRetour.getInt("min_montant"), LeRetour.getInt("max_montant"), 1));
						
						
						//comboBox.addItem(LeRetour.getString("libelle_type_pret"));
		            }
		        }catch(SQLException e){
		        	// TODO Auto-generated catch block
					e.printStackTrace();
		        }
				
				//slider.setValue(1);
				//slider.setMinimum(1);
				//slider.setMaximum(200);
				
				//lepret.setTypepret(comboBox.getItemAt(comboBox.getSelectedIndex()));
			}
		});
	}
	
	private void GenererSelectClient()	{
		JButton btnNewButton = new JButton("...");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				lepret.setTypepret(comboBox.getItemAt(comboBox.getSelectedIndex()));
				lepret.setDuree(slider.getValue());
				lepret.setMontant(spinner.getValue().hashCode());
				
				SelectClient SelectionDuClient = new SelectClient(lepret);
				SelectionDuClient.setVisible(true);
				
				CacherFram(false);
				
			}
		});
		btnNewButton.setBounds(234, 15, 31, 29);
		contentPane.add(btnNewButton);
		lblClient = new JLabel("Client : ");
		lblClient.setHorizontalAlignment(SwingConstants.RIGHT);
		lblClient.setBounds(15, 19, 56, 20);
		contentPane.add(lblClient);
		
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(73, 16, 146, 26);
		contentPane.add(textField);
		textField.setColumns(10);
	}
	
	private void GenererSelectTypePret()	{
				
		lblPrt = new JLabel("Pr\u00EAt : ");
		lblPrt.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrt.setBounds(15, 58, 56, 20);
		contentPane.add(lblPrt);
		
		comboBox = new JComboBox<>();
		
		comboBox.setBounds(73, 55, 146, 26);
		contentPane.add(comboBox);
	}
	
	private void GenererDureePret()	{
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(15, 97, 250, 70);
		contentPane.add(panel);
		
		label = new JLabel("Dur\u00E9e du pr\u00EAt");
		label.setBounds(5, 5, 104, 20);
		panel.add(label);
		
		nbmois = new JLabel("12 mois");
		nbmois.setHorizontalAlignment(SwingConstants.RIGHT);
		nbmois.setBounds(157, 5, 78, 20);
		panel.add(nbmois);
		
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				nbmois.setText(String.valueOf(slider.getValue() + " mois"));
			}
		});
		slider.setValue(12);
		slider.setMinimum(1);
		slider.setMaximum(480);
		slider.setBounds(15, 28, 220, 26);
		panel.add(slider);
		
		try{       
			LeRetour=Client.RequeteReponse("SELECT * FROM T_TYPE_PRET WHERE libelle_type_pret = '" + comboBox.getItemAt(comboBox.getSelectedIndex()) + "'");
			while(LeRetour.next()){ 
				
				slider.setValue(LeRetour.getInt("min_duree"));
				slider.setMinimum(LeRetour.getInt("min_duree"));
				slider.setMaximum(LeRetour.getInt("max_duree"));
				
				spinner.setModel(new SpinnerNumberModel(LeRetour.getInt("min_montant"), LeRetour.getInt("min_montant"), LeRetour.getInt("max_montant"), 1));
				
				//comboBox.addItem(LeRetour.getString("libelle_type_pret"));
            }
        }catch(SQLException e){
        	// TODO Auto-generated catch block
			e.printStackTrace();
        }
		
	}
	
	private void RemplirCbTypePret()	{
		
		try{       
			LeRetour=Client.RequeteReponse("SELECT * FROM T_TYPE_PRET");
			while(LeRetour.next()){ 
				comboBox.addItem(LeRetour.getString("libelle_type_pret"));
            }
        }catch(SQLException e){
        	// TODO Auto-generated catch block
			e.printStackTrace();
        }
		
	}
	
	public void GenererMontant()	{
		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(15, 150, 250, 125);
		contentPane.add(panel_1);
		
		label_1 = new JLabel("Montant du pr\u00EAt");
		label_1.setBounds(5, 5, 123, 20);
		panel_1.add(label_1);
		
		spinner = new JSpinner();
		spinner.setForeground(UIManager.getColor("Button.background"));
		spinner.setBackground(UIManager.getColor("Button.background"));
		spinner.setModel(new SpinnerNumberModel(0, 0, 0, 1));
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 50));
		spinner.setBounds(15, 28, 220, 92);
		panel_1.add(spinner);
	}
	
	public void RecupValPret()	{
		try{       
			LeRetour=Client.RequeteReponse("SELECT nom_client, prenom_client FROM T_CLIENT WHERE id_client = " + lepret.getClient());
			while(LeRetour.next()){ 
				textField.setText(LeRetour.getString("nom_client") + " " + LeRetour.getString("prenom_client"));
            }      
        }catch(SQLException e){
        	// TODO Auto-generated catch block
			e.printStackTrace();
        }
		
		comboBox.setSelectedItem(lepret.getTypepret());
		
		spinner.setValue(lepret.getMontant());
		slider.setValue(lepret.getDuree());
		
	}
	
	public void CacherFram(boolean val)	{
		this.setVisible(val);
	}
}
