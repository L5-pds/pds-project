package app.views.alexandre;

import app.models.Customer;

public class FixedRateSimulation {
    
    private Customer customer;
    private LoanType type;
    private Insurance insurance;
    private FixedRateLoan loan;
    
    private float totalRate; // taux total (taux d'interet + taux assurance)
    
    public FixedRateSimulation()    {
        this.customer = new Customer();
        //this.type = new LoanType();
        this.insurance = new Insurance(1,1,2,"");
        this.loan = new FixedRateLoan();
    }
    
    public void setLoanType(LoanType lt) {
        type = lt;
    }
    
    public void setInsurance(Insurance ins) {
        insurance = ins;
    }
    
    public Insurance getInsurance() {
        return insurance;
    }
    
    public LoanType getLoanType() {
        return type;
    }
    
    public FixedRateLoan getLoan() {
        return loan;
    }
    
    public float getTotalRate() {
        return totalRate;
    }
}