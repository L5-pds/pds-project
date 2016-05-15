package part_pds;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.List;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CalcAmortizment extends JFrame {

		private static JPanel contentPane;
	 	static DefaultTableModel model = new DefaultTableModel(); //   
	    static JTable table = new JTable(model);
	    Object data[] = new Object[5];
        String[] columnNames = {"Numéro de mensualité", "Restant à payer avant paiement", "Montant des intérèts de la mensualité", "Montant du capital remboursé", "Restant à payer après paiement"}; // column names

	    
	    double montant=10000; // Le montant a emprunte est de 10.000
	    double taux=2.0; // Le taux est a 2%
	    double tauxdassurance=1.0; // Le taux d'assurance est de 1%
	    int nombre_annee=2; // Le nombre d'annees d'emprunt est de 2 ans
	 


	public CalcAmortizment() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.setVisible(true);
		contentPane.add(table);
		
	}
	
	
	public void calculAmortizment(){
        amortissement_calcul(montant,taux,tauxdassurance,nombre_annee);
    }
     
     
     public void amortissement_calcul(double montant, double taux,double tauxdassurance,int nombre_annee){
         // Source : http://java.worldbestlearningcenter.com/2013/04/amortization-program.html
         // Cette source nous premet de calculer toutes nos données sauf le taux d'assurance
    	 for (int columnLength = 0; columnLength < 6; columnLength++) {
    		 model.addColumn(columnNames[columnLength]);
         }
    	 
    	 this.montant=montant;
         this.taux=taux;
         this.tauxdassurance=tauxdassurance;
         this.nombre_annee=nombre_annee;
         
         double newmontant;
         double tauxperyear=(taux/12)/100;
         int nombre_mois=nombre_annee*12;
         
         
         double monthspay,interestpay,principal_pain;
         int i;
         
         monthspay=montant*tauxperyear*Math.pow(1+tauxperyear,(double)nombre_mois)/(Math.pow(1+tauxperyear,(double)nombre_mois)-1);
         //printHeader();
         // On affiche tous les mois sauf le dernier
         for(i=1;i<nombre_mois;i++){  
             
             interestpay=montant*tauxperyear;//interest paid
             principal_pain=monthspay-interestpay; //princial paid
             newmontant=montant-principal_pain; //new balance                 
                  // printSch(i,montant,monthspay,interestpay,principal_pain,newmontant);
                   montant=newmontant;  //update old balance
                   
                   data[0] =i;      
                   data[1]=montant;
                   data[2]=monthspay;          
                   data[3]=interestpay;
                   data[4]=principal_pain;
                   data[5]=newmontant;
                   model.addRow(data);
                   
                  } 
        //last month
           principal_pain=montant;
           interestpay=montant*tauxperyear;
           monthspay=principal_pain+interestpay;
           newmontant=0.0;
          // printSch(i,montant,monthspay,interestpay,principal_pain,newmontant);  
   
     }
     /*
         data[0] = Integer.toString(numberOfTerms + 1); //payment number
         data[1] = nf.format(newPrincipal);  //begin balance
         data[2] = nf.format(interestPaid = principalAmount * (monthlyInterestRate)); //interest paid
         data[3] = nf.format(principalPaid = monthlyPayment - interestPaid); //principal paid
         data[4] = nf.format(principalAmount = principalAmount - principalPaid); //ending balance
         newPrincipal = principalAmount;
         model.addRow(data);
     
     */
     
     /*public static void printSch(int i,double p,double mp,double ip,double pp,double newbal){    
           System.out.format("%-8d%-12.3f%-10.3f%-10.3f%-10.3f%-12.3f\n",i,p,mp,ip,pp,newbal);   
           
           contentPane.add(table);
           } 
     */

     
     public static void main(String[] args){
         CalcAmortizment c = new CalcAmortizment();
         c.calculAmortizment();
     }
	
	
	
	
	

}
