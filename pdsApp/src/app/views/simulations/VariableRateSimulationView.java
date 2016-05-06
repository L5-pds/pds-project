package app.views.simulations;


	import java.awt.BorderLayout;
	import java.awt.Component;
	import java.awt.Container;
	import java.awt.FlowLayout;
	import java.awt.Image;

	import javax.swing.BoxLayout;
	import javax.swing.ImageIcon;
	import javax.swing.JButton;
	import javax.swing.JComboBox;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JPanel;
	import javax.swing.JTextField;
	import javax.swing.plaf.ComboBoxUI;

import app.controllers.WelcomeControllerClient;
import app.views.welcome.WelcomeViewClient;

	public class VariableRateSimulationView extends JFrame {
		
		  	// component of the simulation frame
		
		
		    // Title of the fields
		  	private JLabel label_lastname;
			private JLabel label_firstname;
			private JLabel label_amount;
			private JLabel label_initial_rate;
			private JLabel label_cap;
			
			//Fields to fill for the previous titles
			private JTextField answer_lastname;
			private JTextField answer_firstname;
			private JTextField answer_amount;
			
			// Fields to choose for the previous titles
			private JComboBox answer_initial_rate;
			private JComboBox answer_cap;
			
		
			// Container of the previous components
			private JPanel pan1;
			private JPanel pan2;
			private JPanel pan3;
			private JPanel pan4;
			private JPanel pan5;
			private JPanel body1;
			
			
			  private WelcomeControllerClient wc;
			  private Container contentPane;
			  private JPanel header;
			  private JPanel body2;
			  private ImageIcon trait;
			  private Image im;
			  private JLabel image;
			  private JLabel answerLabel;
			  private JLabel titre_use_case;

	
			  // Layout for the whole project
			  private void template(){
			    setTitle("L5 Simulator");
			    contentPane = this.getContentPane();
			    header = new JPanel();
			    body2 = new JPanel();
			    titre_use_case= new JLabel("Simulateur de prêt à taux variable");

			    trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/LogoL5.png"));
			    im = trait.getImage();
			    im  = im.getScaledInstance(150,91,1);
			    image = new JLabel( new ImageIcon(im));
			    header.setLayout(new FlowLayout(FlowLayout.LEFT));
			    header.add(image);
			    header.add(titre_use_case);
			    

			    answerLabel = new JLabel("");
			    answerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			    body2.add(answerLabel);
			    body2.setLayout(new BoxLayout(body2, BoxLayout.Y_AXIS));

			    contentPane.add(header, BorderLayout.NORTH);
			    contentPane.add(body2, BorderLayout.CENTER);
			    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    setSize(600, 500);
			    setVisible(true);
			  }

			
			
			
			public VariableRateSimulationView(){
				
				 
				template();
				this.setSize(600,500);
				this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
			
				
				// Instantiation of JComboBox for the rates
			    String[] tab1 = {"3,2", "4,3", "5", "7,8"};
			    answer_initial_rate= new JComboBox(tab1);
			    
			    
			 // Instantiation of JComboBox for the cap 
			    String[] tab2 = {"1", "2", "3"};
			    answer_cap= new JComboBox(tab2);
			    
			    
	//Instanciation of five JPanel and JLabel for the fields firstname,lastname,amount,cap and iniatial rate in order to range them in the JFrame more easly.		
			    
			    
			    // Instantiation of Lastname JLabel and JPanel and inclusion in a Jpanel.
				 pan1= new JPanel();
				 pan1.setLayout(new FlowLayout());
				 label_lastname= new JLabel("Nom");
				 pan1.add(label_lastname);
				 answer_lastname= new JTextField();
				 answer_lastname.setColumns(22);
				 pan1.add(answer_lastname);


				  
				// Instantiation of firstname JLabel and JPanel and inclusion in a Jpanel.
				 pan2= new JPanel();
				 pan2.setLayout(new FlowLayout());
				 label_firstname= new JLabel("Prenom");
				 pan2.add(label_firstname);
				 answer_firstname= new JTextField();
				 answer_firstname.setColumns(20);
				 pan2.add(answer_firstname);

				
				  
				 // Instantiation of amount JLabel and JPanel and inclusion in a Jpanel.
				 pan3= new JPanel();
				 pan3.setLayout(new FlowLayout());
				 label_amount= new JLabel("Montant");
				 pan3.add(label_amount);
				 answer_amount= new JTextField();
				 answer_amount.setColumns(20);
				 pan3.add(answer_amount);
			
				  
				// Instantiation of iniatialrate JLabel and JPanel and inclusion in a Jpanel.
				 pan4= new JPanel();
				 pan4.setLayout(new FlowLayout());
				 label_initial_rate= new JLabel("Taux initiale");
				 pan4.add(label_initial_rate);
				 pan4.add(answer_initial_rate);
				
				
				// Instantiation of cap JLabel and JPanel and inclusion in a Jpanel.
				 pan5= new JPanel();
				 pan5.setLayout(new FlowLayout());
				 label_cap= new JLabel("Cap");
				 pan5.add(label_cap);
				 pan5.add(answer_cap);
				 
				 // instantiation of the Jpanel body1, which will contain all the Jpanel.
				 body1= new JPanel();
				 body1.setLayout(new BoxLayout(body1, BoxLayout.Y_AXIS));
				
			
				 body1.add(pan1);
				 body1.add(pan2);
				 body1.add(pan3);
				 body1.add(pan4);
				 body1.add(pan5);
				 
				 this.add(body1);
				

				 

	}
			public static void main(String[] args){
				VariableRateSimulationView ihm = new VariableRateSimulationView();
				ihm.setVisible(true);
			}
			
	}


