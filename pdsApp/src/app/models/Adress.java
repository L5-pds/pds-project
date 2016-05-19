package app.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Thibault
 */
public class Adress {
    
    private int id;
    private int street_nb;
    private String street_name;
    private String city_name;
    private String zip_code;
    
    public Adress() {
        this.id = 0;
        this.street_nb = 0;
        this.street_name = "";
        this.city_name = "";
        this.zip_code = "";
    }
    
    public Adress(int id,
            int street_nb,
            String street_name,
            String city_name,
            String zip_code) {
        this.id = id;
        this.street_nb = street_nb;
        this.street_name = street_name;
        this.city_name = city_name;
        this.zip_code = zip_code;
    }
    
    public int getId()  {
        return this.id;
    }
    
    public int getStreetNb()  {
        return this.street_nb;
    }
    
    public String getStreetName()  {
        return this.street_name;
    }
    
    public String getCityName()  {
        return this.city_name;
    }
    
    public String getZipCode()  {
        return this.zip_code;
    }
    
}
