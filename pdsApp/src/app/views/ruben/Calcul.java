package app.views.ruben;

import app.views.alexandre.FixedRateSimulation;
import java.text.DecimalFormat;

public class Calcul {
    public double amount; // Le amount a emprunte est de 10.000
    public double rate; // Le rate est a 2%
    public double insuranceRate =1; // Le rate d'assurance est de 1%
    public int number_month ; // Le nombre d'annees d'emprunt est de 2 ans
    public double totalinsurance;
    public double totalInterest;
    public double totalMonthlyPayement;
    DecimalFormat df = new DecimalFormat("0.00");
    public String data[][] = new String [number_month+1][7];
    
    public Calcul(double amount,double rate,int number_month){
        this.amount=amount;
        this.rate=rate;
        this.number_month=number_month;
        data = new String [number_month+1][7];
        
        calculate();
    }
    
    public void calculate(){
        double newamount;
        double rateperyear = (rate / 12) / 100;
        double txassur = (insuranceRate/100);
        double monthspay, interestpay, principal_pain;
        int i;

        monthspay = amount * rateperyear * Math.pow(1 + rateperyear, (double) number_month) / (Math.pow(1 + rateperyear, (double) number_month) - 1);
        txassur=(this.amount*txassur)/12;
        // On affiche tous les mois sauf le dernier
        for (i = 1; i <= number_month; i++) {

            interestpay = amount * rateperyear;//interest paid
            principal_pain = monthspay - interestpay; //princial paid
            newamount = amount - principal_pain; //new balance 

            data[i][0] = String.valueOf(i);
            data[i][1] = df.format(amount);
            data[i][2] = df.format(monthspay);
            data[i][3] = df.format(interestpay);
            data[i][4] = df.format(principal_pain);
            data[i][5] = df.format(txassur);
            data[i][6] = df.format(newamount);
            amount = newamount;  //update old balance
            
            totalinsurance+=txassur;
            totalInterest+=interestpay;
            totalMonthlyPayement+=principal_pain;    
        }

        
           
    }
}
