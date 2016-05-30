package app.models;

public class FixedRateSimulation {
    
    private Customer customer;
    private LoanType type;
    private Insurance insurance;
    
    private int id;
    private String wording;
    private double interestRate;
    private int amount;
    private int duration;
    private double monthlyPayment;
    private double totalRate; // total loan rate (interest rate + insurance rate)
    private double owedAmount;
    
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
    
    public void setInterestRate(double r) {
        interestRate = r;
    }

    public String getWording() {
        return wording;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getAmount() {
        return amount;
    }

    public int getDuration() {
        return duration;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }
    
    public void setMonthlyPayment(double mp) {
        monthlyPayment = mp;
    }
    
    public double getTotalRate() {
        return totalRate;
    }
 
    public void setDuration(int d) {
        duration = d;
    }
    
    public void setAmount(int a) {
        amount = a;
    }
    
    public double getOwedAmount() {
        return owedAmount;
    }
    
    public void setCustomer(Customer c) {
        customer = c;
    }
    
    public void setLoanWording(String w) {
        wording = w;
    }
    
    public int getId() {
        return id;
    }
}