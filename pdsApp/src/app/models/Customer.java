package app.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Customer {
  private int id;
  private int id_agency;
  private int id_address;
  private String lastName;
  private String firstName;
  private String mail;
  private Double salary;
  private String password;
  private String birth;
  private String gender;

  public Customer()   {
    this.id = 0;
    this.id_agency = 0;
    this.id_address = 0;
    this.lastName = "";
    this.firstName = "";
    this.mail = "";
    this.salary = 0.0;
    this.password = "";
    this.birth = "";
    this.gender = "";
  }

  public Customer(int id,
                int id_agency,
                int id_address,
                String lastName,
                String firstName,
                String mail,
                Double salary,
                String password,
                String birth,
                String gender) {
    this.id = id;
    this.id_agency = id_agency;
    this.id_address = id_address;
    this.lastName = lastName;
    this.firstName = firstName;
    this.mail = mail;
    this.salary = salary;
    this.password = password;
    this.birth = birth;
    this.gender = gender;
  }

  public void serverGetAllUser(int indexPool) {

  }

  public void serverGetOnlyUser(int id) {

  }

  public void getCustomerCount() {

  }

}
