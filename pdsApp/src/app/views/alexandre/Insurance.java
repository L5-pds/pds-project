package app.views.alexandre;

public class Insurance {
    
    private int insuranceId;
    private int loanTypeId;
    private float rate; // taux d'assurance
    private String wording;
    
    public Insurance(int id, int ltid, float r, String w) {
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
    
    public float getRate() {
        return rate;
    }
}
