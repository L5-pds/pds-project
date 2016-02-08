package FrameSelect;
import Tools.*;
import BDD.*;
import Main.Simu_Pret;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class SelectClient extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField txtSearch;
	private JButton btnNewButton;
	private JLabel lblId;
	private JLabel lblNom;
	private JLabel lblPrnom;
	private JLabel lblMail;
	private JLabel Lidclient;
	private JLabel Lnom;
	private JLabel Lprenom;
	private JLabel Lmail;
	private JButton Bt_ValideClient;
	private JComboBox<String> CB_ListAgence;
	
	private PretGlobal lepret;
	
	BDD_Tools Client = new BDD_Tools();
	ResultSet LeRetour=null;
	ModelTableau ResultModel;
	
	/**
	 * Construteur.
	 */
	public SelectClient(PretGlobal passagepret) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 643, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lepret=passagepret;
		
		GenereComposantLabel();
		
		GenereComposantTableau("SELECT id_client, nom_client, prenom_client FROM T_CLIENT ORDER BY nom_client");
		
		GenereComposantSearch();
		
		SetVisibleValidation(false);
		
		
	}
	
	public void GenereComposantLabel()	{
		
		lblId = new JLabel("Num\u00E9ro client :");
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblId.setBounds(165, 20, 122, 20);
		contentPane.add(lblId);
		
		lblNom = new JLabel("Nom :");
		lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNom.setBounds(165, 38, 122, 20);
		contentPane.add(lblNom);
		
		lblPrnom = new JLabel("Pr\u00E9nom :");
		lblPrnom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrnom.setBounds(165, 56, 122, 20);
		contentPane.add(lblPrnom);
		
		lblMail = new JLabel("Mail :");
		lblMail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMail.setBounds(165, 74, 122, 20);
		contentPane.add(lblMail);
		
		Lidclient = new JLabel("");
		Lidclient.setHorizontalAlignment(SwingConstants.LEFT);
		Lidclient.setBounds(290, 20, 198, 20);
		contentPane.add(Lidclient);
		
		Lnom = new JLabel("");
		Lnom.setBounds(290, 38, 198, 20);
		contentPane.add(Lnom);
		Lnom.setHorizontalAlignment(SwingConstants.LEFT);
		
		Lprenom = new JLabel("");
		Lprenom.setHorizontalAlignment(SwingConstants.LEFT);
		Lprenom.setBounds(290, 56, 198, 20);
		contentPane.add(Lprenom);
		
		Lmail = new JLabel("");
		Lmail.setHorizontalAlignment(SwingConstants.LEFT);
		Lmail.setBounds(290, 74, 198, 20);
		contentPane.add(Lmail);
		
		Bt_ValideClient = new JButton("Valider");
		Bt_ValideClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
					lepret.setClient(Integer.parseInt(Lidclient.getText()));
					
					Simu_Pret Testing = new Simu_Pret(lepret);
					Testing.setVisible(true);
					
					CloseFrame(false);
				
				
			}
		});
		Bt_ValideClient.setBounds(491, 34, 115, 42);
		contentPane.add(Bt_ValideClient);
		
	}
	
private void CloseFrame(boolean val)	{
	this.setVisible(val);
}
	
public void SetVisibleValidation(boolean Choix)	{
		
		if(Choix == true)
		{
			lblId.setVisible(true);
			lblNom.setVisible(true);
			lblPrnom.setVisible(true);
			lblMail.setVisible(true);
			Lidclient.setVisible(true);
			Lnom.setVisible(true);
			Lprenom.setVisible(true);
			Lmail.setVisible(true);
			Bt_ValideClient.setVisible(true);
		}else
		{
			lblId.setVisible(false);
			lblNom.setVisible(false);
			lblPrnom.setVisible(false);
			lblMail.setVisible(false);
			Lidclient.setVisible(false);
			Lnom.setVisible(false);
			Lprenom.setVisible(false);
			Lmail.setVisible(false);
			Bt_ValideClient.setVisible(false);
		}
		
	}
	
	public void GenereComposantTableau(String Requete)	{
		
		//LeRetour=Client.RequeteReponse("SELECT id_client, nom_client, prenom_client FROM T_CLIENT ORDER BY nom_client");
		LeRetour=Client.RequeteReponse(Requete);
		ResultModel = new ModelTableau( LeRetour );
		
		table = new JTable(ResultModel);
		table.setShowVerticalLines(false);
		table.setSurrendersFocusOnKeystroke(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//javax.swing.JOptionPane.showMessageDialog(null,table.getSelectedRow(), "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE);
				LeRetour=Client.RequeteReponse("SELECT id_client, nom_client, prenom_client, mail_client FROM T_CLIENT WHERE id_client = " + table.getValueAt(table.getSelectedRow(),0) );
				//javax.swing.JOptionPane.showMessageDialog(null,table.getValueAt(table.getSelectedRow(), 0));
				    
				try {
					LeRetour.next();
					//javax.swing.JOptionPane.showMessageDialog(null,LeRetour.getString("id_client"));
					Lidclient.setText(LeRetour.getString("id_client"));
					Lnom.setText(LeRetour.getString("nom_client"));
					Lprenom.setText(LeRetour.getString("prenom_client"));
					Lmail.setText(LeRetour.getString("mail_client"));
					SetVisibleValidation(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		table.setBackground(UIManager.getColor("Button.background"));
		table.getColumnModel().getColumn(0).setPreferredWidth(8);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(renderer);
		
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.setBounds(12, 110, 594, 273);
		contentPane.add(table);
	}

	public void GenereComposantSearch()	{
		
		txtSearch = new JTextField();
		txtSearch.setBounds(12, 12, 149, 19);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);
		
		btnNewButton = new JButton("Search");
		btnNewButton.setBounds(12, 77, 149, 25);
		contentPane.add(btnNewButton);
		
		CB_ListAgence = new JComboBox<>();
		CB_ListAgence.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				
				SetVisibleValidation(false);
				
			}
		});
		CB_ListAgence.setBounds(12, 40, 149, 26);
		contentPane.add(CB_ListAgence);
		
		CB_ListAgence.removeAllItems();
		LeRetour=Client.RequeteReponse("SELECT nom_agence FROM T_AGENCE ORDER BY nom_agence");
		try {
			CB_ListAgence.addItem("ALL AGENCE");
			while(LeRetour.next())
			{
				CB_ListAgence.addItem(LeRetour.getString("nom_agence"));
			}

			CB_ListAgence.setSelectedIndex(0);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int ValIndexCBListAgence = CB_ListAgence.getSelectedIndex();
				if(ValIndexCBListAgence == 0)
				{
					LeRetour=Client.RequeteReponse("SELECT id_client, nom_client, prenom_client FROM T_CLIENT WHERE nom_client LIKE '%" + txtSearch.getText() + "%' ORDER BY nom_client");
				}else
				{
					LeRetour=Client.RequeteReponse("SELECT id_client, nom_client, prenom_client FROM T_CLIENT WHERE nom_client LIKE '%" + txtSearch.getText() + "%' AND id_agence = (SELECT id_agence FROM T_AGENCE WHERE nom_agence LIKE '%" + CB_ListAgence.getItemAt(CB_ListAgence.getSelectedIndex()) + "%') ORDER BY nom_client");
				}
				
				ResultModel = new ModelTableau( LeRetour );
				table.setModel(ResultModel);
				
				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
				renderer.setHorizontalAlignment(SwingConstants.CENTER);
				table.getColumnModel().getColumn(0).setCellRenderer(renderer);
				table.getColumnModel().getColumn(0).setPreferredWidth(8);
				table.getColumnModel().getColumn(1).setPreferredWidth(100);
				table.getColumnModel().getColumn(2).setPreferredWidth(100);
				
				SetVisibleValidation(false);
				
			}
		});
		
		
	}
}
