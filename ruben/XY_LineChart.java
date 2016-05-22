
package app.views.ruben;

import java.awt.Color;
import javafx.geometry.Rectangle2D;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;


public class XY_LineChart extends ApplicationFrame {

    String title;
    String data[][];
    
    public XY_LineChart(final String title,String data[][]) {

        super(title);
        this.data=data;

        final XYDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
        

    }
    
    private XYDataset createDataset() {
        
        final XYSeries series1 = new XYSeries("Reste du remboursement");
         for (int i=1; i<48;i++){
            series1.add(Double.parseDouble(data[i][1].replace(",",".")), (i));
        }
        
        final XYSeries series2 = new XYSeries("Interêts");
        for (int i=1; i<48;i++){
            series2.add(Double.parseDouble(data[i][3].replace(",",".")), (i));
        }

       /* final XYSeries series3 = new XYSeries("Assurance");
                for (int i=1; i<48;i++){
            series3.add(Double.parseDouble(data[i][3].replace(",",".")), (i));
        }
       */     
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        //dataset.addSeries(series3);
          
    return dataset; 
    }
    
    
    
    
    
    
     
  private JFreeChart createChart(final XYDataset dataset) {
        
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Graphique représentatif de la simulation de l'emprunt",// chart title
            "Somme/Montant", // x axis label
            "Mois Numéro ",  // y axis label
            dataset,         // data
            PlotOrientation.VERTICAL,
            true,            // include legend
            true,            // tooltips
            false            // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        //plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
                
        return chart;
        
    }
  
}