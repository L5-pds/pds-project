package app.controllers;
import app.views.simulations.VariableRateSimulationView;
import app.listeners.VariableRateSimulationListener;

public class VariableRateSimulationController {

  private double monthlyPayment;
  private double amount;
  private double interestRate;
  private double duration;

  private VariableRateSimulationListener listener;

  public VariableRateSimulationController(){
  }

  public void addListener(VariableRateSimulationListener l) {
    listener = l;
  }

  public void initialization(){
    this.amount=listener.labelString(listener.getAnswerAmount());
    this.interestRate=listener.labelString(listener.getAnswerInitialRate());
    this.duration=listener.labelString(listener.getAnswerTime())*12;
  }

  public double calculateMonthlyPayment(double i){


    double numerator;
    double denominator;

    numerator= amount*((i/100)/12);
    denominator= (1-(1/(Math.pow((1+((i/100)/12)),duration))));

    monthlyPayment=(numerator/denominator);

    double n =Math.floor(100*monthlyPayment)/100;


    return n;
  }




}
