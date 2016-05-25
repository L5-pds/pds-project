package app.controllers;

import app.listeners.*;
import app.models.*;
import app.helpers.*;
import app.models.other.datasetBarChart;
import app.models.other.datasetPieChart;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import org.jfree.data.general.DefaultPieDataset;

public class UserCommunicate implements Runnable {
  private Socket socket;
  private WelcomeListenerServer listener;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private Serialization gsonSerial;
  private String message = null;
  private int poolIndex=-1;
  private Advisor user = null;

  public UserCommunicate(Socket socket, WelcomeListenerServer l){
    this.socket = socket;
    this.listener = l;
    this.gsonSerial = new Serialization();
  }

  public void run() {
    String query;
    String[] splitedQuery;
    String method;
    String typeObject;
    String object;
    String sqlQuery; // à supprimer quand les requetes BD seront dans les controleurs
    ResultSet rs; // à supprimer quand les requetes BD seront dans les controleurs
    boolean userConnected = false;

    try {
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    out = new PrintWriter(socket.getOutputStream());
    } catch (Exception e1) {
        try {
        socket.close();
        } catch (Exception e2) {
            listener.changeTextLog("WARNING - Erreur de fermeture de la socket");
        }
        listener.changeTextLog("WARNING - Erreur de déclaration des variables de communications (in/out)");
    }

    //Authentication process
    while((!socket.isClosed()) && (!userConnected)){
      try {
        query = in.readLine();
        splitedQuery = query.split("/");

        method = splitedQuery[0];
        typeObject = splitedQuery[1];
        object = splitedQuery[2];

        if (method.equals("AUTH")){
          if(typeObject.equals("User")){
            user = gsonSerial.unserializeUser(object);
            userConnected = getConnection(user.getLogin(), user.getPassword());
          }
        }
      } catch (Exception e) {
        listener.changeTextLog("CONNECT_WARNING - gone");
        if (poolIndex != -1){
          Server.connectionPool[poolIndex].use(false);
          listener.updateInfoLabel();
        }
        try {
          socket.close();
        } catch (IOException e1) {
          listener.changeTextLog("SERVER_WARNING - closing socket error");
        }
      }
    }//end of while loop

    //Communicate process
    while((!socket.isClosed()) && (userConnected)){
      try {
        query = in.readLine();
        splitedQuery = query.split("/");

        method = splitedQuery[0];
        typeObject = splitedQuery[1];
        object = splitedQuery[2];

        switch (method) {
            case "INSERT": //Case for insert into database
                switch (typeObject) {
                    case "Address": //Case for insert into database new address
                        Address newAddress = gsonSerial.unserializeAddress(object);
                        String request = "INSERT INTO t_adress VALUES ("
                                + newAddress.getId() + ", "
                                + newAddress.getStreetNb() + ", "
                                + "'" + newAddress.getStreetName() + "'" + ", "
                                + "'" + newAddress.getCityName() + "'" + ", "
                                + "'" + newAddress.getZipCode() + "'"
                                + ");";
                        String response = Server.connectionPool[poolIndex].requestWithoutResult(request);
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - add new address - " + response);
                        out.println(response);
                        out.flush();
                        break;
                    default:
                        //Coding
                        break;
                }
                break;
            case "UPDATE": //Case for update database
                switch (typeObject) {
                    case "Address": //Case for update existing address into database
                        //Coding
                        break;
                    default:
                        //Coding
                        break;
                }
                break;
            case "DELETE": //Case for delete from database
                switch (typeObject) {
                    case "Address": //Case for delete existing address from database
                        //Coding
                        break;
                    default:
                        //Coding
                        break;
                }
                break;
            case "SPECIF_1": //Spécifique THIBAULT DON'T TOUCHE !!!
                String request;
                ResultSet response;
                ArrayList<String> listString = new ArrayList<String>();
                datasetPieChart returnDatasetPieChart = null;
                datasetBarChart returnDatasetBarChart = null;
                switch (typeObject) {
                    case "Address":
                        request = "SELECT COUNT(*) AS COUNTADRESS FROM t_address;";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        response.next();
                        response.getInt("COUNTADRESS");
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - count address - " + response.getInt("COUNTADRESS"));
                        out.println("success/" + response.getInt("COUNTADRESS"));
                        out.flush();
                    case "LoanPerType":
                        request = "SELECT t_type_loan.wording AS name, COUNT(t_loan.id_loan) AS value " + 
                                "FROM t_type_loan, t_loan, t_advisor " + 
                                "WHERE t_loan.id_type_loan = t_type_loan.id_type_loan " + 
                                "AND t_loan.id_advisor = t_advisor.id_advisor " + 
                                "AND t_advisor.id_agency = " + object + " " + 
                                "GROUP BY t_type_loan.wording;";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        returnDatasetPieChart = new datasetPieChart();
                        if(response != null)    {
                            while (response.next())  {
                                returnDatasetPieChart.addToCollect(response.getString("name"), response.getInt("value"));
                            }
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get statistic of loan per type - " + response.getRow() + " line");
                        } else  {
                            returnDatasetPieChart.addToCollect("ERROR", 1);
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get statistic of loan per type - error");
                        }
                        out.println(gsonSerial.serializedatasetPieChart(returnDatasetPieChart));
                        out.flush();
                        break;
                    case "LoanPerTypeByYear":
                        request = "SELECT COUNT(t_loan.id_loan) AS value, t_type_loan.wording AS column, t_advisor.login AS row " +
                                "FROM t_loan, t_type_loan, t_advisor " +
                                "WHERE t_loan.id_type_loan = t_type_loan.id_type_loan " +
                                "AND t_loan.id_advisor = t_advisor.id_advisor " +
                                "AND t_advisor.id_agency = " + object + " " +
                                "GROUP BY t_type_loan.wording, t_advisor.login;";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        returnDatasetBarChart = new datasetBarChart();
                        if(response != null)    {
                            while (response.next())  {
                                returnDatasetBarChart.addToCollect(response.getDouble("value"), response.getString("row"), response.getString("column"));
                            }
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get statistic of loan per type - " + response.getRow() + " line");
                        } else  {
                            returnDatasetBarChart.addToCollect(1, "ERROR", "ERROR");
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get statistic of loan per type - error");
                        }
                        out.println(gsonSerial.serializedatasetBarChart(returnDatasetBarChart));
                        out.flush();
                        break;
                    case "LoanPerAdvisor":
                        request = "SELECT t_advisor.login AS name, COUNT(t_loan.id_loan) AS value " + 
                                "FROM t_advisor, t_loan " +
                                "WHERE t_advisor.id_advisor = t_loan.id_advisor " + 
                                "AND t_advisor.id_agency = " + object + " " +
                                "GROUP BY t_advisor.login;";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        returnDatasetPieChart = new datasetPieChart();
                        if(response != null)    {
                            while (response.next())  {
                                returnDatasetPieChart.addToCollect(response.getString("name"), response.getInt("value"));
                            }
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get statistic of loan per type - " + response.getRow() + " line");
                        } else  {
                            returnDatasetPieChart.addToCollect("ERROR", 1);
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get statistic of loan per type - error");
                        }
                        out.println(gsonSerial.serializedatasetPieChart(returnDatasetPieChart));
                        out.flush();
                        break;
                    case "AdvisorClassement":
                        request = "SELECT first_name AS firstn, last_name AS lastn " + 
                                "FROM t_advisor " + 
                                "WHERE id_agency = " + object + ";";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);

                        if(response != null)    {
                            while (response.next())  {
                                listString.add(response.getString("firstn") + " " + response.getString("lastn"));
                            }
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get the advisor classement - " + response.getRow() + " line");
                        } else  {
                            listString.add("ERROR");
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get the advisor classement - error");
                        }
                        out.println(gsonSerial.serializeArrayList(listString));
                        out.flush();
                        break;
                    default:
                        //Coding
                        break;
                }
                break;
            case "SPECIF_2": //Spécifique TARIK DON'T TOUCHE !!!
                switch (typeObject) {
                    case "Address":
                        //Coding
                    default:
                        //Coding
                        break;
                }
                break;
            case "SPECIF_3": //Spécifique RUBEN
                switch (typeObject) {
                    case "Address":
                        //Coding
                    default:
                        //Coding
                        break;
                }
                break;
            case "FIXEDRATE": // fixed rate loan simulation case
                switch (typeObject) {
                    // case : get loan types list
                    case "GetLoanTypes" :
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - requesting loan types");
                        
                        // get the loan types list from the database
                        sqlQuery = "SELECT wording FROM t_type_loan;";
                        rs = Server.connectionPool[poolIndex].requestWithResult(sqlQuery);
                        
                        // put each loan type in an ArrayList
                        ArrayList<String> loanTypes = new ArrayList<>();
                        while(rs.next()) {
                            loanTypes.add(rs.getString(1));
                        }
                        
                        // serialize and send the loan types list to the client
                        out.println("SUCCESS/" + gsonSerial.serializeArrayList(loanTypes));
                        out.flush();
                        
                        break;

                    // case : get the details of a loan type
                    case "GetLoanTypeDetails" :
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - requesting loan type details");
                        LoanType lt;
                        
                        // attributes of a loan type
                        String wording = object;
                        float loanRate;
                        int length_min, length_max, amount_min, amount_max;

                        // get the loan type details from the database
                        sqlQuery = "SELECT rate, length_min, length_max, amount_min, amount_max"
                                + "FROM t_type_loan"
                                + "WHERE wording = '" + wording + "'";
                        rs = Server.connectionPool[poolIndex].requestWithResult(sqlQuery);

                        // get the values from the result set
                        loanRate = rs.getFloat("rate");
                        length_min = rs.getInt("length_min");
                        length_max = rs.getInt("length_max");
                        amount_min = rs.getInt("amount_min");
                        amount_max = rs.getInt("amount_max");
        
                         // create a LoanType with the values taken from the database, serialize it, and send it to the client
                        lt = new LoanType(wording, loanRate, length_min, length_max, amount_min, amount_max);
                        out.println("SUCCESS/" + gsonSerial.serializeLoanType(lt));
                        out.flush();
                    
                        break;
                        
                    // case : get the details of an insurance
                    case "GetInsurances" :
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - requesting insurances");
                        
                        String loanType = object;
                        Insurance insurance;
                        ArrayList<Insurance> insurancesList = new ArrayList<>();
                        
                        // get the insurances for the given loan type
                        sqlQuery = "SELECT id_insurance, i.id_type_loan, i.rate, i.wording "
                                + "FROM t_insurance i, t_type_loan t "
                                + "WHERE i.id_type_loan = t.id_type_loan "
                                + "AND t.wording = '" + loanType + "';";
                        rs = Server.connectionPool[poolIndex].requestWithResult(sqlQuery);
                        
                        // fill an ArrayList with the insurances data taken from the database
                        while (rs.next()) {
                            insurancesList.add(new Insurance(rs.getInt("id_insurance"), rs.getInt("id_type_loan"), rs.getFloat("rate"), rs.getString("wording")));
                        }
                        
                        System.out.println("arraylist :");
                        for (Insurance i : insurancesList) {
                            System.out.println(i);
                        }
                        
                        // serialize and send the ArrayList to the client
                        out.println("SUCCESS/" + gsonSerial.serializeArrayList(insurancesList));
                        out.flush();
                        
                        break;
                        
                    // case : simulate a fixed rate loan
                    case "CalculateLoan" :
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - requesting fixed rate loan simulation");
                        
                        // attributes of a fixed rate credit
                        //Customer customer;
                        String wording1;
                        LoanType type;
                        float rate1;
                        int customerId, amount, duration, monthlyPayment;
                        
                        // get the loan data from the client
                        FixedRateSimulation frs = gsonSerial.unserializeFixedRateSimulation(object);
                        
                        
                        break;
                        
                    // case : malformed query
                    default:
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - bad query");
                        out.println("FAIL/.");
                        out.flush();
                        break;
                }
                break;
            case "SPECIF_5": //Spécifique MARIAM
                switch (typeObject) {
                    case "Address":
                        //Coding
                    default:
                        //Coding
                        break;
                }
                break;
            case "SPECIF_6": //Spécifique LINDA
                switch (typeObject) {
                    case "Address":
                        //Coding
                    default:
                        //Coding
                        break;
                }
                break;
            default:
                //coding
                break;
        }


      } catch (IOException | SQLException e) {
        listener.changeTextLog("CONNECT_WARNING - " + user.getLogin() + " - gone");
        if (poolIndex != -1){
          Server.connectionPool[poolIndex].use(false);
          listener.updateInfoLabel();
        }
        try {
          socket.close();
        } catch (IOException e1) {
          listener.changeTextLog("SERVER_WARNING - closing socket error");
        }
      }
    }//end of while loop
  }

  public boolean getConnection(String login, String pwd){
    boolean userConnected = false;
    boolean userAuth = false;
    boolean userAlreadyUse = false;
    boolean userNoPool = true;

    userAuth=authentication(login, pwd);
    if(userAuth == false)   {
        out.println("Error/Authentification incorrecte blabla blabla blabla blabla blabla");
        out.flush();
        listener.changeTextLog("CONNECT_WARNING - " + login + " - dismissed");
        return false;
    }

    user.getAdvisor();
    if(user.getError() == true)   {
        out.println("Error/Erreur de récupération des informations");
        out.flush();
        listener.changeTextLog("CONNECT_WARNING - " + login + " - dismissed");
        return false;
    }

    for (int i = 0; i < Server.poolSize; i++) {
        if (Server.connectionPool[i].getUser().equals(login)) {
            out.println("Error/Cet utilisateur est déja connecté (ressayer ultérieurement)");
            out.flush();
            listener.changeTextLog("CONNECT_WARNING - " + login + " - already connect");
            userAlreadyUse = true;
            return false;
        }
    }

    for (int i = 0; i < Server.poolSize; i++) {
        if (Server.connectionPool[i].isUsed() == false) {
            Server.connectionPool[i].use(true);
            Server.connectionPool[i].setUser(login);
            listener.updateInfoLabel();
            poolIndex = i;
            out.println("Success/" + gsonSerial.serializeUser(user));
            out.flush();
            listener.changeTextLog("CONNECT_WARNING - " + login + " - connected");
            userNoPool = false;
            userConnected = true;
            return true;
        }
    }

    if (userNoPool == true) {
        out.println("Error/Aucune connexion disponible (ressayer ultérieurement)");
        out.flush();
        listener.changeTextLog("CONNECT_WARNING - " + login + " - no connection available");
        return false;
    }

    return userConnected;
  }

  private boolean authentication(String login, String pass) {
    boolean authentic = false;
    Connection conn = null;
    Statement stat = null;
    ResultSet results = null;

    try {
      conn = Server.getConnection();
      stat = conn.createStatement();
      results = stat.executeQuery("SELECT * FROM t_advisor WHERE login = '" + login + "' AND password = '" + pass + "';");

      results.next();

      if(results.getRow() != 0)
        authentic = true;

      results.close();
      stat.close();
      conn.close();
    }
    catch (SQLException e) {
      listener.changeTextLog("ERREUR (SQL) pour l'authentification de " + login);
      authentic = false;
    }

    return authentic;
  }
}
