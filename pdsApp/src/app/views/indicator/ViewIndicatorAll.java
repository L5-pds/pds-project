package app.views.indicator;

import app.controllers.*;
import app.listeners.*;
import app.models.*;
import app.models.component.*;
import app.views.welcome.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.*;
import java.time.LocalDate;
import java.util.*;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javax.swing.*;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdesktop.swingx.JXDatePicker;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ViewIndicatorAll implements ListenerIndicator {
    private final ControllerIndicator cci;
    private final JPanel body;
    private final Container cont;
    private final Advisor user;
    
    public ViewIndicatorAll(ControllerIndicator cci, JPanel body, Container cont, Advisor user) {
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
        lblTitle.setFont(new java.awt.Font("Verdana", 1, 25)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setText("     Classement collaborateur     ");
                
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
        lblTitle.setFont(new java.awt.Font("Verdana", 1, 25)); // NOI18N
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        JLabel lblRanch = new JLabel("Plage");
        lblRanch.setHorizontalAlignment(SwingConstants.CENTER);
        lblRanch.setFont(new java.awt.Font("Verdana", 0, 17));
        lblRanch.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lbldateBegin = new JLabel("Du:");
        lbldateBegin.setFont(new java.awt.Font("Verdana", 0, 17));
        lbldateBegin.setAlignmentY(Component.CENTER_ALIGNMENT);
        lbldateBegin.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lbldateEnd = new JLabel("Au:");
        lbldateEnd.setFont(new java.awt.Font("Verdana", 0, 17));
        lbldateEnd.setAlignmentY(Component.CENTER_ALIGNMENT);
        lbldateEnd.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JXDatePicker dateBegin1 = new JXDatePicker(new Date());
        dateBegin1.setFont(new java.awt.Font("Verdana", 0, 15));
        JXDatePicker dateEnd1 = new JXDatePicker(new Date());
        dateEnd1.setFont(new java.awt.Font("Verdana", 0, 15));
        
        JPanel paneRangeDateLoanLbl = new JPanel(); //To ADD
        paneRangeDateLoanLbl.setBackground(new Color(0,0,0,0));
        paneRangeDateLoanLbl.setLayout(new BoxLayout(paneRangeDateLoanLbl, BoxLayout.Y_AXIS));
        paneRangeDateLoanLbl.add(lbldateBegin);
        paneRangeDateLoanLbl.add(lbldateEnd);
        
        JPanel paneRangeDateLoanDate = new JPanel(); //To ADD
        paneRangeDateLoanDate.setBackground(new Color(0,0,0,0));
        paneRangeDateLoanDate.setLayout(new BoxLayout(paneRangeDateLoanDate, BoxLayout.Y_AXIS));
        paneRangeDateLoanDate.add(dateBegin1);
        paneRangeDateLoanDate.add(dateEnd1);
        
        JPanel paneRangeDateLoan = new JPanel();
        paneRangeDateLoan.setBackground(new Color(0,0,0,0));
        paneRangeDateLoan.setLayout(new BorderLayout());
        paneRangeDateLoan.add(lblRanch, BorderLayout.NORTH);
        paneRangeDateLoan.add(paneRangeDateLoanLbl, BorderLayout.WEST);
        paneRangeDateLoan.add(paneRangeDateLoanDate, BorderLayout.EAST);
        
        
        JLabel lblComboAdvisor = new JLabel("      Conseillé      ");
        lblComboAdvisor.setHorizontalAlignment(SwingConstants.CENTER);
        lblComboAdvisor.setFont(new java.awt.Font("Verdana", 0, 17));
        JComboBox ComboAdvisor = new JComboBox(); //To ADD
        ComboAdvisor.setBackground(Color.WHITE);
        ComboAdvisor.setFont(new java.awt.Font("Verdana", 0, 15));
        ComboAdvisor.setModel(new DefaultComboBoxModel(new String[] {"TOUS"}));
        
        JPanel paneAdvisor = new JPanel();
        paneAdvisor.setBackground(new Color(0,0,0,0));
        paneAdvisor.setLayout(new BorderLayout());
        paneAdvisor.add(lblComboAdvisor, BorderLayout.NORTH);
        paneAdvisor.add(ComboAdvisor, BorderLayout.CENTER);
        
        
        JLabel lblComboTypeLoan = new JLabel("     Type de prêt     "); //To ADD
        lblComboTypeLoan.setHorizontalAlignment(SwingConstants.CENTER);
        lblComboTypeLoan.setFont(new java.awt.Font("Verdana", 0, 17));
        JComboBox ComboTypeLoan = new JComboBox(); //To ADD
        ComboTypeLoan.setBackground(Color.WHITE);
        ComboTypeLoan.setFont(new java.awt.Font("Verdana", 0, 15));
        ComboTypeLoan.setModel(new DefaultComboBoxModel(new String[] {"TOUS"}));
        
        JPanel paneTypeLoan = new JPanel();
        paneTypeLoan.setBackground(new Color(0,0,0,0));
        paneTypeLoan.setLayout(new BorderLayout());
        paneTypeLoan.add(lblComboTypeLoan, BorderLayout.NORTH);
        paneTypeLoan.add(ComboTypeLoan, BorderLayout.CENTER);
        
        
        JLabel lblRanchCustomer = new JLabel("Plage");
        lblRanchCustomer.setFont(new java.awt.Font("Verdana", 0, 17));
        lblRanchCustomer.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblRanchCustomer.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lbldateBeginCustomer = new JLabel("Du:");
        lbldateBeginCustomer.setFont(new java.awt.Font("Verdana", 0, 17));
        lbldateBeginCustomer.setHorizontalAlignment(SwingConstants.CENTER);
        lbldateBeginCustomer.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lbldateEndCustomer = new JLabel("Au:");
        lbldateEndCustomer.setFont(new java.awt.Font("Verdana", 0, 17));
        lbldateEndCustomer.setHorizontalAlignment(SwingConstants.CENTER);
        lbldateEndCustomer.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JXDatePicker dateBegin1Customer = new JXDatePicker(new Date());
        dateBegin1Customer.setFont(new java.awt.Font("Verdana", 0, 15));
        JXDatePicker dateEnd1Customer = new JXDatePicker(new Date());
        dateEnd1Customer.setFont(new java.awt.Font("Verdana", 0, 15));
        
        JPanel paneRangeDateCustomerLbl = new JPanel(); //To ADD
        paneRangeDateCustomerLbl.setBackground(new Color(0,0,0,0));
        paneRangeDateCustomerLbl.setLayout(new BoxLayout(paneRangeDateCustomerLbl, BoxLayout.Y_AXIS));
        paneRangeDateCustomerLbl.add(lbldateBeginCustomer);
        paneRangeDateCustomerLbl.add(lbldateEndCustomer);
        
        JPanel paneRangeDateCustomerDate = new JPanel(); //To ADD
        paneRangeDateCustomerDate.setBackground(new Color(0,0,0,0));
        paneRangeDateCustomerDate.setLayout(new BoxLayout(paneRangeDateCustomerDate, BoxLayout.Y_AXIS));
        paneRangeDateCustomerDate.add(dateBegin1Customer);
        paneRangeDateCustomerDate.add(dateEnd1Customer);
        
        JPanel paneRangeDateCustomer = new JPanel();
        paneRangeDateCustomer.setBackground(new Color(0,0,0,0));
        paneRangeDateCustomer.setLayout(new BorderLayout());
        paneRangeDateCustomer.add(lblRanchCustomer, BorderLayout.NORTH);
        paneRangeDateCustomer.add(paneRangeDateCustomerLbl, BorderLayout.WEST);
        paneRangeDateCustomer.add(paneRangeDateCustomerDate, BorderLayout.EAST);
        
        
        JButton btnValide = new JButton("Valider");
        btnValide.setFont(new java.awt.Font("Verdana", 0, 17));
        
        
        JPanel jpcombo = new JPanel();
        jpcombo.setLayout(new FlowLayout(FlowLayout.CENTER));
        jpcombo.setBackground(new Color(215,203,233,255));
        jpcombo.add(paneRangeDateLoan);
        jpcombo.add(paneAdvisor);
        jpcombo.add(paneTypeLoan);
        jpcombo.add(paneRangeDateCustomer);
        jpcombo.add(new JLabel(" "));
        jpcombo.add(btnValide);
        
        
        JLabel label = new JLabel("Nombre de prêt contractés : 1452");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Verdana", Font.PLAIN, 30));
        JLabel label_1 = new JLabel("Bénéfices perçu : 452154€");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setFont(new Font("Verdana", Font.PLAIN, 30));
        JLabel label_2 = new JLabel("Bénéfices restants : 452154€");
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setFont(new Font("Verdana", Font.PLAIN, 30));
        JLabel label_3 = new JLabel("Durée moyenne des prêts : 45 mois");
        label_3.setHorizontalAlignment(SwingConstants.CENTER);
        label_3.setFont(new Font("Verdana", Font.PLAIN, 30));
        JLabel label_4 = new JLabel("Montant moyen des prêts : 45124€");
        label_4.setHorizontalAlignment(SwingConstants.CENTER);
        label_4.setFont(new Font("Verdana", Font.PLAIN, 30));
        
        
        JPanel jpinfo = new JPanel();
        jpinfo.setLayout(new GridLayout(5, 0, 0, 0));
        jpinfo.setBackground(new Color(215,203,233,255));
        jpinfo.add(label_4);
        jpinfo.add(label_3);
        jpinfo.add(label_2);
        jpinfo.add(label_2);
        jpinfo.add(label_1);
        jpinfo.add(label);
        
        thePane.add(new JLabel(" "));
        thePane.add(lblTitle);
        thePane.add(new JLabel(" "));
        thePane.add(jpcombo);
        thePane.add(jpinfo);
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
        lblTitle.setFont(new java.awt.Font("Verdana", 1, 25)); // NOI18N
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
