package app.views.simulations;

import app.controllers.FixedRateSimulationController;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FixedRateSimulationView extends JFrame {
    
    private JButton btnSimulate;
    private JComboBox cbCreditType;
    private JTextField amount;
    private JLabel baseRate;
    
    FixedRateSimulationController controller;
}
