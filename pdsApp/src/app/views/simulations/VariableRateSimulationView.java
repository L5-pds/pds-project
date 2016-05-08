package app.views.simulations;


	import java.awt.BorderLayout;
	import java.awt.Component;
	import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
	import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			private JLabel label_time;
		
			
			//Fields to fill for the previous titles
			
			private JTextField answer_lastname;
			private JTextField answer_firstname;
			private JTextField answer_amount;
			private JTextField answer_time;
			JButton bouton = new JButton("Valider");
			
			// Fields to choose for the previous titles
			//private JComboBox answer_initial_rate;
			
			private JTextField answer_initial_rate;
			private JComboBox answer_cap;
			
			
		
			// Container of the previous components
			private JPanel pan1;
			private JPanel pan2;
			private JPanel pan3;
			private JPanel pan4;
			private JPanel pan5;
			private JPanel pan6;
			private JPanel pan7;
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
			    setSize(800,800);
			    setVisible(true);
			  }

			
			
			
			public VariableRateSimulationView(){
				
				 
				template();
				this.setSize(600,500);
				this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
			
			    
			    
			 // Instantiation of JComboBox for the cap 
			    String[] tab2 = {" ","-1%  et +1%", "-2%  et +2%", "-3%  et 3%"};
			    answer_cap= new JComboBox(tab2);
			    answer_cap.setPreferredSize(new Dimension (250,20));
			    answer_cap.setSelectedIndex(0); // definition of the default value
			    
			    
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
				 answer_initial_rate=new JTextField();
				 answer_initial_rate.setColumns(20);
				 pan4.add(answer_initial_rate);
				
				
				// Instantiation of cap JLabel and JPanel and inclusion in a Jpanel.
				 pan5= new JPanel();
				 pan5.setLayout(new FlowLayout());
				 label_cap= new JLabel("Cap");
				 pan5.add(label_cap);
				 pan5.add(answer_cap);
				 
				 //Instantiation of time JLabel and JPanel and inclusion in a Jpanel.
				 pan6= new JPanel();
				 pan6.setLayout(new FlowLayout());
				 label_time= new JLabel("Durée");
				 pan6.add(label_time);
				 answer_time=new JTextField();
				 answer_time.setColumns(22);
				 pan6.add(answer_time);
				 
				 // instantiation of the Jpanel body1, which will contain all the Jpanel.
				 body1= new JPanel();
				 body1.setLayout(new BoxLayout(body1, BoxLayout.Y_AXIS));
				 
				 // add of component bouton in a Jpanel
				 pan7= new JPanel();
				 pan7.add(bouton);
				
			
				 body1.add(pan1);
				 body1.add(pan2);
				 body1.add(pan3);
				 body1.add(pan5);
				 body1.add(pan4);
				 body1.add(pan6);
				 body1.add(pan7);
				 
				 this.add(body1);
				
				
			
				 
	// Add of actionListener on answer_cap
			answer_cap.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent arg0) {
			    	  if (answer_cap.getSelectedItem()== "-1%  et +1%"){
				    	answer_initial_rate.setText("1,5");
			    	  }
				    	
				    if (answer_cap.getSelectedItem()== "-2%  et +2%"){
					    	answer_initial_rate.setText("2,5");
				    	}
					    	
					 if (answer_cap.getSelectedItem()== "-3%  et +3%"){
						    	answer_initial_rate.setText("3,5");
			      }
			    }});
	
			
			}
			
			public static void main(String[] args){
				VariableRateSimulationView ihm = new VariableRateSimulationView();
				ihm.setVisible(true);
			}
			
	}


