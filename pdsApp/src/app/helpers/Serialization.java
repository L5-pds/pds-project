package app.helpers;

import app.models.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Serialization {

  final GsonBuilder builder;
  final Gson gson;

  public Serialization() {
    builder = new GsonBuilder();
    gson = builder.create();
  }

  public String serializeUser(Advisor p) {
    return gson.toJson(p);
  }

  public String serializeCustomer(Customer c) {
      return gson.toJson(c);
  }

  public String serializeAddress(Address a) {
      return gson.toJson(a);
  }

  public Advisor unserializeUser(String u) {
      return gson.fromJson(u, Advisor.class);
  }

  public Customer unserializeCustomer(String c) {
      return gson.fromJson(c, Customer.class);
  }

  public Address unserializeAddress(String a) {
      return gson.fromJson(a, Address.class);
  }

}
