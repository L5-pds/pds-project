package app.views.ruben;

import app.views.alexandre.FixedRateSimulation;
import java.text.DecimalFormat;

public class Calcul {
// Begin variable declaration
    public double amount;

    public double rate;

    public double insuranceRate = 1;

    public int number_month;

    public double totalinsurance;

    public double totalInterest;

    public double totalMonthlyPayement;

    DecimalFormat df = new DecimalFormat("0.00");

    public String data[][] = new String[number_month + 1][7];

// END variable declaration

    public Calcul(double amount, double rate, int number_month) {
        this.amount = amount;
        this.rate = rate;
        this.number_month = number_month;
        data = new String[number_month + 1][7];
        calculate();
    }

    public Calcul() {
        // Call methode calculate
        calculate();
    }

    public void calculate() {
        // Methode to calculate all information necessary to generate an amortization table
        double newamount;
        double rateperyear = (rate / 12) / 100;
        double txassur = (insuranceRate / 100);
        double monthspay, interestpay, principal_pain;
        int i;
        monthspay = amount * rateperyear * Math.pow(1 + rateperyear, (double) number_month) / (Math.pow(1 + rateperyear, (double) number_month) - 1);
        txassur = (this.amount * txassur) / 12;
        for (i = 1; i <= number_month; i++) {
            //loop starting from i to the number of months of the loan
            interestpay = amount * rateperyear;
            principal_pain = monthspay - interestpay;
            newamount = amount - principal_pain;
            // ADD value to the table
            data[i][0] = String.valueOf(i);
            data[i][1] = df.format(amount);
            data[i][2] = df.format(monthspay);
            data[i][3] = df.format(interestpay);
            data[i][4] = df.format(principal_pain);
            data[i][5] = df.format(txassur);
            data[i][6] = df.format(newamount);
            amount = newamount;
            totalinsurance += txassur;
            totalInterest += interestpay;
            totalMonthlyPayement += principal_pain;
        }
    }
}
