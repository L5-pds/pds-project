package app.controllers;

import app.models.FixedRateSimulation;

public class FixedRateSimulationControllerClient {
    
    private FixedRateSimulation model;
    
    public FixedRateSimulationControllerClient(FixedRateSimulation m) {
        model = m;
    }
    
    public String[] getCreditTypes() {
        // tableau de test
        String[] typesArray = {"Consommation", "Automobile", "Immobilier"};
        return typesArray;
    }
    
}
