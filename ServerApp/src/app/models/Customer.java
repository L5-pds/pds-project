package app.models;

public class Customer {

  public String lastName;
  public String firstName;
  public String mail;

  public String addressNumber;
  public String street;
  public String zipCode;

  public Customer(String lastName,
        String firstName,
        String mail,
        String addressNumber,
        String street,
        String zipCode){

    this.lastName = lastName;
    this.firstName = firstName;
    this.mail = mail;
    this.addressNumber = addressNumber;
    this.street = street;
    this.zipCode = zipCode;
  }

}
