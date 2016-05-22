package app.helpers;

import app.models.*;
import app.models.other.datasetPieChart;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.jfree.data.general.DefaultPieDataset;

public class Serialization {

  final GsonBuilder builder;
  final Gson gson;

  public Serialization() {
    builder = new GsonBuilder();
    gson = builder.create();
  }

  public String serializeUser(Advisor p) {
    return gson.toJson(p);
  }

  public String serializeCustomer(Customer c) {
      return gson.toJson(c);
  }

  public String serializeAddress(Address a) {
      return gson.toJson(a);
  }

  public String serializeDefaultPieDataset(DefaultPieDataset dpd) {
      return gson.toJson(dpd);
  }

  public String serializeArrayList(ArrayList dpd) {
      return gson.toJson(dpd);
  }

  public String serializedatasetPieChart(datasetPieChart dpd) {
      return gson.toJson(dpd);
  }

  public Advisor unserializeUser(String u) {
      return gson.fromJson(u, Advisor.class);
  }

  public Customer unserializeCustomer(String c) {
      return gson.fromJson(c, Customer.class);
  }

  public Address unserializeAddress(String a) {
      return gson.fromJson(a, Address.class);
  }

  public datasetPieChart unserializedatasetPieChart(String dpd) {
      return gson.fromJson(dpd, datasetPieChart.class);
  }

  public ArrayList unserializeArrayList(String dpd) {
      return gson.fromJson(dpd, ArrayList.class);
  }

}
