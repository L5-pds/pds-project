package app.views.indicator;

import app.controllers.*;
import app.listeners.*;
import app.models.*;
import app.models.component.*;
import app.models.other.PaneSearchIndicator;
import app.models.other.dataSearchIndicator;
import app.views.welcome.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.*;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private JPanel thePaneTable;

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

        // Create the panel for tab
        thePaneTable = new JPanel();

        // Create panel for top left of screen
        JPanel topPanLeft = new JPanel();
        topPanLeft.setBackground(new Color(0,0,0,0));
        topPanLeft.setLayout(new GridLayout(0, 2, 0, 0));
        topPanLeft.add(makePieChart("Répartition des prêts suivant le type",
                cci.getPieDatasetLoanPerType(user.getAgency())));
        topPanLeft.add(makePieChart("Intérêts des prêts par conseillés",
                cci.getPieDatasetLoanPerAdvisor(user.getAgency())));

        // Create panel for top of screen
        JPanel topPan = new JPanel();
        topPan.setBackground(new Color(0,0,0,0));
        topPan.setLayout(new GridLayout(0, 2, 0, 0));
        topPan.setBorder((BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255, 255, 255, 100))));
        topPan.add(topPanLeft);
        topPan.add(makeBarChart("Interêts de l'agence par année*",
                "Année",
                "Interêts",
                cci.getBarDatasetLoanPerTypeByYears(user.getAgency())));

        // Create panel for bottom of screen
        JPanel bottomPan = new JPanel();
        bottomPan.setBackground(new Color(0,0,0,0));
        bottomPan.setLayout(new BorderLayout());
        bottomPan.setBorder((BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(255, 255, 255, 100))));
        bottomPan.add(makePanBottomMiddle(makeSearchPane()), BorderLayout.CENTER);

        bottomPan.add(makeOptionPane(), BorderLayout.EAST);
        bottomPan.add(makePanBottomMiddle(makeSearchPane()), BorderLayout.CENTER);
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

    /**
     * Method for the panel on the bottom center with specific indicator
     * @param search
     * JPanel with search composant
     * @return
     */
    private JPanel makePanBottomMiddle(JPanel search)  {
        JPanel thePane = new JPanel();
        thePane.setBackground(new Color(0,0,0,0));
        thePane.setLayout(new BoxLayout(thePane, BoxLayout.Y_AXIS));
        thePane.setBorder((BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(255, 255, 255, 100))));

        JLabel lblTitle = new JLabel("Indicateurs suivant des axes spécifiques");
        lblTitle.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel paneShearchIndicator = new JPanel();
        paneShearchIndicator.setLayout(new BorderLayout());
        paneShearchIndicator.setBackground(new Color(215,203,233,255));

        paneShearchIndicator.add(search, BorderLayout.WEST);

        PaneSearchIndicator tableInfo = new PaneSearchIndicator();

        makeTablePane(tableInfo);
        paneShearchIndicator.add(thePaneTable, BorderLayout.CENTER);

        thePane.add(new JLabel(" "));
        thePane.add(lblTitle);
        thePane.add(new JLabel(" "));
        thePane.add(paneShearchIndicator);

        return thePane;
    }

    @Override
    public void makeTablePane(PaneSearchIndicator theTableForPane)  {
        thePaneTable.removeAll();

        thePaneTable.setLayout(new BorderLayout());
        thePaneTable.setBackground(new Color(215,203,233,255));

        thePaneTable.add(theTableForPane.getAllTable(), BorderLayout.CENTER);
        thePaneTable.add(theTableForPane.getInfoPane(), BorderLayout.WEST);

        thePaneTable.revalidate();
        thePaneTable.repaint();
    }

    /**
     * Method for the panel on the bottom right with options
     * @return
     */
    private JPanel makeOptionPane()  {
        ImageIcon trait;
        Image im;
        JLabel buttonRefresh;
        JLabel buttonPrint;
        JLabel buttonAdvisor;
        JLabel buttonHelp;
        JLabel lblTitle;

        // Creat label with title
        lblTitle = new JLabel("  Options  ");
        lblTitle.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create button for refresh with a jlabel
        im = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconRefresh.png")).getImage().getScaledInstance(50, 50, 1);
        buttonRefresh = new JLabel(new ImageIcon(im));
        buttonRefresh.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonRefresh.setToolTipText("Rafraichir la fenêtre");
        buttonRefresh.addMouseListener(new MouseAdapter()   {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cci.refreshAllPane();
            }

            public void mouseEntered(MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconRefreshHover.png")).getImage().getScaledInstance(50, 50, 1);
                buttonRefresh.setIcon(new ImageIcon(im));
            }

            public void mouseExited(MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconRefresh.png")).getImage().getScaledInstance(50, 50, 1);
                buttonRefresh.setIcon(new ImageIcon(im));
            }
        });

        // Create button for print with a jlabel
        trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconPrinter.png"));
        im = trait.getImage().getScaledInstance(50, 50, 1);
        buttonPrint = new JLabel(new ImageIcon(im));
        buttonPrint.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPrint.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPrint.setToolTipText("Imprimer le rapport");
        buttonPrint.addMouseListener(new MouseAdapter()   {
            public void mouseEntered(MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconPrinterHover.png")).getImage().getScaledInstance(50, 50, 1);
                buttonPrint.setIcon(new ImageIcon(im));
            }

            public void mouseExited(MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconPrinter.png")).getImage().getScaledInstance(50, 50, 1);
                buttonPrint.setIcon(new ImageIcon(im));
            }
        });

        // Create button for advisor informations with a jlabel
        trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconUser.png"));
        im = trait.getImage().getScaledInstance(50, 50, 1);
        buttonAdvisor = new JLabel(new ImageIcon(im));
        buttonAdvisor.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonAdvisor.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonAdvisor.setToolTipText("Analyse par conseillés");
        buttonAdvisor.addMouseListener(new MouseAdapter()   {
            public void mouseEntered(MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconUserHover.png")).getImage().getScaledInstance(50, 50, 1);
                buttonAdvisor.setIcon(new ImageIcon(im));
            }

            public void mouseExited(MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconUser.png")).getImage().getScaledInstance(50, 50, 1);
                buttonAdvisor.setIcon(new ImageIcon(im));
            }
        });

        // Create button for help with a jlabel
        trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconHelp.png"));
        im = trait.getImage().getScaledInstance(50, 50, 1);
        buttonHelp = new JLabel(new ImageIcon(im));
        buttonHelp.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonHelp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonHelp.setToolTipText("Afficher le manuel");
        buttonHelp.addMouseListener(new MouseAdapter()   {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    cci.openManual();
                } catch (IOException ex) {
                    Logger.getLogger(ViewIndicatorAll.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            public void mouseEntered(MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconHelpHover.png")).getImage().getScaledInstance(50, 50, 1);
                buttonHelp.setIcon(new ImageIcon(im));
            }

            public void mouseExited(MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconHelp.png")).getImage().getScaledInstance(50, 50, 1);
                buttonHelp.setIcon(new ImageIcon(im));
            }
        });

        // Create the panel
        JPanel thePane = new JPanel();
        thePane.setLayout(new BoxLayout(thePane, BoxLayout.Y_AXIS));
        thePane.setBackground(new Color(215,203,233,255));
        thePane.setBorder((BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(255, 255, 255, 100))));

        // Add all composent in the panel
        thePane.add(new JLabel(" "));
        thePane.add(lblTitle);
        thePane.add(new JLabel(" "));
        thePane.add(buttonRefresh);
        thePane.add(new JLabel(" "));
        thePane.add(buttonPrint);
        //thePane.add(new JLabel(" "));
        //thePane.add(buttonAdvisor);
        thePane.add(new JLabel(" "));
        thePane.add(buttonHelp);
        return thePane;
    }


    /**
     * Method for the panel with search composent
     * @return
     */
    private JPanel makeSearchPane() {

        // Create the panel
        JPanel thePane = new JPanel();
        thePane.setLayout(new BoxLayout(thePane, BoxLayout.Y_AXIS));
        thePane.setBackground(new Color(215,203,233,255));
        thePane.setBorder((BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(255, 255, 255, 100))));

        // Creat label with title
        JLabel lblTitle = new JLabel("Axes d'analyses");
        lblTitle.setFont(new java.awt.Font("Verdana", Font.BOLD, 17)); // NOI18N
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblRanchLoan = new JLabel("Prêts du ... au ...");
        lblRanchLoan.setHorizontalAlignment(SwingConstants.CENTER);
        lblRanchLoan.setFont(new java.awt.Font("Verdana", 0, 17));
        lblRanchLoan.setAlignmentX(Component.CENTER_ALIGNMENT);

        JXDatePicker dateBegin = new JXDatePicker(new Date());
        dateBegin.setFont(new java.awt.Font("Verdana", 0, 15));

        JXDatePicker dateEnd = new JXDatePicker(new Date());
        dateEnd.setFont(new java.awt.Font("Verdana", 0, 15));

        JPanel paneRangeDateLoanLbl = new JPanel(); //To ADD
        paneRangeDateLoanLbl.setBackground(new Color(0,0,0,0));
        paneRangeDateLoanLbl.setLayout(new FlowLayout());
        paneRangeDateLoanLbl.add(dateBegin);
        paneRangeDateLoanLbl.add(dateEnd);

        JLabel lblComboAdvisor = new JLabel("Conseillé");
        lblComboAdvisor.setHorizontalAlignment(SwingConstants.CENTER);
        lblComboAdvisor.setFont(new java.awt.Font("Verdana", 0, 17));
        lblComboAdvisor.setAlignmentX(Component.CENTER_ALIGNMENT);

        dataSearchIndicator dataComposent = cci.getComboData(user.getAgency());

        JComboBox ComboAdvisor = new JComboBox(); //To ADD
        ComboAdvisor.setBackground(Color.WHITE);
        ComboAdvisor.setFont(new java.awt.Font("Verdana", 0, 15));
        ComboAdvisor.setModel(new DefaultComboBoxModel(new String[] {"TOUS"}));
        ComboAdvisor.setAlignmentX(Component.CENTER_ALIGNMENT);
        ComboAdvisor.setModel(new DefaultComboBoxModel(dataComposent.getAdvisor().toArray()));

        JPanel paneComboAdvisor = new JPanel(); //To ADD
        paneComboAdvisor.setBackground(new Color(0,0,0,0));
        paneComboAdvisor.setLayout(new BoxLayout(paneComboAdvisor, BoxLayout.Y_AXIS));
        paneComboAdvisor.add(ComboAdvisor);

        JLabel lblComboTypeLoan = new JLabel("Type de prêt"); //To ADD
        lblComboTypeLoan.setHorizontalAlignment(SwingConstants.CENTER);
        lblComboTypeLoan.setFont(new java.awt.Font("Verdana", 0, 17));
        lblComboTypeLoan.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox ComboTypeLoan = new JComboBox(); //To ADD
        ComboTypeLoan.setBackground(Color.WHITE);
        ComboTypeLoan.setFont(new java.awt.Font("Verdana", 0, 15));
        ComboTypeLoan.setModel(new DefaultComboBoxModel(new String[] {"TOUS"}));
        ComboTypeLoan.setAlignmentX(Component.CENTER_ALIGNMENT);

        ComboTypeLoan.setModel(new DefaultComboBoxModel(dataComposent.getTypeLoan().toArray()));


        JPanel paneComboTypeLoan = new JPanel(); //To ADD
        paneComboTypeLoan.setBackground(new Color(0,0,0,0));
        paneComboTypeLoan.setLayout(new BoxLayout(paneComboTypeLoan, BoxLayout.Y_AXIS));
        paneComboTypeLoan.add(ComboTypeLoan);

        JLabel lblComboTypeCustomer = new JLabel("Catégorie 'âge du client'"); //To ADD
        lblComboTypeCustomer.setHorizontalAlignment(SwingConstants.CENTER);
        lblComboTypeCustomer.setFont(new java.awt.Font("Verdana", 0, 17));
        lblComboTypeCustomer.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox ComboTypeCustomer = new JComboBox();
        ComboTypeCustomer.setBackground(Color.WHITE);
        ComboTypeCustomer.setFont(new java.awt.Font("Verdana", 0, 15));
        ComboTypeCustomer.setModel(new DefaultComboBoxModel(new String[] {"TOUS",
            "Jeune (moins de 30 ans)",
            "Adulte (entre 30 ans et 60 ans)",
            "Senior (plus de 60 ans)"}));
        ComboTypeCustomer.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel paneComboTypeCustomer = new JPanel(); //To ADD
        paneComboTypeCustomer.setBackground(new Color(0,0,0,0));
        paneComboTypeCustomer.setLayout(new BoxLayout(paneComboTypeCustomer, BoxLayout.Y_AXIS));
        paneComboTypeCustomer.add(ComboTypeCustomer);

        Image im = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconSearch.png")).getImage().getScaledInstance(149, 49, 1);
        JLabel btValid = new JLabel(new ImageIcon(im));
        btValid.setAlignmentX(Component.CENTER_ALIGNMENT);
        btValid.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btValid.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                //javax.swing.JOptionPane.showMessageDialog(null,"Le message est : " + ComboTypeCustomer.getSelectedItem().toString());
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String dateNow = formatter.format(Calendar.getInstance().getTime());
                Date begin = dateBegin.getDate();
                Date end = dateEnd.getDate();


                if(end.getTime() < begin.getTime()) {
                    Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconError.png")).getImage().getScaledInstance(75, 75, 1);
                        JOptionPane.showMessageDialog(null, 
                          "La date de fin de votre recherche est inccompatible avec votre date de début de recherche", 
                          "Erreur", 
                          JOptionPane.WARNING_MESSAGE,
                          new ImageIcon(im));
                    return;
                }
                if((Calendar.getInstance().getTime().getTime() < begin.getTime()) ||
                        (Calendar.getInstance().getTime().getTime() < end.getTime())) {
                    Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconError.png")).getImage().getScaledInstance(75, 75, 1);
                        JOptionPane.showMessageDialog(null, 
                          "Une date est ultérieur à autjourd'hui. Merci de ressayer.", 
                          "Erreur", 
                          JOptionPane.WARNING_MESSAGE,
                          new ImageIcon(im));
                    return;
                }

                String dateBegin = formatter.format(begin);
                String dateEnd = formatter.format(end);

                String typeAdvisor = ComboAdvisor.getSelectedItem().toString();
                String typeLoan = ComboTypeLoan.getSelectedItem().toString();
                String typeCustomer = ComboTypeCustomer.getSelectedItem().toString();

                try {
                    cci.setNewTable(dateBegin,
                            dateEnd,
                            typeAdvisor,
                            typeLoan,
                            typeCustomer,
                            user.getAgency());
                } catch (ParseException ex) {
                    Logger.getLogger(ViewIndicatorAll.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        thePane.add(lblTitle);

        thePane.add(lblRanchLoan);
        thePane.add(paneRangeDateLoanLbl);

        thePane.add(lblComboAdvisor);
        thePane.add(paneComboAdvisor);

        thePane.add(lblComboTypeLoan);
        thePane.add(paneComboTypeLoan);

        thePane.add(lblComboTypeCustomer);
        thePane.add(paneComboTypeCustomer);
        thePane.add(new JLabel(" "));

        thePane.add(btValid);

        return thePane;
    }

    /**
     * Method for the panel on the bottom with user informations
     * @return
     */
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
                "  |  Date: " + dateFormat.format(actuelle) +
                "  |  * Les prêts de type immobilier sont en k€");

        // Create the panel
        JPanel thePane = new JPanel();
        thePane.setLayout(new FlowLayout());
        thePane.setBackground(new Color(215,203,233,255));
        thePane.setBorder((BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(255, 255, 255, 100))));

        // Add all composent in the panel
        thePane.add(lblInformation);
        return thePane;
    }

    /**
     * Method for create pichart with a specific graphique
     * @param chartTitle
     * Title of chart
     * @param dataset
     * Data for chart
     * @return
     */
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
        theChartPanel.getChart().removeLegend();

        return theChartPanel;
    }

    /**
     * Method for create barchart with a specific graphique
     * @param chartTitle
     * Title of chart
     * @param abscissTitle
     * Title in absciss
     * @param ordonatTitle
     * Title in ordonat
     * @param dataset
     * Data for chart
     * @return
     */
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
