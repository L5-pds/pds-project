package app.models;

public class Insurance {
    
    private int insuranceId;
    private int loanTypeId;
    private double rate;
    private String wording;
    
    public Insurance() {
        
    }
    
    public Insurance(int id, int ltid, double r, String w) {
        insuranceId = id;
        loanTypeId = ltid;
        rate = r;
        wording = w;
    }
    
    public String toString() {
        return wording + " (" + rate + "%)";
    }

    public int getInsuranceId() {
        return insuranceId;
    }
    
    public double getRate() {
        return rate;
    }
    
    public void setRate(double r) {
        rate = r;
    } 
    
    public String getWording() {
        return wording;
    }
    
    public void setId(int i) {
        insuranceId = i;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }
    
    
}
