package app.models;

public class Insurance {
    
    private int insuranceId;
    private int loanTypeId;
    private float rate;
    private String wording;
    
    public Insurance(int id, int ltid, float r, String w) {
        insuranceId = id;
        loanTypeId = ltid;
        rate = r;
        wording = w;
    }
    
    public String toString() {
        return wording + " (" + rate + ")";
    }
}
