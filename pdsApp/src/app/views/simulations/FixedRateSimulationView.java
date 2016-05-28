package app.views.simulations;

import app.controllers.FixedRateSimulationControllerClient;
import app.models.Insurance;
import app.models.LoanType;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FixedRateSimulationView {

    // JPanel that contains all the components
    JPanel panel;
    
    // components of the simulation frame
    private JComboBox cbLoanType;
    private JComboBox cbInsurance;
    private JLabel lblTotalRate;
    private JSpinner spAmount;
    private JSpinner spDuration;
    private JTextField txtFieldRate;
    private JButton btnSimulate;
    private JButton btnCancel;

    // controller for the fixed rate credit simulation
    FixedRateSimulationControllerClient controller;

    public FixedRateSimulationView(FixedRateSimulationControllerClient c, JPanel p) {
        // set the panel layout
        panel = p;
        
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }*/
        
        // assign a controller to the view
        controller = c;
        
        // prepare the JPanel to the addition of the components
        panel.setVisible(false);
        panel.removeAll();
        panel.setLayout(new GridBagLayout());

        // initialisation of the components
        // fill the JComboBox with the loan types list
        cbLoanType = new JComboBox();
        ArrayList<LoanType> loanTypesList = controller.getLoanTypes();
        for (LoanType lt : loanTypesList) {
            cbLoanType.addItem(lt);
        }
        cbLoanType.insertItemAt("", 0); // add blank first item in JComboBox
        cbLoanType.setSelectedIndex(0); // select the JComboBox blank field
        
        btnCancel = new JButton("Annuler");

        // add the action listeners to the components
        cbLoanType.addItemListener(new cbLoanTypeItemListener());
        btnCancel.addActionListener(new BtnCancelListener());

        // add components to the panel using the GridBagLayout and GridBagConstraints
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 0;
        gc.weighty = 1;
        gc.insets = new Insets(10, 10, 10, 10);
        gc.anchor = GridBagConstraints.LINE_START;

        gc.gridx = 0;
        gc.gridy = 0;
        panel.add(new JLabel("Type de prêt :"), gc);

        gc.gridx = 1;
        gc.gridy = 0;
        panel.add(cbLoanType, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        panel.add(btnCancel, gc);
        
        // display the JPanel
        panel.setVisible(true);
    }
    
    public void displayInsurances() {
        // hide the JPanel
        panel.setVisible(false);
        
        // remove components from the JPanel
        panel.remove(btnCancel);
        
        // disable the modification of the loan type
        cbLoanType.setEnabled(false);
        
        // initilisation of the new component
        // fill the JComboBox with the insurances list
        cbInsurance = new JComboBox();
        ArrayList<Insurance> insurancesList = controller.getInsurances(controller.getLoanType().getId());
        for (Insurance ins : insurancesList) {
            cbInsurance.addItem(ins);
        }
        cbInsurance.insertItemAt("", 0); // add blank first item in JComboBox
        cbInsurance.setSelectedIndex(0); // select the JComboBox blank field
        
        // add the action listeners to the components
        cbInsurance.addItemListener(new cbInsuranceItemListener());
        
        // add components to the panel using the GridBagLayout and GridBagConstraints
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 0;
        gc.weighty = 1;
        gc.insets = new Insets(10, 10, 10, 10);
        gc.anchor = GridBagConstraints.LINE_START;

        gc.gridx = 0;
        gc.gridy = 1;
        panel.add(new JLabel("Assurance :"), gc);

        gc.gridx = 1;
        gc.gridy = 1;
        panel.add(cbInsurance, gc);
        
        gc.gridx = 1;
        gc.gridy = 2;
        panel.add(btnCancel, gc);
        
        // perform the operations needed after the removal and the addition of components
        panel.revalidate();
        panel.repaint();

        // display the JPanel
        panel.setVisible(true);
    } 
    
    // display the fixed rate loan simulation form, adapted to the chosen loan type
    public void displayForm() {
        // hide the JPanel
        panel.setVisible(false);

        // remove components from the JPanel
        panel.remove(btnCancel);
        
        // disable the modification of the insurance
        cbInsurance.setEnabled(false);
        
        // initialisation of the new components
        SpinnerNumberModel smAmount = new SpinnerNumberModel(controller.getMinAmount(), controller.getMinAmount(), controller.getMaxAmount(), 100);
        spAmount = new JSpinner(smAmount);
        SpinnerNumberModel smDuration = new SpinnerNumberModel(controller.getMinLength(), controller.getMinLength(), controller.getMaxLength(), 1);
        spDuration = new JSpinner(smDuration);
        txtFieldRate = new JTextField(5);
        double baseRate = controller.getBaseRate();
        txtFieldRate.setText(Double.toString(baseRate));
        txtFieldRate.getDocument().addDocumentListener(new TxtFieldRateDocumentListener());
        btnSimulate = new JButton("Simuler");
        lblTotalRate = new JLabel("tauxtal");
        
        // add the action listeners to the components
        btnSimulate.addActionListener(new BtnSimulateListener());

        // add components to the panel using the GridBagLayout and GridBagConstraints
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 0;
        gc.weighty = 1;
        gc.insets = new Insets(10, 10, 10, 10);
        gc.anchor = GridBagConstraints.LINE_START;
        
        gc.gridx = 0;
        gc.gridy = 2;
        panel.add(new JLabel("Taux d'intérêt base :"), gc);
        
        gc.gridx = 1;
        gc.gridy = 2;
        panel.add(new JLabel(Double.toString(baseRate)), gc);
        
        gc.gridx = 0;
        gc.gridy = 3;
        panel.add(new JLabel("Taux d'intérêt :"), gc);
        
        gc.gridx = 1;
        gc.gridy = 3;
        panel.add(txtFieldRate, gc);
        
        gc.gridx = 0;
        gc.gridy = 4;
        panel.add(new JLabel("TEG : "), gc);
        
        gc.gridx = 1;
        gc.gridy = 4;
        panel.add(lblTotalRate, gc);
        
        gc.gridx = 0;
        gc.gridy = 5;
        panel.add(new JLabel("Montant :"), gc);
        
        gc.gridx = 1;
        gc.gridy = 5;
        panel.add(spAmount, gc);
        
        gc.gridx = 0;
        gc.gridy = 6;
        panel.add(new JLabel("Durée :"), gc);
        
        gc.gridx = 1;
        gc.gridy = 6;
        panel.add(spDuration, gc);
        
        gc.gridx = 0;
        gc.gridy = 7;
        panel.add(btnSimulate, gc);
        
        gc.gridx = 1;
        gc.gridy = 7;
        panel.add(btnCancel, gc);
        
        // perform the operations needed after the removal and the addition of components
        panel.revalidate();
        panel.repaint();
        
        // display the JPanel
        panel.setVisible(true);
    }
    
    public void displayResult() {
        // hide the JPanel
        panel.setVisible(false);

        // remove components from the JPanel
        panel.removeAll();
        
        panel.add(new JLabel("Monthly : " + controller.getMonthlyPayment()));
        
        // perform the operations needed after the removal and the addition of components
        panel.revalidate();
        panel.repaint();
        
        // display the JPanel
        panel.setVisible(true);
    }
    
    public boolean isInteger(String s) {
        return true;
    }
    
    public boolean isFloat(String s) {
        return false;
    }
    
    // ######################
    // inner listener classes
    // ######################
    
    // item listener for the cbLoanType JComboBox
    class cbLoanTypeItemListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            // perform action when an item from the JComboBox is selected
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // get the loan type and display the insurances JComboBox
                if (!cbLoanType.getSelectedItem().toString().isEmpty()) {
                    LoanType lt = (LoanType) cbLoanType.getSelectedItem();
                    controller.setLoanType(lt);
                    displayInsurances();
                }
            }
            
        }
    }

    // item listener for the cbInsurance JComboBox
    class cbInsuranceItemListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            // perform action when an item from the JComboBox is selected
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // get the insurance type and display the loan form
                if (!cbInsurance.getSelectedItem().toString().isEmpty()) {
                    Insurance ins = (Insurance) cbInsurance.getSelectedItem();
                    controller.setInsurance(ins);
                    displayForm();
                }
            }
        }
    }
    
    class TxtFieldRateDocumentListener implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            
        }
        
        public void removeUpdate(DocumentEvent e) {
            
        }
        
        public void insertUpdate(DocumentEvent e) {
            
        }
        
        public void updateTotalRate() {
            //lblTotalRate.setText();
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
            //Integer.parseInt(spDuration.getText());
            //(Integer) spAmount.getValue();
            int duration, amount;
            controller.setInterestRate(Double.parseDouble(txtFieldRate.getText()));
            duration = (Integer) spDuration.getValue();
            controller.setDuration(duration);
            amount = (Integer) spAmount.getValue();
            controller.setAmount(amount);
            
            controller.calculateLoan();
            displayResult();
        }
    }
}
