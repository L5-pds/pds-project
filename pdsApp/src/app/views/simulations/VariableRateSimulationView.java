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

public class VariableRateSimulationView extends JFrame implements VariableRateSimulationListener{


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

  public VariableRateSimulationView(VariableRateSimulationController controller){
    this.controller = controller;
    template();
    this.setExtendedState(this.MAXIMIZED_BOTH);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
  }

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

  //Creation of the results view
private void resultsIHM1(String interestRate) {


    body1.removeAll();

    //Update of the title
    titre_use_case.setText(answer_firstname.getText()+ " " + answer_lastname.getText()+ " "+ "voici le résultat de votre première simulation de prêt:");

    lowerBoundary=labelString(interestRate)-borne();
    upperBoundary=labelString(interestRate)+borne();


    double lowerBoundaryRound= Math.floor(100*lowerBoundary)/100;
    double upperBoundaryRound=Math.floor(100*upperBoundary)/100;

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
  }


private void resultsIHM2(String title) {
    double interest= this.labelString(this.getAnswerInitialRate());
    double monthPayment2= controller.calculateMonthlyPayment(interest);
    int duree= (int) (this.labelString(this.getAnswerTime()));

    //Amount to pay for one year
      double monthPayment;
      double totalMonth =0;


    JFrame fenetre= new JFrame();

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
      fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      fenetre.setExtendedState(this.MAXIMIZED_BOTH);

      //Instantiation of the titles
        String[] titles = {"Année", "Taux", "Mensualité", "Total Payé"};
        Object[][] data = null;

        //Instantiation of the table
        DefaultTableModel model = new DefaultTableModel(data,titles);
      JTable table = new JTable(model);



  if(title=="Scénarios Favorables"){

      for(int i=1; i<=duree; i++){

          //Update of the interest rate and the monthly payment
          interest=Math.floor(100*(interest+ (1.0/duree)))/100;
          monthPayment2=controller.calculateMonthlyPayment(interest);
          monthPayment=monthPayment2*12;
          //Add of new line in the table
          model.addRow(new Object[]{i,interest,monthPayment2,monthPayment});

          }}

  else{

     for(int i=1; i<=duree; i++){
          interest=Math.floor(100*(interest-(1.0/duree)))/100;
          monthPayment2=controller.calculateMonthlyPayment(interest);
          monthPayment=monthPayment2*12;
          model.addRow(new Object[]{i,interest,monthPayment2,monthPayment});

          }
  }


     //System.out.println(labelString(model.getValueAt(0,3).toString()));
     model.addRow(new Object[]{"Le montant","total","a payer est :",totalMonth});

     pan1.add(new JScrollPane(table),BorderLayout.CENTER);
     onglet.add("scénario1",pan1);
     onglet.add("scénari2",pan2);
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



  public static void main(String[] args){
    VariableRateSimulationController c = new VariableRateSimulationController();
    VariableRateSimulationView ihm = new VariableRateSimulationView(c);
    c.addListener(ihm);
  }

}


