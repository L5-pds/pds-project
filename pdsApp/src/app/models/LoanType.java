package app.models;

public class LoanType {
    
    private String wording; // loan type
    private float rate; // rate of the loan type
    private int minLength; // minimum length of the loan type in months
    private int maxLength; // maximum length of the loan type in months
    private int minAmount; // minimum amount of the loan type
    private int maxAmount; // maximum amount of the loan type

    // returns the wording of the loan type
    public String getWording() {
        return wording;
    }

    // returns the rate of the loan type
    public float getRate() {
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
