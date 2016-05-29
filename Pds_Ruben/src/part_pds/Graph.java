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
import java.text.DecimalFormat;

public class Graph extends JFrame {

        CalcAm c = new CalcAm();
	public JPanel contentPane;
	public ChartPanel pan;
        DecimalFormat df = new DecimalFormat("0.00");// Convertir un double avec 2 chiffres apres la virgule

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Graph frame = new Graph();
					frame.data();
                                        frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

        public void data(){
            
           System.out.println(c.getName());
           System.out.println(c.getSurname());
           System.out.println(c.getTaux());
           System.out.println(c.getTauxdassurance());
           System.out.println(c.getNombre_annee());
           System.out.println(c.getTotalinteret());
           System.out.println(c.getTotalmensualite());
           System.out.println(c.getDateStr());
           System.out.println(c.getMontant());
           
           /*
           Affiche les chiffres avec harmonie
           int no = 124750;
           String str = String.format("%,d", no);
           System.out.println(str);
*/
        }
        
        
	/**
	 * Create the frame.
	 */
	public Graph() {
		
	        DefaultPieDataset test = new DefaultPieDataset();
                test.setValue("Capital amorti "+df.format(c.getTotalinteret()/c.getTotalmensualite()*100)+"%", c.getMontant());
	        test.setValue("Interets "+c.getTaux()*100+"%", c.getTaux()*100);
	        test.setValue("Assurance "+c.getTauxdassurance()+"%",c.getTauxdassurance());
	        JFreeChart chart = ChartFactory.createPieChart("Répartition Capital Amorti/Interets/Assurance", test, true, true, true);
	        
	        pan = new ChartPanel(chart, false);
	        pan.setBounds(0, 0, 200, 200);
	        getContentPane().add(pan, BorderLayout.NORTH);
	        JButton btnImprimer = new JButton("Imprimer");
	           
                System.out.println(df.format(c.getTotalassurance())+df.format(c.getTotalassurance())+""+df.format(c.getTotalinteret()));
	        // 4800  10000  413
                // 14000/15213 = 0,92
                
                // System.out.println((c.getTotalassurance()+c.getTotalmensualite())/(c.getTotalassurance()+c.getTotalinteret()+c.getTotalmensualite()));
                System.out.println((c.getTotalassurance()+c.getTotalmensualite()));
                getContentPane().add(btnImprimer, BorderLayout.SOUTH);
	        this.setVisible(true);
	        this.setSize(500,500);
	        	

	        
	        
	}
	
}
