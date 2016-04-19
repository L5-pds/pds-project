package app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Serialization {

  final GsonBuilder builder;
  final Gson gson;

  public Serialization() {
    builder = new GsonBuilder();
    gson = builder.create();
  }

  public String serialiser(User p) {
    return gson.toJson(p);
  }

  public String serialiser(Customer c) {
    return gson.toJson(c);
  }

  public User deserialiser(String p) {
    return gson.fromJson(p, User.class);
  }

}
