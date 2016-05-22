
package app.models.other;

import java.util.ArrayList;
import org.jfree.data.general.DefaultPieDataset;

public class datasetPieChart {
    private String key;
    private int value;
    private ArrayList<datasetPieChart> collect;
    
    public datasetPieChart()    {
        this.key = "";
        this.value = -1;
        this.collect = new ArrayList();
    }
    
    public datasetPieChart(String key, int value)    {
        this.key = key;
        this.value = value;
        this.collect = null;
    }
    
    public String getKey()  {
        return this.key;
    }
    
    public int getValue()   {
        return this.value;
    }
    
    public void addToCollect(String key, int value)  {
        this.collect.add(new datasetPieChart(key, value));
    }
    
    public DefaultPieDataset getDataSet()   {
        
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        for(int i=0 ; i<this.collect.size() ; i++)  {
            dataset.setValue(this.collect.get(i).getKey(), this.collect.get(i).getValue());
        }
        
        return dataset;
    }
}
