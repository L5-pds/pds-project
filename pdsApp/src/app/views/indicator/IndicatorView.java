package app.views.indicator;

import app.controllers.*;
import app.listeners.*;
import app.models.component.RoundJTextField;
import app.views.welcome.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        panel.setLayout(new GridLayout(0, 2, 0, 0));

        JPanel panel_2 = new JPanel();
        panel_2.setBackground(Color.YELLOW);
        panel.add(panel_2);

        JPanel panel_3 = new JPanel();
        panel_3.setBackground(Color.BLUE);
        panel.add(panel_3);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.GREEN);
        panel_1.setLayout(new GridLayout(0, 3, 0, 0));

        JPanel panel_4 = new JPanel();
        panel_4.setBackground(Color.PINK);
        panel_1.add(panel_4);

        JPanel panel_5 = new JPanel();
        panel_5.setBackground(Color.MAGENTA);
        panel_1.add(panel_5);

        JPanel panel_6 = new JPanel();
        panel_6.setBackground(Color.ORANGE);
        panel_1.add(panel_6);
        
        body.setLayout(new GridLayout(2, 0, 0, 0));
        body.setBackground(new Color(215,203,233,200));
        body.add(panel);
        body.add(panel_1);
        body.revalidate();
        body.repaint();
        
        cont.revalidate();
        cont.repaint();
    }
    
}
