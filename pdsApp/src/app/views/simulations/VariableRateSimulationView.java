package app.views.simulations;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.table.DefaultTableModel;

import app.controllers.VariableRateSimulationController;
import app.views.welcome.WelcomeViewClient;
import app.listeners.VariableRateSimulationListener;
import app.models.component.RoundJTextField;

public class VariableRateSimulationView extends JPanel implements VariableRateSimulationListener{

  private JPanel body; // the application body
  private Container cont;
  private VariableRateSimulationController controller;

  
  private JLabel labelResult;
  private JLabel labelAmount;
  private JLabel labelInitialRate;
  private JLabel labelCap;
  private JLabel labelTime;
  private JLabel labelTitle;


  private JTextField answerLastname;
  private JTextField answerFirstname;
  private JTextField answerAmount;
  private JComboBox answerTime;
  private JTextField answerInitialRate;
  private JComboBox answerCap;

  private JButton bouton1 = new JButton("Valider");
  private JButton bouton2 = new JButton("Afficher les scénarios favorables");
  private JButton bouton3 = new JButton("Afficher les scénarios défavorables");
  private JPanel body1; // my use-case body
 

  //Interest rate max and min value
  private double lowerBoundary;
  private double upperBoundary;
  double stringToInt;


  public VariableRateSimulationView(VariableRateSimulationController controller, JPanel body, Container cont){
    this.controller = controller;
    this.body = body;
    this.cont = cont;
  }
  
  public void setIHM() {
      body.removeAll();
      body.add(initComponent());
      cont.revalidate();
      cont.repaint();
  }
 
  public JPanel initComponent(){
	  
	    // Instantiation of JComboBox for the cap
	    String[] tab1 = {"","-1%  et +1%", "-2%  et +2%"};
	    answerCap = new JComboBox(tab1);
	    answerCap.setPreferredSize(new Dimension (250,20));
	    answerCap.setSelectedIndex(0); // definition of the default value
	    answerCap.setFont(new Font(tab1.toString(), Font.ITALIC, 20));

	    // Instantiation of JComboBox for the time
	    String[] tab2 = {"","7","10","15","20","25","30"};
	    setAnswer_time(new JComboBox(tab2));
	    answerTime.setPreferredSize(new Dimension (250,20));
	    answerTime.setSelectedIndex(0);
	    answerTime.setFont(new Font(tab2.toString(), Font.ITALIC, 20));
	
	    labelAmount= new JLabel("Montant");
	    setAnswer_amount(new RoundJTextField(20));
	    answerAmount.setColumns(20);
	    
	    
	    labelAmount.setFont(new java.awt.Font("Verdana", 0,30));
	    labelAmount.setHorizontalAlignment(javax.swing.JTextField.CENTER);
	    
	    answerAmount.setFont(new java.awt.Font("Verdana", 0,30)); 
	    answerAmount.setHorizontalAlignment(javax.swing.JTextField.CENTER);


	    labelInitialRate= new JLabel("Taux initial:");
	    setAnswer_initial_rate(new RoundJTextField(20));
	    answerInitialRate.setColumns(20);
	    
	    
	    labelInitialRate.setFont(new java.awt.Font("Verdana", 0, 30)); 
	    labelInitialRate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
	    
	    answerInitialRate.setFont(new java.awt.Font("Verdana", 0,30)); 
	    answerInitialRate.setHorizontalAlignment(javax.swing.JTextField.CENTER);

	    labelCap= new JLabel("Cap :");
	    labelCap.setFont(new java.awt.Font("Verdana", 0,30));
	    labelCap.setHorizontalAlignment(javax.swing.JTextField.CENTER);
	    answerCap.setPreferredSize(new Dimension(30,40));
	    
	    
	    labelTime= new JLabel("Durée :");
	    labelTime.setFont(new java.awt.Font("Verdana", 0,30));
	    labelTime.setHorizontalAlignment(javax.swing.JTextField.CENTER);
	    answerTime.setPreferredSize(new Dimension(30,40));
	    
	    labelResult= new JLabel();
	    labelResult.setFont(new java.awt.Font("Verdana", 0,0));
	    
	    
	   
	  

	    body1= new JPanel();
		body1.setBackground(new Color(215,203,233,255));
		body1.setLayout(new BoxLayout(body1,BoxLayout.Y_AXIS));
		
		body1.add(labelAmount);
		body1.add(answerAmount);
		body1.add(labelTime);
	    body1.add(answerTime);
		body1.add(labelCap);
		body1.add(answerCap);
		body1.add(labelInitialRate);
		body1.add(answerInitialRate);
		body1.add(bouton1);
		
		
		// Add actionListener on answer_cap
	    answerCap.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent arg0) {

	        if (answerCap.getSelectedItem()=="-1%  et +1%"){

	          switch(answerTime.getSelectedIndex())
	          {
	          case 1:
	            answerInitialRate.setText("2.95");
	            break;
	          case 2:
	            answerInitialRate.setText("2.17");
	            break;
	          case 3:
	            answerInitialRate.setText("2.28");
	            break;
	          case 4:
	            answerInitialRate.setText("2.56");
	            break;
	          case 5:
	            answerInitialRate.setText("2.85");
	          case 6:
	            answerInitialRate.setText("3.32");

	          }
	        }
	        
	        else if (answerCap.getSelectedItem()=="-2%  et +2%"){

		          switch(answerTime.getSelectedIndex())
		          {
		          case 1:
		            answerInitialRate.setText("2.92");
		            break;
		          case 2:
		            answerInitialRate.setText("2.14");
		            break;
		          case 3:
		            answerInitialRate.setText("2.19");
		            break;
		          case 4:
		            answerInitialRate.setText("2.56");
		            break;
		          case 5:
		            answerInitialRate.setText("2.75");
		          case 6:
		            answerInitialRate.setText("3.29");

		          }
		        }
	          else answerInitialRate.setText("0.00");

	    }});


	    // Add of an event when clicking on the bouton1
	    bouton1.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent arg0) {
	    	  resultsIHM1(getAnswerInitialRate());
	        
	    	  cont.revalidate();
	    	  cont.repaint();
	      }
	    });

	    // Add of an event when clicking on the bouton2
	    bouton2.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent arg0) {
	        resultsIHM2("Scénarios Favorables");

	      }
	    });
	    // Add of an event when clicking on the bouton3
	    bouton3.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent arg0) {
	        resultsIHM2("Scénarios défavorables");
	      }
	    });

	    this.add(body1);
	    this.setVisible(true);
		return body1;

  }
  
  //Creation of the results view
  private JPanel resultsIHM1(String interestRate) {
    body1.removeAll();
 
    
    lowerBoundary=labelString(interestRate)-borne();
    upperBoundary=labelString(interestRate)+borne();

    double lowerBoundaryRound= Math.floor(100*lowerBoundary)/100;
    double upperBoundaryRound=Math.floor(100*upperBoundary)/100;
    controller.initialization();
    double monthPayment1= controller.calculateMonthlyPayment(this.labelString(this.getAnswerInitialRate()));
    double totalToPay = monthPayment1*(this.labelString(this.getAnswerTime())*12);
    
    
 
    labelAmount.setText("Montant du prêt:  " + getAnswerAmount()+ " euros");
    labelTime.setText("Durée du prêt:   "+ answerTime.getSelectedItem()+ "ans");
    labelCap.setText("Le cap choisit est "+ answerCap.getSelectedItem());
    labelInitialRate.setText("Taux d'intérêt actuel: " + "  " + interestRate + "%");
    labelResult.setText("Le taux d'intérêt sera au minimum "+lowerBoundaryRound+"%  "+ "et au maximum "+upperBoundaryRound+ "%");
    
 
    body1.setLayout(new GridLayout(7,1,20,20));
    body1.add(labelAmount);
    body1.add(labelTime);
    body1.add(labelCap);
    body1.add(labelInitialRate);
    body1.add(labelResult);
    body1.add(bouton2);
    body1.add(bouton3);
    return body1;
  }



private void resultsIHM2(String title) {
	
    double interest= this.labelString(this.getAnswerInitialRate());
    controller.initialization();
    double monthPayment2= controller.calculateMonthlyPayment(interest);
    int duree= (int) (this.labelString(this.getAnswerTime()));
    double monthPayment; //Amount to pay for one year

    JFrame fenetre= new JFrame();
    fenetre.setExtendedState(fenetre.MAXIMIZED_BOTH);
    JPanel pan1= new JPanel();
    pan1.setLayout(new BorderLayout());

    JPanel pan2= new JPanel();
    pan2.setLayout(new BorderLayout());

    JPanel pan3= new JPanel();
    pan3.setLayout(new BorderLayout());

    JTabbedPane onglet;
    onglet = new JTabbedPane();

    fenetre.setLocationRelativeTo(null);
    fenetre.setTitle(title);


    //Instantiation of the titles
    String[] titles = {"Année", "Taux", "Mensualité", "Total Payé"};
    Object[][] data = null;

    //Instantiation of the table
    DefaultTableModel model1 = new DefaultTableModel(data,titles);
    JTable table1 = new JTable(model1);
    table1.setRowHeight(50);
    
    DefaultTableModel model2 = new DefaultTableModel(data,titles);
    JTable table2 = new JTable(model2);
    table2.setRowHeight(50);
    
    DefaultTableModel model3 = new DefaultTableModel(data,titles);
    JTable table3 = new JTable(model3);
    table3.setRowHeight(50);
    
    
    
  
    

    if(title=="Scénarios Favorables"){
    
     
    	//Scenario 1
    	double totalToPay1= 0;
    	for(int i=1; i<=duree; i++){
        monthPayment2=controller.calculateMonthlyPayment(interest);
        monthPayment=monthPayment2*12;
        model1.addRow(new Object[]{i,interest,monthPayment2,monthPayment}); // Add of the line in the table
        totalToPay1=totalToPay1+labelString(model1.getValueAt(i - 1,3).toString());// Sum of monthlypayment
        interest=Math.floor(100*(interest-(1.0/duree)))/100;// update of the interest rate
      }
    	model1.addRow(new Object[]{"Total",totalToPay1}); //Add of the total to pay in the table
    	interest= this.labelString(this.getAnswerInitialRate());// update of the interest rate
    	
    	 double interet1=totalToPay1 - (labelString(answerAmount.getText()));// The total amount interest rate to pay 
         model1.addRow(new Object[]{"Taux d'intéret",interet1});
    	
    	//Scenario 2
    	double totalToPay2= 0;
    	for(int i=1; i<=duree; i++){
            monthPayment2=controller.calculateMonthlyPayment(interest);
            monthPayment=monthPayment2*12;
            model2.addRow(new Object[]{i,interest,monthPayment2,monthPayment});
            totalToPay2=totalToPay2+labelString(model2.getValueAt(i - 1,3).toString());
          }
    	model2.addRow(new Object[]{"Total",totalToPay2});
    	interest= this.labelString(this.getAnswerInitialRate());
    	
    	 double interet2=totalToPay2 - (labelString(answerAmount.getText()));
         model2.addRow(new Object[]{"Taux d'intéret",interet2});
    	
    	//Scenario 3
    	double totalToPay31=0 ;
    	for(int i=1; i<=(duree/2)+1; i++){
            interest=Math.floor(100*(interest+(1.0/duree)))/100;
            monthPayment2=controller.calculateMonthlyPayment(interest);
            monthPayment=monthPayment2*12;
            model3.addRow(new Object[]{i,interest,monthPayment2,monthPayment});
            totalToPay31=totalToPay31+labelString(model2.getValueAt(i - 1,3).toString());
              }
    	
    	
    	double totalToPay32 = totalToPay31;
    	for(int i=(duree/2)+2; i<=duree; i++){
            interest=Math.floor(100*(interest-(1.0/duree)))/100;
            monthPayment2=controller.calculateMonthlyPayment(interest);
            monthPayment=monthPayment2*12;
            model3.addRow(new Object[]{i,interest,monthPayment2,monthPayment});
            totalToPay32=totalToPay32+labelString(model3.getValueAt(i - 1,3).toString());
              }
    	model3.addRow(new Object[]{"Total",totalToPay32});
       
    double interet3=totalToPay32 - (labelString(answerAmount.getText()));
    model3.addRow(new Object[]{"Taux d'intéret",interet3});
    }
    
    else{
    	
    	
      //Scenarios 1
      interest= this.labelString(this.getAnswerInitialRate());
      double totalToPay1= 0;
      for(int i=1; i<=duree; i++){

        interest=Math.floor(100*(interest+(1.0/duree)))/100;
        monthPayment2=controller.calculateMonthlyPayment(interest);
        monthPayment=monthPayment2*12;
        model1.addRow(new Object[]{i,interest,monthPayment2,monthPayment});
        totalToPay1=totalToPay1+labelString(model1.getValueAt(i - 1,3).toString());
      }
      model1.addRow(new Object[]{"Total",totalToPay1});
      
      double interet1=totalToPay1 - (labelString(answerAmount.getText()));
      model1.addRow(new Object[]{"Taux d'intéret",interet1});
      
      
     
      //Scenarios 2
      interest= this.labelString(this.getAnswerInitialRate());
      double totalToPay21= 0;
      for(int i=1; i<=(duree/2)+1; i++){
          monthPayment2=controller.calculateMonthlyPayment(interest);
          monthPayment=monthPayment2*12;
          model2.addRow(new Object[]{i,interest,monthPayment2,monthPayment});
          totalToPay21=totalToPay21+labelString(model2.getValueAt(i - 1,3).toString());
        }
      
      
     
      double totalToPay22=totalToPay21;
      for(int i=(duree/2)+2; i<=duree; i++){
          interest=Math.floor(100*(interest+(1.0/duree)))/100;
          monthPayment2=controller.calculateMonthlyPayment(interest);
          monthPayment=monthPayment2*12;
          model2.addRow(new Object[]{i,interest,monthPayment2,monthPayment});
          totalToPay22=totalToPay22+labelString(model2.getValueAt(i - 1,3).toString());
        }
       model2.addRow(new Object[]{"Total",totalToPay22});
       double interet2=totalToPay22 - (labelString(answerAmount.getText()));
       model2.addRow(new Object[]{"Taux d'intéret",interet2});
      
      
       
       
       //Scenarios 3
       interest= this.labelString(this.getAnswerInitialRate());
       double totalToPay31= 0;
  	   for(int i=1; i<=(duree/2)+1; i++){
  	    //Update of the interest rate and the monthly payment
        interest=Math.floor(100*(interest-(1.0/duree)))/100;
        monthPayment2=controller.calculateMonthlyPayment(interest);
        monthPayment=monthPayment2*12;
        //Add of new line in the table
        model3.addRow(new Object[]{i,interest,monthPayment2,monthPayment});
        totalToPay31=totalToPay31+labelString(model3.getValueAt(i - 1,3).toString());
        }
  	   
  	   
 
  	   double totalToPay32= totalToPay31;
  	   for(int i=(duree/2)+2; i<=duree; i++){
        interest=Math.floor(100*(interest+(1.0/duree)))/100;
        monthPayment2=controller.calculateMonthlyPayment(interest);
        monthPayment=monthPayment2*12;
        model3.addRow(new Object[]{i,interest,monthPayment2,monthPayment});
        totalToPay32=totalToPay32+labelString(model3.getValueAt(i - 1,3).toString());
        
      }
    model3.addRow(new Object[]{"Total",totalToPay32});
    
    double interet3=totalToPay32 - (labelString(answerAmount.getText()));
    model3.addRow(new Object[]{"Taux d'intéret",interet3});
    
  	}

    pan1.add(new JScrollPane(table1),BorderLayout.CENTER);
    onglet.add("scénario1",pan2);
    
    
    pan2.add(new JScrollPane(table2),BorderLayout.CENTER);
    onglet.add("scénario2",pan1);
    
    
    pan3.add(new JScrollPane(table3),BorderLayout.CENTER);
    onglet.add("scénario3",pan3);

    fenetre.getContentPane().add(onglet);
    fenetre.setVisible(true);
}


  // Convert a string to int
  public double labelString(String g){
    try {
      stringToInt=Double.parseDouble(g);

    } catch (NumberFormatException e) {

    }
    return stringToInt;
  }



  // Return an int according to the cap
  public double borne(){
    final double cas1 =1;
    final double cas2 =2;

    if (answerCap.getSelectedItem()== "-1%  et +1%")
      return cas1;
    else if (answerCap.getSelectedItem()== "-2%  et +2%")
      return cas2 ;
    else
      return 0.0;

  }

  public String getAnswerAmount() {
    return answerAmount.getText();
  }

  public void setAnswer_amount(JTextField answer_amount) {
    this.answerAmount = answer_amount;
  }

  public String getAnswerInitialRate() {
    return answerInitialRate.getText();
  }

  public void setAnswer_initial_rate(JTextField answer_initial_rate) {
    this.answerInitialRate = answer_initial_rate;
  }

  public String getAnswerTime() {
    return answerTime.getSelectedItem().toString();
  }
  
  public String getAnswerLastname() {
	    return answerLastname.getText();
	  }
  
  public String getAnswerFistname() {
	    return answerFirstname.getText();
	  }

  public void setAnswer_time(JComboBox answer_time) {
    this.answerTime = answer_time;
  }



  //public static void main(String[] args){
  //  VariableRateSimulationController c = new VariableRateSimulationController();
  //  VariableRateSimulationView ihm = new VariableRateSimulationView(c);
  //  c.addListener(ihm);
  //}

}


