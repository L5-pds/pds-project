package app.views.ruben;

import app.views.welcome.WelcomeViewClient;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javafx.scene.input.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChart extends JPanel
{
    JPanel contentPane;
    ChartPanel chartPanel;
    public javax.swing.JButton printButton;
    String data[][];
    Calcul theCalc;
    public LineChart (Calcul tmp, String applicationTitle , String chartTitle ,  String data[][]){
        this.theCalc = tmp;
        this.data=data;
        JFreeChart lineChart = ChartFactory.createLineChart(
            chartTitle,
            "Mois","Montant restant à payer",
            createDataset(),
            PlotOrientation.VERTICAL,
            true,true,false);
        chartPanel = new ChartPanel(lineChart);   
        chartPanel.setPreferredSize(new Dimension(500, 400));
      //contentPane = new JPanel();
      //scontentPane.add(chartPanel);
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      this.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setBackground(new Color(215,203,233,255));
      this.setPreferredSize (new Dimension(500, 400));
      initComponents();
      datamouse();
      this.add(chartPanel);
   }
        
    public void datamouse(){
           chartPanel.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent me) {
            chartPanel.getEntityForPoint(getX(),getY());
          }
        });
}
    
   private DefaultCategoryDataset createDataset()
   {
       Calcul ca = this.theCalc;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for (int i=1; i<ca.number_month;i+=3){
            dataset.addValue(Double.parseDouble(data[i][6].replace(",",".")), "Montant à payer" , String.valueOf(i));
        }
      return dataset;
   }
   
    private void initComponents() {
    ImageIcon trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconPrinter.png"));
    Image im = trait.getImage();
    im  = im.getScaledInstance(60,60,1);
    JLabel buttonPrint = new JLabel(new ImageIcon(im));
    buttonPrint.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                printChartActionPerformed(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconPrinterHover.png")).getImage().getScaledInstance(50, 50, 1);
                buttonPrint.setIcon(new ImageIcon(im));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconPrinter.png")).getImage().getScaledInstance(50, 50, 1);
                buttonPrint.setIcon(new ImageIcon(im));
            }
        });
        this.add(buttonPrint);
        this.add(new JLabel(" "));

    }                        

    private void printChartActionPerformed(java.awt.event.MouseEvent evt) {                                           
       // TODO add your handling code here:
       PrinterJob job = PrinterJob.getPrinterJob();
         job.setPrintable(chartPanel);
         boolean ok = job.printDialog();
         if (ok) {
             try {
                  job.print();
             } catch (PrinterException ex) {
              /* The job did not successfully complete */
             }
         }
       
    } 
}