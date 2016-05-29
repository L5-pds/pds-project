package app.models;


public class TypeLoan {
    int type_loan;
    String wording;
    double rate;
    int length_min;
    int length_max;
    double amout_min;
    double amount_max;

public TypeLoan(int type_loan,
    String wording,
    double rate,
    int length_min,
    int length_max,
    double amout_min,
    double amount_max){
    
    this.type_loan=type_loan;
    this.wording=wording;
    this.rate=rate;
    this.length_min=length_min;
    this.length_max=length_max;
    this.amout_min=amout_min;
    this.amount_max=amount_max;

}
    public int getType_loan() {
        return type_loan;
    }

    public void setType_loan(int type_loan) {
        this.type_loan = type_loan;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getLength_min() {
        return length_min;
    }

    public void setLength_min(int length_min) {
        this.length_min = length_min;
    }

    public int getLength_max() {
        return length_max;
    }

    public void setLength_max(int length_max) {
        this.length_max = length_max;
    }

    public double getAmout_min() {
        return amout_min;
    }

    public void setAmout_min(double amout_min) {
        this.amout_min = amout_min;
    }

    public double getAmount_max() {
        return amount_max;
    }

    public void setAmount_max(double amount_max) {
        this.amount_max = amount_max;
    }
}