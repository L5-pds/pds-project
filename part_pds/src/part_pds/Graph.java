package part_pds;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.MessageFormat;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.text.NumberFormat;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;

public class Graph extends JFrame {

	public JPanel contentPane;
	public ChartPanel pan;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Graph frame = new Graph();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Graph() {
		
		 	int leftToPay = 100;
	        int totalToPay = 1000;
	        int paid = 900;
	        
	        DefaultPieDataset test = new DefaultPieDataset();
	        test.setValue("Reste à payer: "+leftToPay+"E", new Integer(leftToPay));
	        test.setValue("Payé: "+paid+"E", new Integer(paid));
	        test.setValue("Total: "+totalToPay+"E", new Integer(0));
	        JFreeChart chart = ChartFactory.createPieChart("Prêt", test, true, true, true);
	        
	        
	        
	        pan = new ChartPanel(chart, false);
	        pan.setBounds(0, 0, 200, 200);

	        
	        
	        getContentPane().add(pan, BorderLayout.NORTH);
	        
	        JButton btnImprimer = new JButton("Imprimer");
	        btnImprimer.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	                printButtonActionPerformed(e);
	        	}

				public void printButtonActionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
			            /* print the table */
			            boolean complete = pan.print();

			            /* if printing completes */
			            if (complete) {
			                /* show a success message */
			                JOptionPane.showMessageDialog(this,
			                                              "Printing Complete",
			                                              "Printing Result",
			                                              JOptionPane.INFORMATION_MESSAGE);
			            } else {
			                /* show a message indicating that printing was cancelled */
			                JOptionPane.showMessageDialog(this,
			                                              "Printing Cancelled",
			                                              "Printing Result",
			                                              JOptionPane.INFORMATION_MESSAGE);
			            }
			        } catch (PrinterException pe) {
			            /* Printing failed, report to the user */
			            JOptionPane.showMessageDialog(this,
			                                          "Printing Failed: " + pe.getMessage(),
			                                          "Printing Result",
			                                          JOptionPane.ERROR_MESSAGE);
			        }
			        
				}
	        });
	        
	        
	        getContentPane().add(btnImprimer, BorderLayout.SOUTH);
	        this.setVisible(true);
	        this.setSize(500,500);
	        	

	        
	        
	}
	
}
