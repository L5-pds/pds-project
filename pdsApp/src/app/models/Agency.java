package app.models;

public class Agency {
    private int id;
    private String name;
    private int idAddress;
    private String phoneNb;
    private String faxNb;
    
    public Agency() {
        this.id = -1;
        this.name = "";
        this.idAddress = -1;
        this.phoneNb = "";
        this.faxNb = "";
    }
    
    public Agency(int id, 
            String name, 
            int idAddress, 
            String phoneNb, 
            String faxNb) {
        this.id = id;
        this.name = name;
        this.idAddress = idAddress;
        this.phoneNb = phoneNb;
        this.faxNb = faxNb;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getPhone() {
        return this.phoneNb;
    }
    
    public String getFax() {
        return this.faxNb;
    }
    
}
