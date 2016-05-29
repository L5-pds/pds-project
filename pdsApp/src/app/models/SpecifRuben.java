package app.models;

public class SpecifRuben {

    int id_client;

    String last_name;

    String first_name;

    String mail;

    int id_type_loan;

    String wording;

    double rate;

    int id_loan;

    double amount;
    
    int length_loan;

    public SpecifRuben(int id_client, String last_name, String first_name, String mail, int id_type_loan, String wording, double rate, int id_loan, double amount, int length_loan) {
        this.id_client = id_client;
        this.last_name = last_name;
        this.first_name = first_name;
        this.mail = mail;
        this.id_type_loan = id_type_loan;
        this.wording = wording;
        this.rate = rate;
        this.id_loan = id_loan;
        this.amount = amount;
        this.length_loan=length_loan;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getLength_loan() {
        return length_loan;
    }
    
    public void setLength_loan(int length_loan) {
        this.length_loan = length_loan;
    }
    
    
    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getId_type_loan() {
        return id_type_loan;
    }

    public void setId_type_loan(int id_type_loan) {
        this.id_type_loan = id_type_loan;
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

    public int getId_loan() {
        return id_loan;
    }

    public void setId_loan(int id_loan) {
        this.id_loan = id_loan;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
