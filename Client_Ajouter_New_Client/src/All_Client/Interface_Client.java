package All_Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Interface_Client extends JFrame {

	private JPanel contentPane;
	private JTextField tb_password;
	private JTextField tb_login;
	private static Socket socket;
	private PrintWriter out = null;
	private BufferedReader in = null;
	public static String login = null, pass = null;
	private boolean connect = false;
	
	/**
	 * Create the frame.
	 */
	public Interface_Client(Socket socket_temp) {
		
		socket = socket_temp;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 529, 233);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		create_IHM();
		//setBounds(100, 100, 529, 233);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
				out = new PrintWriter(socket.getOutputStream());
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				login = tb_login.getText();
				pass = tb_password.getText();			
				
				SerialisationPersonne sp = new SerialisationPersonne();
				Personne Connexion = new Personne(login,pass);
				
				out.println(sp.serialiser(Connexion));
				out.flush();
				
				String Reponse_Connect=in.readLine();
				if(Reponse_Connect.equals("connect_ok")){
					 
					tb_login.setBackground(new Color(0, 204, 102));
					tb_password.setBackground(new Color(0, 204, 102));
					connect = true;
					tb_login.setEnabled(false);
					tb_password.setEnabled(false);
					btnNewButton.setEnabled(false);
					setBounds(100, 100, 529, 700);
					  }
					
				else {
						javax.swing.JOptionPane.showMessageDialog(null,"Erreur: " + Reponse_Connect, "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE);
						tb_login.setBackground(new Color(255, 0, 51));
						tb_password.setBackground(new Color(255, 0, 51));
						System.exit(0);
					  }
				
				} catch (IOException e) {
					javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus", "Alerte", javax.swing.JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});
		btnNewButton.setBounds(436, 56, 56, 77);
		contentPane.add(btnNewButton);
	}
	
	private void create_IHM()	{
		ImageIcon   trait = new ImageIcon(this.getClass().getResource("/picture/LogoL5.png"));
		Image         im  =  trait.getImage();
		int	hauteur	= 152;
		int	largeur	= 250;
		im	= im.getScaledInstance(largeur,hauteur,1);
		JLabel image = new JLabel(  new ImageIcon(im));
		image.setBounds(15, 16, 250, 152);
		contentPane.add(image);
		
		tb_password = new JTextField();
		tb_password.setBounds(280, 107, 146, 26);
		contentPane.add(tb_password);
		tb_password.setColumns(10);
		
		tb_login = new JTextField();
		tb_login.setColumns(10);
		tb_login.setBounds(280, 56, 146, 26);
		contentPane.add(tb_login);
		
		JLabel lblPseudo = new JLabel("Pseudo");
		lblPseudo.setBounds(280, 37, 69, 20);
		contentPane.add(lblPseudo);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(280, 88, 69, 20);
		contentPane.add(lblPassword);
	}
	
}
