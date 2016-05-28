package app.models;

public class LoanType {
    
    private int loanTypeId; // loan type id
    private String wording; // loan type wording
    private double rate; // rate of the loan type
    private int minLength; // minimum length of the loan type in months
    private int maxLength; // maximum length of the loan type in months
    private int minAmount; // minimum amount of the loan type
    private int maxAmount; // maximum amount of the loan type

    public LoanType(int id, String w, double r, int minL, int maxL, int minA, int maxA) {
        loanTypeId = id;
        wording = w;
        rate = r;
        minLength = minL;
        maxLength = maxL;
        minAmount = minA;
        maxAmount = maxA;
    }
    
    public String toString() {
        return wording;
    }
    
    // returns the loan type id
    public int getId() {
        return loanTypeId;
    }
    
    // returns the wording of the loan type
    public String getWording() {
        return wording;
    }

    // returns the rate of the loan type
    public double getRate() {
        return rate;
    }

    // returns the minimum length of the loan type in months
    public int getMinLength() {
        return minLength;
    }

    // returns the maximum length of the loan type in months
    public int getMaxLength() {
        return maxLength;
    }

    // returns the minimum amount of the loan type
    public int getMinAmount() {
        return minAmount;
    }

    // returns the maximum amount of the loan type
    public int getMaxAmount() {
        return maxAmount;
    }
    
}
