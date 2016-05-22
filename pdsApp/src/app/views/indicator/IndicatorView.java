package app.views.indicator;

import app.controllers.*;
import app.listeners.*;
import app.models.*;
import app.models.component.RoundJTextField;
import app.views.welcome.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
    private Advisor user;
    
    public IndicatorView(ControllerClientIndicator cci, JPanel body, Container cont, Advisor user) {
      this.cci = cci;
      this.body = body;
      this.cont = cont;
      this.user = user;
    }

    public void setIHM() {
        body.removeAll();

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
        
        JPanel topPanLeft = new JPanel();
        topPanLeft.setBackground(new Color(0,0,0,0));
        topPanLeft.setLayout(new GridLayout(0, 2, 0, 0));
        topPanLeft.add(makePieChart("Répartition des prêts suivant le type", cci.getPieDatasetLoanPerType()));
        topPanLeft.add(makePieChart("Répartition des prêts par conseillés", union));
        
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
        
        JPanel topPan = new JPanel();
        topPan.setBackground(new Color(0,0,0,0));
        topPan.setLayout(new GridLayout(0, 2, 0, 0));
        topPan.setBorder((BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255, 255, 255, 100))));
        topPan.add(topPanLeft);
        topPan.add(makeBarChart("Bénéfices prévisionnel de l'agence par année", "Année", "Résultat prévisionnel", dataset));
                
        JPanel bottomPan = new JPanel();
        bottomPan.setBackground(new Color(0,0,0,0));
        bottomPan.setLayout(new BorderLayout());
        bottomPan.setBorder((BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(255, 255, 255, 100))));
        bottomPan.add(makePanBottomLeft(), BorderLayout.WEST);
        bottomPan.add(makePanBottomMiddle(), BorderLayout.CENTER);
        bottomPan.add(makeOptionPane("  Options  "), BorderLayout.EAST);
        bottomPan.add(makeUserPane(), BorderLayout.SOUTH);
        
        body.setLayout(new GridLayout(2, 0, 0, 0));
        body.setBackground(new Color(215,203,233,255));
        body.add(topPan);
        body.add(bottomPan);
        body.revalidate();
        body.repaint();
            
        cont.revalidate();
        cont.repaint();
    }
    
    private JPanel makePieChart(String chartTitle, DefaultPieDataset dataset) {

        // Creat the chart with parameters
        JFreeChart theChart = ChartFactory.createPieChart(chartTitle, dataset, true, true, false);
        // Set properties for the chart
        theChart.setBorderVisible(false);
        theChart.setBackgroundPaint(new Color(215,203,233,255));
        
        // Creat the plot with the chart in parameter
        Plot thePlot = theChart.getPlot();
        // Set propoerties for the composent of chart
        thePlot.setBackgroundPaint(new Color(215,203,233,255));
        thePlot.setOutlineVisible(false);
        
        // Creat the chartpanel with the chart in parameter
        ChartPanel theChartPanel = new ChartPanel(theChart);
        // Set properties for the chartpanel
        theChartPanel.setBackground(new Color(215,203,233,255));
        theChartPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        theChartPanel.setOpaque(true);
        
        return theChartPanel;
    }
    
    private JPanel makeBarChart(String chartTitle, String abscissTitle, String ordonatTitle, DefaultCategoryDataset dataset) {
        // Creat the chart with parameters
        JFreeChart theChart = ChartFactory.createBarChart(chartTitle, abscissTitle, ordonatTitle, dataset, PlotOrientation.VERTICAL, true, true, false);
        // Set properties for the chart
        theChart.setBorderVisible(false);
        theChart.setBackgroundPaint(new Color(215,203,233,255));
        
        // Creat the plot with the chart in parameter
        Plot thePlot = theChart.getPlot();
        // Set propoerties for the composent of chart
        thePlot.setBackgroundPaint(new Color(215,203,233,255));
        thePlot.setOutlineVisible(false);
        
        // Creat the chartpanel with the chart in parameter
        ChartPanel theChartPane = new ChartPanel(theChart);
        // Set properties for the chartpanel
        theChartPane.setBackground(new Color(215,203,233,255));
        theChartPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        theChartPane.setOpaque(true);
        theChartPane.setDomainZoomable(false);
        theChartPane.setRangeZoomable(false);
        
        return theChartPane;
    }
    
    private JPanel makePanBottomLeft()  {
        JPanel thePane = new JPanel();
        //thePane.setLayout(new GridLayout(11, 1));
        thePane.setLayout(new BoxLayout(thePane, BoxLayout.Y_AXIS));
        thePane.setBackground(new Color(0,0,0,0));
        thePane.setBorder((BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(255, 255, 255, 100))));
        
        JLabel lblTitle = new JLabel();
        lblTitle.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setText("          Classement collaborateur          ");
        
        JLabel lbl1 = new JLabel();
        lbl1.setFont(new java.awt.Font("Verdana", 0, 15)); // NOI18N
        lbl1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl1.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl1.setText("1 - BOURGEOIS Thibault");
        
        JLabel lbl2 = new JLabel();
        lbl2.setFont(new java.awt.Font("Verdana", 0, 15)); // NOI18N
        lbl2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl2.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl2.setText("2 - EDERY Ruben");
        
        JLabel lbl3 = new JLabel();
        lbl3.setFont(new java.awt.Font("Verdana", 0, 15)); // NOI18N
        lbl3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl3.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl3.setText("3 - KICH Tarik");
        
        JLabel lbl4 = new JLabel();
        lbl4.setFont(new java.awt.Font("Verdana", 0, 15)); // NOI18N
        lbl4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl4.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl4.setText("4 - GENGOUL Alexandre");
        
        JLabel lbl5 = new JLabel();
        lbl5.setFont(new java.awt.Font("Verdana", 0, 15)); // NOI18N
        lbl5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl5.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl5.setText("5 - BOUZID Linda");
        
        JButton btMore = new JButton();
        btMore.setFont(new java.awt.Font("Verdana", 0, 20)); // NOI18N
        btMore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btMore.setAlignmentX(Component.CENTER_ALIGNMENT);
        btMore.setText("Rapport spécifique");
        
        thePane.add(new JLabel(" "));
        thePane.add(lblTitle);
        thePane.add(new JLabel(" "));
        thePane.add(cci.getAdvisorClassement(this.user.getAgency()));
        thePane.add(new JLabel(" "));
        thePane.add(btMore);
        thePane.add(new JLabel(" "));
        return thePane;
    }
    
    private JPanel makePanBottomMiddle()  {
        JPanel thePane = new JPanel();
        thePane.setBackground(new Color(0,0,0,0));
        thePane.setLayout(new BoxLayout(thePane, BoxLayout.Y_AXIS));
        thePane.setBorder((BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(255, 255, 255, 100))));

        JLabel lblTitle = new JLabel("Indicateurs suivant des axes spécifiques");
        lblTitle.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        thePane.add(new JLabel(" "));
        thePane.add(lblTitle);
        thePane.add(new JLabel("Imaginer des ComboBox et un jolie récapitulatif suivant les résultats des combobox"));
        return thePane;
    }
    
    private JPanel makeOptionPane(String paneTitle)  {
        ImageIcon trait;
        Image im;
        JLabel buttonRefresh;
        JLabel buttonPrint;
        JLabel lblTitle;
        
        // Creat label with title
        lblTitle = new JLabel(paneTitle);
        lblTitle.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create button for refresh with a jlabel
        trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconRefresh.png"));
        im = trait.getImage().getScaledInstance(50, 50, 1);
        buttonRefresh = new JLabel(new ImageIcon(im));
        buttonRefresh.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create button for refresh with a jlabel
        trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconPrinter.png"));
        im = trait.getImage().getScaledInstance(50, 50, 1);
        buttonPrint = new JLabel(new ImageIcon(im));
        buttonPrint.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel thePane = new JPanel();
        thePane.setLayout(new BoxLayout(thePane, BoxLayout.Y_AXIS));
        thePane.setBackground(new Color(0,0,0,0));
        thePane.setBorder((BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(255, 255, 255, 100))));

        thePane.add(new JLabel(" "));
        thePane.add(lblTitle);
        thePane.add(new JLabel(" "));
        thePane.add(buttonRefresh);
        thePane.add(new JLabel(" "));
        thePane.add(buttonPrint);
        return thePane;
    }
    
    private JPanel makeUserPane()   {
        
        // Date format (a commenter)
        Locale locale = Locale.getDefault();
	Date actuelle = new Date();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd à HH:mm:ss");
        
        // Creat label with information
        JLabel lblInformation = new JLabel();
        lblInformation.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        lblInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblInformation.setText("Utilisateur: " + user.getFirstName() + " " + user.getLastName() +
                "  |  Agence: " + user.getAgencyInfo().getName() +
                "  |  Date: " + dateFormat.format(actuelle));
        
        JPanel thePane = new JPanel();
        thePane.setLayout(new FlowLayout());
        thePane.setBackground(new Color(0,0,0,0));
        thePane.setBorder((BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(255, 255, 255, 100))));

        thePane.add(lblInformation);
        return thePane;
    }
    
}
