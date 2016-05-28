package app.controllers;

import app.models.FixedRateSimulation;

public class FixedRateSimulationControllerServer {
    
    private FixedRateSimulation model;
    
    public FixedRateSimulationControllerServer(FixedRateSimulation m) {
        model = m;
    }
    
    public void calculateMonthlyPayment() {
        // get the data needed to calculate the monthly payment value
        int amount = model.getAmount();
        int duration = model.getDuration();
        double totalRate = model.getInsurance().getRate() + model.getInterestRate();
        
        //System.out.println("Total rate : " + totalRate);
        
        // calculate monthly payment
        double monthlyPayment = (amount*((totalRate/100)/12))/(1-Math.pow((double)(1+((totalRate/100)/12)),(double)-duration));
        //System.out.println("monthly : " + monthlyPayment);
        
        // update the model
        model.setMonthlyPayment(monthlyPayment);
    }
}
