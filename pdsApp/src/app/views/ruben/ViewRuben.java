package app.views.ruben;

import app.controllers.*;
import app.listeners.*;
import app.models.SpecifRuben;
import app.views.alexandre.FixedRateSimulation;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ViewRuben implements ListenerRuben {

    private ControllerRuben cci;

    private JPanel body;

    private Container cont;

    public static JButton btnAmort;

    public static JButton btnGraphiques;

    Calcul ca;

    public static String firstname;

    public static String lastname;

    public static int typeloan;

    public ViewRuben(ControllerRuben cci, JPanel body, Container cont) {
        this.cci = cci;
        this.body = body;
        this.cont = cont;
        this.ca = ca;
        setIHM();
    }

    public void setIHM() {
        body.removeAll();
        body.setVisible(true);
        homePage();
        cont.revalidate();
        cont.repaint();
    }

    public void homePage() {
        // Add a textfild for research an id of loan
        JTextField numClient = new JTextField(30);
        body.add(numClient);

        btnAmort = new JButton("Tableau d'amortissement");
        btnAmort.addActionListener(new ActionListener() {

            /**
             *
             * requirement to put just integer because we need id loan
             */

            public void actionPerformed(ActionEvent arg0) {

                //If the user put space
                if(numClient.getText().equals(" ")){
                    JOptionPane.showMessageDialog(cont, "ERREUR, veuillez entrer des chiffres", "Erreur", 1);
                    setIHM();
                }
                //if the user put nothing
                else if(numClient.getText().isEmpty()){
                    JOptionPane.showMessageDialog(cont, "ERREUR, veuillez entrer des chiffres, Vous n'avez rien saisi", "Erreur", 1);
                    setIHM();
                }

                else{
                // data recovery of database
                    try{
                        SpecifRuben allCustomer = cci.getInfo(Integer.parseInt(numClient.getText()));
                        // To print name and typr loan
                        firstname = allCustomer.getFirst_name();
                        lastname=allCustomer.getLast_name();
                        typeloan = allCustomer.getId_type_loan();
                        Calcul ca = new Calcul(allCustomer.getAmount(), allCustomer.getRate(), allCustomer.getLength_loan());
                        CalcAm c = new CalcAm(ca.data, ca.number_month, ca.insuranceRate, ca.rate, ca.amount, ca.totalMonthlyPayement, ca.totalInterest, ca.totalinsurance);
                        body.removeAll();
                        body.add(c.pane);
                        body.revalidate();
                        body.repaint();
                    }
                    catch(NumberFormatException e){
                        JOptionPane.showMessageDialog(cont, "Inserer seulement des chiffres"+e, "Erreur", 1);
                        setIHM();

                    }
                }
            }
        });
        btnAmort.setBounds(16, 48, 225, 29);
        body.add(btnAmort);
        btnGraphiques = new JButton("Graphiques");
        btnGraphiques.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // data recovery of database to recovery data to calculate the loan and all data necessary for the client of the bank
                SpecifRuben allCustomer = cci.getInfo(Integer.parseInt(numClient.getText()));
                Calcul ca = new Calcul(allCustomer.getAmount(), allCustomer.getRate(), allCustomer.getLength_loan());
                Chart am = new Chart(ca);
                LineChart lc = new LineChart(ca, "Restant à payer selon les trimestres", "Restant à payer selon les trimestres", ca.data);
                body.removeAll();
                body.add(btnAmort); // Call amortization button and add it
                body.add(am);
                body.add(lc);
                body.revalidate();
                body.repaint();
            }
        });
        btnGraphiques.setBounds(234, 48, 210, 29);
        body.add(btnGraphiques);
    }

    public static String getFirstname() {
        return firstname;
    }
    public static String getLastname() {
            return lastname;
    }

    public static int getTypeloan() {
        return typeloan;
    }



}
