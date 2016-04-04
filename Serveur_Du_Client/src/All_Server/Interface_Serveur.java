package All_Server;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.TextArea;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Interface_Serveur extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static TextArea lblLogServer = new TextArea();
	private static JButton btnStartServer = new JButton("Start server");
	private Server LeServeur;
	private static JLabel lblUserconnect;
	private static JLabel lblConnectdispo;
	
	/**
	 * Create the frame.
	 */
	public Interface_Serveur() {
		
		printIHM();
		
		LeServeur = new Server();
		
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					LeServeur.Launch();
			}
		});
		
	}
	
	public static void changeTextLog(String AddText)	{
		lblLogServer.setText(lblLogServer.getText() + AddText + System.getProperty("line.separator"));
	}
	
	public static void change_user_connect(int connect, int dispo)	{
		lblUserconnect.setText(connect + " connexions");
		lblConnectdispo.setText(dispo + " disponibles");
	}
	
	private void printIHM()	{
		setTitle("Serveur");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblLogServer.setBounds(10, 91, 760, 645);
		lblLogServer.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 15));
		contentPane.add(lblLogServer);
		
		btnStartServer.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnStartServer.setBorder(null);
		btnStartServer.setBackground(Color.LIGHT_GRAY);
		btnStartServer.setBounds(10, 11, 549, 69);
		contentPane.add(btnStartServer);
		
		lblUserconnect = new JLabel("0 utilis\u00E9es");
		lblUserconnect.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserconnect.setBounds(574, 24, 189, 20);
		contentPane.add(lblUserconnect);
		
		lblConnectdispo = new JLabel("0 disponibles");
		lblConnectdispo.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnectdispo.setBounds(574, 45, 189, 20);
		contentPane.add(lblConnectdispo);
	}
}
