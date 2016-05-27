package app.helpers;

public class Item{
  private int id;
  private Double wage;
  private String description;

  public Item(int id, String description, Double wage) {
        this.id = id;
        this.wage = wage/12;
        this.description = description;
    }

  public int getId() {
        return id;
  }

  public Double getWage() {
        return wage;
  }

  public String getDescription() {
    return description;
  }

  public String toString() {
    return description;
  }
}
