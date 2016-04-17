package All_Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SerialisationPersonne {

  final GsonBuilder builder;
  final Gson gson;

  public SerialisationPersonne() {
    builder = new GsonBuilder();
    gson = builder.create();
  }

  public String serialiser(Personne p) {
    return gson.toJson(p);
  }

  public String serialiser(New_client nc) {
    return gson.toJson(nc);
  }

  public Personne deserialiser(String p) {
    return gson.fromJson(p, Personne.class);
  }

}
