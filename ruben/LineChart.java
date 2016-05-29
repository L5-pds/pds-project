package app.views.ruben;

import java.awt.event.MouseAdapter;
import javafx.scene.input.MouseEvent;
import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChart extends ApplicationFrame
{
    ChartPanel chartPanel;
    public javax.swing.JButton printButton;
    String data[][];
   
    public LineChart (String applicationTitle , String chartTitle ,  String data[][]){
        super(applicationTitle);
        this.data=data;
        JFreeChart lineChart = ChartFactory.createLineChart(
            chartTitle,
            "Mois","Montant restant à payer",
            createDataset(),
            PlotOrientation.VERTICAL,
            true,true,false);
      chartPanel = new ChartPanel(lineChart);
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      setContentPane(chartPanel);
      datamouse();
   }
        
    public void datamouse(){
           chartPanel.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent me) {
            chartPanel.getEntityForPoint(getX(),getY());
          }
        });
}
    
   private DefaultCategoryDataset createDataset( )
   {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for (int i=1; i<48;i++){
            dataset.addValue(Double.parseDouble(data[i][6].replace(",",".")), "montant" , String.valueOf(i));
            chartPanel.getEntityForPoint(getX(),getY());
        }
      return dataset;
   }
}


//mouse adapter
//Mettre le tout dans la meme frame
