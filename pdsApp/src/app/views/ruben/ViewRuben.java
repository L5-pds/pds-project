package app.views.ruben;

import app.controllers.*;
import app.listeners.*;
import app.views.alexandre.FixedRateSimulation;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class ViewRuben implements ListenerRuben {
    
    private ControllerRuben cci;
    private JPanel body;
    private Container cont;
    JButton btnAmort;
    JButton btnGraphiques;
    Calcul ca;

    
    public ViewRuben(ControllerRuben cci, JPanel body, Container cont) {
      this.cci = cci;
      this.body = body;
      this.cont = cont;
      this.ca=ca;
      setIHM();
    }
    
    public void setIHM() {
        body.removeAll();
        body.setVisible(true);
        homePage();
        cont.revalidate();
        cont.repaint();
        
        cci.getInfo(8388);
        
    }

    public void homePage(){
       
        btnAmort = new JButton("Tableau d'amortissement");
            btnAmort.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                Calcul ca = new Calcul();
                CalcAm c = new CalcAm(
                ca.data, 
                ca.number_month,
                ca.insuranceRate,
                ca.rate,
                ca.amount,
                ca.totalMonthlyPayement, 
                ca.totalInterest,
                ca.totalinsurance);
                body.removeAll();
                body.add(c.pane);
                body.revalidate();
                body.repaint();
                }
            });
            
            btnAmort.setBounds(16, 48, 225, 29);
            body.add(btnAmort);
            
            btnGraphiques = new JButton("Graphiques");
            btnGraphiques.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    Calcul ca = new Calcul();
                    Chart am = new Chart(ca);
                    LineChart lc = new LineChart("Restant à payer selon les trimestres", "Restant à payer selon les trimestres", ca.data);
                    body.removeAll();
                    body.add(am);
                    body.add(lc);
                    body.revalidate();
                    body.repaint();
                    }
            });
            btnGraphiques.setBounds(234, 48, 210, 29);
            body.add(btnGraphiques);
    }
}
