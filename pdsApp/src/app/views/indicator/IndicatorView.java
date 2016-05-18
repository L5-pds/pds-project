package app.views.indicator;

import app.controllers.*;
import app.listeners.*;
import app.views.welcome.*;
import java.awt.Color;
import java.awt.Container;
import javax.swing.*;

public class IndicatorView implements ListenerClientIndicator {
    private ControllerClientIndicator cci;
    private JPanel body;
    private Container cont;
    
    public IndicatorView(ControllerClientIndicator cci, JPanel body, Container cont) {
      this.cci = cci;
      this.body = body;
      this.cont = cont;
    }

    public void setIHM() {
        body.removeAll();
        
        JButton button5 = new javax.swing.JButton();
        button5.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
        button5.setText("test_5");
        button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cci.testCountAdress();
            }
        });
        body.setBackground(new Color(215,203,233,200));
        body.add(button5);
        body.revalidate();
        body.repaint();
        
        cont.revalidate();
        cont.repaint();
    }
    
}
