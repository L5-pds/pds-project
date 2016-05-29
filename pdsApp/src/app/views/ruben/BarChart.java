package app.views.ruben;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class BarChart extends JPanel {

    JPanel contentPane;
    ChartPanel chartPanel;
    public JButton printButton;
    String data[][];

    int number_month = 48;

    public BarChart(String applicationTitle, String chartTitle, String data[][]) {
        this.data = data;
        JFreeChart barChart = ChartFactory.createBarChart(chartTitle, "Category", "Score", createDataset(), PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(500, 400));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setBackground(new Color(215, 203, 233, 255));
        this.setPreferredSize(new Dimension(500, 400));
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < number_month; i += 12) {
            dataset.addValue(Double.parseDouble(data[i][6].replace(",", ".")), "Montant restant", "Année " + String.valueOf(i));
        }
        return dataset;
    }
}
