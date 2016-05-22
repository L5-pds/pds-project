package app.controllers;

import app.helpers.Serialization;
import app.listeners.*;
import app.models.other.datasetPieChart;
import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.data.general.DefaultPieDataset;

public class ControllerClientIndicator {
    private ListenerClientIndicator listener;

    private static Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Serialization s;

    public ControllerClientIndicator(Socket socket) {
        this.socket = socket;
        this.s = new Serialization();
        try {
          out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
          javax.swing.JOptionPane.showMessageDialog(null,"Le message est : " + e.getMessage());
        }
    }

    public void addListener(ListenerClientIndicator l) {
        this.listener = l;
        listener.setIHM();
    }

    public void testmsg(String tes) {
        javax.swing.JOptionPane.showMessageDialog(null,"Le message est : " + tes);
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
        } catch (Exception e) {
          javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus");
        }
        return resutl;
    }
    
    public DefaultPieDataset getPieDatasetLoanPerType()    {
        
        DefaultPieDataset dataset = null;
            
        try {
            out.println("SPECIF_1/LoanPerType/ ");
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
            JLabel lbl5 = new JLabel();
            lbl5.setFont(new java.awt.Font("Verdana", 0, 15)); // NOI18N
            lbl5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            lbl5.setAlignmentX(Component.CENTER_ALIGNMENT);
            lbl5.setText(responseAll.get(i));
            
            lblTab.add(lbl5);
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
