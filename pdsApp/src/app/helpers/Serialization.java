package app.helpers;

import app.models.*;
import app.models.other.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

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

  public String serializeSpecifRuben(SpecifRuben a) {
      return gson.toJson(a);
  }

  public String serializedatasetPieChart(datasetPieChart dpd) {
      return gson.toJson(dpd);
  }

  public String serializedatasetBarChart(datasetBarChart dpd) {
      return gson.toJson(dpd);
  }

  public String serializeArrayList(ArrayList dpd) {
      return gson.toJson(dpd);
  }

  public String serializeDataSearchIndicator(dataSearchIndicator dpd) {
      return gson.toJson(dpd);
  }

  public String serializeListArray(ArrayList<String[]> dpd) {
      return gson.toJson(dpd);
  }

  public String serializePaneSearchIndicator(PaneSearchIndicator dpd) {
      return gson.toJson(dpd);
  }

  public String serializeDefaultTableModel(DefaultTableModel dpd) {
      return gson.toJson(dpd);
  }
  
  public String serializeFixedRateSimulation(FixedRateSimulation frs) {
      return gson.toJson(frs);
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

  public datasetBarChart unserializedatasetBarChart(String dpd) {
      return gson.fromJson(dpd, datasetBarChart.class);
  }

  public ArrayList unserializeArrayList(String dpd) {
      return gson.fromJson(dpd, ArrayList.class);
  }

  public ArrayList unserializeListArray(String dpd) {
      return gson.fromJson(dpd, new TypeToken<ArrayList<String[]>>() {}.getType());
  }

  public dataSearchIndicator unserializeDataSearchIndicator(String dpd) {
      return gson.fromJson(dpd, dataSearchIndicator.class);
  }

  public PaneSearchIndicator unserializePaneSearchIndicator(String dpd) {
      return gson.fromJson(dpd, PaneSearchIndicator.class);
  }

  public DefaultTableModel unserializeDefaultTableModel(String dpd) {
      return gson.fromJson(dpd, DefaultTableModel.class);
  }

  public SpecifRuben unserializeSpecifRuben(String dpd) {
      return gson.fromJson(dpd, SpecifRuben.class);
  }

   public FixedRateSimulation unserializeFixedRateSimulation(String frs) {
      return gson.fromJson(frs, FixedRateSimulation.class);
  }
   
   public ArrayList<LoanType> unserializeLoanTypeArrayList(String lt) {
    ArrayList<LoanType> loanTypesList = new ArrayList<>();
    JsonParser parser = new JsonParser();
    JsonArray array = parser.parse(lt).getAsJsonArray();
    for (int i=0 ; i<array.size() ; i++) {
        loanTypesList.add(gson.fromJson(array.get(i), LoanType.class));
    }

    return loanTypesList;
  }
  
  public ArrayList<Insurance> unserializeInsuranceArrayList(String ins) {
    ArrayList<Insurance> insurancesList = new ArrayList<>();
    JsonParser parser = new JsonParser();
    JsonArray array = parser.parse(ins).getAsJsonArray();
    for (int i=0 ; i<array.size() ; i++) {
        insurancesList.add(gson.fromJson(array.get(i), Insurance.class));
    }
    
    return insurancesList;
  }
  
  public ArrayList<Customer> unserializeCustomersArrayList(String c) {
    ArrayList<Customer> customersList = new ArrayList<>();
    JsonParser parser = new JsonParser();
    JsonArray array = parser.parse(c).getAsJsonArray();
    for (int i=0 ; i<array.size() ; i++) {
        customersList.add(gson.fromJson(array.get(i), Customer.class));
    }
    
    return customersList;
  }
  
  public ArrayList<FixedRateSimulation> unserializeFixedRateSimulationArrayList(String l) {
    ArrayList<FixedRateSimulation> simulationsList = new ArrayList<>();
    JsonParser parser = new JsonParser();
    JsonArray array = parser.parse(l).getAsJsonArray();
    for (int i=0 ; i<array.size() ; i++) {
        simulationsList.add(gson.fromJson(array.get(i), FixedRateSimulation.class));
    }
    
    return simulationsList;
  }
}
