package app.controllers;

import app.helpers.Serialization;
import app.models.Customer;
import app.models.FixedRateSimulation;
import app.models.Insurance;
import app.models.LoanType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class FixedRateSimulationControllerClient {
    
    //public long startTime;
    
    private FixedRateSimulation model;
    private ArrayList<FixedRateSimulation> customerSimulations;
    
    private static Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Serialization serializer;
    
    public FixedRateSimulationControllerClient(FixedRateSimulation m, Socket s) {
        model = m;
        socket = s;
        serializer = new Serialization();
    }
    
    public ArrayList<Customer> getCustomers(String name) {
        String query = "FIXEDRATE/GetCustomers/" + name; // query to get the customers
        ArrayList<Customer> customers = null;
        
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
                customers = serializer.unserializeCustomersArrayList(splitAnswer[1]);
            }
            else {
                System.out.println("Erreur, rÃ©ponse du serveur incorrecte");
            }
            
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        
        return customers;
    }
    
    public ArrayList<LoanType> getLoanTypes() {
        String query = "FIXEDRATE/GetLoanTypes/."; // query to get the loan types
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
        String query = "FIXEDRATE/GetInsurances/" + loanTypeId; // query to get the insurances
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
    
    public boolean saveLoanSimulation() {
        boolean success = false;
        String query = "FIXEDRATE/SaveLoan/" + serializer.serializeFixedRateSimulation(model);
        
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
            String result = splitAnswer[0];
            switch (result) {
                case "SUCCESS":
                    success = true;
                    break;
                case "FAILURE":
                    System.out.println("Echec de la sauvegarde du prêt");
                    break;
                default:
                    System.out.println("Erreur, réponse du serveur incorrecte");
                    break;
            }
            
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        
        return success;
    }
    
    public DefaultTableModel getSimulations(int customerId, int loanTypeId) {
        String query = "FIXEDRATE/GetSimulations/" + customerId + "/" + loanTypeId;
        DefaultTableModel mdlSimulations = new DefaultTableModel() {
            // prevent the modification of the DefaultTableModel cells
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        customerSimulations = null;

        try {
            // streams opening
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // send the customer id to the server
            out.println(query);
            out.flush();
            
            // get the server answer and interpret it
            String answer = in.readLine();
            System.out.println("answer : " + answer);
            String[] splitAnswer = answer.split("/");
            if (splitAnswer[0].equals("SUCCESS")) {
                customerSimulations = serializer.unserializeFixedRateSimulationArrayList(splitAnswer[1]);
            }
            else {
                System.out.println("Erreur, réponse du serveur incorrecte");
            }
            
            System.out.println("simulations :");
            for (FixedRateSimulation frs : customerSimulations) {
                System.out.println(frs.getId() + " " + frs.getInterestRate() + " "+ frs.getWording() + " " + frs.getInsurance().getInsuranceId() + " " + frs.getInsurance().getWording());
            }
            
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        
        if (!customerSimulations.isEmpty()) {
            // add attributes of a loan as columns of the DefaultTableModel
            mdlSimulations.addColumn("Num");
            mdlSimulations.addColumn("ID");
            mdlSimulations.addColumn("Montant");
            mdlSimulations.addColumn("Durée");
            mdlSimulations.addColumn("Taux");
            mdlSimulations.addColumn("Mensualité");
            mdlSimulations.addColumn("Montant dû");
            mdlSimulations.addColumn("Libellé");

            DecimalFormat df = new DecimalFormat("#.##"); // to format amounts display
            
            // add simulations data to the DefaultTableModel
            for (FixedRateSimulation s : customerSimulations) {
                mdlSimulations.addRow(new Object[]{customerSimulations.indexOf(s),s.getId(),s.getAmount(),s.getDuration(),s.getTotalRate(),df.format(s.getMonthlyPayment()),df.format(s.getOwedAmount()),s.getWording()});
            }
        }
        
        return mdlSimulations;
    }
    
    public void selectSimulation(int index) {
        Customer c;
        System.out.println("nombre de simu : " + customerSimulations.size());
        System.out.println("affichage des simu :");
        for (FixedRateSimulation s : customerSimulations) {
            System.out.println("1 simulation");
        }
        c = model.getCustomer();
        model = customerSimulations.get(index);
        model.setCustomer(c);
        customerSimulations = null;
    }
    
    public void resetModel(boolean resetCustomer) {
        Customer c = model.getCustomer();
        model = new FixedRateSimulation();
        if (!resetCustomer) {
            model.setCustomer(c);
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
    
    public void setCustomer(Customer c) {
        model.setCustomer(c);
    } 
    
    public void setInterestRate(double rate) {
        model.setInterestRate(rate);
    }
    
    public double getMonthlyPayment() {
        return model.getMonthlyPayment();
    }
    
    public String getLoanTypeWording() {
        return model.getLoanType().getWording();
    }
    
    public String getInsuranceWording() {
        return model.getInsurance().getWording();
    }
    
    public int getAmount() {
        return model.getAmount();
    }
    
    public int getDuration() {
        return model.getDuration();
    }
    
    public double getInterestRate() {
        return model.getInterestRate();
    }
    
    public void setLoanWording(String w) {
        model.setLoanWording(w);
    }
    
    public String getLoanWording() {
        return model.getWording();
    }
    
    public Insurance getInsurance() {
        return model.getInsurance();
    }
    
    public int getInsuranceId() {
        return model.getInsurance().getId();
    }
    
    public String getFirstName() {
        return model.getCustomer().getFirstName();
    }
    
    public String getLastName() {
        return model.getCustomer().getLastName();
    }
    
    public int getCustomerId() {
        return model.getCustomer().getId();
    }
    
    public int getLoanTypeId() {
        return model.getLoanType().getId();
    }
    
    public double getTotalRate() {
        return model.getTotalRate();
    }
    
    public double getOwedAmount() {
        return model.getOwedAmount();
    }
}
