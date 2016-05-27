package app.views.alexandre;

public class FixedRateLoan {
    
    private int id=1;
    private String wording="Immobilier";
    private float interestRate=2; // taux d'interet
    private int amount=10000; // montant
    private int duration=48; // duree en mois
    private int monthlyPayment; // paiement mensuel
    
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

    public int getMonthlyPayment() {
        return monthlyPayment;
    }
    
}
