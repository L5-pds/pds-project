package app.models;

public class Customer {
  private String lastName;
  private String firstName;
  private String mail;

  private String addressNumber;
  private String street;
  private String zipCode;

  Customer(String lastName,
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
