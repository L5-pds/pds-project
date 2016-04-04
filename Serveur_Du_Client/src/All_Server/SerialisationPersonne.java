package All_Server;

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

    public Personne deserialiserp(String p) {
        return gson.fromJson(p, Personne.class);
    }
    
    public New_client deserialiserc(String p) {
        return gson.fromJson(p, New_client.class);
    }
    
}
