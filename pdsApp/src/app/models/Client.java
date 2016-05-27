
package app.models;

import java.util.Date;

public class Client {
    int id_client;
    String last_name;
    String first_name;
    String mail;
    double salary;
    String password;
    int id_agency;
    int id_adress;
    Date birth;
    String sex;
    
    
    
    public Client(int id_client,
    String last_name,
    String first_name,
    String mail,
    double salary,
    String password,
    int id_agency,
    int id_adress,
    Date birth,
    String sex){
        this.id_client=id_client;
        this.last_name=last_name;
        this.first_name=first_name;
        this.mail=mail;
        this.salary=salary;
        this.password=password;
        this.id_agency=id_agency;
        this.id_adress=id_adress;
        this.birth=birth;
        this.sex=sex;
   
    }
    
    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId_agency() {
        return id_agency;
    }

    public void setId_agency(int id_agency) {
        this.id_agency = id_agency;
    }

    public int getId_adress() {
        return id_adress;
    }

    public void setId_adress(int id_adress) {
        this.id_adress = id_adress;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    
}
