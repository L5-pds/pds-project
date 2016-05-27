package app.models;

import java.util.Date;


public class Loan {
  
    int id_loan;
    String wording;
    double amount;
    int length_loan;
    String type_length_loan;
    String type_rate_loan;
    int id_type_loan;
    int id_client;
    int id_advisor;
    Date entry;
    
    
    
    public Loan(int id_loan, 
            String wording,
            double amount,
            int length_loan,
            String type_length_loan,
            String type_rate_loan,
            int id_type_loan,
            int id_client,
            int id_advisor,
            Date entry){
        
        this.id_loan=id_loan;
        this.wording=wording;
        this.amount=amount;
        this.length_loan=length_loan;
        this.type_length_loan=type_length_loan;
        this.type_rate_loan=type_rate_loan;
        this.id_type_loan=id_type_loan;
        this.id_client=id_client;
        this.id_advisor=id_advisor;
        this.entry=entry;
   
    }

    public int getId_loan() {
        return id_loan;
    }

    public void setId_loan(int id_loan) {
        this.id_loan = id_loan;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getLength_loan() {
        return length_loan;
    }

    public void setLength_loan(int length_loan) {
        this.length_loan = length_loan;
    }

    public String getType_length_loan() {
        return type_length_loan;
    }

    public void setType_length_loan(String type_length_loan) {
        this.type_length_loan = type_length_loan;
    }

    public String getType_rate_loan() {
        return type_rate_loan;
    }

    public void setType_rate_loan(String type_rate_loan) {
        this.type_rate_loan = type_rate_loan;
    }

    public int getId_type_loan() {
        return id_type_loan;
    }

    public void setId_type_loan(int id_type_loan) {
        this.id_type_loan = id_type_loan;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_advisor() {
        return id_advisor;
    }

    public void setId_advisor(int id_advisor) {
        this.id_advisor = id_advisor;
    }

    public Date getEntry() {
        return entry;
    }

    public void setEntry(Date entry) {
        this.entry = entry;
    }
}
