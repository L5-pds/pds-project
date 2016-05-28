package app.controllers;

import app.helpers.Serialization;
import app.listeners.*;
import app.models.other.*;
import app.views.welcome.WelcomeViewClient;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ControllerIndicator {
    private ListenerIndicator listener;

    private static Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Serialization s;

    /**
     * Constructor
     * @param socket 
     * get Socket in parameter
     */
    public ControllerIndicator(Socket socket) {
        this.socket = socket;
        this.s = new Serialization();
        try {
          out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
          Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconError.png")).getImage().getScaledInstance(75, 75, 1);
            JOptionPane.showMessageDialog(null, 
              "Erreur (I/O) : " + e.getMessage(), 
              "Erreur", 
              JOptionPane.WARNING_MESSAGE,
              new ImageIcon(im));
        }
    }

    /**
     * Set listener to the controller
     * @param l 
     * get ListenerIndicator in parameter
     */
    public void addListener(ListenerIndicator l) {
        this.listener = l;
        listener.setIHM();
    }

    /**
     * Communicate with server for get all advisor an type loan for search panel
     * @param idAgency
     * Get int in parameter
     * @return
     * return dataSearchIndicator object
     */
    public dataSearchIndicator getComboData(int idAgency)  {
        
        dataSearchIndicator dataComposent = new dataSearchIndicator();
        
        try {
            out.println("SPECIF_1/SelectDataSearch/" + idAgency);
            out.flush();
            dataComposent = s.unserializeDataSearchIndicator(in.readLine());
        } catch (IOException | HeadlessException e) {
          Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconError.png")).getImage().getScaledInstance(75, 75, 1);
            JOptionPane.showMessageDialog(null, 
              "Le serveur ne répond plus", 
              "Erreur", 
              JOptionPane.WARNING_MESSAGE,
              new ImageIcon(im));
        }
        return dataComposent;
    }
    
    /**
     * Recall listener methode for refresh IHM
     */
    public void refreshAllPane()    {
        listener.setIHM();
    }
    
    /**
     * Methode call after search click for get specifique indicator
     * @param dateBegin
     * @param dateEnd
     * @param typeAdvisor
     * @param typeLoan
     * @param typeCustomer
     * @param idAgency
     * @throws ParseException 
     */
    public void setNewTable(String dateBegin, 
            String dateEnd, 
            String typeAdvisor, 
            String typeLoan, 
            String typeCustomer, 
            int idAgency) throws ParseException   {
        
        try {
            //Convert date in long because caracter "/" crash server
            out.println("SPECIF_1/SelectSearchLoan/" + idAgency + ";" + new Date(dateBegin).getTime() + ";" + new Date(dateEnd).getTime() + ";" + typeAdvisor + ";" + typeLoan + ";" + typeCustomer);
            out.flush();
            String testing = in.readLine();
                        
            if((!testing.equals("ERROR")) && (!testing.equals("no line"))) {
                
                String[] querySplited = testing.split("root");

                String jsonInfo = querySplited[0];
                String jsonTable = querySplited[1];
            
                
                PaneSearchIndicator tableInfo = s.unserializePaneSearchIndicator(jsonInfo);
                ArrayList<String> listing = s.unserializeArrayList(jsonTable);
                
                for(int i=0 ; i<listing.size() ; i++)   {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String[] spliting = listing.get(i).split(";");
                    tableInfo.addRow(spliting[0], 
                            spliting[1], 
                            Double.parseDouble(spliting[2]), 
                            spliting[3], 
                            spliting[4], 
                            Double.parseDouble(spliting[5]), 
                            format.parse(spliting[6]));
                }
                
                listener.makeTablePane(tableInfo);
            }else {
                if(testing.equals("ERROR")) {
                    javax.swing.JOptionPane.showMessageDialog(null,"Erreur d'execution de la requête.");
                }else   {
                    javax.swing.JOptionPane.showMessageDialog(null,"Aucun prêt ne correspond à votre demande.");
                }
                listener.setIHM();
            }
            
        } catch (IOException | HeadlessException e) {
          Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconError.png")).getImage().getScaledInstance(75, 75, 1);
            JOptionPane.showMessageDialog(null, 
              "Le serveur ne répond plus", 
              "Erreur", 
              JOptionPane.WARNING_MESSAGE,
              new ImageIcon(im));
        }
        
    }
    
    /**
     * Communicate with server for get data for fill piechart count loan by type
     * @param idAgency
     * @return 
     */
    public DefaultPieDataset getPieDatasetLoanPerType(int idAgency)    {

        DefaultPieDataset dataset = null;

        try {
            out.println("SPECIF_1/LoanPerType/" + idAgency);
            out.flush();

            datasetPieChart response = s.unserializedatasetPieChart(in.readLine());
            dataset = response.getDataSet();
        } catch (IOException | NumberFormatException e) {
            Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconError.png")).getImage().getScaledInstance(75, 75, 1);
            JOptionPane.showMessageDialog(null, 
              "Le serveur ne répond plus", 
              "Erreur", 
              JOptionPane.WARNING_MESSAGE,
              new ImageIcon(im));
            System.exit(0);
        }

        return dataset;
    }

    /**
     * Communicate with server for get data for fill barchart with loan by type by years
     * @param idAgency
     * @return 
     */
    public DefaultCategoryDataset getBarDatasetLoanPerTypeByYears(int idAgency)    {

        DefaultCategoryDataset dataset = null;

        try {
            out.println("SPECIF_1/LoanPerTypeByYear/" + idAgency);
            out.flush();

            datasetBarChart response = s.unserializedatasetBarChart(in.readLine());
            dataset = response.getDataSet();
        } catch (IOException | NumberFormatException e) {
            Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconError.png")).getImage().getScaledInstance(75, 75, 1);
            JOptionPane.showMessageDialog(null, 
              "Le serveur ne répond plus", 
              "Erreur", 
              JOptionPane.WARNING_MESSAGE,
              new ImageIcon(im));
            System.exit(0);
        }

        return dataset;
    }

    /**
     * Communicate with server for get data for fill piechart profit by advisor
     * @param idAgency
     * @return 
     */
    public DefaultPieDataset getPieDatasetLoanPerAdvisor(int idAgency)    {

        DefaultPieDataset dataset = null;

        try {
            out.println("SPECIF_1/LoanPerAdvisor/" + idAgency);
            out.flush();

            datasetPieChart response = s.unserializedatasetPieChart(in.readLine());
            dataset = response.getDataSet();
        } catch (IOException | NumberFormatException e) {
            Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconError.png")).getImage().getScaledInstance(75, 75, 1);
            JOptionPane.showMessageDialog(null, 
              "Le serveur ne répond plus", 
              "Erreur", 
              JOptionPane.WARNING_MESSAGE,
              new ImageIcon(im));
            System.exit(0);
        }

        return dataset;
    }

    public void openManual() throws IOException    {
        java.awt.Desktop.getDesktop().open(new File(WelcomeViewClient.class.getResource("/document/Manual.pdf").getFile()));
    }
    
}
