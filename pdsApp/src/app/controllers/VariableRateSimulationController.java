package app.controllers;
import app.views.simulations.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import app.helpers.Serialization;
import app.listeners.VariableRateSimulationListener;

public class VariableRateSimulationController {

  private double monthlyPayment;
  private double amount;
  private double interestRate;
  private double duration;

  private VariableRateSimulationListener listener;

  private static Socket socket;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private Serialization s;  
  
  public VariableRateSimulationController(Socket socket){
	  this.socket = socket;
      this.s = new Serialization();
      try {
        out = new PrintWriter(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null,"Le message est : " + e.getMessage());
      }
  }

  public void addListener(VariableRateSimulationListener l) {
    listener = l;
    listener.setIHM();
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
