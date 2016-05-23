/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.models.other;

import java.util.ArrayList;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Thibault
 */
public class datasetBarChart {
    private String rowKey;
    private String columnKey;
    private double value;
    private ArrayList<datasetBarChart> collect;
    
    public datasetBarChart()    {
        this.rowKey = "";
        this.columnKey = "";
        this.value = -1;
        this.collect = new ArrayList();
    }
    
    public datasetBarChart(double value, String rowKey, String columnKey)    {
        this.rowKey = rowKey;
        this.columnKey = columnKey;
        this.value = value;
        this.collect = null;
    }
    
    public String getRowKey()  {
        return this.rowKey;
    }
    
    public String getColumnKey()  {
        return this.columnKey;
    }
    
    public double getValue()   {
        return this.value;
    }
    
    public void addToCollect(double value, String rowKey, String columnKey)  {
        this.collect.add(new datasetBarChart(value, rowKey, columnKey));
    }
    
    public DefaultCategoryDataset getDataSet()   {
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for(int i=0 ; i<this.collect.size() ; i++)  {
            dataset.addValue(this.collect.get(i).getValue(), this.collect.get(i).getRowKey(), this.collect.get(i).getColumnKey());
        }
        
        return dataset;
    }
    
}
