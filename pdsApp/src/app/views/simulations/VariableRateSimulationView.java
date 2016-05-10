package app.views.simulations;


	import java.awt.BorderLayout;
	import java.awt.Component;
	import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
	import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
	import javax.swing.ImageIcon;
	import javax.swing.JButton;
	import javax.swing.JComboBox;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
			private JLabel label_slide;
			
		
			
			//Fields to fill for the previous titles
			
			private JTextField answer_lastname;
			private JTextField answer_firstname;
			private JTextField answer_amount;
			private JTextField answer_time;
			private JTextField answer_initial_rate;
			private JComboBox answer_cap;
			
			JButton bouton = new JButton("Valider");
			
			
		
			// Container of the previous components
			private JPanel pan1;
			private JPanel pan2;
			private JPanel pan3;
			private JPanel pan4;
			private JPanel pan5;
			private JPanel pan6;
			private JPanel pan7;
			private JPanel body1;
			
			//Instantiation of JSlider and creation of two attributes for the interval
		   JSlider slide = new JSlider();
		   private int borneInf;
		   private int borneSup;
		   
			
			//Attributes for the whole project
		    private WelcomeControllerClient wc;
			private Container contentPane;
			private JPanel header;
			private ImageIcon trait;
			private Image im;
			private JLabel image;
			private JLabel titre_use_case;	
			
		
	
		  

	
			  
			// Layout for the whole project
			private void template(){
			    setTitle("L5 Simulator");
			    contentPane = this.getContentPane();
			    header = new JPanel();
			    titre_use_case= new JLabel("Simulateur de prêt à taux variable");

			    trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/LogoL5.png"));
			    im = trait.getImage();
			    im  = im.getScaledInstance(150,91,1);
			    image = new JLabel( new ImageIcon(im));
			    header.setLayout(new FlowLayout(FlowLayout.LEFT));
			    header.add(image);
			    header.add(titre_use_case);
			    
			    contentPane.add(header, BorderLayout.NORTH);
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
			    
			    
			    //Instanciation of five JPanel and JLabel for the fields firstname,lastname,amount,cap and initial rate in order to range them in the JFrame more easier.		
			    
			    
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
				 this.setVisible(true);
				 
			
				
			
				 
				 // Add of actionListener on answer_cap
				 answer_cap.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent arg0) {
			    	  if (answer_cap.getSelectedItem()== "-1%  et +1%"){
				    	answer_initial_rate.setText("1,5");
			    	  }
				    	
				    if (answer_cap.getSelectedItem()== "-2%  et +2%"){
					    	answer_initial_rate.setText("2,5");
				    	}
					    	
					 if (answer_cap.getSelectedItem()=="-3%  et 3%"){
						    	answer_initial_rate.setText("3,5");
			      }
			    }});
				 
				 
				 
				 // Creation of the result interface
				 bouton.addActionListener(new ActionListener() {
				      public void actionPerformed(ActionEvent arg0) {
				    	  VariableRateSimulationView ihm= new VariableRateSimulationView();
				    	  
				    	
				    	  
				    	  ihm.titre_use_case.setText(answer_firstname.getText()+ " " + answer_lastname.getText()+ " "+ "voici le résultat de votre première simulation de prêt:");
				    	  ihm.body1.removeAll();
						    	
						  ihm.label_lastname.setText("Montant du prêt:" +"    "+ answer_amount.getText());
						  ihm.label_firstname.setText("Durée du prêt:" +"  "+ answer_time.getText());
						  ihm.label_amount.setText("Montant de la mensualité:");
						  ihm.label_initial_rate.setText("Total à payer:");
						  ihm.label_cap.setText("Taux d'intérêt actuel:" + "  " + answer_initial_rate.getText());
						  ihm.label_time.setText("Variation du taux d'intérêt:"+ "  ");
						    	
						  ihm.pan1=new JPanel();
						  ihm.pan1.setLayout(new FlowLayout());
						  ihm.pan1.add(ihm.label_lastname);
						  ihm.body1.add(ihm.pan1);
						    	
						  ihm.pan2=new JPanel();
						  ihm.pan2.setLayout(new FlowLayout());
						  ihm.pan2.add(ihm.label_firstname);
						  ihm.body1.add(ihm.pan2);
						    	
						  ihm.pan3=new JPanel();
						  ihm.pan3.setLayout(new FlowLayout());
						  ihm.pan3.add(ihm.label_amount);
						  ihm.body1.add(ihm.pan3);
						    	
						  ihm.pan4=new JPanel();
						  ihm.pan4.setLayout(new FlowLayout());
						  ihm.pan4.add(ihm.label_initial_rate);
						  ihm.body1.add(ihm.pan4);
						    	
						  ihm.pan5=new JPanel();
						  ihm.pan5.setLayout(new FlowLayout());
						  ihm.pan5.add(ihm.label_cap);
						  ihm.body1.add(ihm.pan5);
						   
							 
						  ihm.pan6=new JPanel();
						  ihm.pan6.setLayout(new FlowLayout());
						  ihm.pan6.add(ihm.label_time);
						  ihm.pan6.add(slide);
						  ihm.body1.add(ihm.pan6);
						  
				
								 
						  slide.setMaximum(borneSup);
						  slide.setMinimum(borneInf);
					      slide.setValue(30);
						  slide.setPaintTicks(true);
						  slide.setPaintLabels(true);
						  slide.setMinorTickSpacing((int)0.5);
						  slide.setMajorTickSpacing(1);
						
						  
						  ihm.template();
								 
								 
							 }
				    	  
				    });
			}
			
			
				 
			 /* int i ;
			  try {

				  String command = answer_initial_rate.getText();
				   i = Integer.parseInt(command);
				   
				      borneInf= ihm.borne()-i;
				      borneSup= ihm.borne()+i;
				      
			} catch (NumberFormatException e) {
			  
			}*/
			
				 /*slide.addChangeListener(new ChangeListener(){
				    

					public void stateChanged(ChangeEvent event){
				        label_slide.setText("Valeur actuelle : " + ((JSlider)event.getSource()).getValue());
				      }
				    });      
				    this.getContentPane().add(slide, BorderLayout.CENTER);
				    this.getContentPane().add(label_slide, BorderLayout.SOUTH);      
				  }   */
		
			/*public int borne(){
				
				final int cas1 =1;
				final int cas2 =2;
				final int cas3 =3;
				
				if (answer_cap.getSelectedItem()== "-1%  et +1%")
				return cas1;
				
				else {
					if (answer_cap.getSelectedItem()== "-2%  et +2%")
						return cas2 ;
					else{
				
				if (answer_cap.getSelectedItem()== "-3%  et +3%")
					 return cas3;
					}}
				return 0;
		
			}
				*/

			
			
				    	
	/* bouton.addActionListener(new ActionListener() {
				      public void actionPerformed(ActionEvent arg0) {
				    	  answer_amount.addKeyListener(new KeyAdapter(){
						    public void keyReleased(KeyEvent e){
						        String command = answer_amount.getText();
						        int i; 

						        i = Integer.parseInt(command); 


						        try{
						           if (i > 50000 || i==50000);
						        
						        }catch(Exception ex){
						            System.out.print("Le montant minimum d'un prêt est 50 000");
						        }
						    }
						});
				   
				      }
				    });
				  }
				  */
			
		
			      

			
			public static void main(String[] args){
				VariableRateSimulationView ihm = new VariableRateSimulationView();
				ihm.setVisible(true);
			}
			
	}


