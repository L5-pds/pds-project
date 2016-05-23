package app.views.simulations;


import java.awt.BorderLayout;
import java.awt.Color;
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

public class VariableRateSimulationView extends JPanel implements VariableRateSimulationListener{

	private JPanel body;
	private Container cont;
	private VariableRateSimulationController controller;
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
  private JComboBox answer_time;
  private JTextField answer_initial_rate;
  private JComboBox answer_cap;

  private JButton bouton1 = new JButton("Valider");
  private JButton bouton2 = new JButton("Afficher les scénarios favorables");
  private JButton bouton3 = new JButton("Afficher les scénarios défavorables");

  // Container of the previous components
  private JPanel pan1;
  private JPanel pan2;
  private JPanel pan3;
  private JPanel pan4;
  private JPanel pan5;
  private JPanel pan6;
  private JPanel pan7;
  private JPanel pan8;
  private JPanel body1;

  //Interest rate max and min value
  private double lowerBoundary;
  private double upperBoundary;
  double i;

  //Attributes for the whole project
  private Container contentPane;
  private JPanel header;
  private ImageIcon trait;
  private Image im;
  private JLabel image;
  private JLabel titre_use_case;

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
	    answer_cap = new JComboBox(tab1);
	    answer_cap.setPreferredSize(new Dimension (250,20));
	    answer_cap.setSelectedIndex(0); // definition of the default value

	    // Instantiation of JComboBox for the time
	        String[] tab2 = {"","7","10","15","20","25","30"};
	        setAnswer_time(new JComboBox(tab2));
	        answer_time.setPreferredSize(new Dimension (250,20));
	        answer_time.setSelectedIndex(0); // definition of the default value

	    //Instantiation of five JPanel and JLabel for the fields firstname,lastname,amount,cap
	    //and initial rate in order to range them in the JFrame more easier.
	    // Instantiation of Lastname JLabel and JPanel and inclusion in a Jpanel.
	    pan1= new JPanel();
	    pan1.setLayout(new FlowLayout());
	    label_lastname= new JLabel("Nom");
	    pan1.add(label_lastname);
	    answer_lastname= new JTextField();
	    answer_lastname.setColumns(22);
	    pan1.add(answer_lastname);

	    // Instantiation of firstname JLabel and JPanel and inclusion in a Panel.
	    pan2= new JPanel();
	    pan2.setLayout(new FlowLayout());
	    label_firstname= new JLabel("Prenom");
	    pan2.add(label_firstname);
	    answer_firstname= new JTextField();
	    answer_firstname.setColumns(20);
	    pan2.add(answer_firstname);

	    // Instantiation of amount JLabel and JPanel and inclusion in a Panel.
	    pan3= new JPanel();
	    pan3.setLayout(new FlowLayout());
	    label_amount= new JLabel("Montant");
	    pan3.add(label_amount);
	    setAnswer_amount(new JTextField());
	    answer_amount.setColumns(20);
	    pan3.add(answer_amount);

	    // Instantiation of JLabel and JPanel and inclusion in a Panel.
	    pan4= new JPanel();
	    pan4.setLayout(new FlowLayout());
	    label_initial_rate= new JLabel("Taux initiale");
	    pan4.add(label_initial_rate);
	    setAnswer_initial_rate(new JTextField());
	    answer_initial_rate.setColumns(20);
	    pan4.add(answer_initial_rate);

	    // Instantiation of cap JLabel and JPanel and inclusion in a Panel.
	    pan5= new JPanel();
	    pan5.setLayout(new FlowLayout());
	    label_cap= new JLabel("Cap");
	    pan5.add(label_cap);
	    pan5.add(answer_cap);

	    //Instantiation of time JLabel and JPanel and inclusion in a Panel.
	    pan6= new JPanel();
	    pan6.setLayout(new FlowLayout());
	    label_time= new JLabel("Durée");
	    pan6.add(label_time);
	    pan6.add(answer_time);

	    // add of component bouton in a Panel
	    pan7= new JPanel();
	    pan7.add(bouton1);

	    // instantiation of the Panel body1, which will contain all the Panels.
	    body1= new JPanel();
	    body1.setLayout(new BoxLayout(body1, BoxLayout.Y_AXIS));
	    
	    body1.add(pan1);
	    body1.add(pan2);
	    body1.add(pan3);
	    body1.add(pan6);
	    body1.add(pan5);
	    body1.add(pan4);
	    body1.add(pan7);

	    // Add actionListener on answer_cap
	    answer_cap.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent arg0) {

	        if (answer_cap.getSelectedItem()=="-1%  et +1%"){

	          switch(answer_time.getSelectedIndex())
	          {
	          case 1:
	            answer_initial_rate.setText("1.05");
	            break;
	          case 2:
	            answer_initial_rate.setText("1.17");
	            break;
	          case 3:
	            answer_initial_rate.setText("1.28");
	            break;
	          case 4:
	            answer_initial_rate.setText("1.56");
	            break;
	          case 5:
	            answer_initial_rate.setText("1.85");
	          case 6:
	            answer_initial_rate.setText("2.32");

	          }
	        }
	          else answer_initial_rate.setText("0.00");

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
    //Update of the title
    
    lowerBoundary=labelString(interestRate)-borne();
    upperBoundary=labelString(interestRate)+borne();

    double lowerBoundaryRound= Math.floor(100*lowerBoundary)/100;
    double upperBoundaryRound=Math.floor(100*upperBoundary)/100;
    controller.initialization();
    double monthPayment1= controller.calculateMonthlyPayment(this.labelString(this.getAnswerInitialRate()));
    double totalToPay = monthPayment1*(this.labelString(this.getAnswerTime())*12);

    label_lastname.setText("Montant du prêt:  " + getAnswerAmount());
    label_firstname.setText("Durée du prêt:   "+ answer_time.getSelectedItem()+ "ans");
    label_amount.setText("Montant de la mensualité:   "+ monthPayment1);
    label_initial_rate.setText("Total à payer:  " +totalToPay);
    label_cap.setText("Taux d'intérêt actuel: " + "  " + interestRate + "%");
    label_time.setText("Le taux d'intérêt sera au minimum   "+lowerBoundaryRound+"%  "+ "et au maximum   "+upperBoundaryRound+ "%");

    pan1=new JPanel();
    pan1.setLayout(new FlowLayout());
    pan1.add(label_lastname);
    body1.add(pan1);

    pan2=new JPanel();
    pan2.setLayout(new FlowLayout());
    pan2.add(label_firstname);
    body1.add(pan2);

    pan3=new JPanel();
    pan3.setLayout(new FlowLayout());
    pan3.add(label_amount);
    body1.add(pan3);

    pan4=new JPanel();
    pan4.setLayout(new FlowLayout());
    pan4.add(label_initial_rate);
    body1.add(pan4);

    pan5=new JPanel();
    pan5.setLayout(new FlowLayout());
    pan5.add(label_cap);
    body1.add(pan5);

    pan6=new JPanel();
    pan6.setLayout(new FlowLayout());
    pan6.add(label_time);
    body1.add(pan6);

    pan8= new JPanel();
    pan8.add(bouton2);
    pan8.add(bouton3);
    body1.add(pan8);
	return body;
  }


private void resultsIHM2(String title) {
    double interest= this.labelString(this.getAnswerInitialRate());
    controller.initialization();
    double monthPayment2= controller.calculateMonthlyPayment(interest);
    int duree= (int) (this.labelString(this.getAnswerTime()));

    //Amount to pay for one year
    double monthPayment;

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
    
    DefaultTableModel model2 = new DefaultTableModel(data,titles);
    JTable table2 = new JTable(model2);
    
    DefaultTableModel model3 = new DefaultTableModel(data,titles);
    JTable table3 = new JTable(model3);
    
  
    

    if(title=="Scénarios Favorables"){
    
     
    	//Scenario 1
    	double totalToPay1= 0;
    	for(int i=1; i<=duree; i++){
        //Update of the interest rate and the monthly payment
        interest=Math.floor(100*(interest-(1.0/duree)))/100;
        monthPayment2=controller.calculateMonthlyPayment(interest);
        monthPayment=monthPayment2*12;
        //Add of new line in the table
        model1.addRow(new Object[]{i,interest,monthPayment2,monthPayment});
        totalToPay1=totalToPay1+labelString(model1.getValueAt(i - 1,3).toString());
      }
    	model1.addRow(new Object[]{"Total",totalToPay1});
    	
    	
    	//Scenario 2
    	double totalToPay2= 0;
    	for(int i=1; i<=duree; i++){
            monthPayment2=controller.calculateMonthlyPayment(interest);
            monthPayment=monthPayment2*12;
            model2.addRow(new Object[]{i,interest,monthPayment2,monthPayment});
            totalToPay2=totalToPay2+labelString(model2.getValueAt(i - 1,3).toString());
          }
    	model2.addRow(new Object[]{"Total",totalToPay2});
    	
    	
    	//Scenario 3
    	double totalToPay31=0 ;
    	for(int i=1; i<=(duree/2)+1; i++){
            interest=Math.floor(100*(interest+(1.0/duree)))/100;
            monthPayment2=controller.calculateMonthlyPayment(interest);
            monthPayment=monthPayment2*12;
            model3.addRow(new Object[]{i,interest,monthPayment2,monthPayment});
            totalToPay31=totalToPay31+labelString(model2.getValueAt(i - 1,3).toString());
              }
    	
    	
    	// Scenario 3
    	double totalToPay32 = totalToPay31;
    	for(int i=(duree/2)+2; i<=duree; i++){
            interest=Math.floor(100*(interest-(1.0/duree)))/100;
            monthPayment2=controller.calculateMonthlyPayment(interest);
            monthPayment=monthPayment2*12;
            model3.addRow(new Object[]{i,interest,monthPayment2,monthPayment});
            totalToPay32=totalToPay32+labelString(model3.getValueAt(i - 1,3).toString());
              }
    	model3.addRow(new Object[]{"Total",totalToPay32});
       }
    
    else{
    	
      //Scenarios 1
      double totalToPay1= 0;
      for(int i=1; i<=duree; i++){

        interest=Math.floor(100*(interest+(1.0/duree)))/100;
        monthPayment2=controller.calculateMonthlyPayment(interest);
        monthPayment=monthPayment2*12;
        model1.addRow(new Object[]{i,interest,monthPayment2,monthPayment});
        totalToPay1=totalToPay1+labelString(model1.getValueAt(i - 1,3).toString());
      }
      model1.addRow(new Object[]{"Total",totalToPay1});
      
      
      //Scenarios 2
      double totalToPay21= 0;
      for(int i=1; i<=(duree/2)+1; i++){
          monthPayment2=controller.calculateMonthlyPayment(interest);
          monthPayment=monthPayment2*12;
          model2.addRow(new Object[]{i,interest,monthPayment2,monthPayment});
          totalToPay21=totalToPay21+labelString(model2.getValueAt(i - 1,3).toString());
        }
      
     
      double totalToPay22=totalToPay21;
      for(int i=(duree/2)+1; i<=duree; i++){
          interest=Math.floor(100*(interest+(1.0/duree)))/100;
          monthPayment2=controller.calculateMonthlyPayment(interest);
          monthPayment=monthPayment2*12;
          model2.addRow(new Object[]{i,interest,monthPayment2,monthPayment});
          totalToPay22=totalToPay22+labelString(model2.getValueAt(i - 1,3).toString());
        }
       model2.addRow(new Object[]{"Total",totalToPay22});
      
      
       
       
       //Scenarios 3
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
  	   for(int i=(duree/2)+1; i<=duree; i++){
        interest=Math.floor(100*(interest+(1.0/duree)))/100;
        monthPayment2=controller.calculateMonthlyPayment(interest);
        monthPayment=monthPayment2*12;
        model3.addRow(new Object[]{i,interest,monthPayment2,monthPayment});
        totalToPay32=totalToPay32+labelString(model3.getValueAt(i - 1,3).toString());
        
      }
    model3.addRow(new Object[]{"Total",totalToPay32});
    
  	}

    pan1.add(new JScrollPane(table1),BorderLayout.CENTER);
    onglet.add("scénario1",pan1);
    
    
    pan2.add(new JScrollPane(table2),BorderLayout.CENTER);
    onglet.add("scénario2",pan2);
    
    
    pan3.add(new JScrollPane(table3),BorderLayout.CENTER);
    onglet.add("scénario3",pan3);

    fenetre.getContentPane().add(onglet);
    fenetre.setVisible(true);
}


  // Convert a string to int
  public double labelString(String g){
    try {
      i=Double.parseDouble(g);

    } catch (NumberFormatException e) {

    }
    return i;
  }



  // Return an int according to the cap
  public double borne(){
    final double cas1 =1;
    final double cas2 =2;

    if (answer_cap.getSelectedItem()== "-1%  et +1%")
      return cas1;
    else if (answer_cap.getSelectedItem()== "-2%  et +2%")
      return cas2 ;
    else
      return 0.0;

  }

  public String getAnswerAmount() {
    return answer_amount.getText();
  }

  public void setAnswer_amount(JTextField answer_amount) {
    this.answer_amount = answer_amount;
  }

  public String getAnswerInitialRate() {
    return answer_initial_rate.getText();
  }

  public void setAnswer_initial_rate(JTextField answer_initial_rate) {
    this.answer_initial_rate = answer_initial_rate;
  }

  public String getAnswerTime() {
    return answer_time.getSelectedItem().toString();
  }

  public void setAnswer_time(JComboBox answer_time) {
    this.answer_time = answer_time;
  }



  //public static void main(String[] args){
  //  VariableRateSimulationController c = new VariableRateSimulationController();
  //  VariableRateSimulationView ihm = new VariableRateSimulationView(c);
  //  c.addListener(ihm);
  //}

}


