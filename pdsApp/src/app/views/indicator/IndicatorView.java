package app.views.indicator;

import app.controllers.*;
import app.listeners.*;
import app.models.*;
import app.views.welcome.*;
import java.awt.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class IndicatorView implements ListenerClientIndicator {
    private final ControllerClientIndicator cci;
    private final JPanel body;
    private final Container cont;
    private final Advisor user;
    
    public IndicatorView(ControllerClientIndicator cci, JPanel body, Container cont, Advisor user) {
      this.cci = cci;
      this.body = body;
      this.cont = cont;
      this.user = user;
    }

    /**
     * Penser à écrir une expliquation plus approfondie
     * Et l'écrire en anglais
     */
    @Override
    public void setIHM() {
        body.removeAll();
        
        // Create panel for top left of screen 
        JPanel topPanLeft = new JPanel();
        topPanLeft.setBackground(new Color(0,0,0,0));
        topPanLeft.setLayout(new GridLayout(0, 2, 0, 0));
        topPanLeft.add(makePieChart("Répartition des prêts suivant le type", 
                cci.getPieDatasetLoanPerType(user.getAgency())));
        topPanLeft.add(makePieChart("Répartition des prêts par conseillés", 
                cci.getPieDatasetLoanPerAdvisor(user.getAgency())));
                
        // Create panel for top of screen
        JPanel topPan = new JPanel();
        topPan.setBackground(new Color(0,0,0,0));
        topPan.setLayout(new GridLayout(0, 2, 0, 0));
        topPan.setBorder((BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255, 255, 255, 100))));
        topPan.add(topPanLeft);
        topPan.add(makeBarChart("Bénéfices prévisionnel de l'agence par année", 
                "Année", 
                "Résultat prévisionnel", 
                cci.getBarDatasetLoanPerTypeByYears(user.getAgency())));
        
        // Create panel for bottom of screen
        JPanel bottomPan = new JPanel();
        bottomPan.setBackground(new Color(0,0,0,0));
        bottomPan.setLayout(new BorderLayout());
        bottomPan.setBorder((BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(255, 255, 255, 100))));
        bottomPan.add(makePanBottomLeft(), BorderLayout.WEST);
        bottomPan.add(makePanBottomMiddle(), BorderLayout.CENTER);
        bottomPan.add(makeOptionPane(), BorderLayout.EAST);
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
    
    // Method for the panel on the bottom left with advisor classment
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
    
    // Method for the panel on the bottom center with specific indicator
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
    
    // Method for the panel on the bottom right with options
    private JPanel makeOptionPane()  {
        ImageIcon trait;
        Image im;
        JLabel buttonRefresh;
        JLabel buttonPrint;
        JLabel lblTitle;
        
        // Creat label with title
        lblTitle = new JLabel("  Options  ");
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
        
        // Create the panel
        JPanel thePane = new JPanel();
        thePane.setLayout(new BoxLayout(thePane, BoxLayout.Y_AXIS));
        thePane.setBackground(new Color(0,0,0,0));
        thePane.setBorder((BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(255, 255, 255, 100))));

        // Add all composent in the panel
        thePane.add(new JLabel(" "));
        thePane.add(lblTitle);
        thePane.add(new JLabel(" "));
        thePane.add(buttonRefresh);
        thePane.add(new JLabel(" "));
        thePane.add(buttonPrint);
        return thePane;
    }
    
    // Method for the panel on the bottom with user informations
    private JPanel makeUserPane()   {
        
        // Date format (a commenter)
        Locale locale = Locale.getDefault();
	Date actuelle = new Date();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd à HH:mm:ss");
        
        // Creat label with information
        JLabel lblInformation = new JLabel();
        lblInformation.setFont(new java.awt.Font("Verdana", 0, 15)); // NOI18N
        lblInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblInformation.setText("Utilisateur: " + user.getFirstName() + " " + user.getLastName() +
                "  |  Agence: " + user.getAgencyInfo().getName() +
                "  |  Date: " + dateFormat.format(actuelle));
        
        // Create the panel
        JPanel thePane = new JPanel();
        thePane.setLayout(new FlowLayout());
        thePane.setBackground(new Color(0,0,0,0));
        thePane.setBorder((BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(255, 255, 255, 100))));

        // Add all composent in the panel
        thePane.add(lblInformation);
        return thePane;
    }
        
    // Method for create pichart with a specific graphique
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
    
    // Method for create barchart with a specific graphique
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
    
}
