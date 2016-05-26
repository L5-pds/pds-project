package app.models;

public class FixedRateSimulation {
    
    private Customer customer;
    private LoanType type;
    private Insurance insurance;
    
    private String wording;
    private float rate;
    private int amount;
    private int duration;
    private int monthlyPayment;

    public void setLoanType(LoanType lt) {
        type = lt;
    }
    
    public void setInsurance(Insurance ins) {
        insurance = ins;
    }
}