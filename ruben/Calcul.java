package app.views.ruben;

import java.text.DecimalFormat;

public class Calcul {
    public double montant = 10000; // Le montant a emprunte est de 10.000
    public double taux = 2.0; // Le taux est a 2%
    public double tauxdassurance = 1.0; // Le taux d'assurance est de 1%
    public int nombre_annee = 4; // Le nombre d'annees d'emprunt est de 2 ans
    public double totalassurance;
    public double totalinteret;
    public double totalmensualite;
    DecimalFormat df = new DecimalFormat("0.00");
    public String data[][] = new String [nombre_annee*12][7];
    
    public Calcul(){
        calculate();
    }
    
    public void calculate(){
        double newmontant;
        double tauxperyear = (taux / 12) / 100;
        int nombre_mois = nombre_annee * 12;
        double txassur = (tauxdassurance/100);
        double monthspay, interestpay, principal_pain;
        int i;

        monthspay = montant * tauxperyear * Math.pow(1 + tauxperyear, (double) nombre_mois) / (Math.pow(1 + tauxperyear, (double) nombre_mois) - 1);
        txassur=this.montant*txassur;
        // On affiche tous les mois sauf le dernier
        for (i = 1; i < nombre_mois; i++) {

            interestpay = montant * tauxperyear;//interest paid
            principal_pain = monthspay - interestpay; //princial paid
            newmontant = montant - principal_pain; //new balance 

            totalassurance+=txassur;
            totalinteret+=interestpay;
            totalmensualite+=principal_pain;
            // printSch(i,montant,monthspay,interestpay,principal_pain,newmontant);

            data[i][0] = String.valueOf(i);
            data[i][1] = df.format(montant);
            data[i][2] = df.format(monthspay);
            data[i][3] = df.format(interestpay);
            data[i][4] = df.format(principal_pain);
            data[i][5] = df.format(txassur);
            data[i][6] = df.format(newmontant);
            montant = newmontant;  //update old balance
        }

        //last month
        principal_pain = montant;
        interestpay = montant * tauxperyear;
        monthspay = principal_pain + interestpay;
        newmontant = 0.0;
        // printSch(i,montant,monthspay,interestpay,principal_pain,newmontant);

        data[47][0]= String.valueOf(nombre_annee*12);
        data[47][1]=df.format(principal_pain);
        data[47][2]=df.format(monthspay);
        data[47][3]=df.format(interestpay);
        data[47][4]=df.format(newmontant);
        data[47][5]=df.format(txassur);
        data[47][6]=df.format(newmontant); 

        totalassurance+=txassur;
        totalinteret+=interestpay;
        totalmensualite+=principal_pain;       
    }
}
