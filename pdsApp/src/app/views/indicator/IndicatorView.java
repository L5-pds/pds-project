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
import java.awt.Image;
import java.awt.Paint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;



import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

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
               
        JPanel topPan = new JPanel();
        topPan.setBackground(new Color(0,0,0,0));
        topPan.setLayout(new GridLayout(0, 2, 0, 0));
        topPan.setBorder((BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255, 255, 255, 100))));

        JPanel test = new JPanel();
        test.setBackground(new Color(0,0,0,0));
        test.setLayout(new GridLayout(0, 2, 0, 0));
        
        test.add(makePanTopLeft());
        test.add(makePanMiddleLeft());
        
        topPan.add(test);

        topPan.add(makePanTopRight());

        JPanel bottomPan = new JPanel();
        bottomPan.setBackground(new Color(0,0,0,0));
        bottomPan.setLayout(new GridLayout(0, 3, 0, 0));
        bottomPan.setBorder(javax.swing.BorderFactory.createBevelBorder(1, new Color(0, 0, 0, 255), new Color(0,0,0,0)));

        JPanel panel_4 = new JPanel();
        panel_4.setBackground(new Color(0,0,0,0));
        panel_4.setBorder(javax.swing.BorderFactory.createBevelBorder(1, new Color(215,203,233,255), new Color(0,0,0,0)));
        bottomPan.add(panel_4);

        JPanel panel_5 = new JPanel();
        panel_5.setBackground(new Color(0,0,0,0));
        panel_5.setBorder(javax.swing.BorderFactory.createBevelBorder(1, new Color(215,203,233,255), new Color(0,0,0,0)));
        bottomPan.add(panel_5);

        JPanel panel_6 = new JPanel();
        panel_6.setBackground(new Color(0,0,0,0));
        panel_6.setBorder(javax.swing.BorderFactory.createBevelBorder(1, new Color(215,203,233,255), new Color(0,0,0,0)));
        bottomPan.add(panel_6);
        
        body.setLayout(new GridLayout(2, 0, 0, 0));
        body.setBackground(new Color(215,203,233,255));
        body.add(topPan);
        body.add(bottomPan);
        body.revalidate();
        body.repaint();
            
        cont.revalidate();
        cont.repaint();
    }
    
    private JPanel makePanTopLeft() {
        JPanel panel_2 = new JPanel(new BorderLayout());
        panel_2.setBackground(new Color(215,203,233,255));
        
        DefaultPieDataset union = new DefaultPieDataset();
        union.setValue("Immobilier",2345);
        union.setValue("Automobile",1234);
        union.setValue("Divers",4321);
        
        JFreeChart repart = ChartFactory.createPieChart("Répartition des prêts suivant le type", union, true, true, false);
        repart.setBorderVisible(false);
        repart.setBackgroundPaint(new Color(215,203,233,255));
        
        Plot plot = repart.getPlot();
        plot.setBackgroundPaint(new Color(215,203,233,255));
        plot.setOutlineVisible(false);
        
        ChartPanel panechart = new ChartPanel(repart);
        panechart.setBackground(new Color(215,203,233,255));
        panechart.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        panechart.setOpaque(true);
        panechart.validate();
        panechart.repaint();
        panel_2.add(panechart, BorderLayout.CENTER);
                
        panel_2.validate();
        panel_2.repaint();
        
        return panel_2;
    }
    
    private JPanel makePanMiddleLeft() {
        JPanel panel_2 = new JPanel(new BorderLayout());
        panel_2.setBackground(new Color(215,203,233,255));
        
        DefaultPieDataset union = new DefaultPieDataset();
        union.setValue("Thibault",2345);
        union.setValue("Linda",1234);
        union.setValue("Ruben",4321);
        union.setValue("Tarik",2345);
        union.setValue("Mariam",1234);
        union.setValue("Alexandre",4321);
        union.setValue("Ulysse",2345);
        union.setValue("Romain",1234);
        union.setValue("Christophe",4321);
        union.setValue("Jeremy",2345);
        union.setValue("Sarah",1234);
        union.setValue("Aurélie",4321);
        
        JFreeChart repart = ChartFactory.createPieChart("Répartition des prêts par conseillés", union, true, true, false);
        repart.setBorderVisible(false);
        repart.setBackgroundPaint(new Color(215,203,233,255));
        
        Plot plot = repart.getPlot();
        plot.setBackgroundPaint(new Color(215,203,233,255));
        plot.setOutlineVisible(false);
        
        ChartPanel panechart = new ChartPanel(repart);
        panechart.setBackground(new Color(215,203,233,255));
        panechart.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        panechart.setOpaque(true);
        panechart.validate();
        panechart.repaint();
        panel_2.add(panechart, BorderLayout.CENTER);
                
        panel_2.validate();
        panel_2.repaint();
        
        return panel_2;
    }
    
    private JPanel makePanTopRight() {
        JPanel panel_3 = new JPanel(new BorderLayout());
        panel_3.setBackground(new Color(215,203,233,255));
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(123456, "Immobilié", "2006");
        dataset.addValue(312456, "Immobilié", "2007");
        dataset.addValue(234561, "Immobilié", "2008");
        dataset.addValue(532146, "Immobilié", "2009");
        dataset.addValue(321564, "Immobilié", "2010");
        dataset.addValue(432123, "Immobilié", "2011");
        dataset.addValue(632134, "Immobilié", "2012");
        dataset.addValue(432156, "Immobilié", "2013");
        dataset.addValue(345631, "Immobilié", "2014");
        dataset.addValue(345753, "Immobilié", "2015");
        dataset.addValue(864345, "Immobilié", "2016");
        
        dataset.addValue(123456, "Automobile", "2006");
        dataset.addValue(312456, "Automobile", "2007");
        dataset.addValue(234561, "Automobile", "2008");
        dataset.addValue(532146, "Automobile", "2009");
        dataset.addValue(321564, "Automobile", "2010");
        dataset.addValue(432123, "Automobile", "2011");
        dataset.addValue(632134, "Automobile", "2012");
        dataset.addValue(312456, "Automobile", "2013");
        dataset.addValue(345631, "Automobile", "2014");
        dataset.addValue(345753, "Automobile", "2015");
        dataset.addValue(864345, "Automobile", "2016");
        
        dataset.addValue(234561, "Divers", "2006");
        dataset.addValue(312456, "Divers", "2007");
        dataset.addValue(234561, "Divers", "2008");
        dataset.addValue(532146, "Divers", "2009");
        dataset.addValue(321564, "Divers", "2010");
        dataset.addValue(432123, "Divers", "2011");
        dataset.addValue(632134, "Divers", "2012");
        dataset.addValue(432156, "Divers", "2013");
        dataset.addValue(345631, "Divers", "2014");
        dataset.addValue(632134, "Divers", "2015");
        dataset.addValue(864345, "Divers", "2016");
        
        JFreeChart repart1 = ChartFactory.createBarChart("Bénéfices prévisionnel de l'agence par année", "Année", "Résultat prévisionnel", dataset, PlotOrientation.VERTICAL, true, true, false);
        repart1.setBorderVisible(false);
        repart1.setBackgroundPaint(new Color(215,203,233,255));
        
        ChartPanel panechart1 = new ChartPanel(repart1);
        panechart1.setBackground(new Color(215,203,233,255));
        panechart1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        Plot plot1 = repart1.getPlot();
        plot1.setBackgroundPaint(new Color(215,203,233,255));
        plot1.setOutlineVisible(false);
        
        panechart1.setOpaque(true);
        panechart1.validate();
        panechart1.repaint();
        panechart1.setDomainZoomable(false);
        panechart1.setRangeZoomable(false);
        panechart1.setZoomAroundAnchor(false);
        
        panechart1.repaint();
        panel_3.add(panechart1, BorderLayout.CENTER);
                   
        panel_3.validate();
        panel_3.repaint();
        
        
        return panel_3;
    }
    
}
