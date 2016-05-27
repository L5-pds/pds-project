package app.controllers;

import app.listeners.*;
import app.models.*;
import app.helpers.*;
import app.models.other.PaneSearchIndicator;
import app.models.other.dataSearchIndicator;
import app.models.other.datasetBarChart;
import app.models.other.datasetPieChart;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
                ArrayList<String> listString = new ArrayList();
                datasetPieChart returnDatasetPieChart = null;
                datasetBarChart returnDatasetBarChart = null;
                switch (typeObject) {
                    case "SelectSearchLoan":
                        
                        String[] querySplited = object.split(";");

                        String idAgency = querySplited[0];
                        String dateBegin = querySplited[1];
                        String dateEnd = querySplited[2];
                        String typeAdvisor = querySplited[3];
                        String typeLoan = querySplited[4];
                        String typeCustomer = querySplited[5];
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        
                        String addAdvisorCondition = "";
                        if(!typeAdvisor.equals("TOUS")) {
                            addAdvisorCondition = "AND t_advisor.login = '" + typeAdvisor + "' ";
                        }
                        String addTypeLoanCondition = "";
                        if(!typeLoan.equals("TOUS")) {
                            addTypeLoanCondition = "AND t_type_loan.wording = '" + typeLoan + "' ";
                        }
                        String addCustomerCondition = "";
                        if(!typeCustomer.equals("TOUS")) {
                            
                            String dateNow = formatter.format(Calendar.getInstance().getTime());
                            Calendar calendar = Calendar.getInstance();
                            String date30;
                            String date60;
                            switch (typeCustomer) {
                                case "Jeune (moins de 30 ans)":
                                    calendar.add(Calendar.YEAR, -30);
                                    date30 = formatter.format(calendar.getTime());
                                    
                                    addCustomerCondition = "AND t_client.birth BETWEEN '" + date30 + "' AND '" + dateNow + "' ";
                                    
                                    break;
                                case "Adulte (entre 30 ans et 60 ans)":
                                    calendar.add(Calendar.YEAR, -30);
                                    date30 = formatter.format(calendar.getTime());
                                    calendar.add(Calendar.YEAR, -30);
                                    date60 = formatter.format(calendar.getTime());
                                    
                                    addCustomerCondition = "AND t_client.birth BETWEEN '" + date60 + "' AND '" + date30 + "' ";
                                    
                                    break;
                                case "Senior (plus de 60 ans)":
                                    calendar.add(Calendar.YEAR, -60);
                                    date60 = formatter.format(calendar.getTime());
                                    
                                    addCustomerCondition = "AND t_client.birth > '" + date60 + "' ";
                                    
                                    break;
                                default:
                                    break;
                            }
                        }
                        
                        request = "SELECT t_client.first_name, t_client.last_name, t_advisor.login, t_loan.amount, t_loan.length_loan, t_type_loan.wording, t_type_loan.rate, t_loan.entry " + 
                                "FROM t_client, t_loan, t_type_loan, t_advisor " + 
                                "WHERE t_loan.id_client = t_client.id_client " + 
                                "AND t_loan.id_advisor = t_advisor.id_advisor " + 
                                "AND t_loan.id_type_loan = t_type_loan.id_type_loan " + 
                                addAdvisorCondition + 
                                addTypeLoanCondition + 
                                addCustomerCondition + 
                                "AND t_advisor.id_agency = " + idAgency + ";";
                                
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        if(response != null)    {
                            
                            int nbLoan = 0;
                            double moyAmount = 0;
                            int moyLenght = 0;
                            long allBenefit = 0;
                            
                            PaneSearchIndicator tableInfo = new PaneSearchIndicator();
                            ArrayList<String> listing= new ArrayList();
                            
                            while (response.next())  {
                                nbLoan++;
                                moyAmount = moyAmount + response.getDouble("amount");
                                moyLenght = moyLenght + response.getInt("length_loan");
                                allBenefit = allBenefit + ((response.getLong("amount") * response.getLong("rate")) - response.getLong("amount"));
                            
                                listing.add(response.getString("first_name") + " " + response.getString("last_name") + ";" + 
                                        response.getString("login") + ";" + 
                                        response.getString("amount") + ";" + 
                                        response.getString("length_loan") + ";" + 
                                        response.getString("wording") + ";" + 
                                        response.getDouble("rate") + ";" + 
                                        response.getString("entry"));
                            }
                            
                            moyAmount = (moyAmount/nbLoan)*100;
                            moyAmount = Math.round(moyAmount)/100;
                            moyLenght = Math.round(moyLenght/nbLoan);
                            
                            tableInfo.setInfoValue(nbLoan, moyAmount, moyLenght, allBenefit);
                            
                            response.last();
                            listener.changeTextLog("Ligne - " + listing.size());
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - specific search - " + response.getRow() + " line");
                            String testing = gsonSerial.serializePaneSearchIndicator(tableInfo) + "root" + gsonSerial.serializeArrayList(listing);
                            out.println(testing);
                            out.flush();
                            break;
                        } else  {
                            listString.add("ERROR");
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - specific search - error");
                        }
                        
                        out.println("ERROR");
                        out.flush();
                        break;
                    case "SelectDataSearch":
                        
                        dataSearchIndicator dataComposent = new dataSearchIndicator();
                        
                        request = "SELECT login FROM t_advisor WHERE id_agency = " + object + ";";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        if(response != null)    {
                            ArrayList<String> listAdvisor = new ArrayList();
                            listAdvisor.add("TOUS");
                            while (response.next())  {
                                listAdvisor.add(response.getString("login"));
                            }
                            dataComposent.setAdvisorList(listAdvisor);
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get all advisor - " + response.getRow() + " line");
                        } else  {
                            listString.add("ERROR");
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get statistic of loan per type - error");
                        }
                        request = "SELECT wording FROM t_type_loan;";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        if(response != null)    {
                            ArrayList<String> listTypeLoan = new ArrayList();
                            listTypeLoan.add("TOUS");
                            while (response.next())  {
                                listTypeLoan.add(response.getString("wording"));
                            }
                            dataComposent.setTypeLoanList(listTypeLoan);
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get all type loan - " + response.getRow() + " line");
                        } else  {
                            listString.add("ERROR");
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get statistic of loan per type - error");
                        }
                        
                        out.println(gsonSerial.serializeDataSearchIndicator(dataComposent));
                        out.flush();
                        break;
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
                        /*
                        request = "SELECT COUNT(t_loan.id_loan) AS value, t_type_loan.wording AS column, t_advisor.login AS row " +
                                "FROM t_loan, t_type_loan, t_advisor " +
                                "WHERE t_loan.id_type_loan = t_type_loan.id_type_loan " +
                                "AND t_loan.id_advisor = t_advisor.id_advisor " +
                                "AND t_advisor.id_agency = " + object + " " +
                                "GROUP BY t_type_loan.wording, t_advisor.login;";
                        */
                        request = "SELECT ((t_type_loan.rate * t_loan.amount) - t_loan.amount) AS value, extract(year from t_loan.entry) AS column, t_type_loan.wording AS row " +
                                "FROM t_loan, t_type_loan, t_advisor " +
                                "WHERE t_loan.id_type_loan = t_type_loan.id_type_loan " +
                                "AND t_advisor.id_advisor = t_loan.id_advisor " +
                                "AND t_advisor.id_agency = " + object + " " +
                                "AND (extract(year from t_loan.entry)) > (extract(year from NOW()) - 10) " +
                                "GROUP BY extract(year from t_loan.entry), t_type_loan.wording, t_type_loan.rate, t_loan.amount " + 
                                "ORDER BY extract(year from t_loan.entry);";
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
                        /*
                        request = "SELECT t_advisor.login AS name, COUNT(t_loan.id_loan) AS value " +
                                "FROM t_advisor, t_loan " +
                                "WHERE t_advisor.id_advisor = t_loan.id_advisor " +
                                "AND t_advisor.id_agency = " + object + " " +
                                "GROUP BY t_advisor.login;";
                        */
                        request = "SELECT t_advisor.login AS name, SUM((t_loan.amount * t_type_loan.rate) - t_loan.amount) AS value " +
                                "FROM t_advisor, t_loan, t_type_loan " +
                                "WHERE t_advisor.id_advisor = t_loan.id_advisor " +
                                "AND t_type_loan.id_type_loan = t_loan.id_type_loan " +
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
                ArrayList<String[]> objectList = new ArrayList<String[]>();
                switch (typeObject) {
                    case "Customer":
                        request = "SELECT * " +
                                "FROM t_client " +
                                "WHERE lower(first_name) LIKE lower('%" + object +"%') "+
                                "OR lower(last_name) LIKE lower('%"+object+"%');";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        if(response != null)  {
                            while (response.next()){
                                String[] customer = {response.getString("id_client"), response.getString("last_name"), response.getString("first_name")};
                                objectList.add(customer);
                            }
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get customers SPECIF_2 - " + response.getRow() + " line");
                        } else  {
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get customers SPECIF_2 - error");
                        }

                        out.println(gsonSerial.serializeListArray(objectList));
                        out.flush();
                    break;
                    case "Simulation" :
                        String tmp = splitedQuery[3];
                        String type="";
                        switch (tmp){
                            case "AUTOMOBILE" :
                                type = "1";
                            break;
                            case "CONSOMMATION" :
                                type = "3";
                            break;
                            case "IMMOBILIER" :
                                type = "2";
                            break;
                        }
                        request = "SELECT * " +
                                "FROM t_loan_simulation " +
                                "WHERE id_client ="+object+
                                " AND id_type_loan ="+type+
                                " ORDER BY entry ASC;";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        if(response != null)  {
                            while (response.next()){
                                String[] sim = {response.getString("id_loan"), response.getString("entry"), response.getString("wording"),response.getString("amount"),response.getString("length_loan")};
                                objectList.add(sim);
                            }
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get simulation SPECIF_2 - " + response.getRow() + " line");
                        } else  {
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get simulation SPECIF_2 - error");
                        }

                        out.println(gsonSerial.serializeListArray(objectList));
                        out.flush();
                    break;
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
            case "SPECIF_4": //Spécifique ALEXANDRE
                switch (typeObject) {
                    case "Address":
                        //Coding
                    default:
                        //Coding
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


      } catch (Exception e) {
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
