/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.models.other;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javafx.scene.layout.Border;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Thibault
 */
public class PaneSearchIndicator {
    
    private DefaultTableModel theModel;
    
    private int nbLoan;
    private double moyAmount;
    private int moyLenght;
    private double allBenefit;
    
    public PaneSearchIndicator(){
        theModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        theModel.addColumn("Client");
        theModel.addColumn("Conseillé");
        theModel.addColumn("Montant");
        theModel.addColumn("Durée");
        theModel.addColumn("Type");
        theModel.addColumn("Taux");
        theModel.addColumn("Date");
        
    }

    public PaneSearchIndicator(int nbLoan, 
            double moyAmount, 
            int moyLenght, 
            double allBenefit){
        
        this.nbLoan = nbLoan;
        this.moyAmount = moyAmount;
        this.moyLenght = moyLenght;
        this.allBenefit = allBenefit;
        
        theModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        theModel.addColumn("Client");
        theModel.addColumn("Conseillé");
        theModel.addColumn("Montant");
        theModel.addColumn("Durée");
        theModel.addColumn("Type");
        theModel.addColumn("Taux");
        theModel.addColumn("Date");
        
    }

    public void addRow(String customer, 
            String advisor, 
            String amount, 
            String lenght, 
            String type, 
            String rate, 
            String date){
        theModel.addRow(new Object[]{customer, advisor, amount, lenght, type, rate, date});
    }
    
    public JScrollPane getAllTable() {
        JTable theTable = new JTable(this.theModel);
        theTable.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 15));
        theTable.getTableHeader().setBackground(new Color(215,203,233,255));
        theTable.setFont(new java.awt.Font("Verdana", 0, 15));
        theTable.setBorder((BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(255, 255, 255, 100))));
        theTable.setRowHeight(25);
        theTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        theTable.setBackground(new Color(215,203,233,255));
        
        JScrollPane returnPane = new JScrollPane(theTable);
        returnPane.setBackground(new Color(215,203,233,255));
        returnPane.getViewport().setBackground(new Color(215,203,233,255));
        returnPane.setBorder(null);
        
        returnPane.revalidate();
        returnPane.repaint();
        
        return returnPane;
    }
    
    public JPanel getInfoPane() {
        JPanel returnPane = new JPanel();
        returnPane.setLayout(new BoxLayout(returnPane, BoxLayout.Y_AXIS));
        returnPane.setBackground(new Color(215,203,233,255));
        
        JLabel lblTitle = new JLabel("     Résultats des indicateurs     ");
        lblTitle.setFont(new java.awt.Font("Verdana", Font.BOLD, 17)); // NOI18N
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel info1 = new JLabel();
        info1.setFont(new Font("Verdana", Font.PLAIN, 18));
        info1.setText("Nombre de prêts: " + nbLoan);
        info1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        info1.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel info11 = new JLabel();
        info11.setFont(new Font("Verdana", Font.PLAIN, 15));
        info11.setText("Immobilier (23%)");
        info11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        info11.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel info12 = new JLabel();
        info12.setFont(new Font("Verdana", Font.PLAIN, 15));
        info12.setText("Automobile (47%)");
        info12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        info12.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel info13 = new JLabel();
        info13.setFont(new Font("Verdana", Font.PLAIN, 15));
        info13.setText("Divers (30%)");
        info13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        info13.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel info2 = new JLabel();
        info2.setFont(new Font("Verdana", Font.PLAIN, 18));
        info2.setText("Montant moyen: " + moyAmount + "€");
        info2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        info2.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel info3 = new JLabel();
        info3.setFont(new Font("Verdana", Font.PLAIN, 18));
        info3.setText("Durée moyenne: " + moyLenght + " mois");
        info3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        info3.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel info4 = new JLabel();
        info4.setFont(new Font("Verdana", Font.PLAIN, 18));
        info4.setText("Bénéfice total: " + allBenefit + "€");
        info4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        info4.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        returnPane.add(lblTitle);
        returnPane.add(new JLabel(" "));
        returnPane.add(new JLabel(" "));
        returnPane.add(info1);
        returnPane.add(info11);
        returnPane.add(info12);
        returnPane.add(info13);
        returnPane.add(new JLabel(" "));
        returnPane.add(info2);
        returnPane.add(new JLabel(" "));
        returnPane.add(info3);
        returnPane.add(new JLabel(" "));
        returnPane.add(info4);
        
        return returnPane;
    }
    
}
