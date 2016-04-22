package app;

public class Customer {

  @SuppressWarnings("unused")
  private String lastName;
  @SuppressWarnings("unused")
  private String firstName;
  @SuppressWarnings("unused")
  private String mail;

  @SuppressWarnings("unused")
  private String addressNb;
  @SuppressWarnings("unused")
  private String street;
  @SuppressWarnings("unused")
  private String zipCode;

  Customer(String lastName,
        String firstName,
        String mail,
        String addressNb,
        String street,
        String zipCode){

    this.lastName = lastName;
    this.firstName = firstName;
    this.mail = mail;
    this.addressNb = addressNb;
    this.street = street;
    this.zipCode = zipCode;
  }

}
