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

  public String serialize(User p) {
    return gson.toJson(p);
  }

  public String serialize(Customer c) {
    return gson.toJson(c);
  }

  public User deserialize(String p) {
    return gson.fromJson(p, User.class);
  }

}
