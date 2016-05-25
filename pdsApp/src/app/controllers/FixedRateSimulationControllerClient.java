package app.controllers;

import app.helpers.Serialization;
import app.models.FixedRateSimulation;
import app.models.Insurance;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

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
    
    // returns an array containing the loan types
    public ArrayList<String> getLoanTypes() {
        String query = "FIXEDRATE/GetLoanTypes/."; // SQL query to get the loan types
        ArrayList<String> loanTypesList = null;
        
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
                loanTypesList = serializer.unserializeArrayList(splitAnswer[1]); // unserialize the loan types array
            }
            else {
                System.out.println("Erreur, réponse du serveur incorrecte");
            }
            
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        
        return loanTypesList;
    }

    public ArrayList<Insurance> getInsurances(String loanType) {
        String query = "FIXEDRATE/GetInsurances/" + loanType; // SQL query to get the insurances
        ArrayList<Insurance> insurances = null;
        
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
                insurances = serializer.unserializeInsuranceArrayList(splitAnswer[1]);
            }
            else {
                System.out.println("Erreur, réponse du serveur incorrecte");
            }
            
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        
        return insurances;
    }
    
}
