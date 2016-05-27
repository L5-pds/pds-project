package app.views.ruben;

import app.views.welcome.WelcomeViewClient;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class Chart extends JPanel {
// Variables declaration - do not modify   

    JPanel paneLeft = new JPanel();
    JPanel paneRight = new JPanel();
    JPanel paneCenter = new JPanel();
    public ChartPanel pan;
    DefaultPieDataset pieChart;
    public Calcul ca;
    DecimalFormat df = new DecimalFormat("0.00");//Convert a double with 2 number after decimal point
    private JButton printChart;
// End of variables declaration                   

    public Chart(Calcul ca) {
        this.ca = ca;
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setBackground(new Color(215, 203, 233, 255));
        this.setPreferredSize(new Dimension(500, 400));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initComponents();
        calculChart();
    }

    public void calculChart() {

        calculPieChart();
        JFreeChart chart = ChartFactory.createPieChart("Répartition Capital Amorti/Interets/Assurance", pieChart, true, true, true);

        pan = new ChartPanel(chart, false);
        pan.setBounds(0, 0, 390, 300);
        this.add(pan);
    }

    private void initComponents() {
        ImageIcon trait = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconPrinter.png"));
        Image im = trait.getImage();
        im = im.getScaledInstance(60, 60, 1);
        JLabel buttonPrint = new JLabel(new ImageIcon(im));
        buttonPrint.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                printChartActionPerformed(evt);
            }
              public void mouseEntered(MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconPrinterHover.png")).getImage().getScaledInstance(50, 50, 1);
                buttonPrint.setIcon(new ImageIcon(im));
            }

            public void mouseExited(MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconPrinter.png")).getImage().getScaledInstance(50, 50, 1);
                buttonPrint.setIcon(new ImageIcon(im));
            }
        });
        this.add(buttonPrint);
        this.add(new JLabel(" "));
    }

    public void calculPieChart() {
        pieChart = new DefaultPieDataset();
        pieChart.setValue("Capital amorti " + df.format(ca.totalMonthlyPayement / (ca.totalinsurance + ca.totalMonthlyPayement + ca.totalInterest) * 100) + "%", (ca.totalMonthlyPayement / (ca.totalinsurance + ca.totalMonthlyPayement + ca.totalInterest) * 100));
        pieChart.setValue("Interets " + df.format(ca.totalInterest / (ca.totalinsurance + ca.totalMonthlyPayement + ca.totalInterest) * 100) + "%", (ca.totalInterest / (ca.totalinsurance + ca.totalMonthlyPayement + ca.totalInterest) * 100));
        pieChart.setValue("Assurance " + (df.format(ca.totalinsurance / (ca.totalinsurance + ca.totalMonthlyPayement + ca.totalInterest) * 100)) + "%", (ca.totalinsurance / (ca.totalinsurance + ca.totalMonthlyPayement + ca.totalInterest) * 100));
    }

    private void printChartActionPerformed(java.awt.event.MouseEvent evt) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(pan);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
            }
        }
    }
}
