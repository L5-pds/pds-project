package app.views.simulations;

import app.controllers.*;
import app.helpers.*;
import app.listeners.*;
import app.models.component.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.*;
import java.text.DecimalFormat;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class CompareSimulationView implements CompareSimulationListener{

  private CompareSimulationController cci;
  private JPanel body;
  private Container cont;
  private JComboBox cbClient;
  private JComboBox cbType;
  private ArrayList<String[]> clients;
  private ArrayList<String[]> simulations;
  private JLabel nameLabel;
  private JLabel simulationLabel;
  private JLabel selectClientLabel;
  private JLabel selectTypeLabel;
  private JLabel errLabel;
  private RoundJTextField nameField;
  private RoundJButton searchButton;
  private RoundJButton validateButton;
  private RoundJButton compareButton;
  private JPanel globalPanel;
  private JPanel tablePanel;
  private JTable tab;

  private Double rate;
  private Double wage;
  private DecimalFormat df = new DecimalFormat("#.00");


  public CompareSimulationView(CompareSimulationController cci, JPanel body, Container cont) {
    df.setMaximumFractionDigits(2);
    this.cci = cci;
    this.body = body;
    this.cont = cont;
  }

  public void setIHM() {
    //Cleaning the Frame body
    body.removeAll();

    //Initialization of fields
    nameLabel = new JLabel();
    selectClientLabel = new JLabel();
    selectTypeLabel = new JLabel();
    simulationLabel = new JLabel();
    errLabel = new JLabel();
    nameField = new RoundJTextField(20);
    searchButton = new RoundJButton();
    compareButton = new RoundJButton();
    validateButton = new RoundJButton();
    globalPanel = new JPanel();
    cbClient = new JComboBox();

    //Set feild's text, typo and position
    nameLabel.setFont(new java.awt.Font("Verdana", 0, 25));
    nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    nameLabel.setText("Nom du client");

    selectClientLabel.setFont(new java.awt.Font("Verdana", 0, 25));
    selectClientLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    selectClientLabel.setText("Selectionnez un client");

    selectTypeLabel.setFont(new java.awt.Font("Verdana", 0, 25));
    selectTypeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    selectTypeLabel.setText("Selectionnez un type de prêt");

    simulationLabel.setFont(new java.awt.Font("Verdana", 0, 25));
    simulationLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    simulationLabel.setText("Selectionnez les simulations à comparer (2 à 5 simulations)");

    errLabel.setFont(new java.awt.Font("Verdana", 0, 25));
    errLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

    nameField.setFont(new java.awt.Font("Verdana", 0, 30));
    nameField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

    //Add actionPerformed for button to call the right step in nextStep method
    searchButton.setText("Chercher");
    searchButton.addActionListener(new ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        nextStep("name");
      }
    });
    validateButton.setText("Valider");
    validateButton.addActionListener(new ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        nextStep("list");
      }
    });
    compareButton.setText("Comparer");
    compareButton.addActionListener(new ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        nextStep("compare");
      }
    });

    //Initialize choices for loan type combobox
    String[] loanTypes = {"AUTOMOBILE", "CONSOMMATION","IMMOBILIER"};
    cbType = new JComboBox(loanTypes);

    //Add component vertically to the panel
    globalPanel.setLayout(new GridLayout(9, 1, 0, 5));
    globalPanel.add(nameLabel);
    globalPanel.add(nameField);
    globalPanel.add(searchButton);

    //Change background color for the container panels
    body.setBackground(new Color(215,203,233,255));
    globalPanel.setBackground(new Color(215,203,233,255));

    //add container panel to the Frame body
    body.add(globalPanel);

    //Repain the Frame to show changes
    cont.revalidate();
    cont.repaint();
  }

  public void nextStep(String step){
    switch(step) {
      case "name" :
        //This is called when the user want to search for a customer name (searchButton)

        //clean the body except of search name field and button
        globalPanel.remove(selectTypeLabel);
        globalPanel.remove(cbType);
        globalPanel.remove(validateButton);
        globalPanel.remove(errLabel);

        //Search only if the user write something
        if (nameField.getText() != null && !nameField.getText().isEmpty())
          clients = cci.getCustomers(nameField.getText());
        else
          clients = null;

        //removing listeners from combobox so that items can be removed/added dynamically
        //whitout calling its actionPerformed method
        for( ActionListener al : cbClient.getActionListeners() )
          cbClient.removeActionListener(al);

        //remove old items, and put new results items
        cbClient.removeAllItems();
        if(clients != null && clients.size() != 0){
          //only add items if the request has results
          for(int i=0; i< clients.size(); i++){
            String[] tmp = clients.get(i);
            //create an item object so that we can get back the id/wage by looking through the item description
            cbClient.addItem(new Item(Integer.parseInt(tmp[0]), tmp[1] +" "+tmp[2]+" - "+tmp[3], Double.parseDouble(tmp[4])));
          }
          //put the actionPerformed back to the combobox
          nextStep("client");
          //add the combobox
          globalPanel.add(selectClientLabel);
          globalPanel.add(cbClient);
        }
        else{
          //If there are no results, we inform the user so
          globalPanel.remove(selectClientLabel);
          globalPanel.remove(cbClient);
          errLabel.setText("aucun client trouvé");
          globalPanel.add(errLabel);
        }
      break;

      case "client" :
        //This is called when the user search for a customer name and find results
        cbClient.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            //Called when the user pick a user from the list
            //add loan types combobox
            globalPanel.add(selectTypeLabel);
            globalPanel.add(cbType);
            //add a button to validate (the user + loan type) choice
            globalPanel.add(validateButton);
            //repaint to show the new combobox and button
            cont.revalidate();
            cont.repaint();
          }
        });
      break;

      case "list" :
        //Cast the selectedItem to an object item
        Item selected = (Item)cbClient.getSelectedItem();
        //save the wage
        wage = selected.getWage();
        //getting all simulations for selected client and the given loan type
        simulations = cci.getSimulations(selected.getId(), cbType.getSelectedItem().toString());
        //the Jtable have (number of simulation) rows and 6 columns declared bellow
        //the last row doesn't count, it only countains the rate
        Object[][] data =  new Object[simulations.size() - 1][6];
        //save the rate from the last results row
        String[] tmp = simulations.get(simulations.size() -1);
        rate =  Double.parseDouble(tmp[0]);
        //initialization of simulations list JTable data
        for(int i=0; i< simulations.size() - 1; i++){
          String[] tmp2 = simulations.get(i);
          data[i] = tmp2;
        }

        //Clear the body and show the simulation list table if the request has results
        if(data.length != 0){
          globalPanel.removeAll();
          globalPanel.add(simulationLabel);
          Object[] columnNames = {"Id", "Date", "Label", "Montant", "Durée", ""};
          tab = table(data, columnNames, true);
          JScrollPane scrollPane = new JScrollPane(tab);
          globalPanel.add(scrollPane);
          globalPanel.add(compareButton);
        }
        //If there are no results, we inform the user so
        else{
          errLabel.setText("Pas de simulations trouvées.");
          globalPanel.add(errLabel);
        }
      break;

      case "compare" :
        // this is called when the user press the "compare" button
        // simIndex to count selected simulations number
        int simIndex=0;
        //boolean to validate selected simulations
        Boolean goCompare=true;
        String[][] checked = new String[5][3];
        for (int i = 0; i < tab.getRowCount(); i++) {
          Boolean isChecked = (Boolean)tab.getValueAt(i, 5);
          if (isChecked){
            if (simIndex < 5){
              //initialization of selected simulations array
              String[] r = {tab.getValueAt(i, 0).toString(), tab.getValueAt(i, 3).toString(), tab.getValueAt(i, 4).toString()};
              checked[simIndex] =r;
              simIndex++;
            }
            else{
              // if the number of selected simulations > 5, we inform the user so
              goCompare = false;
              errLabel.setText("Vous ne pouvez pas comparer plus de 5 simulations");
              globalPanel.add(errLabel);
            }
          }
        }
        if(simIndex < 2){
          //if the number of selected simulations < 2 we inform the user so
          goCompare = false;
          errLabel.setText("Au moins deux simulations doivent être selectionnées");
          globalPanel.add(errLabel);
        }
        if(goCompare)
          //goCompare : validate the number of simulations (in [2,5])
          compare(simIndex, checked);
      break;
    }

    //after each step we repaint the frame container
    cont.revalidate();
    cont.repaint();
  }

  public JTable table(Object[][] data, Object[] columnNames, Boolean bool){
    //this returns 2 different customized Jtables depending on the bool value
    // if bool is true : the jtable columns will have String format except the 5th one
    // which is a boolean (checkbox)
    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    JTable table = new JTable(model) {
      private final long serialVersionUID = 1L;
      public Class getColumnClass(int column) {
        if(bool){
          //case 1 : selecting simulations JTable
          switch (column) {
          //the 5th column in this customized jtable is a boolean (checkbox)
            case 5:
              return Boolean.class;
          //all the other columns have String format
            default:
              return String.class;
          }
        }else{
          // case 2 : results comparison JTable
          // all columns have String format
          return String.class;
        }
      }
    };
    table.setPreferredScrollableViewportSize(table.getPreferredSize());
    if(bool){
      // case 1 : selecting simulations Jtable
      // we set all the checkboxs to false by default
      for (int i = 0; i < table.getRowCount(); i++) {
        table.setValueAt(false, i, 5);
      }
    }

    return table;
  }

  public void compare(int simIndex,String[][] checked){
    //Empty the Frame body
    globalPanel.removeAll();
    body.remove(globalPanel);

    //the table contain simIndex(=number of simulations) rows and 8 columns declared bellow
    Object[][] data =  new Object[simIndex][8];
    for (int i=0; i<simIndex; i++){
      //initialize row fields
      String rowId = checked[i][0];
      String rowAmount = checked[i][1];
      String rowRem = String.valueOf(Double.parseDouble(rowAmount) * (1 + rate/100));
      String rowPeriod = checked[i][2];
      String rowMonthly = String.valueOf(Double.parseDouble(rowRem) / Double.parseDouble(rowPeriod));
      String rowRate = String.valueOf(rate);
      String rowInterest = String.valueOf(Double.parseDouble(rowAmount) * rate/100);
      String rowRatio = String.valueOf(Double.parseDouble(rowMonthly) / wage);
      String[] row = {rowId, rowAmount, df.format(Double.parseDouble(rowRem)), df.format(Double.parseDouble(rowMonthly)), rowPeriod, rowRate, df.format(Double.parseDouble(rowInterest)), new DecimalFormat("0.##").format(Double.parseDouble(rowRatio))};
      //add the row to data
      data[i] = row;
    }

    //Sorting data by ratio value
    for (int i=0; i<data.length -1; i++){
      Object[] tmp;
      //System.out.println("test");
      //System.out.println(data[i][7].toString());
      //System.out.println(data[i+1][7].toString());
      //System.out.println(Double.parseDouble(data[i+1][7].toString()));
      
      if(Double.parseDouble(data[i][7].toString().replace(",", ".")) > Double.parseDouble(data[i+1][7].toString().replace(",", "."))){
        tmp = data[i];
        data[i] = data[i+1];
        data[i+1] = tmp;
      }
    }

    //Set body title
    simulationLabel.setText("Comparaison des totaux principaux");

    //setting coluumn names for the jtable
    Object[] columnNames = {"N° simulation", "Montant total", "Total à rembourser", "Mensualité", "Durée (en mois)", "Taux d'intérêt", "Total des intérêts", "Niveau d'endettement"};
    //creating jtable using the calculated data in data[][]
    tab = table(data, columnNames, false);
    //add Jtable to a scrollable panel
    JScrollPane scrollPane = new JScrollPane(tab);

    //Create new blank Panel to be put in the body
    tablePanel = new JPanel();
    //put jtable + chartPanel vertically
    tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));

    //Commented line bellow to set customized table dimension
    //tablePanel.setPreferredSize(new Dimension(1200,132));

    //tablePanel layout : put JTable in the top + 2 charts in the bottom
    JPanel bottom = new JPanel();
    //Change panel background
    tablePanel.setBackground(new Color(215,203,233,255));
    //create and add charts to the bottom panel
    bottom.add(dualChart(data, "Niveau d'endettement", "Montant (€)", "Ratio (%)", "Salaire", "Mensualité", true));
    bottom.add(dualChart(data, "Durée du prêt", "Durée (année)", "", "Durée", "", false));
    bottom.setBackground(new Color(215,203,233,255));

    //add scrollable jtable + bottom panel(charts) vertically
    tablePanel.add(simulationLabel);
    tablePanel.add(scrollPane);
    tablePanel.add(bottom);

    //add the panel to the main Frame body
    body.add(tablePanel);
  }

  public JPanel dualChart(Object[][] data, String title, String column1, String column2, String bar1, String bar2, Boolean chart1){
    //Create empty panel to contain the chart
    JPanel pChart = new JPanel();
    //calling creating chart method (see bellow)
    JFreeChart jfreechart = createChart(data, title, column1, column2, bar1, bar2, chart1);
    ChartPanel chartpanel = new ChartPanel(jfreechart);
    //setting customized size for the chart
    chartpanel.setPreferredSize(new Dimension(600, 290));
    //remove chart background color (keys and titles background)
    jfreechart.setBackgroundPaint(null);
    //paint the chartpanel and the container panel background
    chartpanel.setBackground(new Color(215,203,233,255));
    pChart.setBackground(new Color(215,203,233,255));
    //add chartpanel to the container panel
    pChart.add(chartpanel);
    return pChart;
  }

  private CategoryDataset createDataset(Object[][] data, String bar1, String bar2, Boolean chart1) {
    //filling the chart with simulations information
    DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
    if(chart1){
    //chart 1 : wage Vs monthly
      for(int i=0; i<data.length; i++){
        defaultcategorydataset.addValue(wage, bar1, data[i][0].toString());
        defaultcategorydataset.addValue(Double.parseDouble(data[i][3].toString().replace(",", ".")), bar2, data[i][0].toString().replace(",", "."));
      }
    }
    else{
    //chart 2 : Periods
      for(int i=0; i<data.length; i++){
        defaultcategorydataset.addValue(Double.parseDouble(data[i][4].toString().replace(",", "."))/12, bar1, data[i][0].toString().replace(",", "."));
      }
    }
    return defaultcategorydataset;
  }

  private JFreeChart createChart(Object[][] data, String title, String column1, String column2, String bar1, String bar2, Boolean chart1){
    //create the chart
    JFreeChart jfreechart = ChartFactory.createBarChart(title, "N° Simulations", column1, createDataset(data, bar1, bar2, chart1), PlotOrientation.VERTICAL, true, true, false);

    //Setting a background color for the graphic bars
    CategoryPlot categoryplot = jfreechart.getCategoryPlot();
    categoryplot.setBackgroundPaint(new Color(238, 238, 255));
    categoryplot.mapDatasetToRangeAxis(1, 1);
    //add simulations as categories
    CategoryAxis categoryaxis = categoryplot.getDomainAxis();
    categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
    //add second axis (ratio)

    //add second column only for chart1
    if(chart1){
      NumberAxis numberaxis = new NumberAxis(column2);
      categoryplot.setRangeAxis(1, numberaxis);
    }
    LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();

    categoryplot.setRenderer(1, lineandshaperenderer);
    categoryplot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
    return jfreechart;
  }
}
