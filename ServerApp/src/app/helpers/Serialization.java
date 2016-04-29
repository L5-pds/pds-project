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

    public String serialize(User u) {
        return gson.toJson(u);
    }

    public String serialize(Customer c) {
        return gson.toJson(c);
    }

    public User unserializeUser(String u) {
        return gson.fromJson(u, User.class);
    }

    public Customer unserializeCustomer(String c) {
        return gson.fromJson(c, Customer.class);
    }

}
