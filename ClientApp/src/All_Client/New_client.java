package All_Client;

public class New_client {

  @SuppressWarnings("unused")
  private String nom;
  @SuppressWarnings("unused")
  private String prenom;
  @SuppressWarnings("unused")
  private String mail;

  @SuppressWarnings("unused")
  private String adresse_num;
  @SuppressWarnings("unused")
  private String adresse_voie;
  @SuppressWarnings("unused")
  private String adresse_cp;

  New_client(String temps_nom,
        String temps_prenom,
        String temps_mail,
        String temps_anum,
        String temps_avoie,
        String temps_acp){

    nom=temps_nom;
    prenom=temps_prenom;
    mail=temps_mail;
    adresse_num=temps_anum;
    adresse_voie=temps_avoie;
    adresse_cp=temps_acp;
  }

}
