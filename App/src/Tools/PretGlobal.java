package Tools;

import java.sql.ResultSet;
import java.sql.SQLException;

import BDD.BDD_Tools;

public class PretGlobal {
	
	private int id_client;
	private String type_pret;
	private int duree_pret;
	private int montant_pret;
	
	BDD_Tools Client = new BDD_Tools();
	ResultSet LeRetour=null;
	
	public PretGlobal()	{
		this.id_client=0;
		this.type_pret="";
		this.duree_pret=0;
		this.montant_pret=0;
	}
	
	public int getClient()	{
		return this.id_client;
	}
	
	public String getTypepret()	{
		return this.type_pret;
	}
	
	public int getDuree()	{
		return this.duree_pret;
	}
	
	public int getMontant()	{
		return this.montant_pret;
	}
	
	public void setClient(int idclient)	{
		this.id_client=idclient;
	}
	
	public void setTypepret(String typepret)	{
		this.type_pret=typepret;
	}
	
	public void setDuree(int duree)	{
		this.duree_pret=duree;
	}
	
	public void setMontant(int montant)	{
		this.montant_pret=montant;
	}
	
	public double CalculeMensualit()	{
		
		double mensualite=0;
		double puiss=0;
		double taux=0;
		
		try{       
			LeRetour=Client.RequeteReponse("SELECT taux_type_pret FROM T_TYPE_PRET WHERE libelle_type_pret = '" + this.type_pret + "'");
			while(LeRetour.next()){ 
				taux=LeRetour.getDouble("taux_type_pret");
            }
        }catch(SQLException e){
        	// TODO Auto-generated catch block
			e.printStackTrace();
        }
		
		
		
		puiss=Math.pow((1+(taux/1200)), (-1)*this.duree_pret);
		
		mensualite=((this.montant_pret*(taux/1200))/(1-(float)puiss));
		
		return mensualite;
	}
	
}
