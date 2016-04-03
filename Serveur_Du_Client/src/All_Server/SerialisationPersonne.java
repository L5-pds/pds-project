package All_Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SerialisationPersonne {
    
    /**********************************
    ecrire une classe qui serialise n'importe quel objet ?
    ***********************************/
    
    final GsonBuilder builder;
    final Gson gson;
    
    public SerialisationPersonne() {
        builder = new GsonBuilder();
        gson = builder.create();
    }
    
    public String serialiser(Personne p) {
        return gson.toJson(p);
    }
    
    public Personne deserialiser(String p) {
        return gson.fromJson(p, Personne.class);
    }
    
}
