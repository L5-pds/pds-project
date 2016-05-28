package app.controllers;

import app.helpers.Serialization;
import app.models.FixedRateSimulation;
import app.models.Insurance;
import app.models.LoanType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class FixedRateSimulationControllerClient {
    
    private FixedRateSimulation model;
    
    private static Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Serialization serializer;
    
    public FixedRateSimulationControllerClient(FixedRateSimulation m, Socket s) {
        model = m;
        socket = s;
        serializer = new Serialization();
    }
    
    public ArrayList<LoanType> getLoanTypes() {
        String query = "FIXEDRATE/GetLoanTypes/."; // SQL query to get the loan types
        ArrayList<LoanType> loanTypes = null;
        
        try {
            // streams opening
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // ask the server for the loan types
            out.println(query);
            out.flush();
            
            // get the server answer and interpret it
            String answer = in.readLine();
            String[] splitAnswer = answer.split("/");
            if (splitAnswer[0].equals("SUCCESS")) {
                loanTypes = serializer.unserializeLoanTypeArrayList(splitAnswer[1]);
            }
            else {
                System.out.println("Erreur, rÃ©ponse du serveur incorrecte");
            }
            
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        
        return loanTypes;
    }

    public ArrayList<Insurance> getInsurances(int loanTypeId) {
        String query = "FIXEDRATE/GetInsurances/" + loanTypeId; // SQL query to get the insurances
        ArrayList<Insurance> insurances = null;
        
        try {
            // streams opening
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // ask the server for the insurances
            out.println(query);
            out.flush();
            
            // get the server answer and interpret it
            String answer = in.readLine();
            String[] splitAnswer = answer.split("/");
            if (splitAnswer[0].equals("SUCCESS")) {
                insurances = serializer.unserializeInsuranceArrayList(splitAnswer[1]);
            }
            else {
                System.out.println("Erreur, rÃ©ponse du serveur incorrecte");
            }
            
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        
        return insurances;
    }
    
    public void calculateLoan() {
        String query = "FIXEDRATE/CalculateLoan/" + serializer.serializeFixedRateSimulation(model);
        FixedRateSimulation simulation;

        try {
            // streams opening
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // send the simulation data to the server and ask for the monthly payment
            out.println(query);
            out.flush();
            
            // get the server answer and interpret it
            String answer = in.readLine();
            String[] splitAnswer = answer.split("/");
            if (splitAnswer[0].equals("SUCCESS")) {
                simulation = serializer.unserializeFixedRateSimulation(splitAnswer[1]);
                model = simulation;
            }
            else {
                System.out.println("Erreur, réponse du serveur incorrecte");
            }
            
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
    
    public void setLoanType(LoanType lt) {
        model.setLoanType(lt);
    }
    
    public void setInsurance(Insurance ins) {
        model.setInsurance(ins);
    }
    
    public double getBaseRate() {
        return model.getLoanType().getRate();
    }
    
    public double getInsuranceRate() {
        return model.getInsurance().getRate();
    }
    
    public LoanType getLoanType() {
        return model.getLoanType();
    }
    
    public int getMinAmount() {
        return model.getLoanType().getMinAmount();
    }
    
    public int getMaxAmount() {
        return model.getLoanType().getMaxAmount();
    }
    
    public int getMinLength() {
        return model.getLoanType().getMinLength();
    }
    
    public int getMaxLength() {
        return model.getLoanType().getMaxLength();
    }
    
    public void setInsuranceRate(double rate) {
        model.getInsurance().setRate(rate);
    }
    
    public void setDuration(int duration) {
        model.setDuration(duration);
    }
    
    public void setAmount(int amount) {
        model.setAmount(amount);
    }
    
    public void setInterestRate(double rate) {
        model.setInterestRate(rate);
    }
    
    public double getMonthlyPayment() {
        return model.getMonthlyPayment();
    }
}
