package app.models;

public class FixedRateSimulation {
    
    private Customer customer;
    private LoanType type;
    private Insurance insurance;
    
    private int id;
    private String wording;
    private float interestRate;
    private int amount;
    private int duration;
    private float monthlyPayment;
    private float totalRate; // total loan rate (interest rate + insurance rate)
    private float owedAmount;
    
    public void setLoanType(LoanType lt) {
        type = lt;
    }
    
    public void setInsurance(Insurance ins) {
        insurance = ins;
    }
	
	public Customer getCustomer() {
		return customer;
	}
    
    public Insurance getInsurance() {
        return insurance;
    }
    
    public LoanType getLoanType() {
        return type;
    }
    
    public void setInterestRate(float r) {
        interestRate = r;
    }

    public String getWording() {
        return wording;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public int getAmount() {
        return amount;
    }

    public int getDuration() {
        return duration;
    }

    public float getMonthlyPayment() {
        return monthlyPayment;
    }
    
    public void setMonthlyPayment(float mp) {
        monthlyPayment = mp;
    }
    
    public float getTotalRate() {
        return totalRate;
    }
 
    public void setDuration(int d) {
        duration = d;
    }
    
    public void setAmount(int a) {
        amount = a;
    }
    
    public float getOwedAmount() {
        return owedAmount;
    }
}