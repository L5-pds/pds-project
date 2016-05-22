package app.controllers;

import app.helpers.Serialization;
import app.models.FixedRateSimulation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
    
    public String[] getCreditTypes() {
        String[] typesArray = {"Consommation", "Automobile", "Immobilier"};
        
        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        
        return typesArray;
    }
    
}
