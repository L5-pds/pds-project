package app.views.simulations;

import app.controllers.FixedRateSimulationControllerClient;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;

public class FixedRateSimulationView extends JPanel {

    // components of the simulation frame
    private JComboBox cbLoanType;
    private JSpinner amount;
    private JLabel baseRate;
    private JButton btnSimulate;
    private JButton btnOk;
    private JButton btnCancel;

    // controller for the fixed rate credit simulation
    FixedRateSimulationControllerClient controller;

    public FixedRateSimulationView(FixedRateSimulationControllerClient c) {
        // set the panel layout
        super(new GridBagLayout());

        // assign a controller to the view
        controller = c;

        // initialisation of the components
        cbLoanType = new JComboBox(controller.getCreditTypes()); // fill the JComboBox with the credit types list
        btnOk = new JButton("OK");
        btnCancel = new JButton("Annuler");

        // add the action listener to the components
        cbLoanType.addActionListener(new CbLoanTypeListener());
        btnCancel.addActionListener(new BtnCancelListener());

        // add components to the panel using the GridBagLayout and GridBagConstraints
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 0;
        gc.weightx = 1;
        gc.insets = new Insets(10, 10, 10, 10);

        gc.gridx = 0;
        gc.gridy = 0;
        add(new JLabel("Type de prêt :"), gc);

        gc.gridwidth = 2;
        gc.gridx = 1;
        gc.gridy = 0;
        add(cbLoanType, gc);

        gc.gridwidth = 1;
        gc.gridx = 2;
        gc.gridy = 1;
        add(btnCancel, gc);
    }
    
    public void displayForm(String loanType) {
        
        
        // perform the operations needed after the removal and the addition of components
        revalidate();
        repaint();
    }

    /**
     * *** inner listener classes ****
     */
    // listener for the cbLoanTypeListener JComboBox
    class CbLoanTypeListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

        }
    }

    // listener for the btnCancel JButton
    class BtnOkListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

        }
    }

    // listener for the btnCancel JButton
    class BtnCancelListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

        }
    }

    // listener for the btnSimulate JButton
    class BtnSimulateListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

        }
    }

    public static void main(String[] args) {
        javax.swing.JFrame f = new javax.swing.JFrame("test");
        app.models.FixedRateSimulation m = new app.models.FixedRateSimulation();
        FixedRateSimulationControllerClient c = new FixedRateSimulationControllerClient(m);
        FixedRateSimulationView v = new FixedRateSimulationView(c);
        f.add(v);
        f.pack();
        f.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);
    }
}
