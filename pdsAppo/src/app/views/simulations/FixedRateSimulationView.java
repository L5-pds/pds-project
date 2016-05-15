package app.views.simulations;

import app.controllers.FixedRateSimulationControllerClient;
import app.models.FixedRateSimulation;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;

public class FixedRateSimulationView extends JFrame implements ActionListener {
    
    // components of the simulation frame
    private JButton btnSimulate;
    private JButton btnCancel;
    private JComboBox cbCreditType;
    private JSpinner amount;
    private JLabel baseRate;
    
    // controller for the fixed rate credit simulation
    FixedRateSimulationControllerClient controller;
    
    public FixedRateSimulationView(FixedRateSimulationControllerClient c) {
        // assign a controller to the view
        controller = c;
        
        // JFrame settings
        setSize(500,500);
        //setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // initialisation of the components
        btnCancel = new JButton("Annuler");
        btnSimulate = new JButton("Simuler");
        cbCreditType = new JComboBox(controller.getCreditTypes()); // fill the JComboBox with the credit types list
        
        // add the action listener to the components
        btnCancel.addActionListener(this);
        btnSimulate.addActionListener(this);
        cbCreditType.addActionListener(this);
        
        // disable the components that ha ve to be disabled when the frame is displayed
        btnSimulate.setEnabled(false);
        
        // add components to the JFrame
        getContentPane().add(cbCreditType);
        getContentPane().add(btnSimulate);
        getContentPane().add(btnCancel);
        
        // make the JFrame visible
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        // ajout retour au menu pour btn cancel
    }
}
