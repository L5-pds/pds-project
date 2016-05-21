package app.controllers;
import app.views.simulations.VariableRateSimulationView;

public class VariableRateSimulationController {
	
	private double monthlyPayment;
	private double amount;
	private double interestRate;
	private double duration;
	
	
	public VariableRateSimulationController(VariableRateSimulationView var){
		this.amount=var.labelString(var.getAnswer_amount().getText());
		this.interestRate=var.labelString(var.getAnswer_initial_rate().getText());
		this.duration=var.labelString(var.getAnswer_time().getSelectedItem().toString())*12;
	
		
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
