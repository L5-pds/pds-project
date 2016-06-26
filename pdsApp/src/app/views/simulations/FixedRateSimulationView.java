package app.views.simulations;

import app.controllers.FixedRateSimulationControllerClient;
import app.models.Customer;
import app.models.Insurance;
import app.models.LoanType;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class FixedRateSimulationView {

    // JPanel that contains all the components
    private JPanel panel;
    
    // components of the simulation frame
    private JButton btnCreateSimulation;
    private JButton btnSearchSimulation;
    private JTextField txtFieldCustomer;
    private JButton btnSearch;
    private JComboBox cbCustomer;
    private JComboBox cbLoanType = null;
    private JComboBox cbInsurance;
    private JLabel lblTotalRate;
    private JTextField txtFieldRate;
    private JButton btnSimulate;
    private JButton btnCancel;
    private JTextField txtFieldWording;
    private JButton btnSave;
    private JTable tblSimulations;
    private DefaultTableModel mdlSimulations;
    private JButton btnNewSimulationModel;
    private JButton btnEditSimulation;
    private JTextField txtFieldAmount;
    private JTextField txtFieldDuration;
    private JButton btnBack;
    
    // GridBagConstraints to set the components locations
    private GridBagConstraints gc;

    // controller for the fixed rate credit simulation
    private FixedRateSimulationControllerClient controller;

    private String mode; // defines if the user is searching a simulation or creating a simulation
    private String simulation_mode; // defines if the user wants to create a new simulation, update a simulation or create new a simulation based on another
    
    public FixedRateSimulationView(FixedRateSimulationControllerClient c, JPanel p) {
        // set the panel and its layout
        panel = p;
        
        // assign a controller to the view
        controller = c;
        
        // configure the GridBagConstraints
        gc = new GridBagConstraints();
        gc.weightx = 0;
        gc.weighty = 1;
        gc.insets = new Insets(10, 10, 10, 10);
        gc.anchor = GridBagConstraints.LINE_START;
        
        // display the customer search interface
        // prepare the JPanel to the addition of the components
        panel.setVisible(false);
        panel.removeAll();
        panel.setLayout(new GridBagLayout());
        
        // initialisation of the components
        cbCustomer = new JComboBox();
        txtFieldCustomer = new JTextField(20);
        btnSearch = new JButton("Rechercher");
        btnSearch.addActionListener(new BtnSearchListener());
        
        // add components to the panel using the GridBagLayout and GridBagConstraints
        //gc.anchor = GridBagConstraints.LINE_START;

        gc.gridx = 0;
        gc.gridy = 0;
        panel.add(new JLabel("Nom du client : "), gc);

        gc.gridx = 1;
        gc.gridy = 0;
        panel.add(txtFieldCustomer, gc);
        
        gc.gridx = 1;
        gc.gridy = 1;
        panel.add(btnSearch, gc);
        
        // perform the operations needed after the removal and the addition of components
        panel.revalidate();
        panel.repaint();
        
        // display the JPanel
        panel.setVisible(true);
    }
    
    public void displayCustomers() {
        boolean found;

        // prepare the JPanel to the addition of the components
        panel.setVisible(false);
        panel.removeAll();

        // remove the action listener to avoid events being triggered while the JComboBox is being filled
        for(ItemListener il : cbCustomer.getItemListeners()) {
            cbCustomer.removeItemListener(il);
        }

        // clear the results of the last search
        cbCustomer.removeAllItems();
        
        // initialisation of the components
        ArrayList<Customer> customersList = controller.getCustomers(txtFieldCustomer.getText());
        for (Customer c : customersList) {
            cbCustomer.addItem(c);
        }
        
        // check if customers were found
        found = !customersList.isEmpty();
        
        if (found) {
            cbCustomer.insertItemAt("", 0); // add blank first item in JComboBox
            cbCustomer.setSelectedIndex(0); // select the JComboBox blank field
            cbCustomer.addItemListener(new CbCustomerItemListener());
        }
        
        // add components to the panel using the GridBagLayout and GridBagConstraints
        //gc.anchor = GridBagConstraints.LINE_START;

        gc.gridx = 0;
        gc.gridy = 0;
        panel.add(new JLabel("Nom du client : "), gc);

        gc.gridx = 1;
        gc.gridy = 0;
        panel.add(txtFieldCustomer, gc);
        
        gc.gridx = 1;
        gc.gridy = 1;
        panel.add(btnSearch, gc);
        
        if (found) {
            gc.gridwidth = 2;
            gc.gridx = 0;
            gc.gridy = 2;
            panel.add(cbCustomer, gc);
            gc.gridwidth = 1;
        }
        else {
            JOptionPane.showMessageDialog(null,"Aucun client trouvé");
        }
        
        // perform the operations needed after the removal and the addition of components
        panel.revalidate();
        panel.repaint();
        
        // display the JPanel
        panel.setVisible(true);
    }
    
    public void displayMenu() {
        // prepare the JPanel to the addition of the components
        panel.setVisible(false);
        panel.removeAll();
        
        // initialisation of the components
        btnSearchSimulation = new JButton("Consulter une simulation");
        btnSearchSimulation.addActionListener(new BtnSearchSimulationListener());
        btnCreateSimulation = new JButton("Nouvelle simulation");
        btnCreateSimulation.addActionListener(new BtnCreateSimulationListener());
        
        // add components to the panel using the GridBagLayout and GridBagConstraints
        //gc.anchor = GridBagConstraints.LINE_START;

        gc.gridx = 0;
        gc.gridy = 0;
        panel.add(new JLabel("Client : " + controller.getFirstName() + " " + controller.getLastName()), gc);
        
        gc.gridx = 0;
        gc.gridy = 1;
        panel.add(btnCreateSimulation, gc);
        
        gc.gridx = 0;
        gc.gridy = 2;
        panel.add(btnSearchSimulation, gc);
        
        // perform the operations needed after the removal and the addition of components
        panel.revalidate();
        panel.repaint();
        
        // display the JPanel
        panel.setVisible(true);
    }
    
    public void displayCustomerSimulations() {
        // get the chosen customer's simulations
        mdlSimulations = controller.getSimulations(controller.getCustomerId(), controller.getLoanTypeId());
        
        // check if some simulations were found for the chosen customer
        boolean found = (mdlSimulations.getColumnCount() != 0);
        
        panel.setVisible(false);
        
        // if some simulations are found
        panel.removeAll();

        // initialisation of the components
        
        if (found) {
            tblSimulations = new JTable(mdlSimulations); // initialise the JTable with a DefaultTableModel given by the controller
            tblSimulations.removeColumn(tblSimulations.getColumn("Num"));
        }
        
        // add components to the panel using the GridBagLayout and GridBagConstraints
        //gc.anchor = GridBagConstraints.LINE_START;

        gc.gridx = 0;
        gc.gridy = 0;
        panel.add(new JLabel("Client : " + controller.getFirstName() + " " + controller.getLastName()), gc);

        gc.gridx = 0;
        gc.gridy = 1;
        panel.add(new JLabel("Type de prêt :"), gc);

        gc.gridx = 1;
        gc.gridy = 1;
        panel.add(cbLoanType, gc);
        
        // if some simulations are found
        if (found) {
            gc.gridwidth = 2;
            gc.gridx = 0;
            gc.gridy = 2;
            panel.add(new JScrollPane(tblSimulations), gc);
            gc.gridwidth = 1;

            btnNewSimulationModel = new JButton("Nouvelle simulation sur ce modèle");
            btnNewSimulationModel.addActionListener(new BtnNewSimulationModelListener());
            gc.gridx = 0;
            gc.gridy = 3;
            panel.add(btnNewSimulationModel, gc);
            
            btnEditSimulation = new JButton("Modifier cette simulation");
            btnEditSimulation.addActionListener(new BtnEditSimulationListener());
            gc.gridx = 1;
            gc.gridy = 3;
            panel.add(btnEditSimulation, gc);
            
            gc.gridx = 2;
            gc.gridy = 3;
        }
        else {
            gc.gridx = 1;
            gc.gridy = 2;
        }
        
        panel.add(btnCancel, gc);

        // perform the operations needed after the removal and the addition of components
        panel.revalidate();
        panel.repaint();
        
        // display the JPanel
        panel.setVisible(true);
        
        if (!found) {
            JOptionPane.showMessageDialog(null,"Aucune simulation de type \"" + controller.getLoanTypeWording() + "\" pour ce client" );
        }
    }
        
    public void displayLoanTypes() {
        // prepare the JPanel to the addition of the components
        panel.setVisible(false);
        panel.removeAll();

        // initialisation of the components
        // fill the JComboBox with the loan types list
        cbLoanType = new JComboBox();
        ArrayList<LoanType> loanTypesList = controller.getLoanTypes();
        System.out.println("lts :");
        for (LoanType lt : loanTypesList) {
            System.out.println("lt : " + lt.getId() + " " + lt.getWording() + " " + lt.getRate());
            cbLoanType.addItem(lt);
        }
        cbLoanType.insertItemAt("", 0); // add blank first item in JComboBox
        cbLoanType.setSelectedIndex(0); // select the JComboBox blank field
        
        btnCancel = new JButton("Annuler");

        // add the action listeners to the components
        cbLoanType.addItemListener(new cbLoanTypeItemListener());
        btnCancel.addActionListener(new BtnCancelListener());

        // add components to the panel using the GridBagLayout and GridBagConstraints
        //gc.anchor = GridBagConstraints.LINE_START;
        
        gc.gridx = 0;
        gc.gridy = 0;
        panel.add(new JLabel("Client : " + controller.getFirstName() + " " + controller.getLastName()), gc);

        gc.gridx = 0;
        gc.gridy = 1;
        panel.add(new JLabel("Type de prêt :"), gc);

        gc.gridx = 1;
        gc.gridy = 1;
        panel.add(cbLoanType, gc);

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
        panel.removeAll();
        
        // initialisation of the new components
        txtFieldAmount = new JTextField(10);
        //txtFieldAmount.addActionListener(new ());
        txtFieldDuration = new JTextField(10);
        //txtFieldDuration.addActionListener(new ())
        
        txtFieldRate = new JTextField(5);
        btnSimulate = new JButton("Simuler");
        lblTotalRate = new JLabel("tauxtal");
        
        cbInsurance = new JComboBox();
        ArrayList<Insurance> insurancesList = controller.getInsurances(controller.getLoanType().getId());
        for (Insurance ins : insurancesList) {
            cbInsurance.addItem(ins);
        }
        
        // if it is a simulation from scratch
        if (simulation_mode.equals("new_simulation_scratch")) {
            cbInsurance.insertItemAt("", 0); // add blank first item in JComboBox
            cbInsurance.setSelectedIndex(0); // select the JComboBox blank field
        }
        // if the simulation is based on another one, select the chosen insurance in the combobox
        else {
            ComboBoxModel cbInsuranceModel = cbInsurance.getModel();
            int size = cbInsuranceModel.getSize();
            int index = 0;
            Insurance ins;
            for(int i=0;i<size;i++) {
                System.out.println("id cb : " + ((Insurance)cbInsuranceModel.getElementAt(i)).getId());
                System.out.println("id modele : " + controller.getInsuranceId());
                System.out.println("index : " + i);
                System.out.println("");
                if (((Insurance)cbInsuranceModel.getElementAt(i)).getId() == controller.getInsuranceId()) {
                    index = i;
                }
            }
            System.out.println("");
            System.out.println("index : " + index);
            cbInsurance.setSelectedItem(3);
        }
        
        // add the action listeners to the components
        btnSimulate.addActionListener(new BtnSimulateListener());

        // case of a simulation modification or a new simulation based on another one : fill the fields with the old simulation values
        if (simulation_mode.equals("new_simulation_model") || simulation_mode.equals("edit_simulation")) {
            txtFieldRate.setText(String.valueOf(controller.getInterestRate()));
            txtFieldAmount.setText(String.valueOf(controller.getAmount()));
            txtFieldDuration.setText(String.valueOf(controller.getDuration()));
        }
            
        // add components to the panel using the GridBagLayout and GridBagConstraints
        //gc.anchor = GridBagConstraints.LINE_START;
        
        gc.gridx = 0;
        gc.gridy = 0;
        panel.add(new JLabel("Client : " + controller.getFirstName() + " " + controller.getLastName()), gc);

        gc.gridx = 0;
        gc.gridy = 1;
        panel.add(new JLabel("Type de prêt :"), gc);

        gc.gridx = 1;
        gc.gridy = 1;
        cbLoanType.setEnabled(false);
        panel.add(cbLoanType, gc);
        
        gc.gridx = 0;
        gc.gridy = 2;
        panel.add(new JLabel("Assurance :"), gc);

        gc.gridx = 1;
        gc.gridy = 2;
        panel.add(cbInsurance, gc);
        
        gc.gridx = 0;
        gc.gridy = 3;
        panel.add(new JLabel("Taux d'intérêt base :"), gc);
        
        gc.gridx = 1;
        gc.gridy = 3;
        panel.add(new JLabel(Double.toString(controller.getBaseRate())), gc);
        
        gc.gridx = 0;
        gc.gridy = 4;
        panel.add(new JLabel("Taux d'intérêt :"), gc);
        
        gc.gridx = 1;
        gc.gridy = 4;
        panel.add(txtFieldRate, gc);
        
        gc.gridx = 0;
        gc.gridy = 5;
        panel.add(new JLabel("TEG : "), gc);
        
        gc.gridx = 1;
        gc.gridy = 5;
        panel.add(lblTotalRate, gc);
        
        gc.gridx = 0;
        gc.gridy = 6;
        panel.add(new JLabel("Montant (" + controller.getMinAmount() + "€ - " + controller.getMaxAmount() + "€) :"), gc);
        
        gc.gridx = 1;
        gc.gridy = 6;
        panel.add(txtFieldAmount, gc);
        
        gc.gridx = 0;
        gc.gridy = 7;
        panel.add(new JLabel("Durée (" + controller.getMinLength() + " mois - " + controller.getMaxLength() + " mois) :"), gc);
        
        gc.gridx = 1;
        gc.gridy = 7;
        panel.add(txtFieldDuration, gc);
        
        gc.gridx = 0;
        gc.gridy = 8;
        panel.add(btnSimulate, gc);
        
        gc.gridx = 1;
        gc.gridy = 8;
        panel.add(btnCancel, gc);
        
        // perform the operations needed after the removal and the addition of components
        panel.revalidate();
        panel.repaint();
        
        // display the JPanel
        panel.setVisible(true);
    }
    
    // display the simulation result
    public void displayResult() {
        // hide the JPanel
        panel.setVisible(false);

        // remove components from the JPanel
        panel.removeAll();
        
        // initialisation of new components
        btnSave = new JButton("Sauvegarder");
        btnSave.addActionListener(new BtnSaveListener());
        txtFieldWording = new JTextField(40);
        if (simulation_mode.equals("edit_simulation")) {
            txtFieldWording.setText(controller.getLoanWording());
        }
        
        // add components to the panel using the GridBagLayout and GridBagConstraints
        //gc.anchor = GridBagConstraints.LINE_START;
        
        gc.gridx = 0;
        gc.gridy = 0;
        panel.add(new JLabel("Client : " + controller.getFirstName() + " " + controller.getLastName()), gc);
        
        gc.gridx = 0;
        gc.gridy = 1;
        panel.add(new JLabel("Type de prêt : "), gc);
        
        gc.gridx = 1;
        gc.gridy = 1;
        panel.add(new JLabel(controller.getLoanTypeWording()), gc);
        
        gc.gridx = 0;
        gc.gridy = 2;
        panel.add(new JLabel("Assurance : "), gc);
        
        gc.gridx = 1;
        gc.gridy = 2;
        panel.add(new JLabel(controller.getInsuranceWording()), gc);
        
        gc.gridx = 0;
        gc.gridy = 3;
        panel.add(new JLabel("Montant : "), gc);
        
        gc.gridx = 1;
        gc.gridy = 3;
        panel.add(new JLabel(controller.getAmount() + " €"), gc);
        
        gc.gridx = 0;
        gc.gridy = 4;
        panel.add(new JLabel("Durée : "), gc);
        
        gc.gridx = 1;
        gc.gridy = 4;
        panel.add(new JLabel(controller.getDuration() + " mois"), gc);
        
        gc.gridx = 0;
        gc.gridy = 5;
        panel.add(new JLabel("Taux d'intérêt : "), gc);
        
        gc.gridx = 1;
        gc.gridy = 5;
        panel.add(new JLabel(controller.getInterestRate() + " %"), gc);
        
        gc.gridx = 0;
        gc.gridy = 6;
        panel.add(new JLabel("Taux assurance : "), gc);
        
        gc.gridx = 1;
        gc.gridy = 6;
        panel.add(new JLabel(controller.getInsuranceRate() + " %"), gc);
        
        DecimalFormat df = new DecimalFormat("#.##"); // to format amounts display
        
        gc.gridx = 0;
        gc.gridy = 7;
        panel.add(new JLabel("TEG : "), gc);
        
        gc.gridx = 1;
        gc.gridy = 7;
        panel.add(new JLabel(df.format(controller.getTotalRate()) + " %"), gc);
        
        gc.gridx = 0;
        gc.gridy = 8;
        panel.add(new JLabel("Montant des mensualités : "), gc);
        
        gc.gridx = 1;
        gc.gridy = 8;
        panel.add(new JLabel(df.format(controller.getMonthlyPayment()) + " €"), gc);
        
        gc.gridx = 0;
        gc.gridy = 9;
        panel.add(new JLabel("Montant total dû : "), gc);
        
        gc.gridx = 1;
        gc.gridy = 9;
        panel.add(new JLabel(df.format(controller.getOwedAmount()) + " €"), gc);
        
        gc.gridx = 0;
        gc.gridy = 10;
        panel.add(new JLabel("Libellé du prêt :"), gc);
        
        gc.gridwidth = 2;
        gc.gridx = 0;
        gc.gridy = 11;
        panel.add(txtFieldWording, gc);
        
        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 12;
        panel.add(btnSave, gc);
        
        gc.gridx = 1;
        gc.gridy = 12;
        //panel.add(btnNewSimulation, gc);
        panel.add(btnCreateSimulation, gc);
        
        btnBack = new JButton("Retour");
        btnBack.addActionListener(new BtnBackListener());
        gc.gridx = 2;
        gc.gridy = 12;
        panel.add(btnBack, gc);
        
        // perform the operations needed after the removal and the addition of components
        panel.revalidate();
        panel.repaint();
        
        // display the JPanel
        panel.setVisible(true);
    }
    
    public boolean checkJTableSelection() {
        return tblSimulations.getSelectedRowCount() == 1;
    }
    
    // ######################
    // inner listener classes
    // ######################
    
    // item listener for the btnSearchSimulation JButton
    class BtnSearchSimulationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mode = "search";
            displayLoanTypes();
        }
    }
    
    // item listener for the btnCreateSimulation JButton
    class BtnCreateSimulationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mode = "simulate";
            simulation_mode = "new_simulation_scratch";
            controller.resetModel(false);
            displayLoanTypes();
        }
    }
    
    // item listener for the cbLoanType JComboBox
    class cbLoanTypeItemListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            // perform action when an item from the JComboBox is selected
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (!cbLoanType.getSelectedItem().toString().isEmpty()) {
                    LoanType lt = (LoanType) cbLoanType.getSelectedItem();
                    // case when the user wants to display a customer's loans
                    if (mode.equals("search")) {
                        controller.setLoanType(lt);
                        displayCustomerSimulations();
                    }
                    // case when the user wants to simulate a loan
                    else if (mode.equals("simulate")) {
                        controller.setLoanType(lt);
                        displayForm();
                    }
                    else {
                        System.out.println("Sélection de type de prêt : mode \"" + mode + "\" invalide");
                        System.exit(1);
                    }
                }
            }
        }
    }
    
    // item listener for the cbCustomer JComboBox
    class CbCustomerItemListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            // perform action when an item from the JComboBox is selected
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Customer c = (Customer) cbCustomer.getSelectedItem();
                controller.setCustomer(c);
                displayMenu();
            }
        }
    }

    // listener for btnSeach JButton
    class BtnSearchListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            displayCustomers();
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
            String error = "Erreur :";
            boolean ok = true;
            
            if (cbInsurance.getSelectedItem().toString().isEmpty()) {
                ok = false;
                error += "\nVous devez sélectionner une assurance";
            }
            if(!txtFieldRate.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
                ok = false;
                error += "\nTaux incorrect";
            }
            if(txtFieldAmount.getText().matches("\\d+")) {
                if (Integer.parseInt(txtFieldAmount.getText()) < controller.getMinAmount()) {
                    ok = false;
                    error += "\nMontant trop petit";
                }
                else if (Integer.parseInt(txtFieldAmount.getText()) > controller.getMaxAmount()) {
                    ok = false;
                    error += "\nMontant trop élevé";
                }
            }
            else {
                ok = false;
                error += "\nMontant incorrect";
            }
            if(txtFieldDuration.getText().matches("\\d+")) {
                if (Integer.parseInt(txtFieldDuration.getText()) < controller.getMinLength()) {
                    ok = false;
                    error += "\nDurée trop courte";
                }
                else if (Integer.parseInt(txtFieldDuration.getText()) > controller.getMaxLength()) {
                    ok = false;
                    error += "\nDurée trop élevée";
                }
            }
            else {
                ok = false;
                error += "\nDurée incorrecte";
            }
            
            if (ok) {
                controller.setInsurance((Insurance)cbInsurance.getSelectedItem());
                int duration, amount;
                controller.setInterestRate(Double.parseDouble(txtFieldRate.getText()));
                duration = Integer.parseInt(txtFieldDuration.getText());
                controller.setDuration(duration);
                amount = Integer.parseInt(txtFieldAmount.getText());
                controller.setAmount(amount);

                controller.calculateLoan();
                displayResult();
            }
            else {
                JOptionPane.showMessageDialog(null,error);
            }
        }
    }
    
    // listener for btnSave JButton
    class BtnSaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.setLoanWording(txtFieldWording.getText());
            if (simulation_mode.equals("edit_simulation")) {
                if (controller.updateLoanSimulation()) {
                    JOptionPane.showMessageDialog(panel, "Le prêt a été mis à jour", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    btnSave.setEnabled(false);
                }
                else {
                    JOptionPane.showMessageDialog(panel, "Echec de la mise à jour du prêt", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                if (controller.saveLoanSimulation()) {
                    JOptionPane.showMessageDialog(panel, "Le prêt a été sauvegardé", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    btnSave.setEnabled(false);
                }
                else {
                    JOptionPane.showMessageDialog(panel, "Echec de la sauvegarde du prêt", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    // listener for btnBack JButton
     class BtnBackListener implements ActionListener {
         public void actionPerformed(ActionEvent e) {
             controller.resetModel(false);
             displayMenu();
         }
     }
     
    // listener for the btnEditSimulation
    class BtnEditSimulationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (checkJTableSelection()) {
                simulation_mode = "edit_simulation";
                controller.selectSimulation((Integer)mdlSimulations.getValueAt(tblSimulations.getSelectedRow(),0));
                displayForm();
            }
            else {
                JOptionPane.showMessageDialog(null,"Vous devez sélectionner une et une seule simulation");
            }
        }
         
    }
    // listener for the btnNewSimulationModel
    class BtnNewSimulationModelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("début");
            if (checkJTableSelection()) {
                simulation_mode = "new_simulation_model";
                System.out.println("Index du pret selectionné : " + (Integer)mdlSimulations.getValueAt(tblSimulations.getSelectedRow(),0) + ", ID : " + (Integer)mdlSimulations.getValueAt(tblSimulations.getSelectedRow(),1));
                controller.selectSimulation((Integer)mdlSimulations.getValueAt(tblSimulations.getSelectedRow(),0));
                //try {Thread.sleep(3000);} catch(Exception ex) {}
                displayForm();
            }
            else {
                JOptionPane.showMessageDialog(null,"Vous devez sélectionner une et une seule simulation");
            }
        }
         
    }
}
