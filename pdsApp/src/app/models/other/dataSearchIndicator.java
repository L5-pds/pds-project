/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.models.other;

import java.util.ArrayList;

/**
 *
 * @author Thibault
 */
public class dataSearchIndicator {
    private ArrayList dataAdvisor;
    private ArrayList dataTypeLoan;
    
    public dataSearchIndicator()    {
        dataAdvisor = new ArrayList();
        dataTypeLoan = new ArrayList();
    }
    
    public void setAdvisorList(ArrayList dataAdvisor) {
        this.dataAdvisor = dataAdvisor;
    }
    
    public void setTypeLoanList(ArrayList dataTypeLoan) {
        this.dataTypeLoan = dataTypeLoan;
    }
    
    public ArrayList getAdvisor()   {
        return this.dataAdvisor;
    }
    
    public ArrayList getTypeLoan()   {
        return this.dataTypeLoan;
    }
}
