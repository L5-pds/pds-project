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
                        SimpleDateFormat formatterQuery = new SimpleDateFormat("yyyy-MM-dd");
                        long val1 = Long.parseLong(dateBegin);
                        long val2 = Long.parseLong(dateEnd);
                        Date dateBegin1=new Date(val1);
                        Date dateEnd1=new Date(val2);
                        
                        String addDateCondition = "AND t_loan.entry BETWEEN '" + formatterQuery.format(dateBegin1) + "' AND '" + formatterQuery.format(dateEnd1) + "' ";
                        
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
                                addDateCondition + 
                                "AND t_advisor.id_agency = " + idAgency + ";";
                                
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        if(response != null)    {
                            
                            int nbLoan = 0;
                            double moyAmount = 0;
                            int moyLenght = 0;
                            double allBenefit = 0;
                            
                            PaneSearchIndicator tableInfo = new PaneSearchIndicator();
                            ArrayList<String> listing= new ArrayList();
                            
                            while (response.next())  {
                                nbLoan++;
                                moyAmount = moyAmount + response.getDouble("amount");
                                moyLenght = moyLenght + response.getInt("length_loan");
                                allBenefit = allBenefit + ((response.getDouble("amount") * response.getDouble("rate")) - response.getDouble("amount"));
                            
                                listing.add(response.getString("first_name") + " " + response.getString("last_name") + ";" + 
                                        response.getString("login") + ";" + 
                                        response.getString("amount") + ";" + 
                                        response.getString("length_loan") + ";" + 
                                        response.getString("wording") + ";" + 
                                        response.getDouble("rate") + ";" + 
                                        response.getString("entry"));
                            }
                            
                            if(nbLoan == 0) {
                                out.println("no line");
                                out.flush();
                                break;
                            }else   {
                                
                                moyAmount = (moyAmount/nbLoan)*100;
                                moyAmount = Math.round(moyAmount)/100;
                                moyLenght = Math.round(moyLenght/nbLoan);

                                tableInfo.setInfoValue(nbLoan, moyAmount, moyLenght, allBenefit);
                                
                                listener.changeTextLog("Ligne - " + listing.size());
                                listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - specific search - " + nbLoan + " line");
                                String testing = gsonSerial.serializePaneSearchIndicator(tableInfo) + "root" + gsonSerial.serializeArrayList(listing);
                                out.println(testing);
                                out.flush();
                                break;
                            }
                        } else  {
                            out.println("ERROR");
                            out.flush();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - specific search - error");
                        }
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
                                "OR lower(mail) LIKE lower('%" + object +"%') "+
                                "OR lower(last_name) LIKE lower('%"+object+"%');";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        if(response != null)  {
                            while (response.next()){
                                String[] customer = {response.getString("id_client"), response.getString("last_name"), response.getString("first_name"), response.getString("mail"), response.getString("salary")};
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
                        String request2 = "SELECT rate FROM t_type_loan WHERE id_type_loan="+type;
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
                        response = Server.connectionPool[poolIndex].requestWithResult(request2);
                        if(response != null)  {
                            while (response.next()){
                                String[] sim = {response.getString("rate")};
                                objectList.add(sim);
                            }
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get rate SPECIF_2 - " + response.getRow() + " line");
                        } else  {
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get rate SPECIF_2 - error");
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
                    case "getAllLoanByCustomer":
                        request = "SELECT t_client.id_client,"
                                + " t_client.last_name,"
                                + " t_client.first_name,"
                                + " t_client.mail,"
                                + " t_type_loan.id_type_loan,"
                                + " t_type_loan.wording,"
                                + " t_type_loan.rate,"
                                + " t_loan.id_loan,"
                                + " t_loan.amount"
                                + " FROM t_loan,"
                                + " t_type_loan,"
                                + " t_client"
                                + " WHERE"
                                + " t_client.id_client= t_loan.id_client "
                                + "AND t_type_loan.id_type_loan =  t_loan.id_type_loan "
                                + "AND t_client.id_client=" + object + ";";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        response.next();
                        
                        listener.changeTextLog("Nom: " + response.getString("last_name"));
                        SpecifRuben tmp = new SpecifRuben(response.getInt("id_client"), 
                        response.getString("last_name") ,
                        response.getString("first_name"),
                        response.getString("mail"),
                        response.getInt("id_type_loan"),
                        response.getString("wording"),
                        response.getDouble("rate"),
                        response.getInt("id_loan"),
                        response.getDouble("amount")        
                        );

                        //response.getInt("COUNTADRESS");
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - GeteSpecifRuben");
                        out.println(gsonSerial.serializeSpecifRuben(tmp));
                        out.flush();
                        //Coding
                        break;
                    default:
                        //Coding
                        break;
                }
                break;
            case "FIXEDRATE": // fixed rate loan simulation case
                switch (typeObject) {
                    // case : get loan types list
                    case "GetCustomers" :
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - requesting customers");
                        
                        String nom = object;
                        String sqlQuery4;
                        ResultSet rs4;
                        ArrayList<Customer> customersList = new ArrayList<>();
                        
                        // get the loan types
                        sqlQuery4 = "SELECT id_client, last_name, first_name, mail "
                                + "FROM t_client "
                                + "WHERE (first_name ilike '" + nom + "%' "
                                + "OR last_name like '" + nom + "%');";
                        rs4 = Server.connectionPool[poolIndex].requestWithResult(sqlQuery4);
                        
                        // fill an ArrayList with the insurances data taken from the database
                        while (rs4.next()) {
                            Customer c = new Customer();
                            c.setId(rs4.getInt("id_client"));
                            c.setLastName(rs4.getString("last_name"));
                            c.setFirstName(rs4.getString("first_name"));
                            c.setMail(rs4.getString("mail"));
                            customersList.add(c);
                        }
                        
                        // serialize and send the ArrayList to the client
                        out.println("SUCCESS/" + gsonSerial.serializeArrayList(customersList));
                        out.flush();
                        
                        break;
                    
                    // case : get loan types list
                    case "GetLoanTypes" :
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - requesting loan types");
                        
                        String sqlQuery1;
                        ResultSet rs1;
                        ArrayList<LoanType> loanTypesList = new ArrayList<>();
                        
                        // get the loan types
                        sqlQuery1 = "SELECT id_type_loan, wording, rate, length_min, length_max, amount_min, amount_max "
                                + "FROM t_type_loan;";
                        rs1 = Server.connectionPool[poolIndex].requestWithResult(sqlQuery1);
                        
                        // fill an ArrayList with the insurances data taken from the database
                        while (rs1.next()) {
                            loanTypesList.add(new LoanType(rs1.getInt("id_type_loan"), rs1.getString("wording"), rs1.getDouble("rate"), rs1.getInt("length_min"), rs1.getInt("length_max"), rs1.getInt("amount_min"), rs1.getInt("amount_max")));
                        }
                        
                        // serialize and send the ArrayList to the client
                        out.println("SUCCESS/" + gsonSerial.serializeArrayList(loanTypesList));
                        out.flush();
                        
                        break;
                        
                    // case : get the details of an insurance
                    case "GetInsurances" :
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - requesting insurances");
                        
                        String loanType = object;
                        ArrayList<Insurance> insurancesList = new ArrayList<>();
                        String sqlQuery2;
                        ResultSet rs2;
                        
                        // get the insurances for the given loan type
                        sqlQuery2 = "SELECT id_insurance, i.id_type_loan, i.rate, i.wording "
                                + "FROM t_insurance i, t_type_loan t "
                                + "WHERE i.id_type_loan = t.id_type_loan "
                                + "AND i.id_type_loan = " + loanType + ";";
                        rs2 = Server.connectionPool[poolIndex].requestWithResult(sqlQuery2);
                        
                        // fill an ArrayList with the insurances data taken from the database
                        while (rs2.next()) {
                            insurancesList.add(new Insurance(rs2.getInt("id_insurance"), rs2.getInt("id_type_loan"), rs2.getDouble("rate"), rs2.getString("wording")));
                        }
                        
                        // serialize and send the ArrayList to the client
                        out.println("SUCCESS/" + gsonSerial.serializeArrayList(insurancesList));
                        out.flush();
                        
                        break;
                        
                    // case : simulate a fixed rate loan
                    case "CalculateLoan" :
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - requesting fixed rate loan simulation");
                        
                        // get the loan simulation data from the client
                        FixedRateSimulation frs = gsonSerial.unserializeFixedRateSimulation(object);
                        
                        //System.out.println("montant pret : " + frs.getAmount());
                        //System.out.println("duree pret : " + frs.getDuration());
                        //System.out.println("taux assurance : " + frs.getInsurance().getRate());
                        //System.out.println("taux intérêt : " + frs.getInterestRate());
                        
                        // calculate loan monthly payment
                        FixedRateSimulationControllerServer c = new FixedRateSimulationControllerServer(frs); 
                        c.calculateMonthlyPayment();
                        
                        // serialize and send the fixed rate loan simulation to the client
                        out.println("SUCCESS/" + gsonSerial.serializeFixedRateSimulation(frs));
                        out.flush();
                        
                        break;
                        
                    case "SaveLoan" :
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - requesting fixed rate loan save");
                        String sqlQuery3;
                        
                        // get the loan simulation data from the client
                        FixedRateSimulation frs1 = gsonSerial.unserializeFixedRateSimulation(object);
                        
                        // insert the loan simulation into the database
                        sqlQuery3 = "insert into t_loan_simulation(wording, amount, length_loan, type_rate_loan, id_type_loan, id_client, monthly_payment) "
                                + "values('" + frs1.getWording() + "',"
                                + frs1.getAmount() + ","
                                + frs1.getDuration() + ","
                                + "'FIXE',"
                                + frs1.getLoanType().getId() + ","
                                + "1," // frs1.getCustomer().getId()
                                + frs1.getMonthlyPayment() + ");";
                        Server.connectionPool[poolIndex].requestWithoutResult(sqlQuery3);
                        
                        // respond to the client
                        out.println("SUCCESS/.");
                        out.flush();
                        
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
                    case "toto":
                    	request = "SELECT login FROM t_client WHERE id_client = " + object + ";";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        
                        response.next();
                        listener.changeTextLog("LINDA INFO - nom=" + response.getString("last_name"));
                        
                        out.println(response.getString("last_name"));
                        out.flush();
                    	break;
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
