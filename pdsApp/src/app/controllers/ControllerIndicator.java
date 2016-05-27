package app.controllers;

import app.helpers.Serialization;
import app.listeners.*;
import app.models.other.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public ControllerIndicator(Socket socket) {
        this.socket = socket;
        this.s = new Serialization();
        try {
          out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
          javax.swing.JOptionPane.showMessageDialog(null,"Le message est : " + e.getMessage());
        }
    }

    public void addListener(ListenerIndicator l) {
        this.listener = l;
        listener.setIHM();
    }

    public void testmsg(String tes) {
        javax.swing.JOptionPane.showMessageDialog(null,"Le message est : " + tes);
    }

    public dataSearchIndicator getComboData(int idAgency)  {
        
        dataSearchIndicator dataComposent = new dataSearchIndicator();
        
        try {
            out.println("SPECIF_1/SelectDataSearch/" + idAgency);
            out.flush();
            dataComposent = s.unserializeDataSearchIndicator(in.readLine());
        } catch (IOException | HeadlessException e) {
          javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus");
        }
        return dataComposent;
    }
    
    public void refreshAllPane()    {
        listener.setIHM();
    }
    
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
          javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus");
        }
        
    }
    
    public String testCountAddress()  {
        String resutl = " ";
        try {
        out.println("SPECIF_1/Address/COUNT");
        out.flush();
        String response = in.readLine();
        String[] splitedQuery = response.split("/");

        String ifsuccess = splitedQuery[0];
        resutl = splitedQuery[1];
        if(ifsuccess.equals("success")) {
            javax.swing.JOptionPane.showMessageDialog(null,"Il y a " + resutl + " adresses dans la base");
        }else {
            javax.swing.JOptionPane.showMessageDialog(null,"Erreur : " + response);
        }
        } catch (IOException | HeadlessException e) {
          javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus");
        }
        return resutl;
    }

    public DefaultPieDataset getPieDatasetLoanPerType(int idAgency)    {

        DefaultPieDataset dataset = null;

        try {
            out.println("SPECIF_1/LoanPerType/" + idAgency);
            out.flush();

            datasetPieChart response = s.unserializedatasetPieChart(in.readLine());
            dataset = response.getDataSet();
        } catch (IOException | NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus");
            System.exit(0);
        }

        return dataset;
    }

    public DefaultCategoryDataset getBarDatasetLoanPerTypeByYears(int idAgency)    {

        DefaultCategoryDataset dataset = null;

        try {
            out.println("SPECIF_1/LoanPerTypeByYear/" + idAgency);
            out.flush();

            datasetBarChart response = s.unserializedatasetBarChart(in.readLine());
            dataset = response.getDataSet();
        } catch (IOException | NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus");
            System.exit(0);
        }

        return dataset;
    }

    public DefaultPieDataset getPieDatasetLoanPerAdvisor(int idAgency)    {

        DefaultPieDataset dataset = null;

        try {
            out.println("SPECIF_1/LoanPerAdvisor/" + idAgency);
            out.flush();

            datasetPieChart response = s.unserializedatasetPieChart(in.readLine());
            dataset = response.getDataSet();
        } catch (IOException | NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus");
            System.exit(0);
        }

        return dataset;
    }

    public JPanel getAdvisorClassement(int idAgency)    {

        JPanel thePane = new JPanel();
        thePane.setLayout(new BoxLayout(thePane, BoxLayout.Y_AXIS));
        thePane.setBackground(new Color(0,0,0,0));

        try {
            out.println("SPECIF_1/AdvisorClassement/" + idAgency);
            out.flush();
            ArrayList<String> responseAll = s.unserializeArrayList(in.readLine());

            ArrayList<JLabel> lblTab = new ArrayList();

            for(int i=0 ; i<responseAll.size() ; i++)   {
                JLabel tmp = new JLabel();
                tmp.setFont(new java.awt.Font("Verdana", 0, 20)); // NOI18N
                tmp.setAlignmentX(Component.CENTER_ALIGNMENT);
                tmp.setText(responseAll.get(i));

                lblTab.add(tmp);
            }
            for(int i=0 ; i<lblTab.size() ; i++)   {
                thePane.add(lblTab.get(i));
            }

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus");
            System.exit(0);
        }
        return thePane;
    }
}
