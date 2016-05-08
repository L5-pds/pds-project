package use_library;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

import org.jfree.ui.RefineryUtilities;

import java.awt.print.*;

public class HelloWorldPrinter implements Printable, ActionListener {

  static BarChart_AWT chart = new BarChart_AWT("Car Usage Statistics", "Which car do you like?");

    public int print(Graphics g, PageFormat pf, int pi) throws PrinterException {



      if(pi>=2) return NO_SUCH_PAGE;
        g.setFont(new Font("arial", Font.BOLD, 30));
        FontMetrics fm = g.getFontMetrics();
        int X = (int) pf.getImageableX(), Y = (int) pf.getImageableY();
        int W = (int) pf.getImageableWidth(), H = (int) pf.getImageableHeight();
        Rectangle2D r = fm.getStringBounds("" + pi, g);
         // Cadre noir autour de la partie Imageable...
        g.drawRect(X+1, Y+1, W-2, H-2);
         // impression du numéro de page
         // en rouge et au milieu de la page
        int x = (int) (W - r.getWidth()) / 2;
        int y = (int) ((H - r.getHeight()) / 2);
        g.setColor(Color.red);
        g.drawString("" + pi, X+x, (int)(Y+y+r.getHeight()));
        return PAGE_EXISTS;

    }

    public void actionPerformed(ActionEvent e) {
         PrinterJob job = PrinterJob.getPrinterJob();
         job.setPrintable(this);
         boolean ok = job.printDialog();
         if (ok) {
             try {
                  job.print();

             } catch (PrinterException ex) {
              /* The job did not successfully complete */
             }
         }
    }

    public static void main(String args[]) {

      UIManager.put("swing.boldMetal", Boolean.FALSE);
      chart.pack( );
      RefineryUtilities.centerFrameOnScreen( chart );
      chart.setVisible( true );

        chart.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {
             System.exit(0);}
        });

        chart.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JButton printButton = new JButton("Print it");
        printButton.addActionListener(new HelloWorldPrinter());
        chart.add(printButton);

        chart.pack();
        chart.setVisible(true);
    }
}
